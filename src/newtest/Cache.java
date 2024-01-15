package newtest;

import battlecode.common.*;

public class Cache extends RobotPlayer {
    public static RobotInfo[] nearbyEnemies, nearbyFriends;
    public static MapLocation closestEnemy;

    public static void initTurn() throws GameActionException {
        if (!rc.isSpawned())
            return;

        nearbyEnemies = rc.senseNearbyRobots(-1, oppTeam);
        nearbyFriends = rc.senseNearbyRobots(-1, myTeam);
        closestEnemy = null;
        int minDis = Integer.MAX_VALUE;
        for (int i = nearbyEnemies.length; --i >= 0;) {
            int dis = nearbyEnemies[i].getLocation().distanceSquaredTo(rc.getLocation());
            if (dis < minDis) {
                minDis = dis;
                closestEnemy = nearbyEnemies[i].getLocation();
            }
        }
    }
}
