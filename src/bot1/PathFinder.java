package bot1;

import battlecode.common.*;
import bot1.fast.*;
import scala.collection.immutable.Stream;

public class PathFinder extends Robot {
    private static MapLocation target = null;
    private static int stuckCnt;
    private static MapLocation myLastLoc;

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

    static public void escort(int flagid) throws GameActionException {
        /* To stay 1 tile away from the escorted duck to prevent congestion */
        Debug.printString(Debug.PATHFINDING, String.format("escort%d", flagid));
        MapLocation carrierLoc = Util.int2loc(Comms.readOppflagsLoc(flagid));
        MapLocation escortLoc = Util.int2loc(Comms.readOppflagsLoc(flagid));
        if (!rc.isMovementReady())
            return;
        // if I am right next to the escorted, move away to make room
        // otherwize, try path to a direction behind the escorted, unless I am gonna be adjacent, then I stop
        if (rc.getLocation().isAdjacentTo(carrierLoc)) {
            tryMoveDir(rc.getLocation().directionTo(carrierLoc).opposite());
            return;
        }
        if (escortLoc != null) {
            target = escortLoc;
        } else {
            MapLocation closestHome = Util.getClosestLoc(carrierLoc, Robot.mySpawnCenters);
            Direction protectDir = carrierLoc.directionTo(closestHome).opposite();
            target = carrierLoc.add(protectDir).add(protectDir);
        }
        Direction dir = BugNav.getMoveDir();
        if (dir != null) {
            if(rc.getLocation().add(dir).isAdjacentTo(carrierLoc)) {
                if (rc.canFill(rc.getLocation().add(dir))) {
                    rc.fill(rc.getLocation().add(dir));
                }
            } else {
                tryMove(dir);
            }
        }
    }

    static public void move(MapLocation loc) throws GameActionException {
        if (!rc.isMovementReady() || loc == null)
            return;
        target = loc;
        Direction dir = BugNav.getMoveDir();
        if (dir == null)
            return;
        Debug.printString(Debug.PATHFINDING, String.format("move%s", loc.toString()));
        tryMove(dir);
    }

    static class BugNav {
        static DirectionStack dirStack = new DirectionStack();
        static MapLocation prevTarget = null; // previous target
        static int currentTurnDir = 0;
        static final int MAX_DEPTH = 20;
        static final int BYTECODE_CUTOFF = 6000;

        static Direction turn(Direction dir) {
            return currentTurnDir == 0 ? dir.rotateLeft() : dir.rotateRight();
        }

        static Direction turn(Direction dir, int turnDir) {
            return turnDir == 0 ? dir.rotateLeft() : dir.rotateRight();
        }

        static Direction getMoveDir() throws GameActionException {
            // different target? ==> previous data does not help!
            if (prevTarget == null || target.distanceSquaredTo(prevTarget) > 0) {
                resetPathfinding();
            }
            prevTarget = target;
            if (myLastLoc == rc.getLocation()) {
                stuckCnt++;
            }
            myLastLoc = rc.getLocation();
            if (dirStack.size == 0) {
                Direction dir = rc.getLocation().directionTo(target);
                if (canMoveOrFill(dir)) {
                    return dir;
                }
                Direction dirL = dir.rotateLeft();
                if (canMoveOrFill(dirL)) {
                    return dirL;
                }
                Direction dirR = dir.rotateRight();
                if (canMoveOrFill(dirR)) {
                    return dirR;
                }
                currentTurnDir = getTurnDir(dir);
                // obstacle encountered, rotate and add new dirs to stack
                while (!canMoveOrFill(dir) && dirStack.size < 8) {
                    if (!rc.onTheMap(rc.getLocation().add(dir))) {
                        currentTurnDir ^= 1;
                        dirStack.clear();
                        return null; // do not move
                    }
                    dirStack.push(dir);
                    dir = turn(dir);
                }
                if (dirStack.size != 8) {
                    return dir;
                }
            }
            else {
                // TODO: don't understand this (why pop 2?)
                // dxx
                // xo
                // x
                // suppose you are at o, x is wall, and d is another duck, you are pathing left and bugging up rn
                // and the duck moves away, you wanna take its spot
                if (dirStack.size > 1 && canMoveOrFill(dirStack.top(2))) {
                    dirStack.pop(2);
                }
                while (dirStack.size > 0 && canMoveOrFill(dirStack.top())) {
                    dirStack.pop();
                }
                if (dirStack.size == 0) {
                    Direction dir = rc.getLocation().directionTo(target);
                    if (canMoveOrFill(dir)) {
                        return dir;
                    }
                    dirStack.push(dir);
                }
                // keep rotating and adding things to the stack
                Direction curDir;
                int stackSizeLimit = Math.min(DirectionStack.STACK_SIZE, dirStack.size + 8);
                while (dirStack.size > 0 && !canMoveOrFill(curDir = turn(dirStack.top()))) {
                    if (!rc.onTheMap(rc.getLocation().add(curDir))) {
                        currentTurnDir ^= 1;
                        dirStack.clear();
                        return null; // do not move
                    }
                    dirStack.push(curDir);
                    if (dirStack.size == stackSizeLimit) {
                        dirStack.clear();
                        return null;
                    }
                }
                Direction moveDir = dirStack.size == 0 ? dirStack.dirs[0] : turn(dirStack.top());
                if (canMoveOrFill(moveDir)) {
                    return moveDir;
                }
            }
            return null;
        }

