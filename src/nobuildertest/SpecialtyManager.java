package nobuildertest;

import battlecode.common.*;

public class SpecialtyManager extends RobotPlayer {
    public static int duckSeqID;
    private static char[] duckID2seq = Constants.STRING_LEN_4200.toCharArray();
    private static int seqIDcnt;
    private static int buildLevel, attackLevel, healLevel;

    // 0-1: builder
    // 2-25: fighter
    // 26+: healer
    public static boolean isBuilder() {
//        return rc.getRoundNum() > 50 && duckSeqID < 2;
        return false;
    }

    public static boolean isBuilder(RobotInfo r) {
//        return rc.getRoundNum() > 50 && duckID2seq[r.getID() % 4096] < 2;
        return false;
    }

    public static boolean isHealer() {
        return duckSeqID > 25;
    }

    public static boolean isHealer(RobotInfo r) {
        return duckID2seq[r.getID() % 4096] > 25;
    }

    public static boolean act() throws GameActionException {
        if (rc.getRoundNum() > GameConstants.SETUP_ROUNDS || !isBuilder())
            return false;
        if (buildLevel == 6)
            return false;
        if (!rc.isActionReady())
            return false;
        for (int i = 8; --i >= 0;) {
            MapLocation loc = rc.getLocation().add(Constants.MOVEABLE_DIRECTIONS[i]);
            if (rc.canDig(loc)) {
                rc.dig(loc);
            }
        }
        if (rc.isActionReady()) {
            for (int i = 8; --i >= 0;) {
                MapLocation loc = rc.getLocation().add(Constants.MOVEABLE_DIRECTIONS[i]);
                if (rc.canFill(loc)) {
                    rc.fill(loc);
                }
            }
        }
        return false;
    }

    public static void initTurn() throws GameActionException {
        // A1, B1, A2, B2, A3, B3.. A50, B50 for turn 1
        // A1, B1, A2, B2, A3, B3.. A50, B50 for turn 2
        buildLevel = rc.getLevel(SkillType.BUILD);
        attackLevel = rc.getLevel(SkillType.ATTACK);
        healLevel = rc.getLevel(SkillType.HEAL);

        Debug.printString(Debug.SPECIALTY, String.format("seq%d", duckSeqID));;

        if (seqIDcnt < 50) {
            if (Comms.readSyncId() == 0) {
                // my turn to report ID
                Comms.writeSyncId(rc.getID() % 4096);
                duckSeqID = seqIDcnt++;
                duckID2seq[rc.getID() % 4096] = (char) duckSeqID;
                Debug.println(Debug.SPECIALTY, String.format("duck id %d seq %d", rc.getID(), duckSeqID));
            } else {
                if (Comms.readSyncId() == rc.getID() % 4096) {
                    Comms.writeSyncId(0);
                } else {
                    duckID2seq[Comms.readSyncId()] = (char) seqIDcnt++;
                }
            }
        }
    }
}
