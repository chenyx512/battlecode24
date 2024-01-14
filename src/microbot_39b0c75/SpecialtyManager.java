package microbot_39b0c75;

import battlecode.common.RobotInfo;

public class SpecialtyManager extends RobotPlayer {
    public static boolean isHealer() {
        return rc.getID() % 2 == 0;
    }

    public static boolean isHealer(RobotInfo r) {
        return r.getID() % 2 == 0;
    }
}
