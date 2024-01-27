package bot1;

import battlecode.common.*;
import bot1.fast.FastMath;

public class RoleAssigner extends RobotPlayer {
    // -1 for unassigned, 0-2 for friendly flag defense, 3-5 for enemy flag attack
    public static int role = -1;
    private static final int MAX_ASSIGNMENT = 15;

    public static void initTurn() throws GameActionException {
        if (Robot.isMaster) {
            for (int i = 3; --i >= 0;) {
                int n = Comms.readHqCongestround(i);
                if (n > 0) {
                    Comms.writeHqCongestround(i, n - 1);
                    Debug.setIndicatorDot(Debug.INFO, Robot.mySpawnCenters[i], 255, 0, 255);
                }
            }
        }
        if (!rc.isSpawned()) {
            if (role != -1) {
                reassign(-1);
            }
            if (canSpawn()) {
                updateRole();
                findBestSpawn();
            }
        } else {
            updateRole();
            if (role >= 0 && role < 3 && Comms.readMyflagsAssigned(role) == 1 && rc.getRoundNum() > 200) {
                // if I am the only one guarding this flag
                MapLocation flagLoc = Util.int2loc(Comms.readMyflagsLoc(role));
                if (rc.getLocation().equals(flagLoc)) {
                    if (rc.canBuild(TrapType.STUN, rc.getLocation())) {
                        rc.build(TrapType.STUN, rc.getLocation());
                    }
                }
                // distress if I can't see the flag
                if (!rc.canSenseLocation(flagLoc) || Cache.nearbyEnemies.length >= Cache.nearbyFriends.length) {
                    Comms.writeMyflagsDistress(role, 1);
                } else if (Comms.readMyflagsOriginalLoc(role) == Comms.readMyflagsLoc(role)
                        && rc.senseNearbyFlags(-1, myTeam).length == 0) {
                    Debug.println(Debug.INFO, "flag lost" + Util.int2loc(Comms.readMyflagsOriginalLoc(role)).toString());
                    Comms.writeMyflagsExists(role, 0);
                }
            }
        }
        if (rc.isSpawned()) {
            int hqid = Util.getClosestID(Robot.mySpawnCenters);
            if (rc.getLocation().isAdjacentTo(Robot.mySpawnCenters[hqid])) {
                int n = rc.senseNearbyRobots(-1, myTeam).length;
                if (n > 8) {
                    Comms.writeHqCongestround(hqid, 15);
                } else {
                    Comms.writeHqCongestround(hqid, 0);
                }
            }
        }
        Debug.printString(Debug.INFO, String.format("r%d", role));
    }

    public static void drawDebug() throws GameActionException {
        if (rc.isSpawned()) {
            double need = getNeedLevel(role);
            int r = need > 0? 255 : 0;
            int g = need > 0? 0 : 255;
            int b = role >= 3? 255 : 0;
            Debug.setIndicatorLine(Debug.ASSIGNMENT, rc.getLocation(), getRoleLocation(), r, g, b);
        }
    }

    public static void act() throws GameActionException {
        MapLocation loc = getRoleLocation();
        if (loc == null) {
            return;
        }
        if (role >= 3) {
            if (Comms.readOppflagsCarried(role - 3) == 1) {
                PathFinder.escort(role - 3);
            } else {
                PathFinder.move(Explorer.getFlagTarget(role - 3, 15));
            }
        } else {
            PathFinder.move(loc);
        }
    }

    private static MapLocation getRoleLocation() throws GameActionException {
        if (role == -1) {
            return Robot.oppSpawnCenters[SpecialtyManager.duckSeqID % 3];
        }
        if (role < 3) {
            return Util.int2loc(Comms.readMyflagsLoc(role));
        }
        return Util.int2loc(Comms.readOppflagsLoc(role - 3));
    }

    private static void updateRole() throws GameActionException {
        double currentRoleNeed = getNeedLevel(role);
        if (currentRoleNeed == -1) {
            double maxNeed = -1;
            int bestRole = -1;
            for (int i = 0; i < 6; i++) {
                double need = getNeedLevel(i);
                if (need > maxNeed) {
                    maxNeed = need;
                    bestRole = i;
                }
            }
            reassign(bestRole);
        } else if (currentRoleNeed < 0 || getNumAssignedFrom(role) > MAX_ASSIGNMENT) {
            // currently in a not-distressed role, consider responding to distress
            // or if too many ducks responding to current distress, try switch
            // (this may cause oscilation but maybe not a bad thing)
            for (int i = 0; i < 6; i++) {
                double need = getNeedLevel(i);
                if (need > 0 && FastMath.fakefloat() < need) {
                    reassign(i);
                    break;
                }
            }
        }
    }

    private static int getNumAssignedFrom(int r) throws GameActionException {
        if (r < 3)
            return Comms.readMyflagsAssigned(r);
        return Comms.readOppflagsAssigned(r - 3);
    }

