package microbot_39b0c75;

import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;

public class SpecialtyManager extends RobotPlayer {
    public static int duckSeqID;
    private static char[] duckID2seq = Constants.STRING_LEN_4200.toCharArray();
    private static int seqIDcnt;

    public static boolean isHealer() {
        return rc.getID() % 2 == 0;
    }

    public static boolean isHealer(RobotInfo r) {
        return r.getID() % 2 == 0;
    }

    public static void initTurn() throws GameActionException {
        if (seqIDcnt < 50) {
            if (Comms.readSyncId() == 0) {
                // my turn to report ID
                Comms.writeSyncId(rc.getID());
                duckSeqID = ++seqIDcnt;
                duckID2seq[rc.getID() - 10000] = (char) duckSeqID;
                Debug.println(Debug.SPECIALTY, String.format("duck id %d seq %d", rc.getID(), duckSeqID));
            } else {
                if (Comms.readSyncId() == rc.getID() - 10000) {
                    Comms.writeSyncId(0);
                } else {
                    duckID2seq[Comms.readSyncId()] = (char) ++seqIDcnt;
                }
            }
        }
    }
}
