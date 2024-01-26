package bfs.bfs;

import battlecode.common.*;

public class BFS34 {

    public static RobotController rc;

    static MapLocation l62; // location representing relative coordinate (-5, -3)
    static double d62; // shortest distance to location from current location
    // static Direction dir62; // best direction to take now to optimally get to location
    static double score62; // heuristic distance from location to target

    static MapLocation l77; // location representing relative coordinate (-5, -2)
    static double d77; // shortest distance to location from current location
    // static Direction dir77; // best direction to take now to optimally get to location
    static double score77; // heuristic distance from location to target

    static MapLocation l92; // location representing relative coordinate (-5, -1)
    static double d92; // shortest distance to location from current location
    // static Direction dir92; // best direction to take now to optimally get to location
    static double score92; // heuristic distance from location to target

    static MapLocation l107; // location representing relative coordinate (-5, 0)
    static double d107; // shortest distance to location from current location
    // static Direction dir107; // best direction to take now to optimally get to location
    static double score107; // heuristic distance from location to target

    static MapLocation l122; // location representing relative coordinate (-5, 1)
    static double d122; // shortest distance to location from current location
    // static Direction dir122; // best direction to take now to optimally get to location
    static double score122; // heuristic distance from location to target

    static MapLocation l137; // location representing relative coordinate (-5, 2)
    static double d137; // shortest distance to location from current location
    // static Direction dir137; // best direction to take now to optimally get to location
    static double score137; // heuristic distance from location to target

    static MapLocation l152; // location representing relative coordinate (-5, 3)
    static double d152; // shortest distance to location from current location
    // static Direction dir152; // best direction to take now to optimally get to location
    static double score152; // heuristic distance from location to target

    static MapLocation l48; // location representing relative coordinate (-4, -4)
    static double d48; // shortest distance to location from current location
    // static Direction dir48; // best direction to take now to optimally get to location
    static double score48; // heuristic distance from location to target

    static MapLocation l63; // location representing relative coordinate (-4, -3)
    static double d63; // shortest distance to location from current location
    // static Direction dir63; // best direction to take now to optimally get to location
    static double score63; // heuristic distance from location to target

    static MapLocation l78; // location representing relative coordinate (-4, -2)
    static double d78; // shortest distance to location from current location
    // static Direction dir78; // best direction to take now to optimally get to location
    static double score78; // heuristic distance from location to target

    static MapLocation l93; // location representing relative coordinate (-4, -1)
    static double d93; // shortest distance to location from current location
    // static Direction dir93; // best direction to take now to optimally get to location
    static double score93; // heuristic distance from location to target

    static MapLocation l108; // location representing relative coordinate (-4, 0)
    static double d108; // shortest distance to location from current location
    // static Direction dir108; // best direction to take now to optimally get to location
    static double score108; // heuristic distance from location to target

    static MapLocation l123; // location representing relative coordinate (-4, 1)
    static double d123; // shortest distance to location from current location
    // static Direction dir123; // best direction to take now to optimally get to location
    static double score123; // heuristic distance from location to target

    static MapLocation l138; // location representing relative coordinate (-4, 2)
    static double d138; // shortest distance to location from current location
    // static Direction dir138; // best direction to take now to optimally get to location
    static double score138; // heuristic distance from location to target

    static MapLocation l153; // location representing relative coordinate (-4, 3)
    static double d153; // shortest distance to location from current location
    // static Direction dir153; // best direction to take now to optimally get to location
    static double score153; // heuristic distance from location to target

    static MapLocation l168; // location representing relative coordinate (-4, 4)
    static double d168; // shortest distance to location from current location
    // static Direction dir168; // best direction to take now to optimally get to location
    static double score168; // heuristic distance from location to target

    static MapLocation l34; // location representing relative coordinate (-3, -5)
    static double d34; // shortest distance to location from current location
    // static Direction dir34; // best direction to take now to optimally get to location
    static double score34; // heuristic distance from location to target

    static MapLocation l49; // location representing relative coordinate (-3, -4)
    static double d49; // shortest distance to location from current location
    // static Direction dir49; // best direction to take now to optimally get to location
    static double score49; // heuristic distance from location to target

    static MapLocation l64; // location representing relative coordinate (-3, -3)
    static double d64; // shortest distance to location from current location
    // static Direction dir64; // best direction to take now to optimally get to location
    static double score64; // heuristic distance from location to target

    static MapLocation l79; // location representing relative coordinate (-3, -2)
    static double d79; // shortest distance to location from current location
    // static Direction dir79; // best direction to take now to optimally get to location
    static double score79; // heuristic distance from location to target

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

    static MapLocation l139; // location representing relative coordinate (-3, 2)
    static double d139; // shortest distance to location from current location
    // static Direction dir139; // best direction to take now to optimally get to location
    static double score139; // heuristic distance from location to target

    static MapLocation l154; // location representing relative coordinate (-3, 3)
    static double d154; // shortest distance to location from current location
    // static Direction dir154; // best direction to take now to optimally get to location
    static double score154; // heuristic distance from location to target

    static MapLocation l169; // location representing relative coordinate (-3, 4)
    static double d169; // shortest distance to location from current location
    // static Direction dir169; // best direction to take now to optimally get to location
    static double score169; // heuristic distance from location to target

    static MapLocation l184; // location representing relative coordinate (-3, 5)
    static double d184; // shortest distance to location from current location
    // static Direction dir184; // best direction to take now to optimally get to location
    static double score184; // heuristic distance from location to target

    static MapLocation l35; // location representing relative coordinate (-2, -5)
    static double d35; // shortest distance to location from current location
    // static Direction dir35; // best direction to take now to optimally get to location
    static double score35; // heuristic distance from location to target

    static MapLocation l50; // location representing relative coordinate (-2, -4)
    static double d50; // shortest distance to location from current location
    // static Direction dir50; // best direction to take now to optimally get to location
    static double score50; // heuristic distance from location to target

    static MapLocation l65; // location representing relative coordinate (-2, -3)
    static double d65; // shortest distance to location from current location
    // static Direction dir65; // best direction to take now to optimally get to location
    static double score65; // heuristic distance from location to target

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

    static MapLocation l155; // location representing relative coordinate (-2, 3)
    static double d155; // shortest distance to location from current location
    // static Direction dir155; // best direction to take now to optimally get to location
    static double score155; // heuristic distance from location to target

    static MapLocation l170; // location representing relative coordinate (-2, 4)
    static double d170; // shortest distance to location from current location
    // static Direction dir170; // best direction to take now to optimally get to location
    static double score170; // heuristic distance from location to target

    static MapLocation l185; // location representing relative coordinate (-2, 5)
    static double d185; // shortest distance to location from current location
    // static Direction dir185; // best direction to take now to optimally get to location
    static double score185; // heuristic distance from location to target

    static MapLocation l36; // location representing relative coordinate (-1, -5)
    static double d36; // shortest distance to location from current location
    // static Direction dir36; // best direction to take now to optimally get to location
    static double score36; // heuristic distance from location to target

    static MapLocation l51; // location representing relative coordinate (-1, -4)
    static double d51; // shortest distance to location from current location
    // static Direction dir51; // best direction to take now to optimally get to location
    static double score51; // heuristic distance from location to target

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

    static MapLocation l171; // location representing relative coordinate (-1, 4)
    static double d171; // shortest distance to location from current location
    // static Direction dir171; // best direction to take now to optimally get to location
    static double score171; // heuristic distance from location to target

    static MapLocation l186; // location representing relative coordinate (-1, 5)
    static double d186; // shortest distance to location from current location
    // static Direction dir186; // best direction to take now to optimally get to location
    static double score186; // heuristic distance from location to target

    static MapLocation l37; // location representing relative coordinate (0, -5)
    static double d37; // shortest distance to location from current location
    // static Direction dir37; // best direction to take now to optimally get to location
    static double score37; // heuristic distance from location to target

    static MapLocation l52; // location representing relative coordinate (0, -4)
    static double d52; // shortest distance to location from current location
    // static Direction dir52; // best direction to take now to optimally get to location
    static double score52; // heuristic distance from location to target

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

    static MapLocation l172; // location representing relative coordinate (0, 4)
    static double d172; // shortest distance to location from current location
    // static Direction dir172; // best direction to take now to optimally get to location
    static double score172; // heuristic distance from location to target

    static MapLocation l187; // location representing relative coordinate (0, 5)
    static double d187; // shortest distance to location from current location
    // static Direction dir187; // best direction to take now to optimally get to location
    static double score187; // heuristic distance from location to target

    static MapLocation l38; // location representing relative coordinate (1, -5)
    static double d38; // shortest distance to location from current location
    // static Direction dir38; // best direction to take now to optimally get to location
    static double score38; // heuristic distance from location to target

    static MapLocation l53; // location representing relative coordinate (1, -4)
    static double d53; // shortest distance to location from current location
    // static Direction dir53; // best direction to take now to optimally get to location
    static double score53; // heuristic distance from location to target

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

    static MapLocation l173; // location representing relative coordinate (1, 4)
    static double d173; // shortest distance to location from current location
    // static Direction dir173; // best direction to take now to optimally get to location
    static double score173; // heuristic distance from location to target

    static MapLocation l188; // location representing relative coordinate (1, 5)
    static double d188; // shortest distance to location from current location
    // static Direction dir188; // best direction to take now to optimally get to location
    static double score188; // heuristic distance from location to target

    static MapLocation l39; // location representing relative coordinate (2, -5)
    static double d39; // shortest distance to location from current location
    // static Direction dir39; // best direction to take now to optimally get to location
    static double score39; // heuristic distance from location to target

    static MapLocation l54; // location representing relative coordinate (2, -4)
    static double d54; // shortest distance to location from current location
    // static Direction dir54; // best direction to take now to optimally get to location
    static double score54; // heuristic distance from location to target

    static MapLocation l69; // location representing relative coordinate (2, -3)
    static double d69; // shortest distance to location from current location
    // static Direction dir69; // best direction to take now to optimally get to location
    static double score69; // heuristic distance from location to target

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

