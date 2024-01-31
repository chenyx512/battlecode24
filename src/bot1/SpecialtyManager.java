package bot1;

import battlecode.common.*;

public class SpecialtyManager extends Robot {
    public static int duckSeqID; // a unique integer for each duck between 1-50, 0 means unset
    private static char[] duckID2seq = Constants.STRING_LEN_4200.toCharArray();
    private static int seqIDcnt;
    public static int masterID;
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
        if (rc.getExperience(SkillType.HEAL) == SkillType.HEAL.getExperience(4) - 1
                && attackLevel <= 3 && (!isBuilder() && !isHealer()))
            return false;
        return true;
    }

    public static int getAttackDamage(RobotInfo r) {
        return Math.round(oppAttackBase * ((float) SkillType.ATTACK.getSkillEffect(r.getAttackLevel()) / 100 + 1));
    }

    public static boolean act() throws GameActionException {
        if (!isBuilder()) {
            return false;
        }
        if (!rc.isActionReady())
            return false;
        boolean useWater = rc.getRoundNum() > 200 && rc.senseMapInfo(rc.getLocation()).getTeamTerritory() != myTeam;
        boolean needLevelUp = buildLevel < 4 && getDisToMyClosestSpawnCenter(rc.getLocation()) > 25;
        for (int i = 8; --i >= 0;) {
            MapLocation loc = rc.getLocation().add(Constants.MOVEABLE_DIRECTIONS[i]);
            if (needLevelUp && (loc.x + loc.y) % 2 == masterID % 2 && rc.canDig(loc)) {
                rc.dig(loc);
            }
            if (useWater && loc.x % 4 == masterID % 4 && loc.y % 4 == masterID % 4 && rc.canBuild(TrapType.WATER, loc)) {
                rc.build(TrapType.WATER, loc);
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
                if (duckSeqID == 1)
                    masterID = rc.getID() - 9999;
                // my turn to report ID
                Comms.writeSyncId(rc.getID() - 9999);
                Debug.betterAssert(duckSeqID == seqIDcnt + 1, String.format("%d %d", duckSeqID, seqIDcnt));
                duckSeqID = ++seqIDcnt;
                duckID2seq[rc.getID() - 9999] = (char) duckSeqID;
            } else {
                if (seqIDcnt == 0) {
                    masterID = Comms.readSyncId();
                }
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
