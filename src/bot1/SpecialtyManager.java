package bot1;

import battlecode.common.*;

public class SpecialtyManager extends RobotPlayer {
    public static int duckSeqID;
    private static char[] duckID2seq = Constants.STRING_LEN_4200.toCharArray();
    private static int seqIDcnt;
    public static int buildLevel, attackLevel, healLevel;

    public static boolean isBuilder() {
        return duckSeqID > 0 && duckSeqID <= Constants.NUM_BUILDER;
    }

    public static boolean isBuilder(RobotInfo r) {
        int seq = duckID2seq[r.getID() % 4096];
        return seq > 0 && seq <= Constants.NUM_BUILDER;
    }

    public static boolean isHealer() {
        return duckSeqID > Constants.NUM_BUILDER
                && duckSeqID <= Constants.NUM_BUILDER + Constants.NUM_HEALER;
    }

    public static boolean isHealer(RobotInfo r) {
        int seq = duckID2seq[r.getID() % 4096];
        return seq > Constants.NUM_BUILDER
                && seq <= Constants.NUM_BUILDER + Constants.NUM_HEALER;
    }

    public static boolean canHeal() {
        if (rc.getExperience(SkillType.HEAL) == 74 && attackLevel <= 3 && (!isBuilder() && !isHealer()))
            return false;
        return true;
    }

    public static boolean act() throws GameActionException {
        if (!isBuilder()) {
            if (Cache.closestEnemy != null || rc.getCrumbs() < 1000 || rc.getRoundNum() < 1500)
                return false;
            if (buildLevel >= 3)
                return false;
        }
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
                duckSeqID = ++seqIDcnt;
                duckID2seq[rc.getID() % 4096] = (char) duckSeqID;
                Debug.println(Debug.SPECIALTY, String.format("duck id %d seq %d", rc.getID(), duckSeqID));
            } else {
                if (Comms.readSyncId() == rc.getID() % 4096) {
                    Comms.writeSyncId(0);
                } else {
                    duckID2seq[Comms.readSyncId()] = (char) ++seqIDcnt;
                }
            }
        }
    }
}
