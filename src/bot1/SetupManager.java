package bot1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;

public class SetupManager extends SpecialtyManager {
    private static int flagCarrierID = -1;
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
                Direction bestDir = Direction.CENTER;
                int bestScore = getFlagLocScore(rc.getLocation());
                for (Direction dir : Constants.MOVEABLE_DIRECTIONS) {
                    if (!rc.canMove(dir)) {
                        continue;
                    }
                    MapLocation loc = rc.getLocation().add(dir);
                    if (getFlagDistance(flagCarrierID, loc) <= GameConstants.MIN_FLAG_SPACING_SQUARED) {
                        continue;
                    }
                    int score = getFlagLocScore(loc);
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
            return true;
        }
        return false;
    }

    private static int getFlagLocScore(MapLocation loc) {
        int dis2enemy = Util.getClosestDis(loc, oppSpawnCenters);
        int dis2hq = getDisToMyClosestSpawnCenter(loc);
        return 2 * dis2enemy + dis2hq;
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
