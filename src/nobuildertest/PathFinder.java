package nobuildertest;

import battlecode.common.*;
import nobuildertest.fast.*;

public class PathFinder extends RobotPlayer{
    private static MapLocation target = null;

    static void randomMove() throws GameActionException {
        int starting_i = FastMath.rand256() % Constants.MOVEABLE_DIRECTIONS.length;
        for (int i = starting_i; i < starting_i + 8; i++) {
            Direction dir = Constants.MOVEABLE_DIRECTIONS[i % 8];
            if (rc.canMove(dir)) rc.move(dir);
        }
    }

    static void tryMoveDir(Direction dir) throws GameActionException {
        if (rc.isMovementReady() && dir != Direction.CENTER) {
            if (rc.canMove(dir)) {
                rc.move(dir);
            } else if (rc.canMove(dir.rotateRight())) {
                rc.move(dir.rotateRight());
            } else if (rc.canMove(dir.rotateLeft())) {
                rc.move(dir.rotateLeft());
            } else {
                randomMove();
            }
        }
    }

    static public void escort(MapLocation loc) throws GameActionException {
        /* To stay 1 tile away from the escorted duck to prevent congestion */
        Debug.printString(Debug.PATHFINDING, String.format("escort%s", loc.toString()));
        if (!rc.isMovementReady())
            return;
        // if I am right next to the escorted, move away to make room
        if (rc.getLocation().isAdjacentTo(loc)) {
            tryMoveDir(rc.getLocation().directionTo(loc).opposite());
            return;
        }
        // otherwize, try path to a direction behind the escorted, unless I am gonna be adjacent, then I stop
        MapLocation closestHome = Util.getClosestLoc(loc, Robot.mySpawnCenters);
        Direction protectDir = loc.directionTo(closestHome).opposite();
        target = loc.add(protectDir).add(protectDir);
        Direction dir = BugNav.getMoveDir();
        if (dir != null && !rc.getLocation().add(dir).isAdjacentTo(loc))
            rc.move(dir);
    }

    static public void move(MapLocation loc) throws GameActionException {
        if (!rc.isMovementReady() || loc == null)
            return;
        Debug.printString(Debug.PATHFINDING, String.format("move%s", loc.toString()));
        target = loc;
        Direction dir = BugNav.getMoveDir();
        if (dir == null)
            return;
        rc.move(dir);
    }

    static class BugNav {

        static final int INF = 1000000;
        static final int MAX_MAP_SIZE = GameConstants.MAP_MAX_HEIGHT;

        static boolean shouldGuessRotation = true; // if I should guess which rotation is the best
        static boolean rotateRight = true; // if I should rotate right or left
        static MapLocation lastObstacleFound = null; // latest obstacle I've found in my way
        static int minDistToEnemy = INF; // minimum distance I've been to the enemy while going around an obstacle
        static MapLocation prevTarget = null; // previous target
        static boolean hasRotatedAvoidingCurrent = false; // if I've rotated due to the current obstacle
        static FastIntSet visited = new FastIntSet();
        static int id = 12620;

