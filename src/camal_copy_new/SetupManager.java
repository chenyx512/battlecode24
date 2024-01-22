package camal_copy_new;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import camal_copy_new.fast.FastMath;

public class SetupManager extends SpecialtyManager {
    private static int flagCarrierID = -1;
    private static MapLocation flagDest;
    private static boolean destReached;
    public static void initTurn() throws GameActionException {
        if (duckSeqID >= 4 && duckSeqID <= 6) {
            if (!rc.isSpawned()) {
                flagCarrierID = duckSeqID - 4;
                rc.spawn(mySpawnCenters[flagCarrierID]);
                RoleAssigner.reassign(flagCarrierID);
            }
        } else {
            if (rc.getRoundNum() > 2) {
                RoleAssigner.initTurn();
            }
        }
    }

    public static boolean act() throws GameActionException{
        if (flagCarrierID != -1) {
            if (!rc.hasFlag()) {
                if (rc.canPickupFlag(rc.getLocation())) {
                    Comms.writeMyflagsId(flagCarrierID, rc.senseNearbyFlags(1)[0].getID());
                    rc.pickupFlag(rc.getLocation());
                }
            } else {
                int START_ROUND = 30;
                int END_ROUND = 160;
                if (rc.getRoundNum() < START_ROUND)
                    return true;
                if (rc.getRoundNum() == START_ROUND) {
                    int bestScore = Integer.MIN_VALUE;
                    for (int i = 3; --i >= 0;) {
                        int score = Util.getClosestDis(mySpawnCenters[i], oppSpawnCenters);
                        if (score > bestScore) {
                            bestScore = score;
                            flagDest = mySpawnCenters[i];
                        }
                    }
                    Debug.println(Debug.INFO, String.format("flag %s going to %s", rc.getLocation().toString(), flagDest.toString()));
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
                        double score = getFlagLocScore(loc) + FastMath.fakefloat() * 2;
                        if (score > bestScore) {
                            bestScore = score;
                            bestDir = dir;
                        }
                    }
                    tryMove(bestDir);

                    Comms.writeMyflagsLoc(flagCarrierID, Util.loc2int(rc.getLocation()));
                    if (rc.getRoundNum() == 200) {
                        Comms.writeMyflagsOriginalLoc(flagCarrierID, Util.loc2int(rc.getLocation()));
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static double getFlagLocScore(MapLocation loc) {
        double dis2enemy = Math.sqrt(Util.getClosestDis(loc, oppSpawnCenters));
        double dis2hq = destReached? Math.sqrt(loc.distanceSquaredTo(flagDest)) : Math.sqrt(getDisToMyClosestSpawnCenter(loc));
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