    static MapLocation l159; // location representing relative coordinate (2, 3)
    static double d159; // shortest distance to location from current location
    // static Direction dir159; // best direction to take now to optimally get to location
    static double score159; // heuristic distance from location to target

    static MapLocation l174; // location representing relative coordinate (2, 4)
    static double d174; // shortest distance to location from current location
    // static Direction dir174; // best direction to take now to optimally get to location
    static double score174; // heuristic distance from location to target

    static MapLocation l189; // location representing relative coordinate (2, 5)
    static double d189; // shortest distance to location from current location
    // static Direction dir189; // best direction to take now to optimally get to location
    static double score189; // heuristic distance from location to target

    static MapLocation l40; // location representing relative coordinate (3, -5)
    static double d40; // shortest distance to location from current location
    // static Direction dir40; // best direction to take now to optimally get to location
    static double score40; // heuristic distance from location to target

    static MapLocation l55; // location representing relative coordinate (3, -4)
    static double d55; // shortest distance to location from current location
    // static Direction dir55; // best direction to take now to optimally get to location
    static double score55; // heuristic distance from location to target

    static MapLocation l70; // location representing relative coordinate (3, -3)
    static double d70; // shortest distance to location from current location
    // static Direction dir70; // best direction to take now to optimally get to location
    static double score70; // heuristic distance from location to target

    static MapLocation l85; // location representing relative coordinate (3, -2)
    static double d85; // shortest distance to location from current location
    // static Direction dir85; // best direction to take now to optimally get to location
    static double score85; // heuristic distance from location to target

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

    static MapLocation l145; // location representing relative coordinate (3, 2)
    static double d145; // shortest distance to location from current location
    // static Direction dir145; // best direction to take now to optimally get to location
    static double score145; // heuristic distance from location to target

    static MapLocation l160; // location representing relative coordinate (3, 3)
    static double d160; // shortest distance to location from current location
    // static Direction dir160; // best direction to take now to optimally get to location
    static double score160; // heuristic distance from location to target

    static MapLocation l175; // location representing relative coordinate (3, 4)
    static double d175; // shortest distance to location from current location
    // static Direction dir175; // best direction to take now to optimally get to location
    static double score175; // heuristic distance from location to target

    static MapLocation l190; // location representing relative coordinate (3, 5)
    static double d190; // shortest distance to location from current location
    // static Direction dir190; // best direction to take now to optimally get to location
    static double score190; // heuristic distance from location to target

    static MapLocation l56; // location representing relative coordinate (4, -4)
    static double d56; // shortest distance to location from current location
    // static Direction dir56; // best direction to take now to optimally get to location
    static double score56; // heuristic distance from location to target

    static MapLocation l71; // location representing relative coordinate (4, -3)
    static double d71; // shortest distance to location from current location
    // static Direction dir71; // best direction to take now to optimally get to location
    static double score71; // heuristic distance from location to target

    static MapLocation l86; // location representing relative coordinate (4, -2)
    static double d86; // shortest distance to location from current location
    // static Direction dir86; // best direction to take now to optimally get to location
    static double score86; // heuristic distance from location to target

    static MapLocation l101; // location representing relative coordinate (4, -1)
    static double d101; // shortest distance to location from current location
    // static Direction dir101; // best direction to take now to optimally get to location
    static double score101; // heuristic distance from location to target

    static MapLocation l116; // location representing relative coordinate (4, 0)
    static double d116; // shortest distance to location from current location
    // static Direction dir116; // best direction to take now to optimally get to location
    static double score116; // heuristic distance from location to target

    static MapLocation l131; // location representing relative coordinate (4, 1)
    static double d131; // shortest distance to location from current location
    // static Direction dir131; // best direction to take now to optimally get to location
    static double score131; // heuristic distance from location to target

    static MapLocation l146; // location representing relative coordinate (4, 2)
    static double d146; // shortest distance to location from current location
    // static Direction dir146; // best direction to take now to optimally get to location
    static double score146; // heuristic distance from location to target

    static MapLocation l161; // location representing relative coordinate (4, 3)
    static double d161; // shortest distance to location from current location
    // static Direction dir161; // best direction to take now to optimally get to location
    static double score161; // heuristic distance from location to target

    static MapLocation l176; // location representing relative coordinate (4, 4)
    static double d176; // shortest distance to location from current location
    // static Direction dir176; // best direction to take now to optimally get to location
    static double score176; // heuristic distance from location to target

    static MapLocation l72; // location representing relative coordinate (5, -3)
    static double d72; // shortest distance to location from current location
    // static Direction dir72; // best direction to take now to optimally get to location
    static double score72; // heuristic distance from location to target

    static MapLocation l87; // location representing relative coordinate (5, -2)
    static double d87; // shortest distance to location from current location
    // static Direction dir87; // best direction to take now to optimally get to location
    static double score87; // heuristic distance from location to target

    static MapLocation l102; // location representing relative coordinate (5, -1)
    static double d102; // shortest distance to location from current location
    // static Direction dir102; // best direction to take now to optimally get to location
    static double score102; // heuristic distance from location to target

    static MapLocation l117; // location representing relative coordinate (5, 0)
    static double d117; // shortest distance to location from current location
    // static Direction dir117; // best direction to take now to optimally get to location
    static double score117; // heuristic distance from location to target

    static MapLocation l132; // location representing relative coordinate (5, 1)
    static double d132; // shortest distance to location from current location
    // static Direction dir132; // best direction to take now to optimally get to location
    static double score132; // heuristic distance from location to target

    static MapLocation l147; // location representing relative coordinate (5, 2)
    static double d147; // shortest distance to location from current location
    // static Direction dir147; // best direction to take now to optimally get to location
    static double score147; // heuristic distance from location to target

    static MapLocation l162; // location representing relative coordinate (5, 3)
    static double d162; // shortest distance to location from current location
    // static Direction dir162; // best direction to take now to optimally get to location
    static double score162; // heuristic distance from location to target


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

        l79 = l95.add(SOUTHWEST); // (-3, -2) from (-2, -1)
        d79 = 99999;
        // dir79 = null;

        l139 = l125.add(NORTHWEST); // (-3, 2) from (-2, 1)
        d139 = 99999;
        // dir139 = null;

        l65 = l81.add(SOUTHWEST); // (-2, -3) from (-1, -2)
        d65 = 99999;
        // dir65 = null;

        l155 = l141.add(NORTHWEST); // (-2, 3) from (-1, 2)
        d155 = 99999;
        // dir155 = null;

        l69 = l83.add(SOUTHEAST); // (2, -3) from (1, -2)
        d69 = 99999;
        // dir69 = null;

        l159 = l143.add(NORTHEAST); // (2, 3) from (1, 2)
        d159 = 99999;
        // dir159 = null;

        l85 = l99.add(SOUTHEAST); // (3, -2) from (2, -1)
        d85 = 99999;
        // dir85 = null;

        l145 = l129.add(NORTHEAST); // (3, 2) from (2, 1)
        d145 = 99999;
        // dir145 = null;

        l108 = l109.add(WEST); // (-4, 0) from (-3, 0)
        d108 = 99999;
        // dir108 = null;

        l52 = l67.add(SOUTH); // (0, -4) from (0, -3)
        d52 = 99999;
        // dir52 = null;

        l172 = l157.add(NORTH); // (0, 4) from (0, 3)
        d172 = 99999;
        // dir172 = null;

        l116 = l115.add(EAST); // (4, 0) from (3, 0)
        d116 = 99999;
        // dir116 = null;

        l93 = l109.add(SOUTHWEST); // (-4, -1) from (-3, 0)
        d93 = 99999;
        // dir93 = null;

        l123 = l109.add(NORTHWEST); // (-4, 1) from (-3, 0)
        d123 = 99999;
        // dir123 = null;

        l51 = l67.add(SOUTHWEST); // (-1, -4) from (0, -3)
        d51 = 99999;
        // dir51 = null;

        l171 = l157.add(NORTHWEST); // (-1, 4) from (0, 3)
        d171 = 99999;
        // dir171 = null;

        l53 = l67.add(SOUTHEAST); // (1, -4) from (0, -3)
        d53 = 99999;
        // dir53 = null;

        l173 = l157.add(NORTHEAST); // (1, 4) from (0, 3)
        d173 = 99999;
        // dir173 = null;

        l101 = l115.add(SOUTHEAST); // (4, -1) from (3, 0)
        d101 = 99999;
        // dir101 = null;

        l131 = l115.add(NORTHEAST); // (4, 1) from (3, 0)
        d131 = 99999;
        // dir131 = null;

        l64 = l80.add(SOUTHWEST); // (-3, -3) from (-2, -2)
        d64 = 99999;
        // dir64 = null;

        l154 = l140.add(NORTHWEST); // (-3, 3) from (-2, 2)
        d154 = 99999;
        // dir154 = null;

        l70 = l84.add(SOUTHEAST); // (3, -3) from (2, -2)
        d70 = 99999;
        // dir70 = null;

        l160 = l144.add(NORTHEAST); // (3, 3) from (2, 2)
        d160 = 99999;
        // dir160 = null;

        l78 = l94.add(SOUTHWEST); // (-4, -2) from (-3, -1)
        d78 = 99999;
        // dir78 = null;

        l138 = l124.add(NORTHWEST); // (-4, 2) from (-3, 1)
        d138 = 99999;
        // dir138 = null;

        l50 = l66.add(SOUTHWEST); // (-2, -4) from (-1, -3)
        d50 = 99999;
        // dir50 = null;

        l170 = l156.add(NORTHWEST); // (-2, 4) from (-1, 3)
        d170 = 99999;
        // dir170 = null;

        l54 = l68.add(SOUTHEAST); // (2, -4) from (1, -3)
        d54 = 99999;
        // dir54 = null;

        l174 = l158.add(NORTHEAST); // (2, 4) from (1, 3)
        d174 = 99999;
        // dir174 = null;

        l86 = l100.add(SOUTHEAST); // (4, -2) from (3, -1)
        d86 = 99999;
        // dir86 = null;

