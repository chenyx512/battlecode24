package bot1;

import battlecode.common.*;
import bot1.fast.*;

public strictfp class RobotPlayer {
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        FastMath.initRand(rc);
        Comms.init(rc);
        PathFinder.init(rc);
        Debug.init(rc);
        MapRecorder.init(rc);

        Robot r = new Robot(rc);

        while (true) {
            try {
                int round = rc.getRoundNum();
                r.initTurn();
                Debug.bytecodeDebug += " BCINIT=" + Clock.getBytecodeNum();
                r.play();
                Debug.bytecodeDebug += "  BCPLAY=" + Clock.getBytecodeNum();
                r.endTurn();
                int roundEnd = rc.getRoundNum();
                if (round < roundEnd){
                    System.out.println("overrun " + round + " " + rc.getLocation().toString() + Debug.bytecodeDebug);
                }

                Debug.flush();
            }  catch (Exception e) {
                System.out.println("Exception");
                e.printStackTrace();
            } finally {
                Clock.yield();
            }
        }
    }
}
