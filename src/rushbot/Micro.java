package rushbot;

import battlecode.common.*;
import rushbot.fast.*;
import java.util.function.ToDoubleFunction;

public class Micro extends Robot {
    private static RobotInfo[] nearbyEnemies, nearbyFriends;
    private static RobotInfo bestTarget, closestEnemy;

    static boolean act() throws GameActionException {
        // assumptions
        assert (GameConstants.ATTACK_RADIUS_SQUARED == GameConstants.HEAL_RADIUS_SQUARED) && (GameConstants.ATTACK_RADIUS_SQUARED == 4);

        nearbyEnemies = rc.senseNearbyRobots(-1, oppTeam);
        nearbyFriends = rc.senseNearbyRobots(-1, myTeam);

        bestTarget = null;
        closestEnemy = null;

        double bestScore = Double.MIN_VALUE;
        double closestDis = Double.MAX_VALUE;
        for (RobotInfo r : nearbyEnemies) {
            double score = getAttackTargetScore(r);
            if (score > bestScore) {
                bestScore = score;
                bestTarget = r;
            }
            double dis = r.getLocation().distanceSquaredTo(rc.getLocation());
            if (dis < closestDis) {
                closestDis = dis;
                closestEnemy = r;
            }
        }

        if (nearbyEnemies.length == 0) {
            // currently healing isn't too profitable, dying is faster regain of health, do not go out of way to heal
            tryHeal();
            return false;
        } else {
            tryAttack();
            if (rc.isActionReady()) {
                int discount = bestTarget.health <= attackHP? 1 : 0;
                if (nearbyEnemies.length - discount <= nearbyFriends.length) {
                    MapLocation targetLocation = bestTarget.location;
                    tryMove(getBestMoveDirection(loc -> getScoreForSingleEnemy(loc, targetLocation)));
                    tryAttack();
                    tryHeal();
                    Debug.printString(String.format("atk%s", targetLocation.toString()));
                } else if (!allowedToStandStill(closestEnemy.location)) {
                    tryDropTrap();
                    tryMove(getBestMoveDirection(Micro::getScoreForKiting));
                    Debug.printString("kiteback");
                } else { // if I can stand still I can also heal...
                    tryDropTrap();
                    tryHeal();
                    Debug.printString("standheal");
                }
            } else {
                if (!allowedToStandStill(closestEnemy.location)) {
                    tryMove(getBestMoveDirection(Micro::getScoreForKiting));
                    Debug.printString("kiteback");
                }
                Debug.printString("stand");
            }
            return true;
        }
    }

    private static void tryAttack() throws GameActionException {
        if (rc.canAttack(bestTarget.location)) {
            rc.attack(bestTarget.location);
        }
    }

    private static void tryDropTrap() throws GameActionException {
        if (rc.getCrumbs() < TrapType.EXPLOSIVE.buildCost || !rc.isActionReady())
            return;
        Direction dir = rc.getLocation().directionTo(closestEnemy.location);
        MapLocation loc = rc.getLocation().add(dir);
        // disallowing building traps too close to save some trap
        for (MapInfo info : rc.senseNearbyMapInfos(loc, 4)) {
            if (info.getTrapType() != TrapType.NONE)
                return;
        }
        if (rc.canBuild(TrapType.EXPLOSIVE, loc)) {
            rc.build(TrapType.EXPLOSIVE, loc);
        } else if (rc.canBuild(TrapType.EXPLOSIVE, rc.getLocation())) {
            rc.build(TrapType.EXPLOSIVE, rc.getLocation());
        }
    }

    private static void tryHeal() throws GameActionException {
        if (!rc.isActionReady())
            return;
        RobotInfo healingTarget = getHealingTarget();
        if (healingTarget != null && rc.canHeal(healingTarget.location)) {
            rc.heal(healingTarget.location);
        }
    }

    private static RobotInfo getHealingTarget() throws GameActionException {
        RobotInfo healingTarget = null;
        double bestScore = Double.MIN_VALUE;
        // consider self healing since senseNearbyRobot doesn't return self
        if (rc.getHealth() < GameConstants.DEFAULT_HEALTH) {
            healingTarget = rc.senseRobotAtLocation(rc.getLocation());
            bestScore = getHealingTargetScore(healingTarget);
        }
        for (RobotInfo r: nearbyFriends) {
            if (r.health == GameConstants.DEFAULT_HEALTH)
                continue;
            double score = getHealingTargetScore(r);
            if (score > bestScore) {
                bestScore = score;
                healingTarget = r;
            }
        }
        return healingTarget;
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
        // prioritize anyone reachable right away
        if (r.location.isWithinDistanceSquared(rc.getLocation(), GameConstants.HEAL_RADIUS_SQUARED)) {
            score += 100;
        }
        return score;
    }