        l146 = l130.add(NORTHEAST); // (4, 2) from (3, 1)
        d146 = 99999;
        // dir146 = null;

        l107 = l108.add(WEST); // (-5, 0) from (-4, 0)
        d107 = 99999;
        // dir107 = null;

        l63 = l79.add(SOUTHWEST); // (-4, -3) from (-3, -2)
        d63 = 99999;
        // dir63 = null;

        l153 = l139.add(NORTHWEST); // (-4, 3) from (-3, 2)
        d153 = 99999;
        // dir153 = null;

        l49 = l65.add(SOUTHWEST); // (-3, -4) from (-2, -3)
        d49 = 99999;
        // dir49 = null;

        l169 = l155.add(NORTHWEST); // (-3, 4) from (-2, 3)
        d169 = 99999;
        // dir169 = null;

        l37 = l52.add(SOUTH); // (0, -5) from (0, -4)
        d37 = 99999;
        // dir37 = null;

        l187 = l172.add(NORTH); // (0, 5) from (0, 4)
        d187 = 99999;
        // dir187 = null;

        l55 = l69.add(SOUTHEAST); // (3, -4) from (2, -3)
        d55 = 99999;
        // dir55 = null;

        l175 = l159.add(NORTHEAST); // (3, 4) from (2, 3)
        d175 = 99999;
        // dir175 = null;

        l71 = l85.add(SOUTHEAST); // (4, -3) from (3, -2)
        d71 = 99999;
        // dir71 = null;

        l161 = l145.add(NORTHEAST); // (4, 3) from (3, 2)
        d161 = 99999;
        // dir161 = null;

        l117 = l116.add(EAST); // (5, 0) from (4, 0)
        d117 = 99999;
        // dir117 = null;

        l92 = l108.add(SOUTHWEST); // (-5, -1) from (-4, 0)
        d92 = 99999;
        // dir92 = null;

        l122 = l108.add(NORTHWEST); // (-5, 1) from (-4, 0)
        d122 = 99999;
        // dir122 = null;

        l36 = l52.add(SOUTHWEST); // (-1, -5) from (0, -4)
        d36 = 99999;
        // dir36 = null;

        l186 = l172.add(NORTHWEST); // (-1, 5) from (0, 4)
        d186 = 99999;
        // dir186 = null;

        l38 = l52.add(SOUTHEAST); // (1, -5) from (0, -4)
        d38 = 99999;
        // dir38 = null;

        l188 = l172.add(NORTHEAST); // (1, 5) from (0, 4)
        d188 = 99999;
        // dir188 = null;

        l102 = l116.add(SOUTHEAST); // (5, -1) from (4, 0)
        d102 = 99999;
        // dir102 = null;

        l132 = l116.add(NORTHEAST); // (5, 1) from (4, 0)
        d132 = 99999;
        // dir132 = null;

        l77 = l93.add(SOUTHWEST); // (-5, -2) from (-4, -1)
        d77 = 99999;
        // dir77 = null;

        l137 = l123.add(NORTHWEST); // (-5, 2) from (-4, 1)
        d137 = 99999;
        // dir137 = null;

        l35 = l51.add(SOUTHWEST); // (-2, -5) from (-1, -4)
        d35 = 99999;
        // dir35 = null;

        l185 = l171.add(NORTHWEST); // (-2, 5) from (-1, 4)
        d185 = 99999;
        // dir185 = null;

        l39 = l53.add(SOUTHEAST); // (2, -5) from (1, -4)
        d39 = 99999;
        // dir39 = null;

        l189 = l173.add(NORTHEAST); // (2, 5) from (1, 4)
        d189 = 99999;
        // dir189 = null;

        l87 = l101.add(SOUTHEAST); // (5, -2) from (4, -1)
        d87 = 99999;
        // dir87 = null;

        l147 = l131.add(NORTHEAST); // (5, 2) from (4, 1)
        d147 = 99999;
        // dir147 = null;

        l48 = l64.add(SOUTHWEST); // (-4, -4) from (-3, -3)
        d48 = 99999;
        // dir48 = null;

        l168 = l154.add(NORTHWEST); // (-4, 4) from (-3, 3)
        d168 = 99999;
        // dir168 = null;

        l56 = l70.add(SOUTHEAST); // (4, -4) from (3, -3)
        d56 = 99999;
        // dir56 = null;

        l176 = l160.add(NORTHEAST); // (4, 4) from (3, 3)
        d176 = 99999;
        // dir176 = null;

        l62 = l78.add(SOUTHWEST); // (-5, -3) from (-4, -2)
        d62 = 99999;
        // dir62 = null;

        l152 = l138.add(NORTHWEST); // (-5, 3) from (-4, 2)
        d152 = 99999;
        // dir152 = null;

        l34 = l50.add(SOUTHWEST); // (-3, -5) from (-2, -4)
        d34 = 99999;
        // dir34 = null;

        l184 = l170.add(NORTHWEST); // (-3, 5) from (-2, 4)
        d184 = 99999;
        // dir184 = null;

        l40 = l54.add(SOUTHEAST); // (3, -5) from (2, -4)
        d40 = 99999;
        // dir40 = null;

        l190 = l174.add(NORTHEAST); // (3, 5) from (2, 4)
        d190 = 99999;
        // dir190 = null;

        l72 = l86.add(SOUTHEAST); // (5, -3) from (4, -2)
        d72 = 99999;
        // dir72 = null;

        l162 = l146.add(NORTHEAST); // (5, 3) from (4, 2)
        d162 = 99999;
        // dir162 = null;



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

        if (rc.onTheMap(l79)) { // check (-3, -2)
            if (rc.canSenseLocation(l79) && rc.sensePassability(l79)) { 
                if (d79 > d95) { // from (-2, -1)
                    d79 = d95;
                }
                if (d79 > d80) { // from (-2, -2)
                    d79 = d80;
                }
                if (d79 > d94) { // from (-3, -1)
                    d79 = d94;
                }
                d79 += 1;
            }
        }

        if (rc.onTheMap(l139)) { // check (-3, 2)
            if (rc.canSenseLocation(l139) && rc.sensePassability(l139)) { 
                if (d139 > d125) { // from (-2, 1)
                    d139 = d125;
                }
                if (d139 > d140) { // from (-2, 2)
                    d139 = d140;
                }
                if (d139 > d124) { // from (-3, 1)
                    d139 = d124;
                }
                d139 += 1;
            }
        }

        if (rc.onTheMap(l65)) { // check (-2, -3)
            if (rc.canSenseLocation(l65) && rc.sensePassability(l65)) { 
                if (d65 > d81) { // from (-1, -2)
                    d65 = d81;
                }
                if (d65 > d80) { // from (-2, -2)
                    d65 = d80;
                }
                if (d65 > d66) { // from (-1, -3)
                    d65 = d66;
                }
                if (d65 > d79) { // from (-3, -2)
                    d65 = d79;
                }
                d65 += 1;
            }
        }

        if (rc.onTheMap(l155)) { // check (-2, 3)
            if (rc.canSenseLocation(l155) && rc.sensePassability(l155)) { 
                if (d155 > d141) { // from (-1, 2)
                    d155 = d141;
                }
                if (d155 > d140) { // from (-2, 2)
                    d155 = d140;
                }
                if (d155 > d156) { // from (-1, 3)
                    d155 = d156;
                }
                if (d155 > d139) { // from (-3, 2)
                    d155 = d139;
                }
                d155 += 1;
            }
        }

        if (rc.onTheMap(l69)) { // check (2, -3)
            if (rc.canSenseLocation(l69) && rc.sensePassability(l69)) { 
                if (d69 > d83) { // from (1, -2)
                    d69 = d83;
                }
                if (d69 > d84) { // from (2, -2)
                    d69 = d84;
                }
                if (d69 > d68) { // from (1, -3)
                    d69 = d68;
                }
                d69 += 1;
            }
        }

        if (rc.onTheMap(l159)) { // check (2, 3)
            if (rc.canSenseLocation(l159) && rc.sensePassability(l159)) { 
                if (d159 > d143) { // from (1, 2)
                    d159 = d143;
                }
                if (d159 > d144) { // from (2, 2)
                    d159 = d144;
                }
                if (d159 > d158) { // from (1, 3)
                    d159 = d158;
                }
                d159 += 1;
            }
        }

        if (rc.onTheMap(l85)) { // check (3, -2)
            if (rc.canSenseLocation(l85) && rc.sensePassability(l85)) { 
                if (d85 > d99) { // from (2, -1)
                    d85 = d99;
                }
                if (d85 > d84) { // from (2, -2)
                    d85 = d84;
                }
                if (d85 > d100) { // from (3, -1)
                    d85 = d100;
                }
                if (d85 > d69) { // from (2, -3)
                    d85 = d69;
                }
                d85 += 1;
            }
        }

        if (rc.onTheMap(l145)) { // check (3, 2)
            if (rc.canSenseLocation(l145) && rc.sensePassability(l145)) { 
                if (d145 > d129) { // from (2, 1)
                    d145 = d129;
                }
                if (d145 > d144) { // from (2, 2)
                    d145 = d144;
                }
                if (d145 > d130) { // from (3, 1)
                    d145 = d130;
                }
                if (d145 > d159) { // from (2, 3)
                    d145 = d159;
                }
                d145 += 1;
            }
        }

        if (rc.onTheMap(l108)) { // check (-4, 0)
            if (rc.canSenseLocation(l108) && rc.sensePassability(l108)) { 
                if (d108 > d109) { // from (-3, 0)
                    d108 = d109;
                }
                if (d108 > d94) { // from (-3, -1)
                    d108 = d94;
                }
                if (d108 > d124) { // from (-3, 1)
                    d108 = d124;
                }
                d108 += 1;
            }
        }

        if (rc.onTheMap(l52)) { // check (0, -4)
            if (rc.canSenseLocation(l52) && rc.sensePassability(l52)) { 
                if (d52 > d67) { // from (0, -3)
                    d52 = d67;
                }
                if (d52 > d66) { // from (-1, -3)
                    d52 = d66;
                }
                if (d52 > d68) { // from (1, -3)
                    d52 = d68;
                }
                d52 += 1;
            }
        }

