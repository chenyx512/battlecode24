package bot1;

import battlecode.common.*;

public class Micro extends Robot {
    private static final int STATE_OFFENSIVE = 1;
    private static final int STATE_HOLDING = 2;
    private static final int STATE_DEFENSIVE = 3;
    private static final int STATE_BUILDING = 4; // legacy, not used
    private static int state;

    private static MicroDirection bestMicro;
    private static boolean shouldPlanStepAttack;
    private static int myTotalStrength, oppTotalStrength;

    static boolean act() throws GameActionException {
        // assumptions
        assert (GameConstants.ATTACK_RADIUS_SQUARED == GameConstants.HEAL_RADIUS_SQUARED) && (GameConstants.ATTACK_RADIUS_SQUARED == 4);
        if (rc.getRoundNum() <= GameConstants.SETUP_ROUNDS) {
            return false;
        }

        if (Cache.nearbyEnemies.length > 0) {
            BFS20.fill(); // this costs 2500 bytecode
            tryAttack();
            tryDropTrap();
            bestMicro = getBestMicro();
            if (bestMicro != null) {
                boolean canHeal = (state == STATE_DEFENSIVE) ||
                        (bestMicro.canAttackNext == 0 && (!SpecialtyManager.isAttacker() || state == STATE_HOLDING || bestMicro.canBeAttackedNext == 0));
                // we should only heal before moving if we have nothing better to do after moving
                if (canHeal && !SpecialtyManager.isBuilder() && bestMicro.canAttack == 0 && bestMicro.canHealHigh == 0) {
                    tryHeal();
                }

                tryMove(bestMicro.dir);
                Cache.closestEnemy = bestMicro.closestEnemyLoc;
                tryAttack();
                tryDropTrap();

                if (canHeal) {
                    tryHeal();
                }
                return true;
            } else {
                // This means there is a thick wall separating us from the enemy, simply treat it as enemy not exist
                Debug.printString(Debug.MICRO, "ign");
            }
        }
        // there's no enemy posing threat
        tryHeal();
        if (rc.isActionReady() && Cache.allyToHeal && !SpecialtyManager.isHealer(Cache.healingTarget) && SpecialtyManager.isHealer()) {
            PathFinder.move(Cache.healingTarget.location);
            tryHeal();
            return true;
        }
        return false;
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
        int lowBar = 450;
        int highBar = 700;
        // be more aggressive on smaller map
        // (mostly just to deal with camel_case style rush so that we get to late game safely)
        if (H * W < 1600 && rc.getRoundNum() < 500 || FlagManager.urgent) {
            highBar = 550;
            lowBar = 350;
        }
        if (rc.getHealth() < lowBar) {
            state = STATE_DEFENSIVE;
        }  else {
            boolean hold = rc.getHealth() < highBar;
            if (SpecialtyManager.isHealer() && !FlagManager.urgent &&
                    Math.sqrt(Robot.getDisToMyClosestSpawnCenter(rc.getLocation())) > (W + H) / 12.0)
                // healers don't be offensive unless our base/flag threatened
                hold = true;
            if (hold) {
                state = STATE_HOLDING;
            } else {
                state = STATE_OFFENSIVE;
            }
        }
        Debug.printString(Debug.MICRO, "micro" + state);

        MicroDirection micro = micros[8];
        int canBeAttacked = 0;
        for (int i = 0; i < 8; ++i) {
            canBeAttacked |= micros[i].canBeAttackedNext;
            if (micros[i].isBetterThan(micro)) micro = micros[i];
        }
        // if all directions are not attackable by enemy and that we can't reach any enemy according to vision range BFS
        // ignore the enemy and give control to macro
        if (canBeAttacked == 0 && canIgnoreEnemy()) {
            return null;
        }
        if (micro.needFill == 1) {
            // if a fill is needed, fill and then recalc where to move
            MapLocation fillLoc = rc.getLocation().add(micro.dir);
            if (rc.canFill(fillLoc)) {
                rc.fill(fillLoc);
                micro = micros[8];
                for (int i = 0; i < 8; ++i) {
                    micros[i].resetAfterFill();
                    if (micros[i].isBetterThan(micro)) micro = micros[i];
                }
                return micro;
            } else {
                Debug.failFast("impossible");
            }
        }
        return micro;
    }

