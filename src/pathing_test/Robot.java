package pathing_test;

import battlecode.common.*;
import bot1.Comms;
import bot1.Debug;
import bot1.PathFinder;
import bot1.fast.*;

public class Robot {
    static RobotController rc;
    static int H, W;

    static MapLocation targetLoc = null;
    static int targetRound = -1;

    Robot (RobotController rc) throws GameActionException {
        this.rc = rc; //this should always go first
        H = rc.getMapHeight();
        W = rc.getMapWidth();
    }

    void initTurn() throws GameActionException {
        bot1.Comms.initTurns();
        if (!rc.isSpawned()) {
            if(bot1.Comms.readDucksAlive(0) == 0) {
                for (MapLocation loc : rc.getAllySpawnLocations()) {
                    if (rc.canSpawn(loc)) {
                        rc.spawn(loc);
                        bot1.Comms.writeDucksAlive(0, 1);
                        break;
                    }
                }
            } else {
                return;
            }
        }
    }

    void play() throws GameActionException {
        if (!rc.isSpawned() || rc.getRoundNum() < 200)
            return;

        if (targetLoc == null || rc.getRoundNum() - targetRound > 100 || rc.getLocation().distanceSquaredTo(targetLoc) <= 9) {
            targetLoc = new MapLocation(FastMath.rand256() % W, FastMath.rand256() % H);
            targetRound = rc.getRoundNum();
        }
        bot1.Debug.setIndicatorDot(bot1.Debug.INFO, targetLoc, 255, 0, 0);
        bot1.Debug.printString(Debug.INFO, String.format("%d %d", targetLoc.x, targetLoc.y));
        PathFinder.move(targetLoc);
    }

    void endTurn() throws GameActionException {
        Comms.endsTurns();
    }
}
