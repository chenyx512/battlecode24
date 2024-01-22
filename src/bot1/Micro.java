package bot1;

import battlecode.common.*;
import bot1.fast.*;

public class Micro extends Robot {
    private static final int STATE_OFFENSIVE = 1;
    private static final int STATE_HOLDING = 2;
    private static final int STATE_DEFENSIVE = 3;
    private static final int STATE_BUILDING = 4;
    private static int state;

    private static MicroDirection bestMicro;
    private static boolean shouldPlanStepAttack;
    private static int myTotalStrength, oppTotalStrength;

    static boolean act() throws GameActionException {
        // assumptions
        assert (GameConstants.ATTACK_RADIUS_SQUARED == GameConstants.HEAL_RADIUS_SQUARED) && (GameConstants.ATTACK_RADIUS_SQUARED == 4);
        if (rc.getRoundNum() <= GameConstants.SETUP_ROUNDS) {
            if (SpecialtyManager.isBuilder() && rc.getRoundNum() > 150) {
                tryDropTrap();
            }
            return false;
        }

        if (Cache.nearbyEnemies.length == 0) {
            tryHeal();
            if (rc.isActionReady() && Cache.allyToHeal && !SpecialtyManager.isHealer(Cache.healingTarget) && SpecialtyManager.isHealer()) {
                PathFinder.move(Cache.healingTarget.location);
                tryHeal();
                return true;
            }
            return false;
        } else {
            tryAttack();
            tryDropTrap();
            bestMicro = getBestMicro();
            tryMove(bestMicro.dir);
            Cache.closestEnemy = bestMicro.closestEnemyLoc;
            tryAttack();
            tryDropTrap();
            if (state != STATE_OFFENSIVE || SpecialtyManager.isHealer()) {
                tryHeal();
            }
            return true;
        }
    }

    private static MicroDirection getBestMicro() throws GameActionException {
        oppTotalStrength = 0;
        myTotalStrength = rc.getHealth();
        // we should plan step attack if we can attack next turn
        shouldPlanStepAttack = false;
        if (rc.getActionCooldownTurns() < 20) {
            shouldPlanStepAttack = true;
        }

        MicroDirection[] micros = new MicroDirection[9];
        for (int i = 9; --i >= 0;)
            micros[i] = new MicroDirection(Constants.ALL_DIRECTIONS[i]);
        RobotInfo[] enemies = Cache.nearbyEnemies;
        for (int i = enemies.length;
             --i >= 0 && Clock.getBytecodesLeft() >= Constants.MICRO_MIN_BYTECODE_REMAINING;) {
            RobotInfo enemy = enemies[i];
            oppTotalStrength += enemy.health;
            micros[0].updateEnemy(enemy);
            micros[1].updateEnemy(enemy);
            micros[2].updateEnemy(enemy);
            micros[3].updateEnemy(enemy);
            micros[4].updateEnemy(enemy);
            micros[5].updateEnemy(enemy);
            micros[6].updateEnemy(enemy);
            micros[7].updateEnemy(enemy);
            micros[8].updateEnemy(enemy);
        }
        RobotInfo[] allies = Cache.nearbyFriends;
        for (int i = allies.length;
             --i >= 0 && Clock.getBytecodesLeft() >= Constants.MICRO_MIN_BYTECODE_REMAINING;) {
            RobotInfo ally = allies[i];
            myTotalStrength += ally.health;
            micros[0].updateAlly(ally);
            micros[1].updateAlly(ally);
            micros[2].updateAlly(ally);
            micros[3].updateAlly(ally);
            micros[4].updateAlly(ally);
            micros[5].updateAlly(ally);
            micros[6].updateAlly(ally);
            micros[7].updateAlly(ally);
            micros[8].updateAlly(ally);
        }
        if (rc.getHealth() < 450) {
            state = STATE_DEFENSIVE;
        } else if (SpecialtyManager.isBuilder()) {
            state = STATE_BUILDING;
        } else if (rc.getHealth() < 700) {
            state = STATE_HOLDING;
        } else {
            state = STATE_OFFENSIVE;
        }
        Debug.printString(Debug.MICRO, "micro" + state);

        MicroDirection micro = micros[8];
        for (int i = 0; i < 8; ++i) {
            if (micros[i].isBetterThan(micro)) micro = micros[i];
        }
        return micro;
    }

