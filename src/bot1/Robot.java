package bot1;

import battlecode.common.*;
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

        Comms.pull();
        if (Comms.readHqLoc(0) == 0) {
            initHQLocs();
            Comms.push();
        }
    }

    void initTurn() throws GameActionException {
        Comms.pull();
        if (!rc.isSpawned()) {
            if(Comms.readDucksAlive(0) == 0) {
                for (MapLocation loc : rc.getAllySpawnLocations()) {
                    if (rc.canSpawn(loc)) {
                        rc.spawn(loc);
                        Comms.writeDucksAlive(0, 1);
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
        Debug.setIndicatorDot(Debug.INFO, targetLoc, 255, 0, 0);
        Debug.printString(Debug.INFO, String.format("%d %d", targetLoc.x, targetLoc.y));
        PathFinder.move(targetLoc);
    }

    void endTurn() throws GameActionException {
        Comms.push();
    }

    void initHQLocs() throws GameActionException {
        MapLocation[] locations = rc.getAllySpawnLocations();
        boolean[] used = new boolean[27];
        for (int i = 3; --i >= 0;) {
            int sumX = 0, sumY = 0, count = 0;
            for (int j = 27; --i >= 0;) {
                if (!used[j]) {
                    if (count == 0) {
                        // Select a starting location for a new group
                        sumX += locations[j].x;
                        sumY += locations[j].y;
                        count++;
                        used[j] = true;
                    } else {
                        // Check if the location is close to the starting location
                        int dx = locations[j].x - sumX / count;
                        int dy = locations[j].y - sumY / count;
                        if (dx * dx + dy * dy <= 8) {
                            sumX += locations[j].x;
                            sumY += locations[j].y;
                            count++;
                            used[j] = true;
                        }
                    }
                }
            }
            assert count == 9;

            Comms.writeHqLoc(i, Util.loc2int(new MapLocation(sumX / 9, sumY / 9)));
            Debug.println(Debug.INFO, String.format("HQ %d: %d %d", i, sumX / 9, sumY / 9));
        }
    }
}
