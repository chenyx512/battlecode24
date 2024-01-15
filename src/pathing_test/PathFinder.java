package pathing_test;

import battlecode.common.*;
import pathing_test.fast.FastIntSet;

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

        static Direction turn(Direction dir) {
            return currentTurnDir == 0 ? dir.rotateLeft() : dir.rotateRight();
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
                    // TODO: add turn dir function call here
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

        // clear some of the previous data
        static void resetPathfinding() {
            dirStack.clear();
        }
    }
}