        if (rc.onTheMap(l172)) { // check (0, 4)
            if (rc.canSenseLocation(l172) && rc.sensePassability(l172)) { 
                if (d172 > d157) { // from (0, 3)
                    d172 = d157;
                }
                if (d172 > d156) { // from (-1, 3)
                    d172 = d156;
                }
                if (d172 > d158) { // from (1, 3)
                    d172 = d158;
                }
                d172 += 1;
            }
        }

        if (rc.onTheMap(l116)) { // check (4, 0)
            if (rc.canSenseLocation(l116) && rc.sensePassability(l116)) { 
                if (d116 > d115) { // from (3, 0)
                    d116 = d115;
                }
                if (d116 > d100) { // from (3, -1)
                    d116 = d100;
                }
                if (d116 > d130) { // from (3, 1)
                    d116 = d130;
                }
                d116 += 1;
            }
        }

        if (rc.onTheMap(l93)) { // check (-4, -1)
            if (rc.canSenseLocation(l93) && rc.sensePassability(l93)) { 
                if (d93 > d109) { // from (-3, 0)
                    d93 = d109;
                }
                if (d93 > d94) { // from (-3, -1)
                    d93 = d94;
                }
                if (d93 > d79) { // from (-3, -2)
                    d93 = d79;
                }
                if (d93 > d108) { // from (-4, 0)
                    d93 = d108;
                }
                d93 += 1;
            }
        }

        if (rc.onTheMap(l123)) { // check (-4, 1)
            if (rc.canSenseLocation(l123) && rc.sensePassability(l123)) { 
                if (d123 > d109) { // from (-3, 0)
                    d123 = d109;
                }
                if (d123 > d124) { // from (-3, 1)
                    d123 = d124;
                }
                if (d123 > d139) { // from (-3, 2)
                    d123 = d139;
                }
                if (d123 > d108) { // from (-4, 0)
                    d123 = d108;
                }
                d123 += 1;
            }
        }

        if (rc.onTheMap(l51)) { // check (-1, -4)
            if (rc.canSenseLocation(l51) && rc.sensePassability(l51)) { 
                if (d51 > d67) { // from (0, -3)
                    d51 = d67;
                }
                if (d51 > d66) { // from (-1, -3)
                    d51 = d66;
                }
                if (d51 > d65) { // from (-2, -3)
                    d51 = d65;
                }
                if (d51 > d52) { // from (0, -4)
                    d51 = d52;
                }
                d51 += 1;
            }
        }

        if (rc.onTheMap(l171)) { // check (-1, 4)
            if (rc.canSenseLocation(l171) && rc.sensePassability(l171)) { 
                if (d171 > d157) { // from (0, 3)
                    d171 = d157;
                }
                if (d171 > d156) { // from (-1, 3)
                    d171 = d156;
                }
                if (d171 > d155) { // from (-2, 3)
                    d171 = d155;
                }
                if (d171 > d172) { // from (0, 4)
                    d171 = d172;
                }
                d171 += 1;
            }
        }

        if (rc.onTheMap(l53)) { // check (1, -4)
            if (rc.canSenseLocation(l53) && rc.sensePassability(l53)) { 
                if (d53 > d67) { // from (0, -3)
                    d53 = d67;
                }
                if (d53 > d68) { // from (1, -3)
                    d53 = d68;
                }
                if (d53 > d69) { // from (2, -3)
                    d53 = d69;
                }
                if (d53 > d52) { // from (0, -4)
                    d53 = d52;
                }
                d53 += 1;
            }
        }

        if (rc.onTheMap(l173)) { // check (1, 4)
            if (rc.canSenseLocation(l173) && rc.sensePassability(l173)) { 
                if (d173 > d157) { // from (0, 3)
                    d173 = d157;
                }
                if (d173 > d158) { // from (1, 3)
                    d173 = d158;
                }
                if (d173 > d159) { // from (2, 3)
                    d173 = d159;
                }
                if (d173 > d172) { // from (0, 4)
                    d173 = d172;
                }
                d173 += 1;
            }
        }

        if (rc.onTheMap(l101)) { // check (4, -1)
            if (rc.canSenseLocation(l101) && rc.sensePassability(l101)) { 
                if (d101 > d115) { // from (3, 0)
                    d101 = d115;
                }
                if (d101 > d100) { // from (3, -1)
                    d101 = d100;
                }
                if (d101 > d85) { // from (3, -2)
                    d101 = d85;
                }
                if (d101 > d116) { // from (4, 0)
                    d101 = d116;
                }
                d101 += 1;
            }
        }

        if (rc.onTheMap(l131)) { // check (4, 1)
            if (rc.canSenseLocation(l131) && rc.sensePassability(l131)) { 
                if (d131 > d115) { // from (3, 0)
                    d131 = d115;
                }
                if (d131 > d130) { // from (3, 1)
                    d131 = d130;
                }
                if (d131 > d145) { // from (3, 2)
                    d131 = d145;
                }
                if (d131 > d116) { // from (4, 0)
                    d131 = d116;
                }
                d131 += 1;
            }
        }

        if (rc.onTheMap(l64)) { // check (-3, -3)
            if (rc.canSenseLocation(l64) && rc.sensePassability(l64)) { 
                if (d64 > d80) { // from (-2, -2)
                    d64 = d80;
                }
                if (d64 > d79) { // from (-3, -2)
                    d64 = d79;
                }
                if (d64 > d65) { // from (-2, -3)
                    d64 = d65;
                }
                d64 += 1;
            }
        }

        if (rc.onTheMap(l154)) { // check (-3, 3)
            if (rc.canSenseLocation(l154) && rc.sensePassability(l154)) { 
                if (d154 > d140) { // from (-2, 2)
                    d154 = d140;
                }
                if (d154 > d139) { // from (-3, 2)
                    d154 = d139;
                }
                if (d154 > d155) { // from (-2, 3)
                    d154 = d155;
                }
                d154 += 1;
            }
        }

        if (rc.onTheMap(l70)) { // check (3, -3)
            if (rc.canSenseLocation(l70) && rc.sensePassability(l70)) { 
                if (d70 > d84) { // from (2, -2)
                    d70 = d84;
                }
                if (d70 > d69) { // from (2, -3)
                    d70 = d69;
                }
                if (d70 > d85) { // from (3, -2)
                    d70 = d85;
                }
                d70 += 1;
            }
        }

        if (rc.onTheMap(l160)) { // check (3, 3)
            if (rc.canSenseLocation(l160) && rc.sensePassability(l160)) { 
                if (d160 > d144) { // from (2, 2)
                    d160 = d144;
                }
                if (d160 > d159) { // from (2, 3)
                    d160 = d159;
                }
                if (d160 > d145) { // from (3, 2)
                    d160 = d145;
                }
                d160 += 1;
            }
        }

        if (rc.onTheMap(l78)) { // check (-4, -2)
            if (rc.canSenseLocation(l78) && rc.sensePassability(l78)) { 
                if (d78 > d94) { // from (-3, -1)
                    d78 = d94;
                }
                if (d78 > d79) { // from (-3, -2)
                    d78 = d79;
                }
                if (d78 > d93) { // from (-4, -1)
                    d78 = d93;
                }
                if (d78 > d64) { // from (-3, -3)
                    d78 = d64;
                }
                d78 += 1;
            }
        }

        if (rc.onTheMap(l138)) { // check (-4, 2)
            if (rc.canSenseLocation(l138) && rc.sensePassability(l138)) { 
                if (d138 > d124) { // from (-3, 1)
                    d138 = d124;
                }
                if (d138 > d139) { // from (-3, 2)
                    d138 = d139;
                }
                if (d138 > d123) { // from (-4, 1)
                    d138 = d123;
                }
                if (d138 > d154) { // from (-3, 3)
                    d138 = d154;
                }
                d138 += 1;
            }
        }

        if (rc.onTheMap(l50)) { // check (-2, -4)
            if (rc.canSenseLocation(l50) && rc.sensePassability(l50)) { 
                if (d50 > d66) { // from (-1, -3)
                    d50 = d66;
                }
                if (d50 > d65) { // from (-2, -3)
                    d50 = d65;
                }
                if (d50 > d51) { // from (-1, -4)
                    d50 = d51;
                }
                if (d50 > d64) { // from (-3, -3)
                    d50 = d64;
                }
                d50 += 1;
            }
        }

        if (rc.onTheMap(l170)) { // check (-2, 4)
            if (rc.canSenseLocation(l170) && rc.sensePassability(l170)) { 
                if (d170 > d156) { // from (-1, 3)
                    d170 = d156;
                }
                if (d170 > d155) { // from (-2, 3)
                    d170 = d155;
                }
                if (d170 > d171) { // from (-1, 4)
                    d170 = d171;
                }
                if (d170 > d154) { // from (-3, 3)
                    d170 = d154;
                }
                d170 += 1;
            }
        }

        if (rc.onTheMap(l54)) { // check (2, -4)
            if (rc.canSenseLocation(l54) && rc.sensePassability(l54)) { 
                if (d54 > d68) { // from (1, -3)
                    d54 = d68;
                }
                if (d54 > d69) { // from (2, -3)
                    d54 = d69;
                }
                if (d54 > d53) { // from (1, -4)
                    d54 = d53;
                }
                if (d54 > d70) { // from (3, -3)
                    d54 = d70;
                }
                d54 += 1;
            }
        }

        if (rc.onTheMap(l174)) { // check (2, 4)
            if (rc.canSenseLocation(l174) && rc.sensePassability(l174)) { 
                if (d174 > d158) { // from (1, 3)
                    d174 = d158;
                }
                if (d174 > d159) { // from (2, 3)
                    d174 = d159;
                }
                if (d174 > d173) { // from (1, 4)
                    d174 = d173;
                }
                if (d174 > d160) { // from (3, 3)
                    d174 = d160;
                }
                d174 += 1;
            }
        }

