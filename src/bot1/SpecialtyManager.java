package bot1;

import battlecode.common.*;

public class SpecialtyManager extends Robot {
    public static int duckSeqID; // a unique integer for each duck between 1-50, 0 means unset
    private static char[] duckID2seq = Constants.STRING_LEN_4200.toCharArray();
    private static int seqIDcnt;
    public static int buildLevel, attackLevel, healLevel;
    public static int attackCD, healCD;
    public static int oppAttackBase = SkillType.ATTACK.skillEffect;

    public static boolean isBuilder() {
        return duckSeqID > 0 && duckSeqID <= Constants.NUM_BUILDER;
    }

    public static boolean isBuilder(RobotInfo r) {
        int seq = duckID2seq[r.getID() - 9999];
        return seq > 0 && seq <= Constants.NUM_BUILDER;
    }

    public static boolean isHealer() {
        return duckSeqID > Constants.NUM_BUILDER
                && duckSeqID <= Constants.NUM_BUILDER + Constants.NUM_HEALER;
    }

    public static boolean isHealer(RobotInfo r) {
        int seq = duckID2seq[r.getID() - 9999];
        return seq > Constants.NUM_BUILDER
                && seq <= Constants.NUM_BUILDER + Constants.NUM_HEALER;
    }

    public static boolean canHeal() {
        if (rc.getExperience(SkillType.HEAL) == 74 && attackLevel <= 3 && (!isBuilder() && !isHealer()))
            return false;
        return true;
    }

    public static int getAttackDamage(RobotInfo r) {
        return Math.round(oppAttackBase * ((float) SkillType.ATTACK.getSkillEffect(r.getAttackLevel()) / 100 + 1));
    }

    public static boolean act() throws GameActionException {
        if (!isBuilder()) {
//            if (Cache.closestEnemy != null || rc.getCrumbs() < 1000 || rc.getRoundNum() < 1500)
//                return false;
//            if (buildLevel >= 3)
//                return false;
            return false;
        }
        if (buildLevel == 6)
            return false;
        if (!rc.isActionReady())
            return false;
        for (int i = 8; --i >= 0;) {
            MapLocation loc = rc.getLocation().add(Constants.MOVEABLE_DIRECTIONS[i]);
            if ((loc.x + loc.y) % 2 == 0 && rc.canDig(loc)) {
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
        buildLevel = rc.getLevel(SkillType.BUILD);
        attackLevel = rc.getLevel(SkillType.ATTACK);
        healLevel = rc.getLevel(SkillType.HEAL);
        GlobalUpgrade[] oppUpgrades = rc.getGlobalUpgrades(oppTeam);
        for (GlobalUpgrade upgrade: oppUpgrades) {
            if (upgrade == GlobalUpgrade.ATTACK) oppAttackBase = SkillType.ATTACK.skillEffect + upgrade.baseAttackChange;
        }
        attackCD = (int) Math.round(GameConstants.ATTACK_COOLDOWN*(1+.01*SkillType.ATTACK.getCooldown(attackLevel)));
        healCD = (int) Math.round((int) Math.round(GameConstants.HEAL_COOLDOWN*(1+.01*SkillType.HEAL.getCooldown(healLevel))));

        if (duckSeqID == 0) {
            duckSeqID = Comms.readSyncId() + 1;
            Comms.writeSyncId(duckSeqID == 50? 0 : duckSeqID);
        } else if (seqIDcnt < 50) {
            if (Comms.readSyncId() == 0) {
                // my turn to report ID
                Comms.writeSyncId(rc.getID() - 9999);
                Debug.betterAssert(duckSeqID == seqIDcnt + 1, String.format("%d %d", duckSeqID, seqIDcnt));
                duckSeqID = ++seqIDcnt;
                duckID2seq[rc.getID() - 9999] = (char) duckSeqID;
            } else {
                if (Comms.readSyncId() == rc.getID() - 9999) {
                    Comms.writeSyncId(0);
                } else {
                    duckID2seq[Comms.readSyncId()] = (char) ++seqIDcnt;
                }
            }
        }
        Debug.printString(Debug.SPECIALTY, String.format("seq%d", duckSeqID));;
    }
}
