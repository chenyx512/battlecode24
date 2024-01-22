// https://raw.githubusercontent.com/IvanGeffner/Battlecode23/master/fortytwo/Explore.java
package noduckonflag;

import battlecode.common.*;
import noduckonflag.fast.FastMath;

public class Explorer extends RobotPlayer {
    private static MapLocation exploreLoc = null;
    private static MapLocation flagExploreLoc = null;

    static void getEmergencyTarget(int tries) {
        int maxX = W;
        int maxY = H;
        while (tries-- > 0){
            if (exploreLoc != null) return;
            MapLocation newLoc = new MapLocation((int)(FastMath.fakefloat()*maxX), (int)(FastMath.fakefloat()*maxY));
            //if (checkDanger && Robot.comm.isEnemyTerritoryRadial(newLoc)) continue;
            if (!rc.canSenseLocation(newLoc)){
                if (MapRecorder.getData(newLoc) == 0) {
                    exploreLoc = newLoc;
                }
            }
        }
    }

    public static MapLocation getFlagTarget(int flagID, int tries) throws GameActionException {
        MapLocation flagLoc = Util.int2loc(Comms.readOppflagsLoc(flagID));
        if (flagExploreLoc == null) {
            flagExploreLoc = flagLoc;
        }
        if (Comms.readOppflagsOriginalLoc(flagID) != 0) {
            return flagLoc;
        } else {
            if (flagExploreLoc != null ) {
                if (MapRecorder.getData(flagExploreLoc) > 0 || rc.canSenseLocation(flagExploreLoc)) {
                    flagExploreLoc = null;
                }
            }
            // this gets random target around home base
            int maxX = 15;
            int maxY = 15;
            MapLocation baseLoc = flagLoc;
            while (tries-- > 0){
                if (flagExploreLoc != null) return flagExploreLoc;
                int newX = baseLoc.x + (int)(2.0f*FastMath.fakefloat()*maxX - maxX);
                int newY = baseLoc.y + (int)(2.0f*FastMath.fakefloat()*maxY - maxY);
                MapLocation newLoc = new MapLocation(newX, newY);
                if (!rc.onTheMap(newLoc)) continue;
                if (!rc.canSenseLocation(newLoc)){
                    if (MapRecorder.getData(newLoc) == 0) {
                        flagExploreLoc = newLoc;
                        return newLoc;
                    }
                }
            }
            return flagLoc;
        }
    }

    static final int CLOSE_DIST = 200;

    static void getEmergencyTargetClose(int tries){
        // this gets random target around home base
        int maxX = 12;
        int maxY = 12;
        MapLocation baseLoc = Robot.homeSpawn;
        if (baseLoc == null) baseLoc = rc.getLocation();
        while (tries-- > 0){
            if (exploreLoc != null) return;
            int newX = baseLoc.x + (int)(2.0f*FastMath.fakefloat()*maxX - maxX);
            int newY = baseLoc.y + (int)(2.0f*FastMath.fakefloat()*maxY - maxY);
            MapLocation newLoc = new MapLocation(newX, newY);
            if (!rc.onTheMap(newLoc)) continue;
            if (!rc.canSenseLocation(newLoc)){
                if (MapRecorder.getData(newLoc) == 0) {
                    exploreLoc = newLoc;
                }
            }
        }
    }


    public static MapLocation getUnseenExploreTarget() throws GameActionException {
        /* Pick a random target that is not recorded yet
        * */
        if (exploreLoc != null && rc.canSenseLocation(exploreLoc)) exploreLoc = null;
        checkExploreLoc();
        if (exploreLoc == null){
            getEmergencyTarget(25);
        }
        return exploreLoc;
    }

    static void checkExploreLoc() throws GameActionException {
        if (MapRecorder.getData(exploreLoc) > 0) {
            exploreLoc = null;
        } else if (exploreLoc != null) {
            for (Direction dir : Constants.MOVEABLE_DIRECTIONS) {
                MapLocation loc = rc.getLocation().add(dir);
                if (rc.canSenseLocation(loc) && rc.senseMapInfo(loc).isDam()) {
                    exploreLoc = null;
                    return;
                }
            }
        }
    }

    static MapLocation exploreTarget2 = null;

    static void checkExploreTarget2(){
        if (exploreTarget2 == null || rc.canSenseLocation(exploreTarget2)) exploreTarget2 = null;
    }

    public static MapLocation getAnyExploreTarget(int tries){
        /* Pick a not sensed location disregarding whether it has been recorded or not
        * */
        checkExploreTarget2();
        if (exploreTarget2 != null) return exploreTarget2;
        int maxX = rc.getMapWidth();
        int maxY = rc.getMapHeight();
        while (tries-- > 0){
            MapLocation newLoc = new MapLocation((int)(FastMath.fakefloat()*maxX), (int)(FastMath.fakefloat()*maxY));
            if (!rc.canSenseLocation(newLoc)){
                exploreTarget2 = newLoc;
                return exploreTarget2;
            }
        }
        return exploreTarget2;
    }

}
