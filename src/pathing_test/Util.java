package pathing_test;

import battlecode.common.MapLocation;

public class Util {
    static int distance(MapLocation A, MapLocation B) {
        return Math.max(Math.abs(A.x - B.x), Math.abs(A.y - B.y));
    }
}