    static double getAttackTargetScore(RobotInfo r) {
        double score = getRobotScore(r) * r.getHealth();
        if (r.health <= attackHP) // prioritize anything we can kill
            score += 1e9;
        Direction dir = canReachInOneStep(r.location);
        // TODO consider teammates for focus shot
        if (dir == Direction.CENTER) {
            score += 1e8; // prioritize anything we can shoot rn so we can kite back
        } else if (dir != null)  {
            score += 1e7; // prioritize anything we can shoot within one move
        }
        int timeToKill = r.getHealth() / attackHP;
        score += getRobotScore(r) / timeToKill;
        return score;
    }

    private static double getScoreForKiting(MapLocation loc) {
        double score = 0;
        int closestEnemyDis = Integer.MAX_VALUE;
        int enemyInAttackRange = 0;
        int closestFriendDis = Integer.MAX_VALUE;

        for (int i = nearbyEnemies.length; --i >= 0;) {
            RobotInfo enemy = nearbyEnemies[i];
            int dis = enemy.location.distanceSquaredTo(loc);
            closestEnemyDis = Math.min(closestEnemyDis, dis);
            if (dis <= GameConstants.ATTACK_RADIUS_SQUARED) {
                enemyInAttackRange++;
            }
        }
        for (int i = nearbyFriends.length; --i >= 0;) {
            closestFriendDis = Math.min(closestFriendDis, nearbyFriends[i].location.distanceSquaredTo(loc));
        }
        // prefer squares where you can be attacked by the fewest enemy
        score -= enemyInAttackRange * 1e6;
        // prefer squares where you are farther away from the closest enemy
        score += closestEnemyDis * 1e4;
        // prefer squares where you are closer to an ally
        if (nearbyFriends.length > 0)
            score -= closestFriendDis * 1e2;
        return score;
    }

    private static double getScoreForSingleEnemy(MapLocation loc, MapLocation enemyLoc) {
        double score = 0;
        int dis = loc.distanceSquaredTo(enemyLoc);
        if (dis > GameConstants.ATTACK_RADIUS_SQUARED) {
            // prefer tiles closer to the target, up to attack range
            score -= 1e6 * dis;
        }
        int enemyInAttackRange = 0;
        int closestFriendDis = Integer.MAX_VALUE;

        for (int i = nearbyEnemies.length; --i >= 0;) {
            RobotInfo enemy = nearbyEnemies[i];
            if (enemy.location.distanceSquaredTo(loc) <= GameConstants.ATTACK_RADIUS_SQUARED) {
                enemyInAttackRange++;
            }
        }
        for (int i = nearbyFriends.length; --i >= 0;) {
            closestFriendDis = Math.min(closestFriendDis, nearbyFriends[i].location.distanceSquaredTo(loc));
        }
        // prefer squares where you can be attacked by the fewest enemy
        score -= enemyInAttackRange * 1e4;
        // prefer squares where you are closer to an ally
        if (nearbyFriends.length > 0)
            score -= closestFriendDis * 1e2;
        return score;
    }

    private static Direction getBestMoveDirection(ToDoubleFunction<MapLocation> eval) {
        Direction bestDirection = Direction.CENTER;
        double bestScore = -Double.MAX_VALUE;
        for (int i = Direction.allDirections().length; --i >= 0; ) {
            Direction direction = Direction.allDirections()[i];
            if (direction != Direction.CENTER && !rc.canMove(direction)) {
                continue; // occupied
            }
            MapLocation location = rc.getLocation().add(direction);
            double score = eval.applyAsDouble(location);
            if (score > bestScore) {
                bestScore = score;
                bestDirection = direction;
            }
        }
        return bestDirection;
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
        if (enemyLocation.isWithinDistanceSquared(rc.getLocation(), GameConstants.ATTACK_RADIUS_SQUARED)) {
            return false;
        }
        Direction directionToUs = enemyLocation.directionTo(rc.getLocation());
        MapLocation a = enemyLocation.add(directionToUs);
        MapLocation b = enemyLocation.add(directionToUs.rotateLeft());
        MapLocation c = enemyLocation.add(directionToUs.rotateRight());
        if (MapRecorder.getPassible(a) && rc.getLocation().isWithinDistanceSquared(a, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(nearbyFriends, r -> r.location.isWithinDistanceSquared(a, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        if (MapRecorder.getPassible(b) && rc.getLocation().isWithinDistanceSquared(b, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(nearbyFriends, r -> r.location.isWithinDistanceSquared(b, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        if (MapRecorder.getPassible(c) && rc.getLocation().isWithinDistanceSquared(c, GameConstants.ATTACK_RADIUS_SQUARED)) {
            // check that there are allies that can attack too
            if (!LambdaUtil.arraysAnyMatch(nearbyFriends, r -> r.location.isWithinDistanceSquared(c, GameConstants.ATTACK_RADIUS_SQUARED))) {
                return false;
            }
        }
        return true;
    }
}
