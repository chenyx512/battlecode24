// shamelessly copied from 4 musketeer https://github.com/maxwelljones14/BattleCode2023/blob/main/src/MPWorking/Debug.java
package microbot_39b0c75;

import battlecode.common.*;

public class Debug extends RobotPlayer {
    static final boolean VERBOSE = true;
    public static final boolean INFO = true;
    public static final boolean PATHFINDING = true;
    public static final boolean INDICATORS = true;
    public static final boolean ASSIGNMENT = true;

    public static String bytecodeDebug = new String();

    private static StringBuilder sb;

    static void init() {
        sb = new StringBuilder();
    }

    static void flush() {
        rc.setIndicatorString(sb.toString());
        sb = new StringBuilder();
    }

    public static void printString(boolean cond, String s) {
        if (VERBOSE && cond) {
            sb.append(s);
            sb.append(", ");
        }
    }

    public static void printString(String s) {
        Debug.printString(Debug.INFO, s);
    }

    public static void failFast(GameActionException ex) {
        if (Constants.DEBUG_FAIL_FAST) {
            throw new IllegalStateException(ex);
        }
    }

    public static void failFast(String message) {
        if (Constants.DEBUG_FAIL_FAST) {
            throw new IllegalStateException(message);
        }
    }

    public static void betterAssert(boolean cond, String msg) {
        if (!cond) {
            failFast(msg);
        }
    }

    public static void println(boolean cond, String s) {
        if (VERBOSE && cond) {
            System.out.println(s);
        }
    }

    public static void println(boolean cond, String s, int id) {
        if (VERBOSE && cond && (id < 0 || rc.getID() == id)) {
            System.out.println(s);
        }
    }

    public static void println(String s) {
        Debug.println(Debug.INFO, s);
    }

    public static void println(String s, int id) {
        Debug.println(Debug.INFO, s, id);
    }

    public static void print(boolean cond, String s) {
        if (VERBOSE && cond) {
            System.out.print(s);
        }
    }

    public static void setIndicatorDot(boolean cond, MapLocation loc, int r, int g, int b) {
        if (VERBOSE && INDICATORS && cond && loc != null) {
            rc.setIndicatorDot(loc, r, g, b);
        }
    }

    public static void setIndicatorLine(boolean cond, MapLocation startLoc, MapLocation endLoc, int r, int g, int b) {
        if (VERBOSE && INDICATORS && cond && startLoc != null && endLoc != null) {
            rc.setIndicatorLine(startLoc, endLoc, r, g, b);
        }
    }

    public static void setIndicatorDot(MapLocation loc, int r, int g, int b) {
        setIndicatorDot(INDICATORS, loc, r, g, b);
    }

    public static void setIndicatorLine(MapLocation startLoc, MapLocation endLoc, int r, int g, int b) {
        setIndicatorLine(INDICATORS, startLoc, endLoc, r, g, b);
    }
}
