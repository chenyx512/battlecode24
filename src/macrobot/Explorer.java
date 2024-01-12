// https://raw.githubusercontent.com/IvanGeffner/Battlecode23/master/fortytwo/Explore.java
package macrobot;

import battlecode.common.MapLocation;
import macrobot.fast.FastMath;

public class Explorer extends RobotPlayer {
    private static MapLocation exploreLoc = null;

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

    public static MapLocation getUnseenExploreTarget() {
        /* Pick a random target that is not recorded yet
        * */
        if (exploreLoc != null && rc.canSenseLocation(exploreLoc)) exploreLoc = null;
        checkExploreLoc();
        if (exploreLoc == null){
            if (rc.getRoundNum() < 100) getEmergencyTargetClose(15);
            else getEmergencyTarget(15);
        }
        return exploreLoc;
    }

    static void checkExploreLoc(){
        if (MapRecorder.getData(exploreLoc) > 0) {
            exploreLoc = null;
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