        if (rc.onTheMap(l86)) { // check (4, -2)
            if (rc.canSenseLocation(l86) && rc.sensePassability(l86)) { 
                if (d86 > d100) { // from (3, -1)
                    d86 = d100;
                }
                if (d86 > d85) { // from (3, -2)
                    d86 = d85;
                }
                if (d86 > d101) { // from (4, -1)
                    d86 = d101;
                }
                if (d86 > d70) { // from (3, -3)
                    d86 = d70;
                }
                d86 += 1;
            }
        }

        if (rc.onTheMap(l146)) { // check (4, 2)
            if (rc.canSenseLocation(l146) && rc.sensePassability(l146)) { 
                if (d146 > d130) { // from (3, 1)
                    d146 = d130;
                }
                if (d146 > d145) { // from (3, 2)
                    d146 = d145;
                }
                if (d146 > d131) { // from (4, 1)
                    d146 = d131;
                }
                if (d146 > d160) { // from (3, 3)
                    d146 = d160;
                }
                d146 += 1;
            }
        }

        if (rc.onTheMap(l107)) { // check (-5, 0)
            if (rc.canSenseLocation(l107) && rc.sensePassability(l107)) { 
                if (d107 > d108) { // from (-4, 0)
                    d107 = d108;
                }
                if (d107 > d93) { // from (-4, -1)
                    d107 = d93;
                }
                if (d107 > d123) { // from (-4, 1)
                    d107 = d123;
                }
                d107 += 1;
            }
        }

        if (rc.onTheMap(l63)) { // check (-4, -3)
            if (rc.canSenseLocation(l63) && rc.sensePassability(l63)) { 
                if (d63 > d79) { // from (-3, -2)
                    d63 = d79;
                }
                if (d63 > d64) { // from (-3, -3)
                    d63 = d64;
                }
                if (d63 > d78) { // from (-4, -2)
                    d63 = d78;
                }
                d63 += 1;
            }
        }

        if (rc.onTheMap(l153)) { // check (-4, 3)
            if (rc.canSenseLocation(l153) && rc.sensePassability(l153)) { 
                if (d153 > d139) { // from (-3, 2)
                    d153 = d139;
                }
                if (d153 > d154) { // from (-3, 3)
                    d153 = d154;
                }
                if (d153 > d138) { // from (-4, 2)
                    d153 = d138;
                }
                d153 += 1;
            }
        }

        if (rc.onTheMap(l49)) { // check (-3, -4)
            if (rc.canSenseLocation(l49) && rc.sensePassability(l49)) { 
                if (d49 > d65) { // from (-2, -3)
                    d49 = d65;
                }
                if (d49 > d64) { // from (-3, -3)
                    d49 = d64;
                }
                if (d49 > d50) { // from (-2, -4)
                    d49 = d50;
                }
                if (d49 > d63) { // from (-4, -3)
                    d49 = d63;
                }
                d49 += 1;
            }
        }

        if (rc.onTheMap(l169)) { // check (-3, 4)
            if (rc.canSenseLocation(l169) && rc.sensePassability(l169)) { 
                if (d169 > d155) { // from (-2, 3)
                    d169 = d155;
                }
                if (d169 > d154) { // from (-3, 3)
                    d169 = d154;
                }
                if (d169 > d170) { // from (-2, 4)
                    d169 = d170;
                }
                if (d169 > d153) { // from (-4, 3)
                    d169 = d153;
                }
                d169 += 1;
            }
        }

        if (rc.onTheMap(l37)) { // check (0, -5)
            if (rc.canSenseLocation(l37) && rc.sensePassability(l37)) { 
                if (d37 > d52) { // from (0, -4)
                    d37 = d52;
                }
                if (d37 > d51) { // from (-1, -4)
                    d37 = d51;
                }
                if (d37 > d53) { // from (1, -4)
                    d37 = d53;
                }
                d37 += 1;
            }
        }

        if (rc.onTheMap(l187)) { // check (0, 5)
            if (rc.canSenseLocation(l187) && rc.sensePassability(l187)) { 
                if (d187 > d172) { // from (0, 4)
                    d187 = d172;
                }
                if (d187 > d171) { // from (-1, 4)
                    d187 = d171;
                }
                if (d187 > d173) { // from (1, 4)
                    d187 = d173;
                }
                d187 += 1;
            }
        }

        if (rc.onTheMap(l55)) { // check (3, -4)
            if (rc.canSenseLocation(l55) && rc.sensePassability(l55)) { 
                if (d55 > d69) { // from (2, -3)
                    d55 = d69;
                }
                if (d55 > d70) { // from (3, -3)
                    d55 = d70;
                }
                if (d55 > d54) { // from (2, -4)
                    d55 = d54;
                }
                d55 += 1;
            }
        }

        if (rc.onTheMap(l175)) { // check (3, 4)
            if (rc.canSenseLocation(l175) && rc.sensePassability(l175)) { 
                if (d175 > d159) { // from (2, 3)
                    d175 = d159;
                }
                if (d175 > d160) { // from (3, 3)
                    d175 = d160;
                }
                if (d175 > d174) { // from (2, 4)
                    d175 = d174;
                }
                d175 += 1;
            }
        }

        if (rc.onTheMap(l71)) { // check (4, -3)
            if (rc.canSenseLocation(l71) && rc.sensePassability(l71)) { 
                if (d71 > d85) { // from (3, -2)
                    d71 = d85;
                }
                if (d71 > d70) { // from (3, -3)
                    d71 = d70;
                }
                if (d71 > d86) { // from (4, -2)
                    d71 = d86;
                }
                if (d71 > d55) { // from (3, -4)
                    d71 = d55;
                }
                d71 += 1;
            }
        }

        if (rc.onTheMap(l161)) { // check (4, 3)
            if (rc.canSenseLocation(l161) && rc.sensePassability(l161)) { 
                if (d161 > d145) { // from (3, 2)
                    d161 = d145;
                }
                if (d161 > d160) { // from (3, 3)
                    d161 = d160;
                }
                if (d161 > d146) { // from (4, 2)
                    d161 = d146;
                }
                if (d161 > d175) { // from (3, 4)
                    d161 = d175;
                }
                d161 += 1;
            }
        }

        if (rc.onTheMap(l117)) { // check (5, 0)
            if (rc.canSenseLocation(l117) && rc.sensePassability(l117)) { 
                if (d117 > d116) { // from (4, 0)
                    d117 = d116;
                }
                if (d117 > d101) { // from (4, -1)
                    d117 = d101;
                }
                if (d117 > d131) { // from (4, 1)
                    d117 = d131;
                }
                d117 += 1;
            }
        }

        if (rc.onTheMap(l92)) { // check (-5, -1)
            if (rc.canSenseLocation(l92) && rc.sensePassability(l92)) { 
                if (d92 > d108) { // from (-4, 0)
                    d92 = d108;
                }
                if (d92 > d93) { // from (-4, -1)
                    d92 = d93;
                }
                if (d92 > d78) { // from (-4, -2)
                    d92 = d78;
                }
                if (d92 > d107) { // from (-5, 0)
                    d92 = d107;
                }
                d92 += 1;
            }
        }

        if (rc.onTheMap(l122)) { // check (-5, 1)
            if (rc.canSenseLocation(l122) && rc.sensePassability(l122)) { 
                if (d122 > d108) { // from (-4, 0)
                    d122 = d108;
                }
                if (d122 > d123) { // from (-4, 1)
                    d122 = d123;
                }
                if (d122 > d138) { // from (-4, 2)
                    d122 = d138;
                }
                if (d122 > d107) { // from (-5, 0)
                    d122 = d107;
                }
                d122 += 1;
            }
        }

        if (rc.onTheMap(l36)) { // check (-1, -5)
            if (rc.canSenseLocation(l36) && rc.sensePassability(l36)) { 
                if (d36 > d52) { // from (0, -4)
                    d36 = d52;
                }
                if (d36 > d51) { // from (-1, -4)
                    d36 = d51;
                }
                if (d36 > d50) { // from (-2, -4)
                    d36 = d50;
                }
                if (d36 > d37) { // from (0, -5)
                    d36 = d37;
                }
                d36 += 1;
            }
        }

        if (rc.onTheMap(l186)) { // check (-1, 5)
            if (rc.canSenseLocation(l186) && rc.sensePassability(l186)) { 
                if (d186 > d172) { // from (0, 4)
                    d186 = d172;
                }
                if (d186 > d171) { // from (-1, 4)
                    d186 = d171;
                }
                if (d186 > d170) { // from (-2, 4)
                    d186 = d170;
                }
                if (d186 > d187) { // from (0, 5)
                    d186 = d187;
                }
                d186 += 1;
            }
        }

        if (rc.onTheMap(l38)) { // check (1, -5)
            if (rc.canSenseLocation(l38) && rc.sensePassability(l38)) { 
                if (d38 > d52) { // from (0, -4)
                    d38 = d52;
                }
                if (d38 > d53) { // from (1, -4)
                    d38 = d53;
                }
                if (d38 > d54) { // from (2, -4)
                    d38 = d54;
                }
                if (d38 > d37) { // from (0, -5)
                    d38 = d37;
                }
                d38 += 1;
            }
        }

        if (rc.onTheMap(l188)) { // check (1, 5)
            if (rc.canSenseLocation(l188) && rc.sensePassability(l188)) { 
                if (d188 > d172) { // from (0, 4)
                    d188 = d172;
                }
                if (d188 > d173) { // from (1, 4)
                    d188 = d173;
                }
                if (d188 > d174) { // from (2, 4)
                    d188 = d174;
                }
                if (d188 > d187) { // from (0, 5)
                    d188 = d187;
                }
                d188 += 1;
            }
        }

        if (rc.onTheMap(l102)) { // check (5, -1)
            if (rc.canSenseLocation(l102) && rc.sensePassability(l102)) { 
                if (d102 > d116) { // from (4, 0)
                    d102 = d116;
                }
                if (d102 > d101) { // from (4, -1)
                    d102 = d101;
                }
                if (d102 > d86) { // from (4, -2)
                    d102 = d86;
                }
                if (d102 > d117) { // from (5, 0)
                    d102 = d117;
                }
                d102 += 1;
            }
        }

