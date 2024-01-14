package bot1;

import battlecode.common.*;
import bot1.fast.*;

import java.util.Map;
import java.util.function.ToDoubleFunction;

public class Micro extends Robot {
    private static MicroDirection bestMicro;

    static boolean act() throws GameActionException {
        // assumptions
        assert (GameConstants.ATTACK_RADIUS_SQUARED == GameConstants.HEAL_RADIUS_SQUARED) && (GameConstants.ATTACK_RADIUS_SQUARED == 4);
        if (rc.getRoundNum() <= GameConstants.SETUP_ROUNDS)
            return false;

        if (Cache.nearbyEnemies.length == 0) {
            // currently healing isn't too profitable, dying is faster regain of health, do not go out of way to heal
            tryHeal();
            return false;
        } else {
            tryAttack();
            bestMicro = getBestMicro();
            if (bestMicro.canAttack == 0 &&
                    (bot1.SpecialtyManager.isBuilder() ||
                            (bestMicro.builderDis > 9999 && Cache.nearbyEnemies.length > 5)))
                tryDropTrap();
            tryMove(bestMicro.dir);
            Debug.printString(String.format("h%d dh%d", bestMicro.canHeal, bestMicro.disToHealer));
            tryAttack();
            if (bestMicro.numAttackRangeNext == 0 || SpecialtyManager.isHealer()) {
                tryHeal();
            }
            return true;
        }
    }

