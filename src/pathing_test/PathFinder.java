package pathing_test;

import battlecode.common.*;
import pathing_test.fast.FastIntSet;
import pathing_test.fast.FastMath;

class DirectionStack {
    static int STACK_SIZE = 60;
    int size = 0;
    Direction[] dirs = new Direction[STACK_SIZE];

    final void clear() {
        size = 0;
    }

    final void push(Direction d) {
        dirs[size++] = d;
    }

    final Direction top() {
        return dirs[size - 1];
    }

    /**
     * Returns the top n element of the stack
     * @param n
     * @return
     */
    final Direction top(int n) {
        return dirs[size - n];
    }

    final void pop() {
        size--;
    }

    final void pop(int n) {
        size -= n;
    }
}

public class PathFinder {
    private static RobotController rc;
    private static MapLocation target = null;

    public static void init(RobotController r) {
        rc = r;
    }

    public static void move(MapLocation loc) {
        if (!rc.isMovementReady())
            return;
        target = loc;
        BugNav.move();
    }

    static class BugNav {
        static DirectionStack dirStack = new DirectionStack();
        static MapLocation prevTarget = null; // previous target
        static int currentTurnDir = 0;
        static int MAX_DEPTH = 15;

        static Direction turn(Direction dir) {
            return currentTurnDir == 0 ? dir.rotateLeft() : dir.rotateRight();
        }

        static Direction turn(Direction dir, int turnDir) {
            return turnDir == 0 ? dir.rotateLeft() : dir.rotateRight();
        }

        static void move() {
            try {
                // different target? ==> previous data does not help!
                if (prevTarget == null || target.distanceSquaredTo(prevTarget) > 0) {
                    // Debug.println("New target: " + target, id);
                    Debug.println("New target");
                    resetPathfinding();
                }
                prevTarget = target;
                if (dirStack.size == 0) {
                    Direction dir = rc.getLocation().directionTo(target);
                    // TODO: does not use the canPass nonsense in Gone Fishin
                    if (rc.canMove(dir)) {
                        rc.move(dir);
                        return;
                    }
                    Direction dirL = dir.rotateLeft();
                    if (rc.canMove(dirL)) {
                        rc.move(dirL);
                        return;
                    }
                    Direction dirR = dir.rotateRight();
                    if (rc.canMove(dirR)) {
                        rc.move(dirR);
                        return;
                    }
                    currentTurnDir = getTurnDir(dir);
                    // obstacle encountered, rotate and add new dirs to stack
                    while (!rc.canMove(dir) && dirStack.size < 8) {
                        if (!rc.onTheMap(rc.getLocation().add(dir))) {
                            currentTurnDir ^= 1;
                            dirStack.clear();
                            return; // do not move
                        }
                        dirStack.push(dir);
                        dir = turn(dir);
                    }
                    if (dirStack.size == 8) {
                        Debug.println("blocked");
                    }
                    else {
                        rc.move(dir);
                    }
                }
                else {
                    // TODO: don't understand this (why pop 2?)
                    if (dirStack.size > 1 && rc.canMove(dirStack.top(2))) {
                        dirStack.pop(2);
                    }
                    while (dirStack.size > 0 && rc.canMove(dirStack.top())) {
                        dirStack.pop();
                    }
                    if (dirStack.size == 0) {
                        Direction dir = rc.getLocation().directionTo(target);
                        if (!rc.canMove(dir)) {
                            dirStack.push(dir);
                        }
                        else {
                            rc.move(dir);
                            return;
                        }
                    }
                    // keep rotating and adding things to the stack
                    Direction curDir;
                    int stackSizeLimit = Math.min(DirectionStack.STACK_SIZE, dirStack.size + 8);
                    while (dirStack.size > 0 && !rc.canMove(curDir = turn(dirStack.top()))) {
                        if (!rc.onTheMap(rc.getLocation().add(curDir))) {
                            currentTurnDir ^= 1;
                            dirStack.clear();
                            return; // do not move
                        }
                        dirStack.push(curDir);
                        if (dirStack.size == stackSizeLimit) {
                            dirStack.clear();
                            return;
                        }
                    }
                    Direction moveDir = dirStack.size == 0 ? dirStack.dirs[0] : turn(dirStack.top());
                    if (rc.canMove(moveDir)) {
                        rc.move(moveDir);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        static int simulate(int turnDir, Direction dir) throws GameActionException {
            MapLocation now = rc.getLocation();
            DirectionStack dirStack = new DirectionStack();
            while (!canPass(now.add(dir), dir) && dirStack.size < 8) {
                dirStack.push(dir);
                dir = turn(dir, turnDir);
            }
            now = now.add(dir);

            int ans = 0;
            while (dirStack.size > 0) {
                ans++;
                if (ans > MAX_DEPTH) {
                    break;
                }
                while (dirStack.size > 0 && canPass(now.add(dirStack.top()), dirStack.top())) {
                    dirStack.pop();
                }
                // is reference code correct?
                if (dirStack.size > 1 && canPass(now.add(dirStack.top()), dirStack.top(2))) {
                    dirStack.pop(2);
                }

                Direction curDir;
                while (dirStack.size > 0 && !canPass(now.add(curDir = turn(dirStack.top(), turnDir)), curDir)) {
                    dirStack.push(curDir);
                    if (dirStack.size > 8) {
                        return -1;
                    }
                }
                if (dirStack.size > 8 || dirStack.size == 0) {
                    break;
                }
                Direction moveDir = dirStack.size == 0 ? dirStack.dirs[0] : turn(dirStack.top());
                now = now.add(moveDir);
            }
            return ans + Util.distance(now, target);
        }

        static int getTurnDir(Direction dir) throws GameActionException {
            int ansL = simulate(0, dir);
            int ansR = simulate(1, dir);
            if (ansL == -1 || ansR == -1) return FastMath.rand256() % 2;
            if (ansL <= ansR) {
                return 0;
            }
            else {
                return 1;
            }
        }

        // clear some of the previous data
        static void resetPathfinding() {
            dirStack.clear();
        }

        // TODO: YUXUAN
        static boolean canPass(MapLocation loc, Direction targetDir) throws GameActionException {
            if (loc.equals(rc.getLocation())) return true;
            // TODO: YUXUAN IMPL MAPRECORDR CHECK
            if (!MapRecorder.check(loc.add(targetDir))) return false;
            // TODO: YUXUAN CHECK IF SQRT 20
            return rc.sensePassability(loc.add(targetDir));
        }
    }
}
