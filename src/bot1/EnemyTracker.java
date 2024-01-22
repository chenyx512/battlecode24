package bot1;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class EnemyTracker extends Robot {
    public static void initTurn() throws GameActionException {
        if (!rc.isSpawned())
            return;
        boolean newEnemy = (Cache.nearbyEnemies.length > 3)
                && getDisToMyClosestSpawnCenter(Cache.closestEnemy) <= Util.getClosestDis(Cache.closestEnemy, oppSpawnCenters) / 2;
        int enemyIndex = -1;
        for (int i = 3; --i >= 0; ) {
            MapLocation loc = Util.int2loc(Comms.readEnemyLoc(i));
            if (Cache.closestEnemy == null) {
                if (loc != null && rc.getLocation().isWithinDistanceSquared(loc, 4)) {
                    Comms.writeEnemyLoc(i, 0);
                }
            } else if (newEnemy) {
                if (loc == null) {
                    enemyIndex = i;
                } else if (Cache.closestEnemy.isWithinDistanceSquared(loc, 100)) {
                    newEnemy = false;
                }
            }
        }
        if (newEnemy && enemyIndex != -1) {
            Comms.writeEnemyLoc(enemyIndex, Util.loc2int(Cache.closestEnemy));
        }
    }

    public static boolean act() throws GameActionException {
        int closestDis = 100; // closest dis we pursue
        MapLocation target = null;
        for (int i = 3; --i >= 0; ) {
            MapLocation loc = Util.int2loc(Comms.readEnemyLoc(i));
            if (loc != null) {
                int dis = loc.distanceSquaredTo(rc.getLocation());
                if (dis < closestDis) {
                    closestDis = dis;
                    target = loc;
                }
            }
        }
        if (target != null) {
            Debug.setIndicatorLine(Debug.ASSIGNMENT, rc.getLocation(), target, 255, 255, 255);
            PathFinder.move(target);
            return true;
        }
        return false;
    }
}