    private static void tryAttack() throws GameActionException {
        if (!rc.isActionReady())
            return;
        RobotInfo bestTarget = null;
        double bestScore = -Double.MAX_VALUE;
        for (RobotInfo r : rc.senseNearbyRobots(GameConstants.ATTACK_RADIUS_SQUARED, oppTeam)) {
            double score = getAttackTargetScore(r);
            if (score > bestScore) {
                bestScore = score;
                bestTarget = r;
            }
        }
        if (bestTarget != null && rc.canAttack(bestTarget.location)) {
            rc.attack(bestTarget.location);
            if (bestTarget.health <= rc.getAttackDamage()) {
                KillRecorder.recordKill();
            }
            if (rc.isActionReady())
                tryAttack();
        }
    }

    private static void tryDropTrap() throws GameActionException {
        if (!Constants.USE_TRAP)
            return;
        if (!rc.isActionReady() || Cache.closestEnemy == null)
            return;
        if (Cache.nearbyEnemies.length < 6 || Cache.nearbyFriends.length < 4)
            return;
        Direction dir = rc.getLocation().directionTo(Cache.closestEnemy);
        MapLocation loc = rc.getLocation().add(dir);
        MapLocation loc2 = rc.getLocation().add(dir.rotateLeft());
        MapLocation loc3 = rc.getLocation().add(dir.rotateRight());
        MapLocation nextLoc = loc.add(dir);
        // avoid dropping trap when there is obstacle in between
        if (rc.canSenseLocation(nextLoc) && !rc.sensePassability(nextLoc))
            return;
        boolean canStun = true;
        for (Direction d : Constants.MOVEABLE_DIRECTIONS) {
            MapLocation newLoc = rc.getLocation().add(d);
            if (rc.canSenseLocation(newLoc) && rc.senseMapInfo(newLoc).getTrapType() == TrapType.STUN) {
                canStun = false;
                break;
            }
        }
        if (canStun && loc.isWithinDistanceSquared(Cache.closestEnemy, 8) && rc.canBuild(TrapType.STUN, loc)) {
            rc.build(TrapType.STUN, loc);
        } else if (canStun && rc.getLocation().isWithinDistanceSquared(Cache.closestEnemy, 8) && rc.canBuild(TrapType.STUN, rc.getLocation())) {
            rc.build(TrapType.STUN, rc.getLocation());
        } else if (canStun && loc2.isWithinDistanceSquared(Cache.closestEnemy, 8) && rc.canBuild(TrapType.STUN, loc2)) {
            rc.build(TrapType.STUN, loc2);
        } else if (canStun && loc3.isWithinDistanceSquared(Cache.closestEnemy, 8) && rc.canBuild(TrapType.STUN, loc3)) {
            rc.build(TrapType.STUN, loc3);
        }
    }

    private static void tryHeal() throws GameActionException {
        if (!rc.isActionReady())
            return;
        if (!SpecialtyManager.canHeal())
            return;
        RobotInfo healingTarget = null;
        double bestScore = -Double.MAX_VALUE;
        for (RobotInfo r: rc.senseNearbyRobots(GameConstants.HEAL_RADIUS_SQUARED, myTeam)) {
            if (r.health == GameConstants.DEFAULT_HEALTH)
                continue;
            double score = getHealingTargetScore(r);
            if (score > bestScore) {
                bestScore = score;
                healingTarget = r;
            }
        }
        if (healingTarget != null && rc.canHeal(healingTarget.location))
            rc.heal(healingTarget.location);
    }

    private static double getRobotScore(RobotInfo r) {
        // output is between 3 and 10
        double score = 0;
        switch (r.getAttackLevel()) { // according to DPS
            case 0: score += 1; break;
            case 1: score += 1.1; break;
            case 2: score += 1.22; break;
            case 3: score += 1.35; break;
            case 4: score += 1.5; break;
            case 5: score += 1.85; break;
            case 6: score += 2.5; break;
        }
        switch (r.getHealLevel()) { // according to DPS
            case 0: score += 1; break;
            case 1: score += 1.08; break;
            case 2: score += 1.16; break;
            case 3: score += 1.26; break;
            case 4: score += 1.3; break;
            case 5: score += 1.35; break;
            case 6: score += 1.66; break;
        }
        switch (r.getBuildLevel()) { // according to cost of building
            case 0: score += 1; break;
            case 1: score += 1 / 0.9; break;
            case 2: score += 1 / 0.85; break;
            case 3: score += 1 / 0.8; break;
            case 4: score += 1 / 0.7; break;
            case 5: score += 1 / 0.6; break;
            case 6: score += 1 / 0.5; break;
        }
        return score;
    }