    private static MicroDirection getBestMicro() throws GameActionException {
        MicroDirection[] micros = new MicroDirection[9];
        for (int i = 9; --i >= 0;)
            micros[i] = new MicroDirection(Constants.ALL_DIRECTIONS[i]);
        RobotInfo[] enemies = Cache.nearbyEnemies;
        for (int i = enemies.length;
             --i >= 0 && Clock.getBytecodesLeft() >= Constants.MICRO_MIN_BYTECODE_REMAINING;) {
            RobotInfo enemy = enemies[i];
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
        if (bestTarget != null && rc.canAttack(bestTarget.location))
            rc.attack(bestTarget.location);
    }

    private static void tryDropTrap() throws GameActionException {
        if (!rc.isActionReady() || bestMicro.closestEnemyLoc == null)
            return;
        Direction dir = rc.getLocation().directionTo(bestMicro.closestEnemyLoc);
        MapLocation loc = rc.getLocation().add(dir);
        if (rc.canBuild(TrapType.EXPLOSIVE, loc) && rc.sensePassability(loc) && rc.canSenseLocation(loc.add(dir)) && rc.sensePassability(loc.add(dir))) {
            rc.build(TrapType.EXPLOSIVE, loc);
        }
        loc = rc.getLocation().add(dir.rotateLeft());
        if (rc.canBuild(TrapType.EXPLOSIVE, loc) && rc.sensePassability(loc) && rc.canSenseLocation(loc.add(dir)) && rc.sensePassability(loc.add(dir))) {
            rc.build(TrapType.EXPLOSIVE, loc);
        }
        loc = rc.getLocation().add(dir.rotateLeft());
        if (rc.canBuild(TrapType.EXPLOSIVE, loc) && rc.sensePassability(loc) && rc.canSenseLocation(loc.add(dir)) && rc.sensePassability(loc.add(dir))) {
            rc.build(TrapType.EXPLOSIVE, loc);
        }
    }

    private static void tryHeal() throws GameActionException {
        if (!rc.isActionReady())
            return;
        RobotInfo healingTarget = null;
        double bestScore = -Double.MAX_VALUE;
        // consider self healing since senseNearbyRobot doesn't return self
        if (rc.getHealth() < GameConstants.DEFAULT_HEALTH) {
            healingTarget = rc.senseRobotAtLocation(rc.getLocation());
            bestScore = getHealingTargetScore(healingTarget);
        }
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

    static double getHealingTargetScore(RobotInfo r) {
        if (r.health == GameConstants.DEFAULT_HEALTH)
            return -1e9;
        double score = getRobotScore(r) / r.getHealth();
        if (!SpecialtyManager.isHealer(r))
            score += 1e5; // prioritize non-healers, since they always more important
        return score;
    }

    static double getAttackTargetScore(RobotInfo r) {
        double score = 0;
        if (r.health <= attackHP) // prioritize anything we can kill
            score += 1e9;
        if (r.hasFlag()) {
            score += 1e8;
        }
        int timeToKill = (r.getHealth() + attackHP - 1) / attackHP;
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
        int canAttack;
        int canKill;
        int numAttackRange;
        int numAttackRangeNext;
        int allyWithinBlastRange;
        int allyCloseCnt;
        int minDistanceToEnemy = 99999999;
        int minDistanceToAlly = 99999999;
        int builderDis = 99999999;
        int canHeal;
        int disToHealer = 9999999;
        MapLocation closestEnemyLoc;

        public MicroDirection(Direction dir) throws GameActionException {
            this.dir = dir;
            this.loc = rc.getLocation().add(dir);
            if (dir == Direction.CENTER || rc.canMove(dir)) canMove = 1;
        }

        void updateEnemy(RobotInfo enemy) {
            if (canMove == 0) return;
            int dis = loc.distanceSquaredTo(enemy.location);
            if (dis <= GameConstants.ATTACK_RADIUS_SQUARED) {
                numAttackRange++;
                if (rc.isActionReady()) {
                    canAttack = 1;
                    if (enemy.health <= attackHP || enemy.hasFlag)
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
            if ((SpecialtyManager.isHealer(ally) && !SpecialtyManager.isHealer() && rc.getHealth() < 800)
                ||(!SpecialtyManager.isHealer(ally) && SpecialtyManager.isHealer() && ally.getHealth() < 1000 - healHP)) {
                if (dis <= GameConstants.HEAL_RADIUS_SQUARED) {
                    canHeal = 1;
                }
                if (dis < disToHealer)
                    disToHealer = dis;
            }
            if (dis < minDistanceToAlly) {
                minDistanceToAlly = dis;
            }
            if (SpecialtyManager.isBuilder(ally)) {
                builderDis = dis;
            }
        }

        boolean isBetterThan(MicroDirection other) {
            if (bot1.SpecialtyManager.isBuilder()) {
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
            } else if (SpecialtyManager.isHealer() || rc.getHealth() < 500) {
                // healing oriented
                if (canMove != other.canMove) return canMove > other.canMove;
                if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                    return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                if (canAttack != other.canAttack)
                    return canAttack > other.canAttack;
                if (canKill != other.canKill)
                    return canKill > other.canKill;
                if (!SpecialtyManager.isHealer() || rc.isActionReady()) {
                    if (canHeal != other.canHeal)
                        return canHeal > other.canHeal;
                    return disToHealer <= other.disToHealer;
                }
                return minDistanceToEnemy >= other.minDistanceToEnemy;
            } else {
                    if (canMove != other.canMove) return canMove > other.canMove;
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;

                    if (rc.getActionCooldownTurns() < 20) {
                        // if can attack next turn, want to have as few target as possible, but at least 1
                        if (numAttackRangeNext != other.numAttackRangeNext) {
                            if (numAttackRangeNext == 0) return false;
                            if (other.numAttackRangeNext == 0) return true;
                            return numAttackRangeNext < other.numAttackRangeNext;
                        }
                    } else if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }

                    if (allyWithinBlastRange != other.allyWithinBlastRange)
                        return allyWithinBlastRange < other.allyWithinBlastRange;

                    if (minDistanceToEnemy < GameConstants.ATTACK_RADIUS_SQUARED) {
                        return minDistanceToEnemy >= other.minDistanceToEnemy;
                    }
                    return minDistanceToEnemy <= other.minDistanceToEnemy;
            }
        }
    }
}
