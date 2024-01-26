package bfs.bfs;

import battlecode.common.*;

public class BFS10 {

    public static RobotController rc;

    static MapLocation l94; // location representing relative coordinate (-3, -1)
    static double d94; // shortest distance to location from current location
    // static Direction dir94; // best direction to take now to optimally get to location
    static double score94; // heuristic distance from location to target

    static MapLocation l109; // location representing relative coordinate (-3, 0)
    static double d109; // shortest distance to location from current location
    // static Direction dir109; // best direction to take now to optimally get to location
    static double score109; // heuristic distance from location to target

    static MapLocation l124; // location representing relative coordinate (-3, 1)
    static double d124; // shortest distance to location from current location
    // static Direction dir124; // best direction to take now to optimally get to location
    static double score124; // heuristic distance from location to target

    static MapLocation l80; // location representing relative coordinate (-2, -2)
    static double d80; // shortest distance to location from current location
    // static Direction dir80; // best direction to take now to optimally get to location
    static double score80; // heuristic distance from location to target

    static MapLocation l95; // location representing relative coordinate (-2, -1)
    static double d95; // shortest distance to location from current location
    // static Direction dir95; // best direction to take now to optimally get to location
    static double score95; // heuristic distance from location to target

    static MapLocation l110; // location representing relative coordinate (-2, 0)
    static double d110; // shortest distance to location from current location
    // static Direction dir110; // best direction to take now to optimally get to location
    static double score110; // heuristic distance from location to target

    static MapLocation l125; // location representing relative coordinate (-2, 1)
    static double d125; // shortest distance to location from current location
    // static Direction dir125; // best direction to take now to optimally get to location
    static double score125; // heuristic distance from location to target

    static MapLocation l140; // location representing relative coordinate (-2, 2)
    static double d140; // shortest distance to location from current location
    // static Direction dir140; // best direction to take now to optimally get to location
    static double score140; // heuristic distance from location to target

    static MapLocation l66; // location representing relative coordinate (-1, -3)
    static double d66; // shortest distance to location from current location
    // static Direction dir66; // best direction to take now to optimally get to location
    static double score66; // heuristic distance from location to target

    static MapLocation l81; // location representing relative coordinate (-1, -2)
    static double d81; // shortest distance to location from current location
    // static Direction dir81; // best direction to take now to optimally get to location
    static double score81; // heuristic distance from location to target

    static MapLocation l96; // location representing relative coordinate (-1, -1)
    static double d96; // shortest distance to location from current location
    // static Direction dir96; // best direction to take now to optimally get to location
    static double score96; // heuristic distance from location to target

    static MapLocation l111; // location representing relative coordinate (-1, 0)
    static double d111; // shortest distance to location from current location
    // static Direction dir111; // best direction to take now to optimally get to location
    static double score111; // heuristic distance from location to target

    static MapLocation l126; // location representing relative coordinate (-1, 1)
    static double d126; // shortest distance to location from current location
    // static Direction dir126; // best direction to take now to optimally get to location
    static double score126; // heuristic distance from location to target

    static MapLocation l141; // location representing relative coordinate (-1, 2)
    static double d141; // shortest distance to location from current location
    // static Direction dir141; // best direction to take now to optimally get to location
    static double score141; // heuristic distance from location to target

    static MapLocation l156; // location representing relative coordinate (-1, 3)
    static double d156; // shortest distance to location from current location
    // static Direction dir156; // best direction to take now to optimally get to location
    static double score156; // heuristic distance from location to target

    static MapLocation l67; // location representing relative coordinate (0, -3)
    static double d67; // shortest distance to location from current location
    // static Direction dir67; // best direction to take now to optimally get to location
    static double score67; // heuristic distance from location to target

    static MapLocation l82; // location representing relative coordinate (0, -2)
    static double d82; // shortest distance to location from current location
    // static Direction dir82; // best direction to take now to optimally get to location
    static double score82; // heuristic distance from location to target

    static MapLocation l97; // location representing relative coordinate (0, -1)
    static double d97; // shortest distance to location from current location
    // static Direction dir97; // best direction to take now to optimally get to location
    static double score97; // heuristic distance from location to target

    static MapLocation l112; // location representing relative coordinate (0, 0)
    static double d112; // shortest distance to location from current location
    // static Direction dir112; // best direction to take now to optimally get to location
    static double score112; // heuristic distance from location to target

    static MapLocation l127; // location representing relative coordinate (0, 1)
    static double d127; // shortest distance to location from current location
    // static Direction dir127; // best direction to take now to optimally get to location
    static double score127; // heuristic distance from location to target

    static MapLocation l142; // location representing relative coordinate (0, 2)
    static double d142; // shortest distance to location from current location
    // static Direction dir142; // best direction to take now to optimally get to location
    static double score142; // heuristic distance from location to target