        static Direction getMoveDir() throws GameActionException {
            try {
                // different target? ==> previous data does not help!
                if (prevTarget == null || target.distanceSquaredTo(prevTarget) > 0) {
                    // Debug.println("New target: " + target, id);
                    resetPathfinding();
                }

                // If I'm at a minimum distance to the target, I'm free!
                MapLocation myLoc = rc.getLocation();
                // int d = myLoc.distanceSquaredTo(target);
                int d = Util.distance(myLoc, target);
                if (d < minDistToEnemy) {
                    // Debug.println("New min dist: " + d + " Old: " + minDistToEnemy, id);
                    resetPathfinding();
                    minDistToEnemy = d;
                }

                int code = getCode();

                if (visited.contains(code)) {
                    // Debug.println("Contains code", id);
                    resetPathfinding();
                }
                visited.add(code);

                // Update data
                prevTarget = target;

                // If there's an obstacle I try to go around it [until I'm free] instead of
                // going to the target directly
                Direction dir = myLoc.directionTo(target);
                if (lastObstacleFound != null) {
                    // Debug.println("Last obstacle found: " + lastObstacleFound, id);
                    dir = myLoc.directionTo(lastObstacleFound);
                }
                MapLocation nextLoc = myLoc.add(dir);
                boolean avoidingCurrent = false;
                if (rc.canMove(dir)) {
                    // Debug.println("can move: " + dir, id);
                    resetPathfinding();
                }

                // I rotate clockwise or counterclockwise (depends on 'rotateRight'). If I try
                // to go out of the map I change the orientation
                // Note that we have to try at most 16 times since we can switch orientation in
                // the middle of the loop. (It can be done more efficiently)
                for (int i = 8; i-- > 0;) {
                    MapLocation newLoc = myLoc.add(dir);
                    if (rc.canSenseLocation(newLoc)) {
                        MapInfo info = rc.senseMapInfo(newLoc);
                        if (info.isWater() && rc.getCrumbs() >= Constants.CRUMBS_MIN_FOR_FILLING) {
                            if (rc.canFill(newLoc)) {
                                rc.fill(newLoc);
                                // note that flag carrier cannot fill!
                                return null;
                            }
                        }
                        if (rc.canMove(dir)) {
                            // Debug.println("Moving in dir: " + dir, id);
                            return dir;
                        } else if (rc.senseRobotAtLocation(newLoc) != null) {
                            if (FastMath.rand256() % 8 == 0) {
                                // wait with some prob to avoid dead looping with friendly robot
                                return null;
                            }
                        }
                    }

                    if (!rc.onTheMap(newLoc)) {
                        rotateRight = !rotateRight;
                    } else if (!rc.sensePassability(newLoc)) {
                        // This is the latest obstacle found if
                        // - I can't move there
                        // - It's on the map
                        // - It's not passable
                        lastObstacleFound = newLoc;
                        // Debug.println("Found obstacle: " + lastObstacleFound, id);

                        // We assume that if we're avoiding a current and we hit
                        // an obstacle, we should rotate the other way since
                        // the other way is probably open.

                        // If this happens twice, we don't try to switch rotation.
                        // This would be a problem if the devs make a map like
                        // XXXXXX
                        // ---<--
                        // --O<--
                        // ---<--
                        // XXXXXX
                        if (shouldGuessRotation) {
                            // Debug.println("Inferring rot dir around: " + lastObstacleFound, id);
//                            if (MapTracker.canInferRotationAroundObstacle(lastObstacleFound)) {
//                                shouldGuessRotation = false;
//                                if (!MapTracker.isAdjacentToPOI(myLoc, lastObstacleFound)) {
//                                    rotateRight = MapTracker.shouldRotateRightAroundObstacle(
//                                            lastObstacleFound, myLoc, target);
//                                }
//                                // Debug.println("Inferred: " + rotateRight, id);
//                            }
                        } else if (shouldGuessRotation) {
                            // Guessing rotation not on an obstacle is different.
                            shouldGuessRotation = false;
                            // Debug.println("Guessing rot dir", id);
                            // Rotate left and right and find the first dir that you can move in
                            Direction dirL = dir;
                            for (int j = 8; j-- > 0;) {
                                if (rc.canMove(dirL))
                                    break;
                                dirL = dirL.rotateLeft();
                            }

                            Direction dirR = dir;
                            for (int j = 8; j-- > 0;) {
                                if (rc.canMove(dirR))
                                    break;
                                dirR = dirR.rotateRight();
                            }

                            // Check which results in a location closer to the target
                            MapLocation locL = myLoc.add(dirL);
                            MapLocation locR = myLoc.add(dirR);

                            int lDist = Util.distance(target, locL);
                            int rDist = Util.distance(target, locR);
                            int lDistSq = target.distanceSquaredTo(locL);
                            int rDistSq = target.distanceSquaredTo(locR);

                            if (lDist < rDist) {
                                rotateRight = false;
                            } else if (rDist < lDist) {
                                rotateRight = true;
                            } else {
                                rotateRight = rDistSq < lDistSq;
                            }

                            // Debug.println("Guessed: " + rotateRight, id);
                        }
                    }

                    if (rotateRight)
                        dir = dir.rotateRight();
                    else
                        dir = dir.rotateLeft();
                }

                if (rc.canMove(dir)) {
                    return dir;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Debug.println("Last exit", id);
            return null;
        }

        // clear some of the previous data
        static void resetPathfinding() {
            // Debug.println("Resetting pathfinding", id);
            lastObstacleFound = null;
            minDistToEnemy = INF;
            visited.clear();
            shouldGuessRotation = true;
            hasRotatedAvoidingCurrent = false;
        }

        static int getCode() {
            int x = rc.getLocation().x;
            int y = rc.getLocation().y;
            Direction obstacleDir = rc.getLocation().directionTo(target);
            if (lastObstacleFound != null)
                obstacleDir = rc.getLocation().directionTo(lastObstacleFound);
            int bit = rotateRight ? 1 : 0;
            return (((((x << 6) | y) << 4) | obstacleDir.ordinal()) << 1) | bit;
        }
    }
}
