package macrobot;

import battlecode.common.*;

public class Robot extends RobotPlayer {
    public static MapLocation[] mySpawnCenters = new MapLocation[3];
    public static MapLocation[] oppSpawnCenters = new MapLocation[3];

    public static MapLocation homeSpawn;

    public static int baseHeal = SkillType.HEAL.skillEffect;
    public static int attackHP, healHP;
    public static boolean isMaster = false;

    static void init() throws GameActionException {
        initHQLocs(); // also the flag manager
    }

    static void initTurn() throws GameActionException {
        Comms.pull();
        MapRecorder.updateSym();
        RoleAssigner.initTurn(); // takes care of spawning
        FlagManager.initTurn();
        Cache.initTurn();

        // updates self stats
        attackHP = Math.round(SkillType.ATTACK.skillEffect * ((float) SkillType.ATTACK.getSkillEffect(rc.getLevel(SkillType.ATTACK)) / 100 + 1));
        healHP = Math.round(baseHeal * ((float) SkillType.HEAL.getSkillEffect(rc.getLevel(SkillType.HEAL)) / 100 + 1));

        if (rc.isSpawned()) {
            if (rc.canBuyGlobal(GlobalUpgrade.ACTION)) {
                rc.buyGlobal(GlobalUpgrade.ACTION);
            } else if (rc.canBuyGlobal(GlobalUpgrade.HEALING)) {
                rc.buyGlobal(GlobalUpgrade.HEALING);
            }
        }
    }

    static void play() throws GameActionException {
        if (isMaster && rc.getRoundNum() % 10 == 0) {
            for (int i = 0; i < 3; i++) {
                System.out.printf("id%d exist%d carried%d loc%s oriloc%s\n",
                        Comms.readOppflagsId(i), Comms.readOppflagsExists(i), Comms.readOppflagsCarried(i),
                        Util.toString(Util.int2loc(Comms.readOppflagsLoc(i))), Util.toString(Util.int2loc(Comms.readOppflagsOriginalLoc(i))));
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) sb.append(String.format("%d: %2d ", i, Comms.readMyflagsAssigned(i)));
            sb.append(" /ATTK ");
            for (int i = 0; i < 3; i++) sb.append(String.format("%d: %2d ", i, Comms.readOppflagsAssigned(i)));
            System.out.println(sb.toString());
        }
        if (!rc.isSpawned()) {
            return;
        }

        RoleAssigner.drawDebug();

        if (FlagManager.act())
            return;

        if (rc.getRoundNum() <= GameConstants.SETUP_ROUNDS) {
            if (findCrumb())
                return;
            PathFinder.move(Explorer.getUnseenExploreTarget());
        }  else {
            if (Micro.act())
                return;
            if (findCrumb())
                return;
            RoleAssigner.act();
        }
    }

    static void endTurn() throws GameActionException {
        Comms.push();
        if (rc.isSpawned()) {
            MapRecorder.recordSym(2000);
        }
    }

    static boolean findCrumb() throws GameActionException {
        MapLocation[] crumbs = rc.senseNearbyCrumbs(-1);
        if (crumbs.length > 0) {
            PathFinder.move(Util.getClosestLoc(crumbs));
            return true;
        }
        return false;
    }

    static void initHQLocs() throws GameActionException {
        Comms.pull();
        if (Comms.readHqLoc(0) == 0) {
            MapLocation[] locations = rc.getAllySpawnLocations();
            boolean[] used = new boolean[27];
            for (int i = 3; --i >= 0; ) {
                int sumX = 0, sumY = 0, count = 0;
                for (int j = 27; --j >= 0; ) {
                    if (!used[j]) {
                        if (count == 0) {
                            // Select a starting location for a new group
                            sumX += locations[j].x;
                            sumY += locations[j].y;
                            count++;
                            used[j] = true;
                        } else {
                            // Check if the location is close to the starting location
                            int dx = locations[j].x - sumX / count;
                            int dy = locations[j].y - sumY / count;
                            if (dx * dx + dy * dy <= 8) {
                                sumX += locations[j].x;
                                sumY += locations[j].y;
                                count++;
                                used[j] = true;
                            }
                        }
                    }
                }
                assert count == 9;

                mySpawnCenters[i] = new MapLocation(sumX / 9, sumY / 9);
                Comms.writeHqLoc(i, Util.loc2int(mySpawnCenters[i]));
                Debug.println(Debug.INFO, String.format("HQ %d: %d %d", i, sumX / 9, sumY / 9));
                isMaster = true;
            }
            FlagManager.init();
            Comms.push();
        } else {
            for (int i = 3; --i >= 0; ) {
                mySpawnCenters[i] = Util.int2loc(Comms.readHqLoc(i));
            }
        }
        oppSpawnCenters[0] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[0]);
        oppSpawnCenters[1] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[1]);
        oppSpawnCenters[2] = MapRecorder.getSymmetricLoc(Robot.mySpawnCenters[2]);
    }

    public static int getDisToMyClosestSpawnCenter(MapLocation loc) {
        int dis0 = mySpawnCenters[0].distanceSquaredTo(loc);
        int dis1 = mySpawnCenters[1].distanceSquaredTo(loc);
        int dis2 = mySpawnCenters[2].distanceSquaredTo(loc);
        return Math.min(Math.min(dis0, dis1), dis2);
    }

    public static void tryMove(Direction dir) throws GameActionException {
        if (dir == Direction.CENTER)
            return;
        rc.move(dir);
    }
}
// code remnants for spawn camping, not working because defense is too strong
//            Debug.printString(Debug.INFO, String.format("attk%d", attackID));
//            if (rc.senseMapInfo(rc.getLocation()).getSpawnZoneTeam() == oppTeamID) {
//                // I am camping spawn, if it is full ask other to attack elsewhere
//                isCamping = true;
//                if (attackID == Comms.readAttacktargetTarget()
//                        && rc.senseNearbyRobots(oppSpawnCenters[attackID], 2, myTeam).length == 8) {
//                    Debug.println(Debug.INFO, String.format("base %d camped, moving on", attackID));
//                    Comms.writeAttacktargetTarget((attackID + 1) % GameConstants.NUMBER_FLAGS);
//                }
//                    for (Direction dir : Constants.directions) {
//                        // move around to let others in if possible
//                        MapLocation newLoc = rc.getLocation().add(dir);
//                        if (rc.senseMapInfo(newLoc).getSpawnZoneTeam() == oppTeamID && rc.canMove(dir)) {
//                            rc.move(dir);
//                        }
//                        // build traps around enemy spawn with enough resource and some rng (to distribute around camps)
//                        if (rc.canBuild(TrapType.EXPLOSIVE, newLoc)) {
//                            if (rc.getCrumbs() > Constants.CRUMBS_MIN_FOR_CAMPING && FastMath.rand256() % 32 == 0) {
//                                rc.build(TrapType.EXPLOSIVE, newLoc);
//                            }
//                        }
//                    }
//                return;
//            } else if (rc.senseNearbyRobots(oppSpawnCenters[attackID], 2, myTeam).length == 9) {
//                // the HQ I was camping is already camped, move to a new HQ to camp
//                isCamping = false;
//            }
//            if (!isCamping) {
//                attackID = Comms.readAttacktargetTarget();
//            }
//            targetLoc = oppSpawnCenters[attackID];
