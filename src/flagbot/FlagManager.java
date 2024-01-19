package flagbot;

import battlecode.common.FlagInfo;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class FlagManager extends RobotPlayer {
    private static int carriedEnemyFlagIndex = -1;
    private static int lastFlagCarryRound = -1;

    private static MapLocation flagCarryDestination = null;
    private static boolean broadcastInit = false;

    public static void init() throws GameActionException {
        for (int i = 3; --i >= 0;) {
            Comms.writeMyflagsLoc(i, Util.loc2int(Robot.mySpawnCenters[i]));
            Comms.writeMyflagsOriginalLoc(i, Util.loc2int(Robot.mySpawnCenters[i]));
            Comms.writeMyflagsExists(i, 1);

            // opp flag will be be resetted again at round 200 during init turn
            Comms.writeOppflagsLoc(i, Util.loc2int(MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[i])));
            Comms.writeOppflagsExists(i, 1);
        }
    }

    public static void initTurn() throws GameActionException {
        if (!rc.hasFlag() && carriedEnemyFlagIndex != -1) {
            if (rc.getRoundNum() - lastFlagCarryRound == 1) {
                // we just dropped the flag, set carry to false
                Comms.writeOppflagsCarried(carriedEnemyFlagIndex, 0);
            } else if (rc.getRoundNum() - lastFlagCarryRound > 5) {
                // wait for 5 rounds, if no one else pick it up, set it back to original loc
                if (Comms.readOppflagsCarried(carriedEnemyFlagIndex) == 0) {
                    Comms.writeOppflagsLoc(carriedEnemyFlagIndex, Comms.readOppflagsOriginalLoc(carriedEnemyFlagIndex));
                }
                carriedEnemyFlagIndex = -1;
                lastFlagCarryRound = -1;
            }
        }
        if (rc.getRoundNum() > 200) {
            if (!broadcastInit) {
                if (Comms.readOppflagsConfirmed(0) == 1
                        && Comms.readOppflagsConfirmed(1) == 1
                        && Comms.readOppflagsConfirmed(2) == 1) {
                    broadcastInit = true;
                } else {
                    MapLocation[] locs = rc.senseBroadcastFlagLocations();
                    // we hope there is one robot that doesn't see anyflag will be able to init this...
                    if (locs.length == 3) {
                        for (int i = 3; --i >= 0;) {
                            Comms.writeOppflagsLoc(i, Util.loc2int(locs[i]));
                            Comms.writeOppflagsOriginalLoc(i, 0);
                            Comms.writeOppflagsId(i, 0);
                            Comms.writeOppflagsCarried(i, 0);
                            Comms.writeOppflagsConfirmed(i, 1);
                        }
                        broadcastInit = true;
                    }
                }
            }
        }
    }

    public static boolean act() throws GameActionException {
        boolean[] enemyFlagSeen = new boolean[3];
        boolean[] myFlagSeen = new boolean[3];
        boolean hasFlag = rc.hasFlag(); // handle edge case of picking flag up in our own spawn zone
        for (FlagInfo flag : rc.senseNearbyFlags(-1)) {
            if (flag.getTeam() == myTeam) {
                int flagIndex = getMyFlagIndex(flag);
                Comms.writeMyflagsExists(flagIndex, 1);
                Comms.writeMyflagsLoc(flagIndex, Util.loc2int(flag.getLocation()));
                myFlagSeen[flagIndex] = true;
                if ((flag.isPickedUp() && rc.getRoundNum() > 200)
                        || Cache.nearbyEnemies.length > Cache.nearbyFriends.length) {
                    Comms.writeMyflagsDistress(flagIndex, 1);
                }
            } else {
                int flagIndex = getOppFlagIndex(flag);
                enemyFlagSeen[flagIndex] = true;
                if (!flag.isPickedUp()) {
                    // enemy flag found on the ground
                    if (Comms.readOppflagsOriginalLoc(flagIndex) == 0) {
                        Comms.writeOppflagsOriginalLoc(flagIndex, Util.loc2int(flag.getLocation()));
                    }
                    Comms.writeOppflagsCarried(flagIndex, 0);
                    Comms.writeOppflagsLoc(flagIndex, Util.loc2int(flag.getLocation()));
                    if (!SpecialtyManager.isBuilder()) {
                        // builder is free from flag duty
                        if (Comms.readOppflagsLoc(flagIndex) != Comms.readOppflagsOriginalLoc(flagIndex)
                                && !rc.getLocation().isAdjacentTo(flag.getLocation())) {
                            // a dropped flag away from us, pick it up ASAP
                            PathFinder.move(flag.getLocation());
                        }
                        if (rc.canPickupFlag(flag.getLocation())) {
                            flagCarryDestination = Util.getClosestLoc(Robot.mySpawnCenters);
                            carriedEnemyFlagIndex = flagIndex;
                            rc.pickupFlag(flag.getLocation());
                            hasFlag = true;
                            break;
                        }
                    }
                }
            }
        }
        if (hasFlag) {
            lastFlagCarryRound = rc.getRoundNum();
            PathFinder.move(flagCarryDestination);
            Comms.writeOppflagsLoc(carriedEnemyFlagIndex, Util.loc2int(rc.getLocation()));
            Comms.writeOppflagsCarried(carriedEnemyFlagIndex, 1);
            if (rc.senseMapInfo(rc.getLocation()).getSpawnZoneTeam() == myTeamID) {
                // we just captured the flag
                Comms.writeOppflagsExists(carriedEnemyFlagIndex, 0);
                carriedEnemyFlagIndex = -1;
                return false;
            }
            if (Cache.closestEnemy != null) {
                Comms.writeOppflagsEscortLoc(carriedEnemyFlagIndex, Util.loc2int(Cache.closestEnemy));
            } else {
                Comms.writeOppflagsEscortLoc(carriedEnemyFlagIndex, 0);
            }
            return true;
        } else {
            // invalidate false reports of our flag due to flags returning
            for (int i = 3; --i >= 0;) {
                if (Comms.readMyflagsExists(i) == 1 && Comms.readMyflagsDistress(i) == 1) {
                    MapLocation loc = Util.int2loc(Comms.readMyflagsLoc(i));
                    if (rc.getLocation().isWithinDistanceSquared(loc, 2)
                            && Cache.nearbyEnemies.length == 0
                            && (Comms.readMyflagsOriginalLoc(i) == Comms.readMyflagsLoc(i) || !myFlagSeen[i])
                    ) {
                        // reset only if the flag has returned
                        Comms.writeMyflagsDistress(i, 0);
                        Comms.writeMyflagsLoc(i, Comms.readMyflagsOriginalLoc(i));
                    }
                }
            }
        }
        return false;
    }

    public static int getMyFlagIndex(FlagInfo flag) throws GameActionException {
        if (flag.getID() == Comms.readMyflagsId(0)) return 0;
        if (flag.getID() == Comms.readMyflagsId(1)) return 1;
        if (flag.getID() == Comms.readMyflagsId(2)) return 2;
        // otherwise it is an unregistered flag, register it based on location (set in init)
        for (int i = 3; --i >= 0;) {
            if (Util.loc2int(flag.getLocation()) == Comms.readMyflagsLoc(i)) {
                Comms.writeMyflagsId(i, flag.getID());
                return i;
            }
        }
        Debug.failFast("Cannot match my flag ID");
        return -1;
    }

    public static int getOppFlagIndex(FlagInfo flag) throws GameActionException {
        if (flag.getID() == Comms.readOppflagsId(0)) return 0;
        if (flag.getID() == Comms.readOppflagsId(1)) return 1;
        if (flag.getID() == Comms.readOppflagsId(2)) return 2;
        // it is an unregistered flag, register it to the closest broadcast location
        int bestIndex = -1;
        int bestDis = Integer.MAX_VALUE;
        for (int i = 3; --i >= 0;) {
            if (Comms.readOppflagsExists(i) == 1
                    && Comms.readOppflagsOriginalLoc(i) == 0) {
                int dis = Util.int2loc(Comms.readOppflagsLoc(i)).distanceSquaredTo(flag.getLocation());
                if (dis < bestDis) {
                    bestDis = dis;
                    bestIndex = i;
                }
            }
        }
        if (bestIndex == -1) {
            Debug.failFast("Cannot match opp flag ID");
        }
        Comms.writeOppflagsId(bestIndex, flag.getID());
        return bestIndex;
    }
}