    private static double getNeedLevel(int newRole) throws GameActionException {
        // 0-1 means distress
        // -1 means unavailable
        if (newRole == -1) {
            return -1;
        }
        if (newRole < 3) {
            if (Comms.readMyflagsExists(newRole) == 0)
                return -1;
            if (Comms.readMyflagsDistress(newRole) == 1) {
                return getDistressScore(Comms.readMyflagsAssigned(newRole),
                        Util.int2loc(Comms.readMyflagsLoc(newRole)));
            } else {
                return -1;
//                MapLocation flagLoc = Util.int2loc(Comms.readMyflagsLoc(newRole));
//                // for not distressed friendly flag, one and only one duck sits on it
//                // only Healer sits on flag
//                if (!SpecialtyManager.isHealer()) {
//                    return -1;
//                } else if (rc.isSpawned() && rc.getLocation().equals(flagLoc)) {
//                    return 1;
//                } else if (rc.isSpawned() && rc.canSenseLocation(flagLoc)) {
//                    // If I can see the flag, the first person to sit on it wins
//                    if (rc.senseRobotAtLocation(flagLoc) != null) {
//                        return -1;
//                    }
//                    return 1;
//                } else {
//                    // If I cannot see the flag, send one person there only
//                    if (role == newRole) {
//                        return Comms.readMyflagsAssigned(newRole) > 1 ? -1 : 1;
//                    } else {
//                        return Comms.readMyflagsAssigned(newRole) > 0 ? -1 : 1;
//                    }
//                }
            }
        } else {
            int flagid = newRole - 3;
            if (Comms.readOppflagsExists(flagid) == 0)
                return -1;

            if (Comms.readOppflagsCarried(flagid) == 1) {
                MapLocation flagloc = Util.int2loc(Comms.readOppflagsLoc(flagid));
                if (Comms.readOppflagsEscortLoc(flagid) == 0) {
                    return Comms.readOppflagsAssigned(flagid) < 5? 1 : -1;
                } else {
                    return getDistressScore(Comms.readOppflagsAssigned(flagid), flagloc);
                }
            } else {
                return -0.1 - Comms.readOppflagsAssigned(flagid) / 100.0;
            }
        }
    }

    private static double getDistressScore(int numAssigned, MapLocation loc) {
        // prioritize distress close by
        // prioritize distress answered by the fewest duck
        // return the probability that this duck should respond to this distress
        double dis = rc.isSpawned()? rc.getLocation().distanceSquaredTo(loc)
                :  Robot.getDisToMyClosestSpawnCenter(loc);
        final double MIN_DIS = 8;
        dis = Math.max(Math.sqrt(dis), MIN_DIS);
        double disScore = Math.max(0.02, 1 - (dis - MIN_DIS) / (W + H) * 5);
        // limit distress call to max assignment
        double assignedScore = Math.max(0, 1 - (double) numAssigned / MAX_ASSIGNMENT);
        return disScore * assignedScore;
    }

    public static void reassign(int newRole) throws GameActionException {
        if (newRole == role)
            return;
        switch (role) {
            case 0: case 1: case 2:
                Comms.writeMyflagsAssigned(role, Comms.readMyflagsAssigned(role) - 1);
                break;
            case 3: case 4: case 5:
                Comms.writeOppflagsAssigned(role - 3, Comms.readOppflagsAssigned(role - 3) - 1);
                break;
        }
        switch (newRole) {
            case 0: case 1: case 2:
                Comms.writeMyflagsAssigned(newRole, Comms.readMyflagsAssigned(newRole) + 1);
                break;
            case 3: case 4: case 5:
                Comms.writeOppflagsAssigned(newRole - 3, Comms.readOppflagsAssigned(newRole - 3) + 1);
                break;
        }
        role = newRole;
    }

    private static boolean canSpawn() {
        for (MapLocation loc : rc.getAllySpawnLocations()) {
            if (rc.canSpawn(loc))
                return true;
        }
        return false;
    }

    private static void findBestSpawn() throws GameActionException {
        MapLocation bestSpawn = null;
        if (role >= 3 || role == -1) {
            // for offense role, rng to spread out
            trySpawn(FastMath.rand256() % 3);
        } else {
            // for defense role, spawn the closest
            MapLocation missionLoc = getRoleLocation();
            double bestScore = -Double.MAX_VALUE;
            int bestHQID = -1;
            for (int i = 3; --i >= 0;) {
                MapLocation loc = Robot.mySpawnCenters[i];
                double score = - Math.sqrt(loc.distanceSquaredTo(missionLoc)) - Math.sqrt(Util.getClosestDis(loc, Robot.oppSpawnCenters));
                score -= 1e5 * Comms.readHqCongestround(i);
                if (score > bestScore) {
                    bestScore = score;
                    bestHQID = i;
                }
            }
            trySpawn(bestHQID);
        }
    }

    public static void trySpawn(int hqid) throws GameActionException {
        for (Direction dir : Constants.ALL_DIRECTIONS) {
            MapLocation loc = Robot.mySpawnCenters[hqid].add(dir);
            if (rc.canSpawn(loc)) {
                rc.spawn(loc);
            }
        }
    }
}