        if (rc.onTheMap(l132)) { // check (5, 1)
            if (rc.canSenseLocation(l132) && rc.sensePassability(l132)) { 
                if (d132 > d116) { // from (4, 0)
                    d132 = d116;
                }
                if (d132 > d131) { // from (4, 1)
                    d132 = d131;
                }
                if (d132 > d146) { // from (4, 2)
                    d132 = d146;
                }
                if (d132 > d117) { // from (5, 0)
                    d132 = d117;
                }
                d132 += 1;
            }
        }

        if (rc.onTheMap(l77)) { // check (-5, -2)
            if (rc.canSenseLocation(l77) && rc.sensePassability(l77)) { 
                if (d77 > d93) { // from (-4, -1)
                    d77 = d93;
                }
                if (d77 > d78) { // from (-4, -2)
                    d77 = d78;
                }
                if (d77 > d63) { // from (-4, -3)
                    d77 = d63;
                }
                if (d77 > d92) { // from (-5, -1)
                    d77 = d92;
                }
                d77 += 1;
            }
        }

        if (rc.onTheMap(l137)) { // check (-5, 2)
            if (rc.canSenseLocation(l137) && rc.sensePassability(l137)) { 
                if (d137 > d123) { // from (-4, 1)
                    d137 = d123;
                }
                if (d137 > d138) { // from (-4, 2)
                    d137 = d138;
                }
                if (d137 > d153) { // from (-4, 3)
                    d137 = d153;
                }
                if (d137 > d122) { // from (-5, 1)
                    d137 = d122;
                }
                d137 += 1;
            }
        }

        if (rc.onTheMap(l35)) { // check (-2, -5)
            if (rc.canSenseLocation(l35) && rc.sensePassability(l35)) { 
                if (d35 > d51) { // from (-1, -4)
                    d35 = d51;
                }
                if (d35 > d50) { // from (-2, -4)
                    d35 = d50;
                }
                if (d35 > d49) { // from (-3, -4)
                    d35 = d49;
                }
                if (d35 > d36) { // from (-1, -5)
                    d35 = d36;
                }
                d35 += 1;
            }
        }

        if (rc.onTheMap(l185)) { // check (-2, 5)
            if (rc.canSenseLocation(l185) && rc.sensePassability(l185)) { 
                if (d185 > d171) { // from (-1, 4)
                    d185 = d171;
                }
                if (d185 > d170) { // from (-2, 4)
                    d185 = d170;
                }
                if (d185 > d169) { // from (-3, 4)
                    d185 = d169;
                }
                if (d185 > d186) { // from (-1, 5)
                    d185 = d186;
                }
                d185 += 1;
            }
        }

        if (rc.onTheMap(l39)) { // check (2, -5)
            if (rc.canSenseLocation(l39) && rc.sensePassability(l39)) { 
                if (d39 > d53) { // from (1, -4)
                    d39 = d53;
                }
                if (d39 > d54) { // from (2, -4)
                    d39 = d54;
                }
                if (d39 > d55) { // from (3, -4)
                    d39 = d55;
                }
                if (d39 > d38) { // from (1, -5)
                    d39 = d38;
                }
                d39 += 1;
            }
        }

        if (rc.onTheMap(l189)) { // check (2, 5)
            if (rc.canSenseLocation(l189) && rc.sensePassability(l189)) { 
                if (d189 > d173) { // from (1, 4)
                    d189 = d173;
                }
                if (d189 > d174) { // from (2, 4)
                    d189 = d174;
                }
                if (d189 > d175) { // from (3, 4)
                    d189 = d175;
                }
                if (d189 > d188) { // from (1, 5)
                    d189 = d188;
                }
                d189 += 1;
            }
        }

        if (rc.onTheMap(l87)) { // check (5, -2)
            if (rc.canSenseLocation(l87) && rc.sensePassability(l87)) { 
                if (d87 > d101) { // from (4, -1)
                    d87 = d101;
                }
                if (d87 > d86) { // from (4, -2)
                    d87 = d86;
                }
                if (d87 > d71) { // from (4, -3)
                    d87 = d71;
                }
                if (d87 > d102) { // from (5, -1)
                    d87 = d102;
                }
                d87 += 1;
            }
        }

        if (rc.onTheMap(l147)) { // check (5, 2)
            if (rc.canSenseLocation(l147) && rc.sensePassability(l147)) { 
                if (d147 > d131) { // from (4, 1)
                    d147 = d131;
                }
                if (d147 > d146) { // from (4, 2)
                    d147 = d146;
                }
                if (d147 > d161) { // from (4, 3)
                    d147 = d161;
                }
                if (d147 > d132) { // from (5, 1)
                    d147 = d132;
                }
                d147 += 1;
            }
        }

        if (rc.onTheMap(l48)) { // check (-4, -4)
            if (rc.canSenseLocation(l48) && rc.sensePassability(l48)) { 
                if (d48 > d64) { // from (-3, -3)
                    d48 = d64;
                }
                if (d48 > d63) { // from (-4, -3)
                    d48 = d63;
                }
                if (d48 > d49) { // from (-3, -4)
                    d48 = d49;
                }
                d48 += 1;
            }
        }

        if (rc.onTheMap(l168)) { // check (-4, 4)
            if (rc.canSenseLocation(l168) && rc.sensePassability(l168)) { 
                if (d168 > d154) { // from (-3, 3)
                    d168 = d154;
                }
                if (d168 > d153) { // from (-4, 3)
                    d168 = d153;
                }
                if (d168 > d169) { // from (-3, 4)
                    d168 = d169;
                }
                d168 += 1;
            }
        }

        if (rc.onTheMap(l56)) { // check (4, -4)
            if (rc.canSenseLocation(l56) && rc.sensePassability(l56)) { 
                if (d56 > d70) { // from (3, -3)
                    d56 = d70;
                }
                if (d56 > d55) { // from (3, -4)
                    d56 = d55;
                }
                if (d56 > d71) { // from (4, -3)
                    d56 = d71;
                }
                d56 += 1;
            }
        }

        if (rc.onTheMap(l176)) { // check (4, 4)
            if (rc.canSenseLocation(l176) && rc.sensePassability(l176)) { 
                if (d176 > d160) { // from (3, 3)
                    d176 = d160;
                }
                if (d176 > d175) { // from (3, 4)
                    d176 = d175;
                }
                if (d176 > d161) { // from (4, 3)
                    d176 = d161;
                }
                d176 += 1;
            }
        }

        if (rc.onTheMap(l62)) { // check (-5, -3)
            if (rc.canSenseLocation(l62) && rc.sensePassability(l62)) { 
                if (d62 > d78) { // from (-4, -2)
                    d62 = d78;
                }
                if (d62 > d63) { // from (-4, -3)
                    d62 = d63;
                }
                if (d62 > d77) { // from (-5, -2)
                    d62 = d77;
                }
                if (d62 > d48) { // from (-4, -4)
                    d62 = d48;
                }
                d62 += 1;
            }
        }

        if (rc.onTheMap(l152)) { // check (-5, 3)
            if (rc.canSenseLocation(l152) && rc.sensePassability(l152)) { 
                if (d152 > d138) { // from (-4, 2)
                    d152 = d138;
                }
                if (d152 > d153) { // from (-4, 3)
                    d152 = d153;
                }
                if (d152 > d137) { // from (-5, 2)
                    d152 = d137;
                }
                if (d152 > d168) { // from (-4, 4)
                    d152 = d168;
                }
                d152 += 1;
            }
        }

        if (rc.onTheMap(l34)) { // check (-3, -5)
            if (rc.canSenseLocation(l34) && rc.sensePassability(l34)) { 
                if (d34 > d50) { // from (-2, -4)
                    d34 = d50;
                }
                if (d34 > d49) { // from (-3, -4)
                    d34 = d49;
                }
                if (d34 > d35) { // from (-2, -5)
                    d34 = d35;
                }
                if (d34 > d48) { // from (-4, -4)
                    d34 = d48;
                }
                d34 += 1;
            }
        }

        if (rc.onTheMap(l184)) { // check (-3, 5)
            if (rc.canSenseLocation(l184) && rc.sensePassability(l184)) { 
                if (d184 > d170) { // from (-2, 4)
                    d184 = d170;
                }
                if (d184 > d169) { // from (-3, 4)
                    d184 = d169;
                }
                if (d184 > d185) { // from (-2, 5)
                    d184 = d185;
                }
                if (d184 > d168) { // from (-4, 4)
                    d184 = d168;
                }
                d184 += 1;
            }
        }

        if (rc.onTheMap(l40)) { // check (3, -5)
            if (rc.canSenseLocation(l40) && rc.sensePassability(l40)) { 
                if (d40 > d54) { // from (2, -4)
                    d40 = d54;
                }
                if (d40 > d55) { // from (3, -4)
                    d40 = d55;
                }
                if (d40 > d39) { // from (2, -5)
                    d40 = d39;
                }
                if (d40 > d56) { // from (4, -4)
                    d40 = d56;
                }
                d40 += 1;
            }
        }

        if (rc.onTheMap(l190)) { // check (3, 5)
            if (rc.canSenseLocation(l190) && rc.sensePassability(l190)) { 
                if (d190 > d174) { // from (2, 4)
                    d190 = d174;
                }
                if (d190 > d175) { // from (3, 4)
                    d190 = d175;
                }
                if (d190 > d189) { // from (2, 5)
                    d190 = d189;
                }
                if (d190 > d176) { // from (4, 4)
                    d190 = d176;
                }
                d190 += 1;
            }
        }

        if (rc.onTheMap(l72)) { // check (5, -3)
            if (rc.canSenseLocation(l72) && rc.sensePassability(l72)) { 
                if (d72 > d86) { // from (4, -2)
                    d72 = d86;
                }
                if (d72 > d71) { // from (4, -3)
                    d72 = d71;
                }
                if (d72 > d87) { // from (5, -2)
                    d72 = d87;
                }
                if (d72 > d56) { // from (4, -4)
                    d72 = d56;
                }
                d72 += 1;
            }
        }

