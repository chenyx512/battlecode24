package bot1;

import battlecode.common.*;

public class Cache extends RobotPlayer {
    public static RobotInfo[] nearbyEnemies, nearbyFriends;

    public static void initTurn() throws GameActionException {
        if (!rc.isSpawned())
            return;

        nearbyEnemies = rc.senseNearbyRobots(-1, oppTeam);
        nearbyFriends = rc.senseNearbyRobots(-1, myTeam);
    }
}
