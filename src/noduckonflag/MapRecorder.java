package noduckonflag;

import battlecode.common.*;

public class MapRecorder extends RobotPlayer {
    public static final char SEEN_BIT = 1;
    public static final char WALL_BIT = 1 << 1;

    // fun fact: this costs 1 bytecode, but declaring array costs 3600
    public static char[] vals = Constants.STRING_LEN_4200.toCharArray();

    // bit 0b100 means rotational eliminated, 0b010 vertical, 0b001 horizontal
    private static int symmetry;

    public static boolean getPassible(MapLocation loc) {
        int val = vals[Util.loc2int(loc)];
        if ((val & SEEN_BIT) > 0)
            return (val & WALL_BIT) == 0;
        val = vals[Util.loc2int(getSymmetricLoc(loc))];
        return (val & WALL_BIT) == 0;
    }

    public static int getData(MapLocation loc) {
        return vals[Util.loc2int(loc)];
    }

    // record what we can sense on the map, perform sym check if needed
    // always called at the end of a turn and will run until all bytecode consumed
    public static void recordSym(int leaveBytecodeCnt) throws GameActionException {
        MapInfo[] infos = rc.senseNearbyMapInfos();
        for (int i = infos.length; --i >= 0; ) {
            if (Clock.getBytecodesLeft() <= leaveBytecodeCnt) {
                return;
            }
            MapInfo info = infos[i];
            MapLocation loc = info.getMapLocation();
            int locID = Util.loc2int(loc);
            if ((vals[locID] & SEEN_BIT) != 0)
                continue;

            char val = SEEN_BIT;
            if (info.isWall())
                val |= WALL_BIT;
            vals[Util.loc2int(loc)] = val;

            switch (symmetry) {
                case 0b000: case 0b001: case 0b010: case 0b100:
                for (int sym = 0b100; sym > 0; sym >>= 1) {
                    if ((sym & symmetry) > 0)
                        continue;
                    MapLocation symloc = getSymmetricLoc(loc, 0b111 - sym);
                    int symVal = vals[Util.loc2int(symloc)];
                    if ((symVal & SEEN_BIT) == 0) {
                        continue;
                    }

                    if ((val & WALL_BIT) != (symVal & WALL_BIT)) {
                        // wall at one place bot not the other
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

    public static void updateSym() throws GameActionException {
        if (Comms.readSymmetrySym() != symmetry) {
            symmetry |= Comms.readSymmetrySym();
            Robot.oppSpawnCenters[0] = getSymmetricLoc(Robot.mySpawnCenters[0]);
            Robot.oppSpawnCenters[1] = getSymmetricLoc(Robot.mySpawnCenters[1]);
            Robot.oppSpawnCenters[2] = getSymmetricLoc(Robot.mySpawnCenters[2]);
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
