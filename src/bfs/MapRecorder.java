package bfs;

import battlecode.common.*;

import bfs.fast.FastLocSet;

public class MapRecorder extends RobotPlayer {
    private static FastLocSet reportedWalls = new FastLocSet();
    private static FastLocSet walls2report = new FastLocSet();
    private static int myWallReportID = -1;

    private static boolean symConfimred = false;
    // when symmetry is confirmed, all walls need to be flipped
    private static boolean needWallFlip = false;
    private static int wallFlipIndex = -1;

    public static final char SEEN_BIT = 1;
    public static final char WALL_BIT = 1 << 1;

    // fun fact: this costs 1 bytecode, but declaring array costs 3600

    // if val[x] = 0, it is unseen
    // if val[x] = 1, there is no wall
    // if val[x] = 2, there is wall

    public static char[] vals = Constants.STRING_LEN_4200.toCharArray();

    // bit 0b100 means rotational eliminated, 0b010 vertical, 0b001 horizontal
    private static int symmetry;

    public static boolean getPassible(MapLocation loc) {
        int val = vals[Util.loc2int(loc)];

        if (val > 0)
            return val != WALL_BIT;
        val = vals[Util.loc2int(getSymmetricLoc(loc))];
        return val != WALL_BIT;
    }

    public static boolean getVisited(MapLocation loc) {
        return (vals[Util.loc2int(loc)] != 0) || (vals[Util.loc2int(getSymmetricLoc(loc))] != 0);
    }

    public static int getData(MapLocation loc) {
        return vals[Util.loc2int(loc)];
    }

    // record what we can sense on the map, perform sym check if needed
    // always called at the end of a turn and will run until all bytecode consumed
    public static void recordSym(int leaveBytecodeCnt) throws GameActionException {
        // called on turn end
        if (needWallFlip) {
            if (wallFlipIndex == -1) {
                wallFlipIndex = reportedWalls.size;
            }
            for (; --wallFlipIndex >= 0;) {
                MapLocation loc = reportedWalls.pop();
                MapLocation symloc = getSymmetricLoc(loc);
                if (!reportedWalls.contains(symloc)) {
                    reportedWalls.add(symloc);
                    vals[Util.loc2int(symloc)] = WALL_BIT;
                }
                if (Clock.getBytecodesLeft() <= leaveBytecodeCnt) {
                    return;
                }
            }
            needWallFlip = false;
        }

        if (!rc.isSpawned())
            return;

        MapInfo[] infos = rc.senseNearbyMapInfos();
        for (int i = infos.length; --i >= 0; ) {
            if (Clock.getBytecodesLeft() <= leaveBytecodeCnt) {
                return;
            }
            MapInfo info = infos[i];
            MapLocation loc = info.getMapLocation();
            int locID = Util.loc2int(loc);

            if (vals[locID] != 0)
                continue;

            if (info.isWall()) {
                vals[locID] = WALL_BIT;
                if (!reportedWalls.contains(loc)) {
                    walls2report.add(loc);
                    reportedWalls.add(loc);
                }
                if (symConfimred) {
                    MapLocation symloc = getSymmetricLoc(loc);
                    reportedWalls.add(symloc);
                    vals[Util.loc2int(symloc)] = WALL_BIT;
                }
            } else {
                vals[locID] = SEEN_BIT;
            }

            if (!symConfimred) {
                for (int sym = 0b100; sym > 0; sym >>= 1) {
                    if ((sym & symmetry) > 0)
                        continue;
                    MapLocation symloc = getSymmetricLoc(loc, 0b111 - sym);
                    int symVal = vals[Util.loc2int(symloc)];

                    if (symVal == 0) {
                        continue;
                    }

                    if (vals[locID] != symVal) {
                        // wall at one place but not the other
                        eliminateSym(sym, loc);
                    } else if (Robot.getDisToMyClosestSpawnCenter(symloc) <= 2) {
                        if (info.getSpawnZoneTeam() != Robot.oppTeamID) {
                            // if the symmetric location is our spawn point but this point is not opp spawn team
                            eliminateSym(sym, loc);
                        }
                    } else if (info.getSpawnZoneTeam() == Robot.oppTeamID) {
                        // if the symmetric location is not our spawn point but this point is enemy spawn point
                        eliminateSym(sym, loc);
                    }
                }
            }
        }
    }

    public static void initTurn() throws GameActionException {
        Debug.printString(String.format("w%d", reportedWalls.size));
        updateSym();
        // receive wall report
        int canReportIndex = -1;
        for (int i = Comms.WALL_SLOTS; --i >= 0;) {
            int locnum = Comms.readWallLoc(i);
            if (locnum != 0) {
                MapLocation loc = Util.int2loc(locnum);
                reportedWalls.add(loc);
                walls2report.remove(loc);
                vals[locnum] = WALL_BIT;
                if (symConfimred) {
                    MapLocation symloc = getSymmetricLoc(loc);
                    reportedWalls.add(symloc);
                    walls2report.remove(symloc);
                    vals[Util.loc2int(symloc)] = WALL_BIT;
                }
                // remove my report after 1 round, everyone has seen it already
                if (i == myWallReportID) {
                    Comms.writeWallLoc(i, 0);
                    myWallReportID = -1;
                }
            } else {
                canReportIndex = i;
            }
        }
        // report new walls
        if (canReportIndex != -1) {
            MapLocation loc2report = walls2report.pop();
            if (loc2report != null) {
                myWallReportID = canReportIndex;
                Comms.writeWallLoc(myWallReportID, Util.loc2int(loc2report));
            }
        }
    }
    public static void updateSym() throws GameActionException {
        // called on turn start
        if (Comms.readSymmetrySym() != symmetry) {
            symmetry |= Comms.readSymmetrySym();
            Robot.oppSpawnCenters[0] = getSymmetricLoc(Robot.mySpawnCenters[0]);
            Robot.oppSpawnCenters[1] = getSymmetricLoc(Robot.mySpawnCenters[1]);
            Robot.oppSpawnCenters[2] = getSymmetricLoc(Robot.mySpawnCenters[2]);

            switch (symmetry) {
                case 0b011: case 0b101: case 0b110:
                    symConfimred = true;
                    needWallFlip = true;
            }
        }
    }

    private static void eliminateSym(int sym, MapLocation loc) throws GameActionException {
        Comms.writeSymmetrySym(symmetry | sym);
        Comms.push();
        updateSym();
        Debug.println(Debug.INFO, String.format("eliminate sym %d at %d %d now sym=%d", sym, loc.x, loc.y, symmetry));
    }

    public static MapLocation getSymmetricLoc(MapLocation loc, int sym) {
        switch (sym) {
            case 0b000: case 0b001: case 0b010: case 0b011: // rotational
                return new MapLocation(W - loc.x - 1, H - loc.y - 1);
            case 0b100: case 0b101: // vertical
                return new MapLocation(loc.x, H - loc.y - 1);
            case 0b110:  // horizontal
                return new MapLocation(W - loc.x - 1, loc.y);
        }
        assert false;
        return null;
    }

    public static MapLocation getSymmetricLoc(MapLocation loc) {
        switch (symmetry) {
            case 0b000: case 0b001: case 0b010: case 0b011: // horizontal
                return new MapLocation(W - loc.x - 1, H - loc.y - 1);
            case 0b100: case 0b101: // vertical
                return new MapLocation(loc.x, H - loc.y - 1);
            case 0b110:  // horizontal
                return new MapLocation(W - loc.x - 1, loc.y);
        }
        assert false;
        return null;
    }
}
