package sp1_pretest;

import battlecode.common.*;

public class Cache extends RobotPlayer {
    public static RobotInfo[] nearbyEnemies, nearbyFriends;
    public static MapLocation closestEnemy;
    public static boolean allyToHeal;

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
        allyToHeal = false;
        for (int i = nearbyFriends.length; --i >= 0;) {
            if (nearbyFriends[i].getHealth() <= 900) {
                allyToHeal = true;
            }
        }
    }
}
