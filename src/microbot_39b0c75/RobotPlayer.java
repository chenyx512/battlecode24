package microbot_39b0c75;

import battlecode.common.*;
import microbot_39b0c75.fast.*;

public strictfp class RobotPlayer {
    static RobotController rc;
    static int H, W;
    static Team myTeam, oppTeam;
    static int myTeamID, oppTeamID;


    @SuppressWarnings("unused")
    public static void run(RobotController r) throws GameActionException {
        rc = r; //this should always go first

        H = rc.getMapHeight();
        W = rc.getMapWidth();

        myTeam = rc.getTeam();
        oppTeam = myTeam.opponent();
        myTeamID = myTeam == Team.A? 1 : 2;
        oppTeamID = 3 - myTeamID;

        FastMath.initRand(rc);
        Debug.init();
        Robot.init();

        while (true) {
            try {
                int round = rc.getRoundNum();
                Robot.initTurn();
                Debug.bytecodeDebug += " BCINIT=" + Clock.getBytecodeNum();
                Robot.play();
                Debug.bytecodeDebug += "  BCPLAY=" + Clock.getBytecodeNum();
                Robot.endTurn();
                int roundEnd = rc.getRoundNum();
                if (round < roundEnd){
                    System.out.println("overrun " + round + " " + rc.getLocation().toString() + Debug.bytecodeDebug);
                    if (Constants.DEBUG_FAIL_FAST) {
                        rc.resign();
                    }
                }
                Debug.flush();
            }  catch (Exception e) {
                System.out.println("Exception");
                e.printStackTrace();
                if (Constants.DEBUG_FAIL_FAST) {
                    rc.resign();
                }
            } finally {
                Clock.yield();
            }
        }
    }
}