    public static double getHealingTargetScore(RobotInfo r) {
        if (r.health == GameConstants.DEFAULT_HEALTH)
            return -1e9;
        double score = getRobotScore(r);
        if (!SpecialtyManager.isHealer(r))
            score += 1e5; // prioritize non-healers, since they always more important
        return score;
    }

    static double getAttackTargetScore(RobotInfo r) {
        double score = 0;
        if (r.health <= rc.getAttackDamage()) // prioritize anything we can kill
            score += 1e9;
        if (r.hasFlag()) {
            score += 1e8;
        }
        int timeToKill = (r.getHealth() + rc.getAttackDamage() - 1) / rc.getAttackDamage();
        score += getRobotScore(r) / timeToKill;
        return score;
    }

    private static Direction canReachInOneStep(MapLocation loc) {
        /*
        reach means sq_dis <= 4
        returns null if not reachable, otherwise return the direction that can reach, including center
         */
        MapLocation curLoc = rc.getLocation();
        int dis = curLoc.distanceSquaredTo(loc);
        if (dis <= GameConstants.ATTACK_RADIUS_SQUARED) { // WARNING: assume attack and healing same radius
            return Direction.CENTER;
        } else if (dis > 8) {
            return null;
        } else {
            // TODO out of the three, need to evaluate which move is best
            Direction dir = rc.getLocation().directionTo(loc);
            if (rc.canMove(dir) && curLoc.add(dir).distanceSquaredTo(loc) <= GameConstants.ATTACK_RADIUS_SQUARED)
                return dir;
            if (rc.canMove(dir.rotateLeft()) && curLoc.add(dir.rotateLeft()).distanceSquaredTo(loc) <= GameConstants.ATTACK_RADIUS_SQUARED)
                return dir.rotateLeft();
            if (rc.canMove(dir.rotateRight()) && curLoc.add(dir.rotateRight()).distanceSquaredTo(loc) <= GameConstants.ATTACK_RADIUS_SQUARED)
                return dir.rotateRight();
        }
        return null;
    }

