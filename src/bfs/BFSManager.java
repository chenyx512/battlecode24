package bfs;

import battlecode.common.*;


public class BFSManager {
    static int MAX_BROADCASTS = 15;

    static final int MAX_BYTECODE = 500;

    RobotController rc;

    BFS[] bfsList = new BFS[MAX_BROADCASTS];
    BFSGreedy[] bfsGreedy = new BFSGreedy[MAX_BROADCASTS];

    MapLocation[] buffer = new MapLocation[MAX_BROADCASTS];

    int bufferIndex = 0;

    int index = 0;

    static Direction[] dirs = Direction.values();

    /*
     * Constructor
     */
    BFSManager(){
        this.rc = Robot.rc;
    }

    int requestBFS(MapLocation location){
        buffer[bufferIndex++] = new MapLocation(location.x, location.y);
        return bufferIndex-1;
    }

    void reset(int i, MapLocation newLoc){
        if (i >= index){
            if (i < bufferIndex){
                buffer[i] = newLoc;
            } else{
                System.out.println("BFS: this should not happen!");
            }
        } else {
            bfsList[i].reset(newLoc);
        }
    }

    void runBuffer(){
        if (index >= bufferIndex) {
            for (int i = 0; i < index; ++i){
                if (!bfsGreedy[i].init){
                    bfsGreedy[i].init();
                    return;
                }
            }
            for (int i = 0; i < index; ++i){
                if (bfsList[i].reset){
                    bfsList[i].applyReset();
                    bfsGreedy[i].reset();
                    return;
                }
                if (bfsGreedy[i].reset){
                    Constants.indicatorString += " (reset + "+ Clock.getBytecodeNum();
                    bfsGreedy[i].applyReset();
                    Constants.indicatorString += " " + Clock.getBytecodeNum() + ") ";
                    return;
                }
            }
            return;
        }
        if (rc.getRoundNum() <= Robot.roundBirth) return;
        bfsList[index] = new BFS(buffer[index]);
        bfsGreedy[index] = new BFSGreedy(bfsList[index]);
        Robot.bytecodeDebug += " (initialized) ";
        index++;
    }

    /*
     * Runs the BFSs and fill the distance matrix
     */
    void run(){
        if (rc.getRoundNum()%2 == 0) {
            for (int i = 0; i < index; ++i) {
                if (Clock.getBytecodesLeft() < MAX_BYTECODE) return;
                bfsList[i].runBFS();
                bfsGreedy[i].runBFS();
            }
        } else {
            for (int i = index; i-- > 0;) {
                if (Clock.getBytecodesLeft() < MAX_BYTECODE) return;
                bfsList[i].runBFS();
                bfsGreedy[i].runBFS();
            }
        }
    }

    int currentBfsIndex;

    Direction getBestDirection(int bfsIndex) throws GameActionException {
        if (bfsIndex >= index) return null;
        currentBfsIndex = bfsIndex;
        MapLocation myLoc = rc.getLocation();

        Direction bestDirectionMovable = Direction.CENTER;
        MapLocation newLoc = AdjacentTiles.dirDanger[Direction.CENTER.ordinal()].endLoc;
        int bestDistMovable = getDistance(newLoc);
        if (bestDistMovable == 0) return null;

        int initialDist = bestDistMovable;
        int bestGreedyDist = bestDistMovable;
        //Direction greedyDist = Direction.CENTER;

        // TODO: unroll loop
        for (Direction d : dirs){
            if (d == Direction.CENTER) continue;
            if (!AdjacentTiles.dirDanger[d.ordinal()].closeToEnemyHQ){
                if (rc.canMove(d)){
                    int newDist = getDistance(AdjacentTiles.dirDanger[d.ordinal()].endLoc);
                    if (newDist != 0 && newDist <= bestDistMovable) {
                        bestDistMovable = newDist;
                        bestDirectionMovable = d;
                        if (bestDistMovable < bestGreedyDist){
                            bestGreedyDist = bestDistMovable;
                            //greedyDist = d;
                        }
                    }
                }
                else{
                    newLoc = myLoc.add(d);
                    if (!rc.onTheMap(newLoc)) continue;
                    RobotInfo r = rc.senseRobotAtLocation(newLoc);
                    // if (r != null && r.getType() != RobotType.HEADQUARTERS){
                    if (r != null){
                        //rc.setIndicatorDot(newLoc, 255, 255, 0);
                        int newDist = getDistance(AdjacentTiles.dirDanger[d.ordinal()].endLoc);
                        if (newDist != 0 && newDist < bestGreedyDist) {
                            bestGreedyDist = newDist;
                            //greedyDist = d;
                        }
                    }
                }
            }
        }

        if (bestGreedyDist >= initialDist){
            /*for (Direction dir : dirs){
                MapLocation l = AdjacentTiles.dirDanger[dir.ordinal()].endLoc;
                switch (getDistance(l)){
                    case 3: rc.setIndicatorDot(l, 255, 0, 0); break;
                    case 2: rc.setIndicatorDot(l, 0, 255, 0); break;
                    case 4: rc.setIndicatorDot(l, 0, 0, 255); break;
                    default : rc.setIndicatorDot(l, 150, 150, 150); break;
                }
            }*/
            //Constants.indicatorString += "RESETTING. " + "DC: " + initialDist + ". DG: " + bestGreedyDist + ". BD: " + greedyDist.name() + " " + rc.getLocation().x + " " + rc.getLocation().y;
            bfsGreedy[currentBfsIndex].reset();
            return null;
        }

        return bestDirectionMovable;
    }

    int getDistance(MapLocation loc){
        int d = bfsList[currentBfsIndex].getDistance(loc);
        int d2 = bfsGreedy[currentBfsIndex].getDistance(loc);
        if (d2 != 0){
            if (d == 0) return d2;
            if (d2 != d){
                bfsGreedy[currentBfsIndex].reset();
                //Constants.indicatorString += "RESETTING!! ";
                //rc.setIndicatorDot(loc, 255, 255, 0);
                return d;
            }
            return d2;
        }
        return d;
    }
}