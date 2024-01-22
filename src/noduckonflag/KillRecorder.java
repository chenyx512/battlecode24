package noduckonflag;

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
    }

    public static void recordKill() throws GameActionException {
        int oppCnt = Comms.readOppteamCnt() - 1;
        if (oppCnt >= 0) {
            // it's possible that opp count drops below zero cuz round number may be off by one
            // in this case we just don't record this kill
            killCnt[roundNo]++;
            Comms.writeOppteamCnt(oppCnt);
        }
    }
}