        static int simulate(int turnDir, Direction dir) throws GameActionException {
            MapLocation now = rc.getLocation();
            DirectionStack dirStack = new DirectionStack();
            while (!canPass(now, dir) && dirStack.size < 8) {
                dirStack.push(dir);
                dir = turn(dir, turnDir);
            }
            now = now.add(dir);
            int ans = 1;

            while (!now.isAdjacentTo(target)) {
                if (ans > MAX_DEPTH || Clock.getBytecodesLeft() < BYTECODE_CUTOFF) {
                    break;
                }
                Direction moveDir = now.directionTo(target);
                if (dirStack.size == 0) {
                    if (!canPass(now, moveDir)) {
                        // obstacle encountered, rotate and add new dirs to stack
                        while (!canPass(now, moveDir) && dirStack.size < 8) {
                            dirStack.push(moveDir);
                            moveDir = turn(moveDir, turnDir);
                        }
                    }
                } else {
                    if (dirStack.size > 1 && canPass(now, dirStack.top(2))) {
                        dirStack.pop(2);
                    }
                    while (dirStack.size > 0 && canPass(now, dirStack.top())) {
                        dirStack.pop();
                    }

                    while (dirStack.size > 0 && !canPass(now, turn(dirStack.top(), turnDir))) {
                        dirStack.push(turn(dirStack.top(), turnDir));
                        if (dirStack.size > 8) {
                            return -1;
                        }
                    }
                    moveDir = dirStack.size == 0 ? dirStack.dirs[0] : turn(dirStack.top(), turnDir);
                }
                now = now.add(moveDir);
                ans++;
            }
            Debug.setIndicatorDot(Debug.PATHFINDING, now, turnDir == 0? 255 : 0, 0, turnDir == 0? 0 : 255);
            return ans + Util.distance(now, target);
        }

        static int getTurnDir(Direction dir) throws GameActionException {
            MapLocation loc = rc.getLocation().add(dir);
//            if (rc.canSenseLocation(loc) && rc.senseRobotAtLocation(loc) != null)
//                return FastMath.rand256() % 2;
            Debug.bytecodeDebug += "  turnDir=" + Clock.getBytecodeNum();
            int ansL = simulate(0, dir);
            int ansR = simulate(1, dir);
            Debug.bytecodeDebug += "  turnDir=" + Clock.getBytecodeNum();
            Debug.printString(Debug.PATHFINDING, String.format("t%d|%d", ansL, ansR));
            if (ansL == -1 || ansR == -1 || ansL == ansR) return FastMath.rand256() % 2;
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
            stuckCnt = 0;
            myLastLoc = null;
        }

        static boolean canMoveOrFill(Direction dir) throws GameActionException {
            MapLocation loc = rc.getLocation().add(dir);
            if (rc.canMove(dir)) {
                // it is not ok to move onto a tile to block a teammate's movement
                for (int i = Cache.nearbyFriends.length; --i >= 0;) {
                    RobotInfo ally = Cache.nearbyFriends[i];
                    if (!ally.location.isAdjacentTo(loc))
                        continue;
                    boolean ok = false;
                    for (Direction d: Constants.MOVEABLE_DIRECTIONS) {
                        MapLocation a = ally.location.add(d);
                        if (rc.canSenseLocation(a) && rc.sensePassability(a) && (rc.senseRobotAtLocation(a) == null || a.equals(rc.getLocation())) && !loc.equals(a)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        Debug.setIndicatorDot(Debug.MICRO, rc.getLocation().add(dir), 255, 0, 0);
                        return false;
                    }
                }
                return true;
            }
            if (!rc.canSenseLocation(loc))
                return false;
            if (rc.hasFlag()) {
                if (rc.senseRobotAtLocation(loc) != null)
                    return stuckCnt < 10;
                return false;
            }
            MapInfo info = rc.senseMapInfo(loc);
            if (info.isDam())
                return true;
            if (info.isWater()) {
                if (info.getCrumbs() > 0)
                    return true;
                if (rc.canMove(dir.rotateLeft()) || rc.canMove(dir.rotateRight()))
                    return false;
                if (rc.getCrumbs() < 200 || rc.getRoundNum() <= 150)
                    return false;
                int wall_cnt = 0;
                boolean lastWall = false;
                for (Direction d : Constants.MOVEABLE_DIRECTIONS) {
                    // technically this doesn't wrap around... future TODO
                    MapLocation adjLoc = loc.add(d);
                    if (!rc.canSenseLocation(adjLoc) || rc.senseMapInfo(adjLoc).isWall()) {
                        if (!lastWall) {
                            wall_cnt++;
                        }
                    } else {
                        lastWall = false;
                    }
                }
                // We must be allowed to remove water if it is adjacent to two non-connected walls
                if (wall_cnt > 1)
                    return true;
                MapLocation N = loc.add(Direction.NORTH);
                MapLocation E = loc.add(Direction.EAST);
                MapLocation S = loc.add(Direction.SOUTH);
                MapLocation W = loc.add(Direction.WEST);
                boolean canN = rc.canSenseLocation(N) && rc.sensePassability(N);
                boolean canE = rc.canSenseLocation(E) && rc.sensePassability(E);
                boolean canS = rc.canSenseLocation(S) && rc.sensePassability(S);
                boolean canW = rc.canSenseLocation(W) && rc.sensePassability(W);
                // we must be allowed to remove water
                if (!canN && !canS) return true;
                if (!canE && !canW) return true;
                return false;
            }
            return false;
        }

        static boolean canPass(MapLocation loc, Direction targetDir) throws GameActionException {
            MapLocation newLoc = loc.add(targetDir);
            if (!rc.onTheMap(newLoc))
                return false;
            if (rc.canSenseLocation(newLoc)) {
                if (rc.hasFlag()) {
                    return rc.sensePassability(newLoc);
                } else {
                    return !rc.senseMapInfo(newLoc).isWall();
                }
            } else {
                return MapRecorder.getPassible(newLoc);
            }
        }
    }
}

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