    private static boolean allowedToStandStill(MapLocation enemyLocation) {
        if (rc.getHealth() < Constants.MIN_HEALTH_TO_STAND)
            return false;
        if (enemyLocation.isWithinDistanceSquared(rc.getLocation(), GameConstants.ATTACK_RADIUS_SQUARED)) {
            return false;
        }
        Direction directionToUs = enemyLocation.directionTo(rc.getLocation());
        MapLocation a = enemyLocation.add(directionToUs);
        MapLocation b = enemyLocation.add(directionToUs.rotateLeft());
        MapLocation c = enemyLocation.add(directionToUs.rotateRight());
        if (MapRecorder.getPassible(a) && rc.getLocation().isWithinDistanceSquared(a, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(Cache.nearbyFriends, r -> r.location.isWithinDistanceSquared(a, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        if (MapRecorder.getPassible(b) && rc.getLocation().isWithinDistanceSquared(b, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(Cache.nearbyFriends, r -> r.location.isWithinDistanceSquared(b, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        if (MapRecorder.getPassible(c) && rc.getLocation().isWithinDistanceSquared(c, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(Cache.nearbyFriends, r -> r.location.isWithinDistanceSquared(c, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        return true;
    }

    static class MicroDirection {
        Direction dir;
        MapLocation loc;
        int canMove;
        int needFill;
        int canAttack;
        int canKill;
        int numAttackRange;
        int numAttackRangeNext;
        int allyWithinBlastRange;
        int allyCloseCnt;
        int minDistanceToEnemy = 99999999;
        int minDistanceToAlly = 99999999;
        int builderDis = 99999999;
        int canHealLow, canHealHigh;
        int disToHealerHigh = 9999999;
        int disToHealer = 9999999;
        MapLocation closestEnemyLoc;

        public MicroDirection(Direction dir) throws GameActionException {
            this.dir = dir;
            this.loc = rc.getLocation().add(dir);
            if (dir == Direction.CENTER || rc.canMove(dir)) {
                canMove = 1;
            }
            // allow micro water filling
            else if (rc.canFill(this.loc) && Cache.nearbyFriends.length > 5) {
                canMove = 1;
                needFill = 1;
            }
        }

        void updateEnemy(RobotInfo enemy) {
            if (canMove == 0) return;
            int dis = loc.distanceSquaredTo(enemy.location);
            if (dis <= GameConstants.ATTACK_RADIUS_SQUARED) {
                numAttackRange++;
                if (rc.isActionReady() && needFill == 0) {
                    canAttack = 1;
                    if (enemy.health <= rc.getAttackDamage() || enemy.hasFlag)
                        canKill = 1;
                }
            }
            if (dis <= 10) {
                numAttackRangeNext++;
            }
            if (dis < minDistanceToEnemy) {
                minDistanceToEnemy = dis;
                closestEnemyLoc = enemy.location;
            }
        }

        void updateAlly(RobotInfo ally) {
            if (canMove == 0) return;
            int dis = loc.distanceSquaredTo(ally.location);
            if (dis <= 13)
                allyWithinBlastRange++;
            if (ally.getHealth() < 870 && !SpecialtyManager.isHealer(ally)) {
                // first priority is to heal a non-healer
                if (dis <= GameConstants.HEAL_RADIUS_SQUARED && rc.isActionReady()) {
                    canHealHigh = 1;
                }
                disToHealerHigh = Math.min(disToHealerHigh, dis);
            }  else if (ally.health < 870) {
                if (dis <= GameConstants.HEAL_RADIUS_SQUARED  && rc.isActionReady()) {
                    canHealLow = 1;
                }
                disToHealer = Math.min(disToHealer, dis);
            }
            if (dis < minDistanceToAlly) {
                minDistanceToAlly = dis;
            }
            if (SpecialtyManager.isBuilder(ally)) {
                builderDis = dis;
            }
        }

        boolean isBetterThan(MicroDirection other) {
            switch (state) {
                case STATE_BUILDING:
                    // play safe as builder
                    if (canMove != other.canMove) return canMove > other.canMove;
                    if (numAttackRange - canKill != other.numAttackRange - other.canKill)
                        return numAttackRange - canKill < other.numAttackRange - other.canKill;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }
                    if (builderDis != other.builderDis)
                        return builderDis > other.builderDis;
                    if (minDistanceToAlly != other.minDistanceToAlly)
                        return minDistanceToAlly < other.minDistanceToAlly;
                    return minDistanceToEnemy <= other.minDistanceToEnemy;

                case STATE_DEFENSIVE:
                    // play safe
                    if (canMove != other.canMove) return canMove > other.canMove;
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (numAttackRangeNext - canKill != other.numAttackRangeNext - other.canKill) {
                        return numAttackRangeNext - canKill < other.numAttackRangeNext - other.canKill;
                    }
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;
                    if (canHealHigh != other.canHealHigh)
                        return canHealHigh > other.canHealHigh;
                    if (canHealLow != other.canHealLow)
                        return canHealLow > other.canHealLow;
                    if (disToHealerHigh != other.disToHealerHigh)
                        return disToHealerHigh < other.disToHealerHigh;
                    if (disToHealer != other.disToHealer)
                        return disToHealer < other.disToHealer;
                    if (minDistanceToAlly != other.minDistanceToAlly)
                        return minDistanceToAlly < other.minDistanceToAlly;
                    return minDistanceToEnemy >= other.minDistanceToEnemy;

                case STATE_HOLDING:
                    if (canMove != other.canMove) return canMove > other.canMove;
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;
                    if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }
                    if (canHealHigh != other.canHealHigh)
                        return canHealHigh > other.canHealHigh;
                    if (canHealLow != other.canHealLow)
                        return canHealLow > other.canHealLow;
                    if (disToHealerHigh != other.disToHealerHigh)
                        return disToHealerHigh < other.disToHealerHigh;
                    if (disToHealer != other.disToHealer)
                        return disToHealer < other.disToHealer;
                    if (allyCloseCnt != other.allyCloseCnt) {
                        return allyCloseCnt > other.allyCloseCnt;
                    }
                    return minDistanceToEnemy <= other.minDistanceToEnemy;


                case STATE_OFFENSIVE:
                    if (canMove != other.canMove) return canMove > other.canMove;
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;

                    if (shouldPlanStepAttack) {
                        // if can attack next turn, want to have as few target as possible, but at least 1
                        if (numAttackRangeNext != other.numAttackRangeNext) {
                            if (numAttackRangeNext == 0) return false;
                            if (other.numAttackRangeNext == 0) return true;
                            return numAttackRangeNext < other.numAttackRangeNext;
                        }
                    } else if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }
                    if (allyCloseCnt != other.allyCloseCnt) {
                        return allyCloseCnt > other.allyCloseCnt;
                    }
                    return minDistanceToEnemy <= other.minDistanceToEnemy;
            }
            Debug.failFast("impossible micro state");
            return false;
        }
    }
}
