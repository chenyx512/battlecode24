package bot1;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import bot1.fast.FastMath;

import java.util.Map;

public class RoleAssigner extends RobotPlayer {
    // -1 for unassigned, 0-2 for friendly flag defense, 3-5 for enemy flag attack
    private static int role = -1;
    private static final int MAX_ASSIGNMENT = 15;

    public static void initTurn() throws GameActionException {
        if (!rc.isSpawned()) {
            if (role != -1) {
                reassign(-1);
            }
            if (canSpawn()) {
                updateRole();
                findBestSpawn();
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
        updateRole();
        MapLocation loc = getRoleLocation();
        if (loc == null) {
            return;
        }
        if (role >= 3 && Comms.readOppflagsCarried(role - 3) == 1) {
            PathFinder.escort(loc);
        } else {
            PathFinder.move(loc);
        }
    }

    private static MapLocation getRoleLocation() throws GameActionException {
        if (role == -1) {
            Debug.println("warning: cannot find role, setting to default, hopefully game ends soon");
            return Robot.oppSpawnCenters[rc.getID() % 3];
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
                // for not distressed friendly flag, one and only one duck sits on it
                if (role == newRole)
                    return Comms.readMyflagsAssigned(newRole) > 1 ? -1 : 1;
                return Comms.readMyflagsAssigned(newRole) > 0 ? -1 : 1;
            }
        } else {
            int flagid = newRole - 3;
            if (Comms.readOppflagsExists(flagid) == 0)
                return -1;
            if (Comms.readOppflagsCarried(flagid) == 1) {
                return getDistressScore(Comms.readOppflagsAssigned(flagid),
                        Util.int2loc(Comms.readOppflagsLoc(flagid)));
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
        double disScore = Math.max(0.05, 1 - (dis - MIN_DIS) / (W + H) * 4);
        double assignedScore = Math.max(0.05, 1 - (double) numAssigned / MAX_ASSIGNMENT);
        return disScore * assignedScore;
    }

    private static void reassign(int newRole) throws GameActionException {
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
        updateRole();
        MapLocation missionLoc = getRoleLocation();
        int bestDis = Integer.MAX_VALUE;
        for (MapLocation loc : rc.getAllySpawnLocations()) {
            if (rc.canSpawn(loc)) {
                int dis =  loc.distanceSquaredTo(missionLoc);
                if (dis < bestDis) {
                    bestDis = dis;
                    bestSpawn = loc;
                }
            }
        }
        rc.spawn(bestSpawn);
    }
}
