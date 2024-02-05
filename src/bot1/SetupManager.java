package bot1;

import battlecode.common.*;
import bot1.fast.FastMath;

public class SetupManager extends SpecialtyManager {
    private static int flagCarrierID = -1;
    private static int flagDestID;
    private static MapLocation flagDest;
    public static int routeSetterDestID = -1;
    private static int routeSetterSourceID = -1;
    private static boolean destReached;
    public static void initTurn() throws GameActionException {
        if (duckSeqID >= 4 && duckSeqID <= 6) {
            if (!rc.isSpawned()) {
                flagCarrierID = duckSeqID - 4;
                rc.spawn(mySpawnCenters[flagCarrierID]);
                RoleAssigner.reassign(flagCarrierID);
            }
        } else if (duckSeqID >= 7 && duckSeqID <= 12) {
            if (!rc.isSpawned() && rc.getRoundNum() > 1) {
                switch (duckSeqID) {
                    case 7: routeSetterSourceID = 0; routeSetterDestID = 1; break;
                    case 8: routeSetterSourceID = 0; routeSetterDestID = 2; break;
                    case 9: routeSetterSourceID = 1; routeSetterDestID = 0; break;
                    case 10: routeSetterSourceID = 1; routeSetterDestID = 2; break;
                    case 11: routeSetterSourceID = 2; routeSetterDestID = 0; break;
                    case 12: routeSetterSourceID = 2; routeSetterDestID = 1; break;
                }
                RoleAssigner.trySpawn(routeSetterSourceID);
            }
        } else {
            if (rc.getRoundNum() > 4) {
                RoleAssigner.initTurn();
            }
        }
    }

    public static boolean act() throws GameActionException{
        if (flagCarrierID != -1) {
            carryFlag();
            return true;
        } else if (routeSetterDestID != -1) {
            routeSet();
            return true;
        }
        return false;
    }

    private static void routeSet() throws GameActionException {
        MapLocation dest = mySpawnCenters[routeSetterDestID];
        PathFinder.move(dest);
        if (rc.getLocation().isWithinDistanceSquared(dest, 25)) {
            Comms.writeHqconnectedVal(routeSetterSourceID * 3 + routeSetterDestID, 1);
            Comms.writeHqconnectedVal(routeSetterDestID * 3 + routeSetterSourceID, 1);
            routeSetterDestID = -1;
        }
        if (rc.getRoundNum() > 150) {
            routeSetterDestID = -1;
        }
    }

    private static void carryFlag() throws GameActionException {
        if (!rc.hasFlag()) {
            if (rc.getRoundNum() < 198 && rc.canPickupFlag(rc.getLocation())) {
                Comms.writeMyflagsId(flagCarrierID, rc.senseNearbyFlags(1)[0].getID());
                rc.pickupFlag(rc.getLocation());
            } else if (rc.getRoundNum() == 200){
                MapLocation loc = rc.getLocation().add(rc.getLocation().directionTo(Util.getClosestLoc(oppSpawnCenters)));
                if (rc.canBuild(TrapType.WATER, loc)) {
                    rc.build(TrapType.WATER, loc);
                }
                if (rc.canBuild(TrapType.STUN, rc.getLocation())) {
                    rc.build(TrapType.STUN, rc.getLocation());
                }
            }
        } else {
            int START_ROUND = 20;
            int DECISION_ROUND = 70;
            int END_ROUND = 150;
            if (rc.getRoundNum() < START_ROUND)
                return;
            if (rc.getRoundNum() == START_ROUND) {
                int bestScore = Integer.MIN_VALUE;
                for (int i = 3; --i >= 0;) {
                    int score = Util.getClosestDis(mySpawnCenters[i], oppSpawnCenters);
                    if (score > bestScore) {
                        bestScore = score;
                        flagDest = mySpawnCenters[i];
                        flagDestID = i;
                    }
                }
                Debug.println(Debug.INFO, String.format("flag %s going to %s", rc.getLocation().toString(), flagDest.toString()));
            }
            if (rc.getRoundNum() == DECISION_ROUND &&
                    Comms.readHqconnectedVal(flagCarrierID * 3 + flagDestID) + Comms.readHqconnectedVal(flagDestID * 3 + flagCarrierID) == 0) {
                flagDest = mySpawnCenters[flagCarrierID];
                flagDestID = flagCarrierID;
                Debug.println(Debug.INFO, String.format("flag %s going back to %s", rc.getLocation().toString(), flagDest.toString()));
            }
            if (!destReached && rc.getRoundNum() <= END_ROUND) {
                if (getFlagDistance(flagCarrierID, rc.getLocation()) > 52) {
                    PathFinder.move(flagDest);
                    if (rc.getLocation().isWithinDistanceSquared(flagDest, 4)) {
                        destReached = true;
                    }
                } else {
                    destReached = true;
                }
            }
            if (destReached || rc.getRoundNum() > END_ROUND) {
                // find the locally best flag location
                // the rng helps all the flags converge and not have one flag block the other due to distance constraint
                Direction bestDir = Direction.CENTER;
                double bestScore = getFlagLocScore(rc.getLocation());
                for (Direction dir : Constants.MOVEABLE_DIRECTIONS) {
                    if (!rc.canMove(dir)) {
                        continue;
                    }
                    MapLocation loc = rc.getLocation().add(dir);
                    if (getFlagDistance(flagCarrierID, loc) <= GameConstants.MIN_FLAG_SPACING_SQUARED) {
                        continue;
                    }
                    int roundsLeft = 200 - rc.getRoundNum();
                    double rngFactor = 2;
                    if (roundsLeft < 8) {
                        rngFactor = 0;
                    } else if (roundsLeft < 20) {
                        rngFactor = 1;
                    }
                    double score = getFlagLocScore(loc) + FastMath.fakefloat() * rngFactor;
                    if (score > bestScore) {
                        bestScore = score;
                        bestDir = dir;
                    }
                }
                tryMove(bestDir);

                if (rc.getRoundNum() == 198) {
                    Comms.writeMyflagsOriginalLoc(flagCarrierID, Util.loc2int(rc.getLocation()));
                    rc.dropFlag(rc.getLocation());
                }
            }
            Comms.writeMyflagsLoc(flagCarrierID, Util.loc2int(rc.getLocation()));
        }
    }

    private static double getFlagLocScore(MapLocation loc) {
        double dis2enemy = Math.sqrt(Util.getClosestDis(loc, oppSpawnCenters));
        double dis2hq = Math.sqrt(getDisToMyClosestSpawnCenter(loc));
        if (destReached) {
            dis2hq = (dis2hq + Math.sqrt(loc.distanceSquaredTo(flagDest))) / 2;
        }
        return dis2enemy - dis2hq;
    }

    private static int getFlagDistance(int flagid, MapLocation loc) throws GameActionException {
        MapLocation loc0 = Util.int2loc(Comms.readMyflagsLoc(0));
        int dis0 = loc0.distanceSquaredTo(loc);
        MapLocation loc1 = Util.int2loc(Comms.readMyflagsLoc(1));
        int dis1 = loc1.distanceSquaredTo(loc);
        MapLocation loc2 = Util.int2loc(Comms.readMyflagsLoc(2));
        int dis2 = loc2.distanceSquaredTo(loc);
        switch (flagid) {
            case 0: return Math.min(dis1, dis2);
            case 1: return Math.min(dis0, dis2);
            case 2: return Math.min(dis0, dis1);
        }
        Debug.failFast("impossible");
        return 0;
    }
}
