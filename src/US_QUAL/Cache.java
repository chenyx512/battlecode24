package US_QUAL;

import battlecode.common.*;

public class Cache extends RobotPlayer {
    public static RobotInfo[] nearbyEnemies, nearbyFriends;
    public static MapLocation closestEnemy;
    public static boolean allyToHeal;
    public static RobotInfo healingTarget;
    public static MapLocation lastEnemySeen;
    private static int lastEnemySeenRound;

    public static void initTurn() throws GameActionException {
        if (!rc.isSpawned())
            return;

        nearbyEnemies = rc.senseNearbyRobots(-1, oppTeam);
        nearbyFriends = rc.senseNearbyRobots(-1, myTeam);
        closestEnemy = null;
        healingTarget = null;
        lastEnemySeen = null;
        int minDis = Integer.MAX_VALUE;
        for (int i = nearbyEnemies.length; --i >= 0;) {
            int dis = nearbyEnemies[i].getLocation().distanceSquaredTo(rc.getLocation());
            if (dis < minDis) {
                minDis = dis;
                closestEnemy = nearbyEnemies[i].getLocation();
            }
        }
        if (closestEnemy != null) {
            lastEnemySeen = closestEnemy;
            lastEnemySeenRound = rc.getRoundNum();
        } else if (lastEnemySeen != null && (rc.getRoundNum() > lastEnemySeenRound + 5 || rc.getLocation().isAdjacentTo(lastEnemySeen))) {
            lastEnemySeen = null;
        }

        allyToHeal = false;
        double bestHealingTargetScore = -Double.MAX_VALUE;
        for (int i = nearbyFriends.length; --i >= 0;) {
            if (nearbyFriends[i].getHealth() <= 900) {
                allyToHeal = true;
            }
            double score = Micro.getHealingTargetScore(nearbyFriends[i]);
            if (score > bestHealingTargetScore) {
                bestHealingTargetScore = score;
                healingTarget = nearbyFriends[i];
            }
        }
    }
}
