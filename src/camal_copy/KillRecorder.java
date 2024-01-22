package camal_copy;

import battlecode.common.*;

public class KillRecorder extends RobotPlayer {
    public static int cntDiff;
    private static boolean isAlive = false;
    private static int[] killCnt = new int[GameConstants.JAILED_ROUNDS];
    private static int roundNo;

    public static void init() throws GameActionException {
        Comms.writeMyteamCnt(0);
        Comms.writeOppteamCnt(50);
    }

    public static void initTurn() throws GameActionException {
        roundNo = rc.getRoundNum() % GameConstants.JAILED_ROUNDS;
        if (rc.isSpawned() && !isAlive) {
            // we just spawn in
            isAlive = true;
            Comms.writeMyteamCnt(Comms.readMyteamCnt() + 1);
        } else if (!rc.isSpawned() && isAlive) {
            // we just died
            isAlive = false;
            Comms.writeMyteamCnt(Comms.readMyteamCnt() - 1);
        }
        if (killCnt[roundNo] > 0) {
            // opp team duck just spawned
            Comms.writeOppteamCnt(Comms.readOppteamCnt() + killCnt[roundNo]);
            killCnt[roundNo] = 0;
        }
        cntDiff = Comms.readMyteamCnt() - Comms.readOppteamCnt();
        // TODO off by one may cause count to go under 0
    }

    public static void recordKill() throws GameActionException {
        killCnt[roundNo]++;
        Comms.writeOppteamCnt(Comms.readOppteamCnt() - 1);
    }
}