    static MapLocation l157; // location representing relative coordinate (0, 3)
    static double d157; // shortest distance to location from current location
    // static Direction dir157; // best direction to take now to optimally get to location
    static double score157; // heuristic distance from location to target

    static MapLocation l68; // location representing relative coordinate (1, -3)
    static double d68; // shortest distance to location from current location
    // static Direction dir68; // best direction to take now to optimally get to location
    static double score68; // heuristic distance from location to target

    static MapLocation l83; // location representing relative coordinate (1, -2)
    static double d83; // shortest distance to location from current location
    // static Direction dir83; // best direction to take now to optimally get to location
    static double score83; // heuristic distance from location to target

    static MapLocation l98; // location representing relative coordinate (1, -1)
    static double d98; // shortest distance to location from current location
    // static Direction dir98; // best direction to take now to optimally get to location
    static double score98; // heuristic distance from location to target

    static MapLocation l113; // location representing relative coordinate (1, 0)
    static double d113; // shortest distance to location from current location
    // static Direction dir113; // best direction to take now to optimally get to location
    static double score113; // heuristic distance from location to target

    static MapLocation l128; // location representing relative coordinate (1, 1)
    static double d128; // shortest distance to location from current location
    // static Direction dir128; // best direction to take now to optimally get to location
    static double score128; // heuristic distance from location to target

    static MapLocation l143; // location representing relative coordinate (1, 2)
    static double d143; // shortest distance to location from current location
    // static Direction dir143; // best direction to take now to optimally get to location
    static double score143; // heuristic distance from location to target

    static MapLocation l158; // location representing relative coordinate (1, 3)
    static double d158; // shortest distance to location from current location
    // static Direction dir158; // best direction to take now to optimally get to location
    static double score158; // heuristic distance from location to target

    static MapLocation l84; // location representing relative coordinate (2, -2)
    static double d84; // shortest distance to location from current location
    // static Direction dir84; // best direction to take now to optimally get to location
    static double score84; // heuristic distance from location to target

    static MapLocation l99; // location representing relative coordinate (2, -1)
    static double d99; // shortest distance to location from current location
    // static Direction dir99; // best direction to take now to optimally get to location
    static double score99; // heuristic distance from location to target

    static MapLocation l114; // location representing relative coordinate (2, 0)
    static double d114; // shortest distance to location from current location
    // static Direction dir114; // best direction to take now to optimally get to location
    static double score114; // heuristic distance from location to target

    static MapLocation l129; // location representing relative coordinate (2, 1)
    static double d129; // shortest distance to location from current location
    // static Direction dir129; // best direction to take now to optimally get to location
    static double score129; // heuristic distance from location to target

    static MapLocation l144; // location representing relative coordinate (2, 2)
    static double d144; // shortest distance to location from current location
    // static Direction dir144; // best direction to take now to optimally get to location
    static double score144; // heuristic distance from location to target

    static MapLocation l100; // location representing relative coordinate (3, -1)
    static double d100; // shortest distance to location from current location
    // static Direction dir100; // best direction to take now to optimally get to location
    static double score100; // heuristic distance from location to target

    static MapLocation l115; // location representing relative coordinate (3, 0)
    static double d115; // shortest distance to location from current location
    // static Direction dir115; // best direction to take now to optimally get to location
    static double score115; // heuristic distance from location to target

    static MapLocation l130; // location representing relative coordinate (3, 1)
    static double d130; // shortest distance to location from current location
    // static Direction dir130; // best direction to take now to optimally get to location
    static double score130; // heuristic distance from location to target


    public static void init(RobotController r) {
        rc = r;
        team = rc.getTeam();
    }

    private static final Direction[] DIRECTIONS = new Direction[] {null, Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHWEST, Direction.SOUTHEAST, Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH};

    public final static Direction NORTH = Direction.NORTH;
    public final static Direction NORTHEAST = Direction.NORTHEAST;
    public final static Direction EAST = Direction.EAST;
    public final static Direction SOUTHEAST = Direction.SOUTHEAST;
    public final static Direction SOUTH = Direction.SOUTH;
    public final static Direction SOUTHWEST = Direction.SOUTHWEST;
    public final static Direction WEST = Direction.WEST;
    public final static Direction NORTHWEST = Direction.NORTHWEST;
    public final static Direction CENTER = Direction.CENTER;

    public static MapInfo mapInfo;
    public static Direction currentDir;
    public static Team team;
    public static double ans;
    public static double bestScore;
    public static double currDist;

    public static Direction direction(double dist) {
        if (dist==Double.POSITIVE_INFINITY) {
            return null;
        }
        return DIRECTIONS[(int)(dist * 16 % 16)];
    }