    private static boolean canIgnoreEnemy() throws GameActionException {
        for (int i = Cache.nearbyEnemies.length; --i >= 0;) {
            MapLocation loc = Cache.nearbyEnemies[i].location;
            if (BFS20.getDis(loc) < 999) {
                return false;
            }
        }
        return true;
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
        if (BFS20.getDis(Cache.closestEnemy) > 999) // if the enemy is not reachable, don't trap
            return;

        boolean canBuild = SpecialtyManager.isBuilder() || FlagManager.urgent;
        // only allow non-builders to build if urgent or too many friendly units in jail
        if (KillRecorder.cntDiff < -4 && (Cache.nearbyEnemies.length >= 5 && Cache.nearbyFriends.length >= 4))
            canBuild = true;
        if (!canBuild)
            return;

        TrapType trapType = TrapType.STUN;
        int radius = SpecialtyManager.isBuilder()? 16 : 8;
        Direction dir = rc.getLocation().directionTo(Cache.closestEnemy);
        MapLocation loc;
        loc = rc.getLocation();
        if (loc.isWithinDistanceSquared(Cache.closestEnemy, radius) && rc.canBuild(trapType, loc)) {
            if (canTrap(trapType, loc)) {
                rc.build(trapType, loc);
            }
        }
        loc = rc.getLocation().add(dir);
        if (loc.isWithinDistanceSquared(Cache.closestEnemy, radius) && rc.canBuild(trapType, loc)) {
            if (canTrap(trapType, loc)) {
                rc.build(trapType, loc);
            }
        }
        loc = rc.getLocation().add(dir.rotateLeft());
        if (loc.isWithinDistanceSquared(Cache.closestEnemy, radius) && rc.canBuild(trapType, loc)) {
            if (canTrap(trapType, loc)) {
                rc.build(trapType, loc);
            }
        }
        loc = rc.getLocation().add(dir.rotateRight());
        if (loc.isWithinDistanceSquared(Cache.closestEnemy, radius) && rc.canBuild(trapType, loc)) {
            if (canTrap(trapType, loc)) {
                rc.build(trapType, loc);
            }
        }

        if (SpecialtyManager.isBuilder()) {
            for (int i = 8; --i >= 0;) {
                loc = rc.getLocation().add(Constants.MOVEABLE_DIRECTIONS[i]);
                if (loc.x % 4 == SpecialtyManager.masterID % 4 && loc.y % 4 == SpecialtyManager.masterID % 4 && rc.canBuild(TrapType.WATER, loc)) {
                    rc.build(TrapType.WATER, loc);
                }
            }
        }
    }

    private static boolean canTrap(TrapType trapType, MapLocation loc) throws GameActionException {
        if (trapType == TrapType.EXPLOSIVE)
            return true;
        MapLocation x;
        x = loc.add(Direction.NORTH);
        if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
        x = loc.add(Direction.EAST);
        if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
        x = loc.add(Direction.SOUTH);
        if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
        x = loc.add(Direction.WEST);
        if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;

        if (rc.getCrumbs() < 8000) {
            x = loc.add(Direction.NORTHEAST);
            if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
            x = loc.add(Direction.SOUTHEAST);
            if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
            x = loc.add(Direction.SOUTHWEST);
            if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
            x = loc.add(Direction.NORTHWEST);
            if (rc.canSenseLocation(x) && rc.senseMapInfo(x).getTrapType() == TrapType.STUN) return false;
        }
        return true;
    }

