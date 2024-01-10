package bot1;

import battlecode.common.*;

public class Robot extends RobotPlayer {
    private static MapLocation targetLoc = null;
    private static int targetRound = -1;

    public static MapLocation[] mySpawnCenters = new MapLocation[3];
    public static MapLocation[] oppSpawnCenters = new MapLocation[3];

    public static int duckID;
    public static MapLocation homeSpawn;

    static void init() throws GameActionException {
        initHQLocs();
    }

    static void initTurn() throws GameActionException {
        Comms.pull();
        MapRecorder.updateSym();

        if (!rc.isSpawned()) {
            for (MapLocation loc : rc.getAllySpawnLocations()) {
                if (rc.canSpawn(loc)) {
                    rc.spawn(loc);
                    homeSpawn = Util.getClosestLoc(mySpawnCenters);
                    break;
                }
            }
        }
    }

    static void play() throws GameActionException {
        if (!rc.isSpawned())
            return;

        MapLocation[] crumbs = rc.senseNearbyCrumbs(-1);
        if (crumbs.length > 0) {
            targetLoc = Util.getClosestLoc(crumbs);
        } else {
            targetLoc = Explorer.getUnseenExploreTarget();
        }

//        targetLoc = oppSpawnCenters[duckID];
        if (targetLoc != null) {
            PathFinder.move(targetLoc);
        }
    }

    static void endTurn() throws GameActionException {
        Comms.push();
        if (rc.isSpawned()) {
            MapRecorder.recordSym(2000);
        }
    }

    static void initHQLocs() throws GameActionException {
        Comms.pull();
        if (Comms.readHqLoc(0) == 0) {
            MapLocation[] locations = rc.getAllySpawnLocations();
            boolean[] used = new boolean[27];
            for (int i = 3; --i >= 0; ) {
                int sumX = 0, sumY = 0, count = 0;
                for (int j = 27; --j >= 0; ) {
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

                mySpawnCenters[i] = new MapLocation(sumX / 9, sumY / 9);
                Comms.writeHqLoc(i, Util.loc2int(mySpawnCenters[i]));
                Debug.println(Debug.INFO, String.format("HQ %d: %d %d", i, sumX / 9, sumY / 9));
            }
            Comms.push();
        } else {
            for (int i = 3; --i >= 0; ) {
                mySpawnCenters[i] = Util.int2loc(Comms.readHqLoc(i));
            }
        }
        oppSpawnCenters[0] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[0]);
        oppSpawnCenters[1] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[1]);
        oppSpawnCenters[2] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[2]);
    }

    public static int getDisToMyClosestSpawnCenter(MapLocation loc) {
        int dis0 = mySpawnCenters[0].distanceSquaredTo(loc);
        int dis1 = mySpawnCenters[1].distanceSquaredTo(loc);
        int dis2 = mySpawnCenters[2].distanceSquaredTo(loc);
        return Math.min(Math.min(dis0, dis1), dis2);
    }
}