    public static Direction bestDir(MapLocation target) throws GameActionException {

        l112 = rc.getLocation();
        d112 = 0;
        // dir112 = CENTER;

        l111 = l112.add(WEST); // (-1, 0) from (0, 0)
        d111 = 99999;
        // dir111 = null;

        l97 = l112.add(SOUTH); // (0, -1) from (0, 0)
        d97 = 99999;
        // dir97 = null;

        l127 = l112.add(NORTH); // (0, 1) from (0, 0)
        d127 = 99999;
        // dir127 = null;

        l113 = l112.add(EAST); // (1, 0) from (0, 0)
        d113 = 99999;
        // dir113 = null;

        l96 = l112.add(SOUTHWEST); // (-1, -1) from (0, 0)
        d96 = 99999;
        // dir96 = null;

        l126 = l112.add(NORTHWEST); // (-1, 1) from (0, 0)
        d126 = 99999;
        // dir126 = null;

        l98 = l112.add(SOUTHEAST); // (1, -1) from (0, 0)
        d98 = 99999;
        // dir98 = null;

        l128 = l112.add(NORTHEAST); // (1, 1) from (0, 0)
        d128 = 99999;
        // dir128 = null;

        l110 = l111.add(WEST); // (-2, 0) from (-1, 0)
        d110 = 99999;
        // dir110 = null;

        l82 = l97.add(SOUTH); // (0, -2) from (0, -1)
        d82 = 99999;
        // dir82 = null;

        l142 = l127.add(NORTH); // (0, 2) from (0, 1)
        d142 = 99999;
        // dir142 = null;

        l114 = l113.add(EAST); // (2, 0) from (1, 0)
        d114 = 99999;
        // dir114 = null;

        l95 = l111.add(SOUTHWEST); // (-2, -1) from (-1, 0)
        d95 = 99999;
        // dir95 = null;

        l125 = l111.add(NORTHWEST); // (-2, 1) from (-1, 0)
        d125 = 99999;
        // dir125 = null;

        l81 = l97.add(SOUTHWEST); // (-1, -2) from (0, -1)
        d81 = 99999;
        // dir81 = null;

        l141 = l127.add(NORTHWEST); // (-1, 2) from (0, 1)
        d141 = 99999;
        // dir141 = null;

        l83 = l97.add(SOUTHEAST); // (1, -2) from (0, -1)
        d83 = 99999;
        // dir83 = null;

        l143 = l127.add(NORTHEAST); // (1, 2) from (0, 1)
        d143 = 99999;
        // dir143 = null;

        l99 = l113.add(SOUTHEAST); // (2, -1) from (1, 0)
        d99 = 99999;
        // dir99 = null;

        l129 = l113.add(NORTHEAST); // (2, 1) from (1, 0)
        d129 = 99999;
        // dir129 = null;

        l80 = l96.add(SOUTHWEST); // (-2, -2) from (-1, -1)
        d80 = 99999;
        // dir80 = null;

        l140 = l126.add(NORTHWEST); // (-2, 2) from (-1, 1)
        d140 = 99999;
        // dir140 = null;

        l84 = l98.add(SOUTHEAST); // (2, -2) from (1, -1)
        d84 = 99999;
        // dir84 = null;

        l144 = l128.add(NORTHEAST); // (2, 2) from (1, 1)
        d144 = 99999;
        // dir144 = null;

        l109 = l110.add(WEST); // (-3, 0) from (-2, 0)
        d109 = 99999;
        // dir109 = null;

        l67 = l82.add(SOUTH); // (0, -3) from (0, -2)
        d67 = 99999;
        // dir67 = null;

        l157 = l142.add(NORTH); // (0, 3) from (0, 2)
        d157 = 99999;
        // dir157 = null;

        l115 = l114.add(EAST); // (3, 0) from (2, 0)
        d115 = 99999;
        // dir115 = null;

        l94 = l110.add(SOUTHWEST); // (-3, -1) from (-2, 0)
        d94 = 99999;
        // dir94 = null;

        l124 = l110.add(NORTHWEST); // (-3, 1) from (-2, 0)
        d124 = 99999;
        // dir124 = null;

        l66 = l82.add(SOUTHWEST); // (-1, -3) from (0, -2)
        d66 = 99999;
        // dir66 = null;

        l156 = l142.add(NORTHWEST); // (-1, 3) from (0, 2)
        d156 = 99999;
        // dir156 = null;

        l68 = l82.add(SOUTHEAST); // (1, -3) from (0, -2)
        d68 = 99999;
        // dir68 = null;

        l158 = l142.add(NORTHEAST); // (1, 3) from (0, 2)
        d158 = 99999;
        // dir158 = null;

        l100 = l114.add(SOUTHEAST); // (3, -1) from (2, 0)
        d100 = 99999;
        // dir100 = null;

        l130 = l114.add(NORTHEAST); // (3, 1) from (2, 0)
        d130 = 99999;
        // dir130 = null;



        if (rc.onTheMap(l111)) { // check (-1, 0)
            if (rc.canSenseLocation(l111) && rc.sensePassability(l111)) { 
                if (d111 > d112) { // from (0, 0)
                    d111 = 0.4375;
                }
                d111 += 1;
            }
        }

        if (rc.onTheMap(l97)) { // check (0, -1)
            if (rc.canSenseLocation(l97) && rc.sensePassability(l97)) { 
                if (d97 > d112) { // from (0, 0)
                    d97 = 0.5;
                }
                if (d97 > d111) { // from (-1, 0)
                    d97 = d111;
                }
                d97 += 1;
            }
        }

        if (rc.onTheMap(l127)) { // check (0, 1)
            if (rc.canSenseLocation(l127) && rc.sensePassability(l127)) { 
                if (d127 > d112) { // from (0, 0)
                    d127 = 0.375;
                }
                if (d127 > d111) { // from (-1, 0)
                    d127 = d111;
                }
                d127 += 1;
            }
        }

        if (rc.onTheMap(l113)) { // check (1, 0)
            if (rc.canSenseLocation(l113) && rc.sensePassability(l113)) { 
                if (d113 > d112) { // from (0, 0)
                    d113 = 0.3125;
                }
                if (d113 > d97) { // from (0, -1)
                    d113 = d97;
                }
                if (d113 > d127) { // from (0, 1)
                    d113 = d127;
                }
                d113 += 1;
            }
        }

        if (rc.onTheMap(l96)) { // check (-1, -1)
            if (rc.canSenseLocation(l96) && rc.sensePassability(l96)) { 
                if (d96 > d112) { // from (0, 0)
                    d96 = 0.1875;
                }
                if (d96 > d111) { // from (-1, 0)
                    d96 = d111;
                }
                if (d96 > d97) { // from (0, -1)
                    d96 = d97;
                }
                d96 += 1;
            }
        }

        if (rc.onTheMap(l126)) { // check (-1, 1)
            if (rc.canSenseLocation(l126) && rc.sensePassability(l126)) { 
                if (d126 > d112) { // from (0, 0)
                    d126 = 0.125;
                }
                if (d126 > d111) { // from (-1, 0)
                    d126 = d111;
                }
                if (d126 > d127) { // from (0, 1)
                    d126 = d127;
                }
                d126 += 1;
            }
        }

        if (rc.onTheMap(l98)) { // check (1, -1)
            if (rc.canSenseLocation(l98) && rc.sensePassability(l98)) { 
                if (d98 > d112) { // from (0, 0)
                    d98 = 0.25;
                }
                if (d98 > d97) { // from (0, -1)
                    d98 = d97;
                }
                if (d98 > d113) { // from (1, 0)
                    d98 = d113;
                }
                d98 += 1;
            }
        }

        if (rc.onTheMap(l128)) { // check (1, 1)
            if (rc.canSenseLocation(l128) && rc.sensePassability(l128)) { 
                if (d128 > d112) { // from (0, 0)
                    d128 = 0.0625;
                }
                if (d128 > d127) { // from (0, 1)
                    d128 = d127;
                }
                if (d128 > d113) { // from (1, 0)
                    d128 = d113;
                }
                d128 += 1;
            }
        }

        if (rc.onTheMap(l110)) { // check (-2, 0)
            if (rc.canSenseLocation(l110) && rc.sensePassability(l110)) { 
                if (d110 > d111) { // from (-1, 0)
                    d110 = d111;
                }
                if (d110 > d96) { // from (-1, -1)
                    d110 = d96;
                }
                if (d110 > d126) { // from (-1, 1)
                    d110 = d126;
                }
                d110 += 1;
            }
        }

        if (rc.onTheMap(l82)) { // check (0, -2)
            if (rc.canSenseLocation(l82) && rc.sensePassability(l82)) { 
                if (d82 > d97) { // from (0, -1)
                    d82 = d97;
                }
                if (d82 > d96) { // from (-1, -1)
                    d82 = d96;
                }
                if (d82 > d98) { // from (1, -1)
                    d82 = d98;
                }
                d82 += 1;
            }
        }

        if (rc.onTheMap(l142)) { // check (0, 2)
            if (rc.canSenseLocation(l142) && rc.sensePassability(l142)) { 
                if (d142 > d127) { // from (0, 1)
                    d142 = d127;
                }
                if (d142 > d126) { // from (-1, 1)
                    d142 = d126;
                }
                if (d142 > d128) { // from (1, 1)
                    d142 = d128;
                }
                d142 += 1;
            }
        }

        if (rc.onTheMap(l114)) { // check (2, 0)
            if (rc.canSenseLocation(l114) && rc.sensePassability(l114)) { 
                if (d114 > d113) { // from (1, 0)
                    d114 = d113;
                }
                if (d114 > d98) { // from (1, -1)
                    d114 = d98;
                }
                if (d114 > d128) { // from (1, 1)
                    d114 = d128;
                }
                d114 += 1;
            }
        }

        if (rc.onTheMap(l95)) { // check (-2, -1)
            if (rc.canSenseLocation(l95) && rc.sensePassability(l95)) { 
                if (d95 > d111) { // from (-1, 0)
                    d95 = d111;
                }
                if (d95 > d96) { // from (-1, -1)
                    d95 = d96;
                }
                if (d95 > d110) { // from (-2, 0)
                    d95 = d110;
                }
                d95 += 1;
            }
        }

        if (rc.onTheMap(l125)) { // check (-2, 1)
            if (rc.canSenseLocation(l125) && rc.sensePassability(l125)) { 
                if (d125 > d111) { // from (-1, 0)
                    d125 = d111;
                }
                if (d125 > d126) { // from (-1, 1)
                    d125 = d126;
                }
                if (d125 > d110) { // from (-2, 0)
                    d125 = d110;
                }
                d125 += 1;
            }
        }

        if (rc.onTheMap(l81)) { // check (-1, -2)
            if (rc.canSenseLocation(l81) && rc.sensePassability(l81)) { 
                if (d81 > d97) { // from (0, -1)
                    d81 = d97;
                }
                if (d81 > d96) { // from (-1, -1)
                    d81 = d96;
                }
                if (d81 > d82) { // from (0, -2)
                    d81 = d82;
                }
                if (d81 > d95) { // from (-2, -1)
                    d81 = d95;
                }
                d81 += 1;
            }
        }

        if (rc.onTheMap(l141)) { // check (-1, 2)
            if (rc.canSenseLocation(l141) && rc.sensePassability(l141)) { 
                if (d141 > d127) { // from (0, 1)
                    d141 = d127;
                }
                if (d141 > d126) { // from (-1, 1)
                    d141 = d126;
                }
                if (d141 > d142) { // from (0, 2)
                    d141 = d142;
                }
                if (d141 > d125) { // from (-2, 1)
                    d141 = d125;
                }
                d141 += 1;
            }
        }

        if (rc.onTheMap(l83)) { // check (1, -2)
            if (rc.canSenseLocation(l83) && rc.sensePassability(l83)) { 
                if (d83 > d97) { // from (0, -1)
                    d83 = d97;
                }
                if (d83 > d98) { // from (1, -1)
                    d83 = d98;
                }
                if (d83 > d82) { // from (0, -2)
                    d83 = d82;
                }
                d83 += 1;
            }
        }

        if (rc.onTheMap(l143)) { // check (1, 2)
            if (rc.canSenseLocation(l143) && rc.sensePassability(l143)) { 
                if (d143 > d127) { // from (0, 1)
                    d143 = d127;
                }
                if (d143 > d128) { // from (1, 1)
                    d143 = d128;
                }
                if (d143 > d142) { // from (0, 2)
                    d143 = d142;
                }
                d143 += 1;
            }
        }

        if (rc.onTheMap(l99)) { // check (2, -1)
            if (rc.canSenseLocation(l99) && rc.sensePassability(l99)) { 
                if (d99 > d113) { // from (1, 0)
                    d99 = d113;
                }
                if (d99 > d98) { // from (1, -1)
                    d99 = d98;
                }
                if (d99 > d114) { // from (2, 0)
                    d99 = d114;
                }
                if (d99 > d83) { // from (1, -2)
                    d99 = d83;
                }
                d99 += 1;
            }
        }

        if (rc.onTheMap(l129)) { // check (2, 1)
            if (rc.canSenseLocation(l129) && rc.sensePassability(l129)) { 
                if (d129 > d113) { // from (1, 0)
                    d129 = d113;
                }
                if (d129 > d128) { // from (1, 1)
                    d129 = d128;
                }
                if (d129 > d114) { // from (2, 0)
                    d129 = d114;
                }
                if (d129 > d143) { // from (1, 2)
                    d129 = d143;
                }
                d129 += 1;
            }
        }

        if (rc.onTheMap(l80)) { // check (-2, -2)
            if (rc.canSenseLocation(l80) && rc.sensePassability(l80)) { 
                if (d80 > d96) { // from (-1, -1)
                    d80 = d96;
                }
                if (d80 > d95) { // from (-2, -1)
                    d80 = d95;
                }
                if (d80 > d81) { // from (-1, -2)
                    d80 = d81;
                }
                d80 += 1;
            }
        }

        if (rc.onTheMap(l140)) { // check (-2, 2)
            if (rc.canSenseLocation(l140) && rc.sensePassability(l140)) { 
                if (d140 > d126) { // from (-1, 1)
                    d140 = d126;
                }
                if (d140 > d125) { // from (-2, 1)
                    d140 = d125;
                }
                if (d140 > d141) { // from (-1, 2)
                    d140 = d141;
                }
                d140 += 1;
            }
        }

        if (rc.onTheMap(l84)) { // check (2, -2)
            if (rc.canSenseLocation(l84) && rc.sensePassability(l84)) { 
                if (d84 > d98) { // from (1, -1)
                    d84 = d98;
                }
                if (d84 > d83) { // from (1, -2)
                    d84 = d83;
                }
                if (d84 > d99) { // from (2, -1)
                    d84 = d99;
                }
                d84 += 1;
            }
        }

        if (rc.onTheMap(l144)) { // check (2, 2)
            if (rc.canSenseLocation(l144) && rc.sensePassability(l144)) { 
                if (d144 > d128) { // from (1, 1)
                    d144 = d128;
                }
                if (d144 > d143) { // from (1, 2)
                    d144 = d143;
                }
                if (d144 > d129) { // from (2, 1)
                    d144 = d129;
                }
                d144 += 1;
            }
        }

        if (rc.onTheMap(l109)) { // check (-3, 0)
            if (rc.canSenseLocation(l109) && rc.sensePassability(l109)) { 
                if (d109 > d110) { // from (-2, 0)
                    d109 = d110;
                }
                if (d109 > d95) { // from (-2, -1)
                    d109 = d95;
                }
                if (d109 > d125) { // from (-2, 1)
                    d109 = d125;
                }
                d109 += 1;
            }
        }

        if (rc.onTheMap(l67)) { // check (0, -3)
            if (rc.canSenseLocation(l67) && rc.sensePassability(l67)) { 
                if (d67 > d82) { // from (0, -2)
                    d67 = d82;
                }
                if (d67 > d81) { // from (-1, -2)
                    d67 = d81;
                }
                if (d67 > d83) { // from (1, -2)
                    d67 = d83;
                }
                d67 += 1;
            }
        }

        if (rc.onTheMap(l157)) { // check (0, 3)
            if (rc.canSenseLocation(l157) && rc.sensePassability(l157)) { 
                if (d157 > d142) { // from (0, 2)
                    d157 = d142;
                }
                if (d157 > d141) { // from (-1, 2)
                    d157 = d141;
                }
                if (d157 > d143) { // from (1, 2)
                    d157 = d143;
                }
                d157 += 1;
            }
        }

        if (rc.onTheMap(l115)) { // check (3, 0)
            if (rc.canSenseLocation(l115) && rc.sensePassability(l115)) { 
                if (d115 > d114) { // from (2, 0)
                    d115 = d114;
                }
                if (d115 > d99) { // from (2, -1)
                    d115 = d99;
                }
                if (d115 > d129) { // from (2, 1)
                    d115 = d129;
                }
                d115 += 1;
            }
        }

        if (rc.onTheMap(l94)) { // check (-3, -1)
            if (rc.canSenseLocation(l94) && rc.sensePassability(l94)) { 
                if (d94 > d110) { // from (-2, 0)
                    d94 = d110;
                }
                if (d94 > d95) { // from (-2, -1)
                    d94 = d95;
                }
                if (d94 > d80) { // from (-2, -2)
                    d94 = d80;
                }
                if (d94 > d109) { // from (-3, 0)
                    d94 = d109;
                }
                d94 += 1;
            }
        }

        if (rc.onTheMap(l124)) { // check (-3, 1)
            if (rc.canSenseLocation(l124) && rc.sensePassability(l124)) { 
                if (d124 > d110) { // from (-2, 0)
                    d124 = d110;
                }
                if (d124 > d125) { // from (-2, 1)
                    d124 = d125;
                }
                if (d124 > d140) { // from (-2, 2)
                    d124 = d140;
                }
                if (d124 > d109) { // from (-3, 0)
                    d124 = d109;
                }
                d124 += 1;
            }
        }

        if (rc.onTheMap(l66)) { // check (-1, -3)
            if (rc.canSenseLocation(l66) && rc.sensePassability(l66)) { 
                if (d66 > d82) { // from (0, -2)
                    d66 = d82;
                }
                if (d66 > d81) { // from (-1, -2)
                    d66 = d81;
                }
                if (d66 > d80) { // from (-2, -2)
                    d66 = d80;
                }
                if (d66 > d67) { // from (0, -3)
                    d66 = d67;
                }
                d66 += 1;
            }
        }

        if (rc.onTheMap(l156)) { // check (-1, 3)
            if (rc.canSenseLocation(l156) && rc.sensePassability(l156)) { 
                if (d156 > d142) { // from (0, 2)
                    d156 = d142;
                }
                if (d156 > d141) { // from (-1, 2)
                    d156 = d141;
                }
                if (d156 > d140) { // from (-2, 2)
                    d156 = d140;
                }
                if (d156 > d157) { // from (0, 3)
                    d156 = d157;
                }
                d156 += 1;
            }
        }

        if (rc.onTheMap(l68)) { // check (1, -3)
            if (rc.canSenseLocation(l68) && rc.sensePassability(l68)) { 
                if (d68 > d82) { // from (0, -2)
                    d68 = d82;
                }
                if (d68 > d83) { // from (1, -2)
                    d68 = d83;
                }
                if (d68 > d84) { // from (2, -2)
                    d68 = d84;
                }
                if (d68 > d67) { // from (0, -3)
                    d68 = d67;
                }
                d68 += 1;
            }
        }

        if (rc.onTheMap(l158)) { // check (1, 3)
            if (rc.canSenseLocation(l158) && rc.sensePassability(l158)) { 
                if (d158 > d142) { // from (0, 2)
                    d158 = d142;
                }
                if (d158 > d143) { // from (1, 2)
                    d158 = d143;
                }
                if (d158 > d144) { // from (2, 2)
                    d158 = d144;
                }
                if (d158 > d157) { // from (0, 3)
                    d158 = d157;
                }
                d158 += 1;
            }
        }

        if (rc.onTheMap(l100)) { // check (3, -1)
            if (rc.canSenseLocation(l100) && rc.sensePassability(l100)) { 
                if (d100 > d114) { // from (2, 0)
                    d100 = d114;
                }
                if (d100 > d99) { // from (2, -1)
                    d100 = d99;
                }
                if (d100 > d84) { // from (2, -2)
                    d100 = d84;
                }
                if (d100 > d115) { // from (3, 0)
                    d100 = d115;
                }
                d100 += 1;
            }
        }

        if (rc.onTheMap(l130)) { // check (3, 1)
            if (rc.canSenseLocation(l130) && rc.sensePassability(l130)) { 
                if (d130 > d114) { // from (2, 0)
                    d130 = d114;
                }
                if (d130 > d129) { // from (2, 1)
                    d130 = d129;
                }
                if (d130 > d144) { // from (2, 2)
                    d130 = d144;
                }
                if (d130 > d115) { // from (3, 0)
                    d130 = d115;
                }
                d130 += 1;
            }
        }


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d156 + "\t" + d157 + "\t" + d158 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d140 + "\t" + d141 + "\t" + d142 + "\t" + d143 + "\t" + d144 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d124 + "\t" + d125 + "\t" + d126 + "\t" + d127 + "\t" + d128 + "\t" + d129 + "\t" + d130 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d109 + "\t" + d110 + "\t" + d111 + "\t" + d112 + "\t" + d113 + "\t" + d114 + "\t" + d115 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d94 + "\t" + d95 + "\t" + d96 + "\t" + d97 + "\t" + d98 + "\t" + d99 + "\t" + d100 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d80 + "\t" + d81 + "\t" + d82 + "\t" + d83 + "\t" + d84 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + d66 + "\t" + d67 + "\t" + d68 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("DIRECTIONS:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir156 + "\t" + dir157 + "\t" + dir158 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir140 + "\t" + dir141 + "\t" + dir142 + "\t" + dir143 + "\t" + dir144 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir124 + "\t" + dir125 + "\t" + dir126 + "\t" + dir127 + "\t" + dir128 + "\t" + dir129 + "\t" + dir130 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir109 + "\t" + dir110 + "\t" + dir111 + "\t" + dir112 + "\t" + dir113 + "\t" + dir114 + "\t" + dir115 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir94 + "\t" + dir95 + "\t" + dir96 + "\t" + dir97 + "\t" + dir98 + "\t" + dir99 + "\t" + dir100 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir80 + "\t" + dir81 + "\t" + dir82 + "\t" + dir83 + "\t" + dir84 + "\t" + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + dir66 + "\t" + dir67 + "\t" + dir68 + "\t" + "\t" + "\t" + "\t" + "\t" + "\t");

        int target_dx = target.x - l112.x;
        int target_dy = target.y - l112.y;
        switch (target_dx) {
                case -3:
                    switch (target_dy) {
                        case -1:
                            return direction(d94); // destination is at relative location (-3, -1)
                        case 0:
                            return direction(d109); // destination is at relative location (-3, 0)
                        case 1:
                            return direction(d124); // destination is at relative location (-3, 1)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -2:
                            return direction(d80); // destination is at relative location (-2, -2)
                        case -1:
                            return direction(d95); // destination is at relative location (-2, -1)
                        case 0:
                            return direction(d110); // destination is at relative location (-2, 0)
                        case 1:
                            return direction(d125); // destination is at relative location (-2, 1)
                        case 2:
                            return direction(d140); // destination is at relative location (-2, 2)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -3:
                            return direction(d66); // destination is at relative location (-1, -3)
                        case -2:
                            return direction(d81); // destination is at relative location (-1, -2)
                        case -1:
                            return direction(d96); // destination is at relative location (-1, -1)
                        case 0:
                            return direction(d111); // destination is at relative location (-1, 0)
                        case 1:
                            return direction(d126); // destination is at relative location (-1, 1)
                        case 2:
                            return direction(d141); // destination is at relative location (-1, 2)
                        case 3:
                            return direction(d156); // destination is at relative location (-1, 3)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -3:
                            return direction(d67); // destination is at relative location (0, -3)
                        case -2:
                            return direction(d82); // destination is at relative location (0, -2)
                        case -1:
                            return direction(d97); // destination is at relative location (0, -1)
                        case 0:
                            return direction(d112); // destination is at relative location (0, 0)
                        case 1:
                            return direction(d127); // destination is at relative location (0, 1)
                        case 2:
                            return direction(d142); // destination is at relative location (0, 2)
                        case 3:
                            return direction(d157); // destination is at relative location (0, 3)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -3:
                            return direction(d68); // destination is at relative location (1, -3)
                        case -2:
                            return direction(d83); // destination is at relative location (1, -2)
                        case -1:
                            return direction(d98); // destination is at relative location (1, -1)
                        case 0:
                            return direction(d113); // destination is at relative location (1, 0)
                        case 1:
                            return direction(d128); // destination is at relative location (1, 1)
                        case 2:
                            return direction(d143); // destination is at relative location (1, 2)
                        case 3:
                            return direction(d158); // destination is at relative location (1, 3)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -2:
                            return direction(d84); // destination is at relative location (2, -2)
                        case -1:
                            return direction(d99); // destination is at relative location (2, -1)
                        case 0:
                            return direction(d114); // destination is at relative location (2, 0)
                        case 1:
                            return direction(d129); // destination is at relative location (2, 1)
                        case 2:
                            return direction(d144); // destination is at relative location (2, 2)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -1:
                            return direction(d100); // destination is at relative location (3, -1)
                        case 0:
                            return direction(d115); // destination is at relative location (3, 0)
                        case 1:
                            return direction(d130); // destination is at relative location (3, 1)
                    }
                    break;
        }
        
        ans = Double.POSITIVE_INFINITY;
        bestScore = 0;
        currDist = Math.sqrt(l112.distanceSquaredTo(target));
        
        score94 = (currDist - Math.sqrt(l94.distanceSquaredTo(target))) / d94;
        if (score94 > bestScore) {
            bestScore = score94;
            ans = d94;
        }

        score109 = (currDist - Math.sqrt(l109.distanceSquaredTo(target))) / d109;
        if (score109 > bestScore) {
            bestScore = score109;
            ans = d109;
        }

        score124 = (currDist - Math.sqrt(l124.distanceSquaredTo(target))) / d124;
        if (score124 > bestScore) {
            bestScore = score124;
            ans = d124;
        }

        score80 = (currDist - Math.sqrt(l80.distanceSquaredTo(target))) / d80;
        if (score80 > bestScore) {
            bestScore = score80;
            ans = d80;
        }

        score140 = (currDist - Math.sqrt(l140.distanceSquaredTo(target))) / d140;
        if (score140 > bestScore) {
            bestScore = score140;
            ans = d140;
        }

        score66 = (currDist - Math.sqrt(l66.distanceSquaredTo(target))) / d66;
        if (score66 > bestScore) {
            bestScore = score66;
            ans = d66;
        }

        score156 = (currDist - Math.sqrt(l156.distanceSquaredTo(target))) / d156;
        if (score156 > bestScore) {
            bestScore = score156;
            ans = d156;
        }

        score67 = (currDist - Math.sqrt(l67.distanceSquaredTo(target))) / d67;
        if (score67 > bestScore) {
            bestScore = score67;
            ans = d67;
        }

        score157 = (currDist - Math.sqrt(l157.distanceSquaredTo(target))) / d157;
        if (score157 > bestScore) {
            bestScore = score157;
            ans = d157;
        }

        score68 = (currDist - Math.sqrt(l68.distanceSquaredTo(target))) / d68;
        if (score68 > bestScore) {
            bestScore = score68;
            ans = d68;
        }

        score158 = (currDist - Math.sqrt(l158.distanceSquaredTo(target))) / d158;
        if (score158 > bestScore) {
            bestScore = score158;
            ans = d158;
        }

        score84 = (currDist - Math.sqrt(l84.distanceSquaredTo(target))) / d84;
        if (score84 > bestScore) {
            bestScore = score84;
            ans = d84;
        }

        score144 = (currDist - Math.sqrt(l144.distanceSquaredTo(target))) / d144;
        if (score144 > bestScore) {
            bestScore = score144;
            ans = d144;
        }

        score100 = (currDist - Math.sqrt(l100.distanceSquaredTo(target))) / d100;
        if (score100 > bestScore) {
            bestScore = score100;
            ans = d100;
        }

        score115 = (currDist - Math.sqrt(l115.distanceSquaredTo(target))) / d115;
        if (score115 > bestScore) {
            bestScore = score115;
            ans = d115;
        }

        score130 = (currDist - Math.sqrt(l130.distanceSquaredTo(target))) / d130;
        if (score130 > bestScore) {
            bestScore = score130;
            ans = d130;
        }

        
        return direction(ans);
    }
}
