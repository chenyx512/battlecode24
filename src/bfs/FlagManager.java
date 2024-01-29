package bfs;

import battlecode.common.*;

public class FlagManager extends RobotPlayer {
    public static boolean urgent;

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
            if (Robot.isMaster) {
                for (int i = 3; --i >= 0;) {
                    if (Comms.readMyflagsExists(i) == 0)
                        continue;
                    int cnt = Comms.readMyflagsNotSeenCnt(i);
                    final int CUTOFF = 80;
                    cnt = Math.min(cnt + 1, CUTOFF);
                    Comms.writeMyflagsNotSeenCnt(i, cnt);
                    if (Comms.readMyflagsDistress(i) == 1) {
                        MapLocation flagLoc = Util.int2loc(Comms.readMyflagsLoc(i));
                        MapLocation closestBase = Util.getClosestLoc(flagLoc, Robot.oppSpawnCenters);
                        if (rc.getRoundNum() % 2 == 0) {
                            // every 2 rounds, we simulate the flag moving towards enemy base
                            flagLoc = flagLoc.add(flagLoc.directionTo(closestBase));
                            Comms.writeMyflagsLoc(i, Util.loc2int(flagLoc));
                            if (flagLoc.equals(closestBase)) {
                                // if the flag reaches enemy base, try for at most 10 turns before giving up
                                cnt = Math.max(CUTOFF - 10, cnt);
                            }
                        }
                        if (cnt >= CUTOFF) {
                            Debug.println(Debug.INFO, String.format("flag %d not seen, it's gone", i));
                            Comms.writeMyflagsExists(i, 0);
                        }
                    }
                }
            }
        }
    }

    public static boolean act() throws GameActionException {
        urgent = false;

        boolean[] enemyFlagSeen = new boolean[3];
        boolean[] myFlagSeen = new boolean[3];
        boolean hasFlag = rc.hasFlag(); // handle edge case of picking flag up in our own spawn zone
        for (FlagInfo flag : rc.senseNearbyFlags(-1)) {
            if (flag.getTeam() == myTeam) {
                int flagIndex = getMyFlagIndex(flag);
                Comms.writeMyflagsExists(flagIndex, 1);
                Comms.writeMyflagsLoc(flagIndex, Util.loc2int(flag.getLocation()));
                Comms.writeMyflagsNotSeenCnt(flagIndex, 0);
                myFlagSeen[flagIndex] = true;
                if ((flag.isPickedUp() && rc.getRoundNum() > 200)
                        || Cache.nearbyEnemies.length > Cache.nearbyFriends.length) {
                    Comms.writeMyflagsDistress(flagIndex, 1);
                }
                urgent = true;
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
                        // only healers carry flag cuz they useless
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
                } else {
                    urgent = true;
                }
            }
        }
        if (hasFlag) {
            lastFlagCarryRound = rc.getRoundNum();
            if (Cache.closestEnemy != null && Cache.nearbyFriends.length < 2) {
                Comms.writeOppflagsEscortLoc(carriedEnemyFlagIndex, Util.loc2int(Cache.closestEnemy));
                PathFinder.tryMoveDir(Cache.closestEnemy.directionTo(rc.getLocation()));
                flagCarryDestination = Util.getClosestLoc(Robot.mySpawnCenters);
            } else {
                Comms.writeOppflagsEscortLoc(carriedEnemyFlagIndex, 0);
            }
            // If I am stuck in water, call for help
            int nonpassiblecnt = 0;
            for (Direction dir : Constants.MOVEABLE_DIRECTIONS) {
                MapLocation loc = rc.getLocation().add(dir);
                if (!rc.canSenseLocation(loc) || !rc.sensePassability(loc))
                    nonpassiblecnt++;
            }
            if (nonpassiblecnt > 3) {
                Comms.writeOppflagsEscortLoc(carriedEnemyFlagIndex, Util.loc2int(rc.getLocation()));
            }
            // Yukoh TODO:
            // get to the fucking closest Robot.mySpawnCenter
            // which one doens't matter
            if (rc.getID() == 11111) {
                // print something
                Debug.printString("xx"); // this will write to the indicator so u can view it in client
                Debug.println("xxx"); // this will print to stdout, you hvae to run the game from terminal to view it
//                Debug.setIndicatorDot();
//                Debug.setIndicatorLine();
            }
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
        /*
        index is x + y * W
        flag ID corresponds to the starting location of the flag
        broadcast is in order of increasing flag id
         */
        if (flag.getID() == Comms.readOppflagsId(0)) return 0;
        if (flag.getID() == Comms.readOppflagsId(1)) return 1;
        if (flag.getID() == Comms.readOppflagsId(2)) return 2;
        // get the gameworld id of opponent spawn centers
        int s0 = Robot.oppSpawnCenters[0].x + Robot.oppSpawnCenters[0].y * W;
        int s1 = Robot.oppSpawnCenters[1].x + Robot.oppSpawnCenters[1].y * W;
        int s2 = Robot.oppSpawnCenters[2].x + Robot.oppSpawnCenters[2].y * W;
        if (s0 > s1) {int tmp=s0; s0=s1; s1=tmp;}
        if (s1 > s2) {int tmp=s1; s1=s2; s2=tmp;}
        if (s0 > s1) {int tmp=s0; s0=s1; s1=tmp;}
        if (s1 > s2) {int tmp=s1; s1=s2; s2=tmp;}
        if (flag.getID() == s0) return 0;
        if (flag.getID() == s1) return 1;
        if (flag.getID() == s2) return 2;
        Debug.println(String.format("WARNING, unmatched flag id %d at %s, s0=%d s1=%d s2=%d", flag.getID(), flag.getLocation().toString(), s0, s1, s2));
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