    public static void tryHeal() throws GameActionException {
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
        double attackScore = 0;
        switch (r.getAttackLevel()) { // according to DPS
            case 1: attackScore += 1.05 / 0.95 - 1; break;
            case 2: attackScore += 1.07 / 0.93 - 1; break;
            case 3: attackScore += 1.1 / 0.9 - 1; break;
            case 4: attackScore += 1.3 / 0.8 - 1; break;
            case 5: attackScore += 1.35 / 0.65 - 1; break;
            case 6: attackScore += 1.6 / 0.4 - 1; break;
        }
        double buildScore = 0;
        switch (r.getBuildLevel()) { // according to saved cost
            case 1: buildScore += 0.1; break;
            case 2: buildScore += 0.15; break;
            case 3: buildScore += 0.2; break;
            case 4: buildScore += 0.3; break;
            case 5: buildScore += 0.4; break;
            case 6: buildScore += 0.5; break;
        }
        double healScore = 0;
        switch (r.getHealLevel()) { // according to DPS
            case 1: healScore += 1.03 / 0.95 - 1; break;
            case 2: healScore += 1.05 / 0.9 - 1; break;
            case 3: healScore += 1.07 / 0.85 - 1; break;
            case 4: healScore += 1.1 / 0.85 - 1; break;
            case 5: healScore += 1.15 / 0.85 - 1; break;
            case 6: healScore += 1.25 / 0.75 - 1; break;
        }
        return 0.01 + attackScore + buildScore * 4 + healScore;
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

    static class MicroDirection {
        // xsquare micro: evaluate/update each direction separately
        // reference https://github.com/IvanGeffner/Battlecode23/blob/master/fortytwo/MicroAttacker.java
        Direction dir;
        MapLocation loc;
        int canMove;
        int blockTeammate;
        int needFill;
        int canAttack;
        int canKill;
        int numAttackRange;
        int canBeAttackedNext;
        int canAttackNext;
        int numAttackRangeNext;
        int allyWithinBlastRange; // legacy, not used after explosive nerf after sprint 1
        int allyCloseCnt;
        int minDistanceToEnemy = 99999999;
        int minDistanceToAlly = 99999999;
        int builderDis = 99999999;
        int canHealLow, canHealHigh; // high means healing non-healer, low means healer
        int disToHealerHigh = 9999999;
        int disToHealer = 9999999;
        MapLocation closestEnemyLoc;

        public MicroDirection(Direction dir) throws GameActionException {
            this.dir = dir;
            this.loc = rc.getLocation().add(dir);
            if (dir == Direction.CENTER || rc.canMove(dir)) {
                canMove = 1;
            }
            // allow micro water filling when many friendly units around
            else if (rc.canFill(this.loc) && Cache.nearbyFriends.length > 5) {
                canMove = 1;
                needFill = 1;
            }
        }

        void resetAfterFill() {
            canMove = dir == Direction.CENTER || rc.canMove(dir)? 1 : 0;
            needFill= 0;
            canAttack = 0;
            canAttackNext = 0;
            shouldPlanStepAttack = false;
            canKill = 0;
            canHealLow = 0;
            canHealHigh = 0;
        }

        void updateEnemy(RobotInfo enemy) throws GameActionException {
            if (canMove == 0) return;
            int dis = loc.distanceSquaredTo(enemy.location);
            if (dis <= GameConstants.ATTACK_RADIUS_SQUARED) {
                canBeAttackedNext = 1;
                numAttackRange++;
                numAttackRangeNext++;
                if (rc.isActionReady() && (needFill == 0 || Cache.nearbyFriends.length - Cache.nearbyEnemies.length > 5)) {
                    canAttack = 1;
                    canAttackNext = 1;
                    if (enemy.health <= rc.getAttackDamage() || enemy.hasFlag)
                        canKill = 1;
                }
            } else if (dis <= 10) {
                if (canAttackNext == 0 && shouldPlanStepAttack) {
                    canAttackNext = checkCanAttack(loc, enemy.location);
                }
                if (canBeAttackedNext == 0) {
                    canBeAttackedNext = checkCanAttack(enemy.location, loc);
                }
                numAttackRangeNext++;
            }
            if (dis < minDistanceToEnemy) {
                minDistanceToEnemy = dis;
                closestEnemyLoc = enemy.location;
            }
        }

        int checkCanAttack(MapLocation fromLoc, MapLocation toLoc) throws GameActionException {
            Direction dir = fromLoc.directionTo(toLoc);
            MapLocation x = fromLoc.add(dir);
            if (x.isWithinDistanceSquared(toLoc, GameConstants.ATTACK_RADIUS_SQUARED) && rc.canSenseLocation(x) && rc.sensePassability(x) && rc.senseRobotAtLocation(x) == null) {
                return 1;
            }
            x = fromLoc.add(dir.rotateRight());
            if (x.isWithinDistanceSquared(toLoc, GameConstants.ATTACK_RADIUS_SQUARED) && rc.canSenseLocation(x) && rc.sensePassability(x) && rc.senseRobotAtLocation(x) == null) {
                return 1;
            }
            x = fromLoc.add(dir.rotateLeft());
            if (x.isWithinDistanceSquared(toLoc, GameConstants.ATTACK_RADIUS_SQUARED) && rc.canSenseLocation(x) && rc.sensePassability(x) && rc.senseRobotAtLocation(x) == null) {
                return 1;
            }
            return 0;
        }

        void updateAlly(RobotInfo ally) throws GameActionException {
            if (canMove == 0) return;
            int dis = loc.distanceSquaredTo(ally.location);
            if (dis <= 2 && blockTeammate == 0
                    && (ally.health < Math.min(rc.getHealth(), 700) && state != STATE_DEFENSIVE || ally.hasFlag)) {
                // If I am moving adjacent to a teammate, and we are blocking that teammate's way out from the enemy
                // that teammate must be allowed to have another way out of the enemy
                Direction dirOut = closestEnemyLoc.directionTo(ally.location);
                Direction blockedDir = ally.location.directionTo(loc);
                if (blockedDir.equals(dirOut) || blockedDir.equals(dirOut.rotateLeft()) || blockedDir.equals(dirOut.rotateRight())) {
                    // we are blocking the teammate's way out
                    ok: {
                        MapLocation a = ally.location.add(dirOut);
                        if (rc.onTheMap(a) && rc.sensePassability(a) && (rc.senseRobotAtLocation(a) == null || a.equals(rc.getLocation())) && !a.equals(loc)) {
                            break ok;
                        }
                        a = ally.location.add(dirOut.rotateLeft());
                        if (rc.onTheMap(a) && rc.sensePassability(a) && (rc.senseRobotAtLocation(a) == null || a.equals(rc.getLocation())) && !a.equals(loc)) {
                            break ok;
                        }
                        a = ally.location.add(dirOut.rotateLeft());
                        if (rc.onTheMap(a) && rc.sensePassability(a) && (rc.senseRobotAtLocation(a) == null || a.equals(rc.getLocation())) && !a.equals(loc)) {
                            break ok;
                        }
                        blockTeammate = 1;
                        Debug.setIndicatorDot(Debug.MICRO, loc, 255, 0, 0);
                    }
                }
            }
            if (dis <= 13)
                allyWithinBlastRange++; // legacy, not used after explosive nerf after sprint 1
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
            if (canMove != other.canMove) return canMove > other.canMove;
            if (blockTeammate != other.blockTeammate) return blockTeammate < other.blockTeammate;
            if (state != STATE_OFFENSIVE && needFill != other.needFill) // don't fill unless offensive
                return needFill < other.needFill;
            switch (state) {
                case STATE_BUILDING: // legacy, not used
                    // play safe as builder
                    if (numAttackRange - canKill != other.numAttackRange - other.canKill)
                        return numAttackRange - canKill < other.numAttackRange - other.canKill;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }
                    if (SpecialtyManager.isBuilder() && builderDis != other.builderDis)
                        return builderDis > other.builderDis;
                    if (minDistanceToAlly != other.minDistanceToAlly)
                        return minDistanceToAlly < other.minDistanceToAlly;
                    return minDistanceToEnemy <= other.minDistanceToEnemy;

                case STATE_DEFENSIVE:
                    // play safe
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (numAttackRangeNext - canKill != other.numAttackRangeNext - other.canKill) {
                        return numAttackRangeNext - canKill < other.numAttackRangeNext - other.canKill;
                    }
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;

                    if (SpecialtyManager.isBuilder() && builderDis != other.builderDis) // a small attempt to separate builder
                        return builderDis > other.builderDis;
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
                    // if we can step attack anyone, do it
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;

                    if (numAttackRangeNext != other.numAttackRangeNext) {
                        return numAttackRangeNext < other.numAttackRangeNext;
                    }
                    if (SpecialtyManager.isBuilder() && builderDis != other.builderDis)
                        return builderDis > other.builderDis;
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
                    // when surrounding enemy at a tight choke point, someone needs to go in
                    // this prevents getting stuck with enemy in the following bad equilibrium:
                    /* 0 is space, w is wall, 1/2 are the 2 teams
                    no one will go forward because that risks getting attacked by 2 enemies
                    1ww22
                    1w022
                    110w2
                    11ww2
                     */
                    if (myTotalStrength > 900 && myTotalStrength - oppTotalStrength > 500) {
                        if (canAttack != other.canAttack)
                            return canAttack > other.canAttack;
                    }
                    if (numAttackRange - canAttack != other.numAttackRange - other.canAttack)
                        return numAttackRange - canAttack < other.numAttackRange - other.canAttack;
                    if (canAttack != other.canAttack)
                        return canAttack > other.canAttack;
                    if (canKill != other.canKill)
                        return canKill > other.canKill;

                    if (shouldPlanStepAttack) {
                        // if can attack next turn, want to have as few target as possible, but at least 1
                        if (canAttackNext != other.canAttackNext)
                            return canAttackNext > other.canAttackNext;
                        if (canAttack == 1) {
                            if (canBeAttackedNext != other.canBeAttackedNext)
                                return canBeAttackedNext < other.canBeAttackedNext;
                        }
                        if (numAttackRangeNext != other.numAttackRangeNext) {
                            if (numAttackRangeNext == 0) return false;
                            if (other.numAttackRangeNext == 0) return true;
                            return numAttackRangeNext < other.numAttackRangeNext;
                        }
                    } else {
                        if (canBeAttackedNext != other.canBeAttackedNext)
                            return canBeAttackedNext < other.canBeAttackedNext;
                        if (canBeAttackedNext > 0) {
                            if (numAttackRangeNext != other.numAttackRangeNext)
                                return numAttackRangeNext < other.numAttackRangeNext;
                        }
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
