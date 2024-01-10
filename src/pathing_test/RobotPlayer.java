package pathing_test;

import battlecode.common.*;
import bot1.Comms;
import bot1.Debug;
import bot1.PathFinder;
import bot1.Robot;
import bot1.fast.*;

public strictfp class RobotPlayer {
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        bot1.Robot r = new Robot(rc);

        FastMath.initRand(rc);
        Comms.init(rc);
        PathFinder.init(rc);
        bot1.Debug.init(rc);

        while (true) {
            try {
                int round = rc.getRoundNum();
                r.initTurn();
                bot1.Debug.bytecodeDebug += " BCINIT=" + Clock.getBytecodeNum();
                r.play();
                bot1.Debug.bytecodeDebug += "  BCPLAY=" + Clock.getBytecodeNum();
                r.endTurn();
                int roundEnd = rc.getRoundNum();
                if (round < roundEnd){
                    System.out.println("overrun " + round + " " + rc.getLocation().toString() + bot1.Debug.bytecodeDebug);
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