        if (rc.onTheMap(l162)) { // check (5, 3)
            if (rc.canSenseLocation(l162) && rc.sensePassability(l162)) { 
                if (d162 > d146) { // from (4, 2)
                    d162 = d146;
                }
                if (d162 > d161) { // from (4, 3)
                    d162 = d161;
                }
                if (d162 > d147) { // from (5, 2)
                    d162 = d147;
                }
                if (d162 > d176) { // from (4, 4)
                    d162 = d176;
                }
                d162 += 1;
            }
        }


        // System.out.println("LOCAL DISTANCES:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d184 + "\t" + d185 + "\t" + d186 + "\t" + d187 + "\t" + d188 + "\t" + d189 + "\t" + d190 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d168 + "\t" + d169 + "\t" + d170 + "\t" + d171 + "\t" + d172 + "\t" + d173 + "\t" + d174 + "\t" + d175 + "\t" + d176 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d152 + "\t" + d153 + "\t" + d154 + "\t" + d155 + "\t" + d156 + "\t" + d157 + "\t" + d158 + "\t" + d159 + "\t" + d160 + "\t" + d161 + "\t" + d162 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d137 + "\t" + d138 + "\t" + d139 + "\t" + d140 + "\t" + d141 + "\t" + d142 + "\t" + d143 + "\t" + d144 + "\t" + d145 + "\t" + d146 + "\t" + d147 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d122 + "\t" + d123 + "\t" + d124 + "\t" + d125 + "\t" + d126 + "\t" + d127 + "\t" + d128 + "\t" + d129 + "\t" + d130 + "\t" + d131 + "\t" + d132 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d107 + "\t" + d108 + "\t" + d109 + "\t" + d110 + "\t" + d111 + "\t" + d112 + "\t" + d113 + "\t" + d114 + "\t" + d115 + "\t" + d116 + "\t" + d117 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d92 + "\t" + d93 + "\t" + d94 + "\t" + d95 + "\t" + d96 + "\t" + d97 + "\t" + d98 + "\t" + d99 + "\t" + d100 + "\t" + d101 + "\t" + d102 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d77 + "\t" + d78 + "\t" + d79 + "\t" + d80 + "\t" + d81 + "\t" + d82 + "\t" + d83 + "\t" + d84 + "\t" + d85 + "\t" + d86 + "\t" + d87 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + d62 + "\t" + d63 + "\t" + d64 + "\t" + d65 + "\t" + d66 + "\t" + d67 + "\t" + d68 + "\t" + d69 + "\t" + d70 + "\t" + d71 + "\t" + d72 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + d48 + "\t" + d49 + "\t" + d50 + "\t" + d51 + "\t" + d52 + "\t" + d53 + "\t" + d54 + "\t" + d55 + "\t" + d56 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + d34 + "\t" + d35 + "\t" + d36 + "\t" + d37 + "\t" + d38 + "\t" + d39 + "\t" + d40 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("DIRECTIONS:");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir184 + "\t" + dir185 + "\t" + dir186 + "\t" + dir187 + "\t" + dir188 + "\t" + dir189 + "\t" + dir190 + "\t" + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir168 + "\t" + dir169 + "\t" + dir170 + "\t" + dir171 + "\t" + dir172 + "\t" + dir173 + "\t" + dir174 + "\t" + dir175 + "\t" + dir176 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir152 + "\t" + dir153 + "\t" + dir154 + "\t" + dir155 + "\t" + dir156 + "\t" + dir157 + "\t" + dir158 + "\t" + dir159 + "\t" + dir160 + "\t" + dir161 + "\t" + dir162 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir137 + "\t" + dir138 + "\t" + dir139 + "\t" + dir140 + "\t" + dir141 + "\t" + dir142 + "\t" + dir143 + "\t" + dir144 + "\t" + dir145 + "\t" + dir146 + "\t" + dir147 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir122 + "\t" + dir123 + "\t" + dir124 + "\t" + dir125 + "\t" + dir126 + "\t" + dir127 + "\t" + dir128 + "\t" + dir129 + "\t" + dir130 + "\t" + dir131 + "\t" + dir132 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir107 + "\t" + dir108 + "\t" + dir109 + "\t" + dir110 + "\t" + dir111 + "\t" + dir112 + "\t" + dir113 + "\t" + dir114 + "\t" + dir115 + "\t" + dir116 + "\t" + dir117 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir92 + "\t" + dir93 + "\t" + dir94 + "\t" + dir95 + "\t" + dir96 + "\t" + dir97 + "\t" + dir98 + "\t" + dir99 + "\t" + dir100 + "\t" + dir101 + "\t" + dir102 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir77 + "\t" + dir78 + "\t" + dir79 + "\t" + dir80 + "\t" + dir81 + "\t" + dir82 + "\t" + dir83 + "\t" + dir84 + "\t" + dir85 + "\t" + dir86 + "\t" + dir87 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + dir62 + "\t" + dir63 + "\t" + dir64 + "\t" + dir65 + "\t" + dir66 + "\t" + dir67 + "\t" + dir68 + "\t" + dir69 + "\t" + dir70 + "\t" + dir71 + "\t" + dir72 + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + dir48 + "\t" + dir49 + "\t" + dir50 + "\t" + dir51 + "\t" + dir52 + "\t" + dir53 + "\t" + dir54 + "\t" + dir55 + "\t" + dir56 + "\t" + "\t" + "\t");
        // System.out.println("\t" + "\t" + "\t" + "\t" + "\t" + dir34 + "\t" + dir35 + "\t" + dir36 + "\t" + dir37 + "\t" + dir38 + "\t" + dir39 + "\t" + dir40 + "\t" + "\t" + "\t" + "\t");

        int target_dx = target.x - l112.x;
        int target_dy = target.y - l112.y;
        switch (target_dx) {
                case -5:
                    switch (target_dy) {
                        case -3:
                            return direction(d62); // destination is at relative location (-5, -3)
                        case -2:
                            return direction(d77); // destination is at relative location (-5, -2)
                        case -1:
                            return direction(d92); // destination is at relative location (-5, -1)
                        case 0:
                            return direction(d107); // destination is at relative location (-5, 0)
                        case 1:
                            return direction(d122); // destination is at relative location (-5, 1)
                        case 2:
                            return direction(d137); // destination is at relative location (-5, 2)
                        case 3:
                            return direction(d152); // destination is at relative location (-5, 3)
                    }
                    break;
                case -4:
                    switch (target_dy) {
                        case -4:
                            return direction(d48); // destination is at relative location (-4, -4)
                        case -3:
                            return direction(d63); // destination is at relative location (-4, -3)
                        case -2:
                            return direction(d78); // destination is at relative location (-4, -2)
                        case -1:
                            return direction(d93); // destination is at relative location (-4, -1)
                        case 0:
                            return direction(d108); // destination is at relative location (-4, 0)
                        case 1:
                            return direction(d123); // destination is at relative location (-4, 1)
                        case 2:
                            return direction(d138); // destination is at relative location (-4, 2)
                        case 3:
                            return direction(d153); // destination is at relative location (-4, 3)
                        case 4:
                            return direction(d168); // destination is at relative location (-4, 4)
                    }
                    break;
                case -3:
                    switch (target_dy) {
                        case -5:
                            return direction(d34); // destination is at relative location (-3, -5)
                        case -4:
                            return direction(d49); // destination is at relative location (-3, -4)
                        case -3:
                            return direction(d64); // destination is at relative location (-3, -3)
                        case -2:
                            return direction(d79); // destination is at relative location (-3, -2)
                        case -1:
                            return direction(d94); // destination is at relative location (-3, -1)
                        case 0:
                            return direction(d109); // destination is at relative location (-3, 0)
                        case 1:
                            return direction(d124); // destination is at relative location (-3, 1)
                        case 2:
                            return direction(d139); // destination is at relative location (-3, 2)
                        case 3:
                            return direction(d154); // destination is at relative location (-3, 3)
                        case 4:
                            return direction(d169); // destination is at relative location (-3, 4)
                        case 5:
                            return direction(d184); // destination is at relative location (-3, 5)
                    }
                    break;
                case -2:
                    switch (target_dy) {
                        case -5:
                            return direction(d35); // destination is at relative location (-2, -5)
                        case -4:
                            return direction(d50); // destination is at relative location (-2, -4)
                        case -3:
                            return direction(d65); // destination is at relative location (-2, -3)
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
                        case 3:
                            return direction(d155); // destination is at relative location (-2, 3)
                        case 4:
                            return direction(d170); // destination is at relative location (-2, 4)
                        case 5:
                            return direction(d185); // destination is at relative location (-2, 5)
                    }
                    break;
                case -1:
                    switch (target_dy) {
                        case -5:
                            return direction(d36); // destination is at relative location (-1, -5)
                        case -4:
                            return direction(d51); // destination is at relative location (-1, -4)
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
                        case 4:
                            return direction(d171); // destination is at relative location (-1, 4)
                        case 5:
                            return direction(d186); // destination is at relative location (-1, 5)
                    }
                    break;
                case 0:
                    switch (target_dy) {
                        case -5:
                            return direction(d37); // destination is at relative location (0, -5)
                        case -4:
                            return direction(d52); // destination is at relative location (0, -4)
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
                        case 4:
                            return direction(d172); // destination is at relative location (0, 4)
                        case 5:
                            return direction(d187); // destination is at relative location (0, 5)
                    }
                    break;
                case 1:
                    switch (target_dy) {
                        case -5:
                            return direction(d38); // destination is at relative location (1, -5)
                        case -4:
                            return direction(d53); // destination is at relative location (1, -4)
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
                        case 4:
                            return direction(d173); // destination is at relative location (1, 4)
                        case 5:
                            return direction(d188); // destination is at relative location (1, 5)
                    }
                    break;
                case 2:
                    switch (target_dy) {
                        case -5:
                            return direction(d39); // destination is at relative location (2, -5)
                        case -4:
                            return direction(d54); // destination is at relative location (2, -4)
                        case -3:
                            return direction(d69); // destination is at relative location (2, -3)
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
                        case 3:
                            return direction(d159); // destination is at relative location (2, 3)
                        case 4:
                            return direction(d174); // destination is at relative location (2, 4)
                        case 5:
                            return direction(d189); // destination is at relative location (2, 5)
                    }
                    break;
                case 3:
                    switch (target_dy) {
                        case -5:
                            return direction(d40); // destination is at relative location (3, -5)
                        case -4:
                            return direction(d55); // destination is at relative location (3, -4)
                        case -3:
                            return direction(d70); // destination is at relative location (3, -3)
                        case -2:
                            return direction(d85); // destination is at relative location (3, -2)
                        case -1:
                            return direction(d100); // destination is at relative location (3, -1)
                        case 0:
                            return direction(d115); // destination is at relative location (3, 0)
                        case 1:
                            return direction(d130); // destination is at relative location (3, 1)
                        case 2:
                            return direction(d145); // destination is at relative location (3, 2)
                        case 3:
                            return direction(d160); // destination is at relative location (3, 3)
                        case 4:
                            return direction(d175); // destination is at relative location (3, 4)
                        case 5:
                            return direction(d190); // destination is at relative location (3, 5)
                    }
                    break;
                case 4:
                    switch (target_dy) {
                        case -4:
                            return direction(d56); // destination is at relative location (4, -4)
                        case -3:
                            return direction(d71); // destination is at relative location (4, -3)
                        case -2:
                            return direction(d86); // destination is at relative location (4, -2)
                        case -1:
                            return direction(d101); // destination is at relative location (4, -1)
                        case 0:
                            return direction(d116); // destination is at relative location (4, 0)
                        case 1:
                            return direction(d131); // destination is at relative location (4, 1)
                        case 2:
                            return direction(d146); // destination is at relative location (4, 2)
                        case 3:
                            return direction(d161); // destination is at relative location (4, 3)
                        case 4:
                            return direction(d176); // destination is at relative location (4, 4)
                    }
                    break;
                case 5:
                    switch (target_dy) {
                        case -3:
                            return direction(d72); // destination is at relative location (5, -3)
                        case -2:
                            return direction(d87); // destination is at relative location (5, -2)
                        case -1:
                            return direction(d102); // destination is at relative location (5, -1)
                        case 0:
                            return direction(d117); // destination is at relative location (5, 0)
                        case 1:
                            return direction(d132); // destination is at relative location (5, 1)
                        case 2:
                            return direction(d147); // destination is at relative location (5, 2)
                        case 3:
                            return direction(d162); // destination is at relative location (5, 3)
                    }
                    break;
        }
        
