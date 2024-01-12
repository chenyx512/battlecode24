package bot1;

import battlecode.common.FlagInfo;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class FlagManager extends RobotPlayer {
    private static int carriedEnemyFlagIndex = -1;
    private static MapLocation flagCarryDestination = null;

    public static void init() throws GameActionException {
        for (int i = 3; --i >= 0;) {
            Comms.writeMyflagsLoc(i, Util.loc2int(Robot.mySpawnCenters[i]));
            Comms.writeMyflagsOriginalLoc(i, Util.loc2int(Robot.mySpawnCenters[i]));
            Comms.writeMyflagsExists(i, 1);

            Comms.writeOppflagsLoc(i, Util.loc2int(MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[i])));
            Comms.writeOppflagsExists(i, 1);
        }
    }

    public static void initTurn() throws GameActionException {
        if (!rc.hasFlag() && carriedEnemyFlagIndex != -1) {
            // we just dropped the flag, temply set it back,
            // if someone else picked it up they will report it
            Comms.writeOppflagsCarried(carriedEnemyFlagIndex, 0);
            Comms.writeOppflagsLoc(carriedEnemyFlagIndex, Comms.readOppflagsOriginalLoc(carriedEnemyFlagIndex));
            carriedEnemyFlagIndex = -1;
        }
    }


    public static boolean act() throws GameActionException {
        boolean[] enemyFlagSeen = new boolean[3];
        for (FlagInfo flag : rc.senseNearbyFlags(-1)) {
            if (flag.getTeam() == myTeam) {
                int flagIndex = getMyFlagIndex(flag);
                if ((flag.isPickedUp() && rc.getRoundNum() > 200)
                        || Cache.nearbyEnemies.length > Cache.nearbyFriends.length) {
                    Comms.writeMyflagsDistress(flagIndex, 1);
                    Comms.writeMyflagsLoc(flagIndex, Util.loc2int(flag.getLocation()));
                }
            } else {
                int flagIndex = getOppFlagIndex(flag);
                enemyFlagSeen[flagIndex] = true;
                if (!flag.isPickedUp()) {
                    // enemy flag found on the ground
                    if (Comms.readOppflagsOriginalLoc(flagIndex) == 0) {
                        Comms.writeOppflagsOriginalLoc(flagIndex, Util.loc2int(flag.getLocation()));
                    }
                    Comms.writeOppflagsConfirmed(flagIndex, 1);
                    Comms.writeOppflagsLoc(flagIndex, Util.loc2int(flag.getLocation()));
                    if (rc.canPickupFlag(flag.getLocation())) {
                        flagCarryDestination = Util.getClosestLoc(Robot.mySpawnCenters);
                        carriedEnemyFlagIndex = flagIndex;
                        rc.pickupFlag(flag.getLocation());
                        break;
                    }
                }
            }
        }
        if (rc.hasFlag()) {
            PathFinder.move(flagCarryDestination);
            Comms.writeOppflagsLoc(carriedEnemyFlagIndex, Util.loc2int(rc.getLocation()));
            Comms.writeOppflagsCarried(carriedEnemyFlagIndex, 1);
            if (rc.senseMapInfo(rc.getLocation()).getSpawnZoneTeam() == myTeamID) {
                // we just captured the flag
                Comms.writeOppflagsExists(carriedEnemyFlagIndex, 0);
                carriedEnemyFlagIndex = -1;
                return false;
            }
            return true;
        } else {
            // invalidate false reports due to teammate dying or flags returning
            for (int i = 3; --i >= 0;) {
                if (Comms.readMyflagsExists(i) == 1 && Comms.readMyflagsDistress(i) == 1) {
                    MapLocation loc = Util.int2loc(Comms.readMyflagsLoc(i));
                    if (rc.getLocation().isWithinDistanceSquared(loc, 2)
                            && Cache.nearbyEnemies.length == 0) {
                        Comms.writeMyflagsDistress(i, 0);
                        Comms.writeMyflagsLoc(i, Comms.readMyflagsOriginalLoc(i));
                    }
                }
            }
//            for (int i = 3; --i >= 0;) {
//                if (Comms.readOppflagsExists(i) == 1 && Comms.readOppflagsCarried(i) == 1) {
//                    MapLocation loc = Util.int2loc(Comms.readOppflagsLoc(i));
//                    if (rc.getLocation().isWithinDistanceSquared(loc, 8)) {
//                        Comms.writeOppflagsCarried(i, 0);
//                        Comms.writeOppflagsLoc(i, Comms.readOppflagsOriginalLoc(i));
//                    }
//                }
//            }
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
                    && Comms.readOppflagsConfirmed(i) == 0) {
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