        ans = Double.POSITIVE_INFINITY;
        bestScore = 0;
        currDist = Math.sqrt(l112.distanceSquaredTo(target));
        
        score62 = (currDist - Math.sqrt(l62.distanceSquaredTo(target))) / d62;
        if (score62 > bestScore) {
            bestScore = score62;
            ans = d62;
        }

        score77 = (currDist - Math.sqrt(l77.distanceSquaredTo(target))) / d77;
        if (score77 > bestScore) {
            bestScore = score77;
            ans = d77;
        }

        score92 = (currDist - Math.sqrt(l92.distanceSquaredTo(target))) / d92;
        if (score92 > bestScore) {
            bestScore = score92;
            ans = d92;
        }

        score107 = (currDist - Math.sqrt(l107.distanceSquaredTo(target))) / d107;
        if (score107 > bestScore) {
            bestScore = score107;
            ans = d107;
        }

        score122 = (currDist - Math.sqrt(l122.distanceSquaredTo(target))) / d122;
        if (score122 > bestScore) {
            bestScore = score122;
            ans = d122;
        }

        score137 = (currDist - Math.sqrt(l137.distanceSquaredTo(target))) / d137;
        if (score137 > bestScore) {
            bestScore = score137;
            ans = d137;
        }

        score152 = (currDist - Math.sqrt(l152.distanceSquaredTo(target))) / d152;
        if (score152 > bestScore) {
            bestScore = score152;
            ans = d152;
        }

        score48 = (currDist - Math.sqrt(l48.distanceSquaredTo(target))) / d48;
        if (score48 > bestScore) {
            bestScore = score48;
            ans = d48;
        }

        score63 = (currDist - Math.sqrt(l63.distanceSquaredTo(target))) / d63;
        if (score63 > bestScore) {
            bestScore = score63;
            ans = d63;
        }

        score153 = (currDist - Math.sqrt(l153.distanceSquaredTo(target))) / d153;
        if (score153 > bestScore) {
            bestScore = score153;
            ans = d153;
        }

        score168 = (currDist - Math.sqrt(l168.distanceSquaredTo(target))) / d168;
        if (score168 > bestScore) {
            bestScore = score168;
            ans = d168;
        }

        score34 = (currDist - Math.sqrt(l34.distanceSquaredTo(target))) / d34;
        if (score34 > bestScore) {
            bestScore = score34;
            ans = d34;
        }

        score49 = (currDist - Math.sqrt(l49.distanceSquaredTo(target))) / d49;
        if (score49 > bestScore) {
            bestScore = score49;
            ans = d49;
        }

        score169 = (currDist - Math.sqrt(l169.distanceSquaredTo(target))) / d169;
        if (score169 > bestScore) {
            bestScore = score169;
            ans = d169;
        }

        score184 = (currDist - Math.sqrt(l184.distanceSquaredTo(target))) / d184;
        if (score184 > bestScore) {
            bestScore = score184;
            ans = d184;
        }

        score35 = (currDist - Math.sqrt(l35.distanceSquaredTo(target))) / d35;
        if (score35 > bestScore) {
            bestScore = score35;
            ans = d35;
        }

        score185 = (currDist - Math.sqrt(l185.distanceSquaredTo(target))) / d185;
        if (score185 > bestScore) {
            bestScore = score185;
            ans = d185;
        }

        score36 = (currDist - Math.sqrt(l36.distanceSquaredTo(target))) / d36;
        if (score36 > bestScore) {
            bestScore = score36;
            ans = d36;
        }

        score186 = (currDist - Math.sqrt(l186.distanceSquaredTo(target))) / d186;
        if (score186 > bestScore) {
            bestScore = score186;
            ans = d186;
        }

        score37 = (currDist - Math.sqrt(l37.distanceSquaredTo(target))) / d37;
        if (score37 > bestScore) {
            bestScore = score37;
            ans = d37;
        }

        score187 = (currDist - Math.sqrt(l187.distanceSquaredTo(target))) / d187;
        if (score187 > bestScore) {
            bestScore = score187;
            ans = d187;
        }

        score38 = (currDist - Math.sqrt(l38.distanceSquaredTo(target))) / d38;
        if (score38 > bestScore) {
            bestScore = score38;
            ans = d38;
        }

        score188 = (currDist - Math.sqrt(l188.distanceSquaredTo(target))) / d188;
        if (score188 > bestScore) {
            bestScore = score188;
            ans = d188;
        }

        score39 = (currDist - Math.sqrt(l39.distanceSquaredTo(target))) / d39;
        if (score39 > bestScore) {
            bestScore = score39;
            ans = d39;
        }

        score189 = (currDist - Math.sqrt(l189.distanceSquaredTo(target))) / d189;
        if (score189 > bestScore) {
            bestScore = score189;
            ans = d189;
        }

        score40 = (currDist - Math.sqrt(l40.distanceSquaredTo(target))) / d40;
        if (score40 > bestScore) {
            bestScore = score40;
            ans = d40;
        }

        score55 = (currDist - Math.sqrt(l55.distanceSquaredTo(target))) / d55;
        if (score55 > bestScore) {
            bestScore = score55;
            ans = d55;
        }

        score175 = (currDist - Math.sqrt(l175.distanceSquaredTo(target))) / d175;
        if (score175 > bestScore) {
            bestScore = score175;
            ans = d175;
        }

        score190 = (currDist - Math.sqrt(l190.distanceSquaredTo(target))) / d190;
        if (score190 > bestScore) {
            bestScore = score190;
            ans = d190;
        }

        score56 = (currDist - Math.sqrt(l56.distanceSquaredTo(target))) / d56;
        if (score56 > bestScore) {
            bestScore = score56;
            ans = d56;
        }

        score71 = (currDist - Math.sqrt(l71.distanceSquaredTo(target))) / d71;
        if (score71 > bestScore) {
            bestScore = score71;
            ans = d71;
        }

        score161 = (currDist - Math.sqrt(l161.distanceSquaredTo(target))) / d161;
        if (score161 > bestScore) {
            bestScore = score161;
            ans = d161;
        }

        score176 = (currDist - Math.sqrt(l176.distanceSquaredTo(target))) / d176;
        if (score176 > bestScore) {
            bestScore = score176;
            ans = d176;
        }

        score72 = (currDist - Math.sqrt(l72.distanceSquaredTo(target))) / d72;
        if (score72 > bestScore) {
            bestScore = score72;
            ans = d72;
        }

        score87 = (currDist - Math.sqrt(l87.distanceSquaredTo(target))) / d87;
        if (score87 > bestScore) {
            bestScore = score87;
            ans = d87;
        }

        score102 = (currDist - Math.sqrt(l102.distanceSquaredTo(target))) / d102;
        if (score102 > bestScore) {
            bestScore = score102;
            ans = d102;
        }

        score117 = (currDist - Math.sqrt(l117.distanceSquaredTo(target))) / d117;
        if (score117 > bestScore) {
            bestScore = score117;
            ans = d117;
        }

        score132 = (currDist - Math.sqrt(l132.distanceSquaredTo(target))) / d132;
        if (score132 > bestScore) {
            bestScore = score132;
            ans = d132;
        }

        score147 = (currDist - Math.sqrt(l147.distanceSquaredTo(target))) / d147;
        if (score147 > bestScore) {
            bestScore = score147;
            ans = d147;
        }

        score162 = (currDist - Math.sqrt(l162.distanceSquaredTo(target))) / d162;
        if (score162 > bestScore) {
            bestScore = score162;
            ans = d162;
        }

        
        return direction(ans);
    }
}
