package bfs;

import battlecode.common.Direction;
import battlecode.common.Clock;
import battlecode.common.MapLocation;

public class BFSGreedy {
    BFS trueBFS;

    boolean reset = true;
    boolean init = false;

    int queueIndexBeginning = 0;
    int queueIndexEnd = 0;

    int[] vars;

    static int H, W;

    static int H1, W1;

    static int diffE, diffNE, diffN, diffNW, diffW, diffSW, diffS, diffSE;

    public final static Direction NORTH = Direction.NORTH;
    public final static Direction NORTHEAST = Direction.NORTHEAST;
    public final static Direction EAST = Direction.EAST;
    public final static Direction SOUTHEAST = Direction.SOUTHEAST;
    public final static Direction SOUTH = Direction.SOUTH;
    public final static Direction SOUTHWEST = Direction.SOUTHWEST;
    public final static Direction WEST = Direction.WEST;
    public final static Direction NORTHWEST = Direction.NORTHWEST;
    public final static Direction CENTER = Direction.CENTER;

    static final int MAX_BYTECODE = 1500;

    BFSGreedy(BFS trueBFS){
        this.trueBFS = trueBFS;
        H = RobotPlayer.H;
        W = RobotPlayer.W;

        H1 = H - 1;
        W1 = W - 1;

        diffE = H;
        diffNE = H+1;
        diffN = 1;
        diffNW = 1-H;
        diffW = -H;
        diffSW = -H-1;
        diffS = -1;
        diffSE = H-1;
    }

    void init(){
        init = true;
        // TODO: yx more efficient initialization
        vars = new int[trueBFS.vars.length];
        applyReset();
    }

    /*
     * Queues reset
     */
    void reset(){
        reset = true;
    }

    /*
     * Applies reset
     */
    void applyReset(){
        Debug.println("RESETTINGGGG ");
        queueIndexBeginning = trueBFS.queueIndexBeginning;
        queueIndexEnd = trueBFS.queueIndexEnd;
        System.arraycopy(trueBFS.vars, 0, vars, 0, trueBFS.vars.length);
        reset = false;
    }

    /*
     * Returns the distance from maplocation m to the target location
     */
    int getDistance(MapLocation m){
        if (reset) return 0;
        int aux = m.x*H + m.y;
        return (vars[aux] & 0xFFFF);
    }

    /*
     * Returns the first location to be traversed in the queue
     */
    MapLocation getFront(){
        if (reset) return null;
        if (queueIndexBeginning >= queueIndexEnd) return null;
        int code = (vars[queueIndexBeginning] >>> 16)&0xFFFF;
        return new MapLocation(code/H, code%H);
    }

    static int x, y, aux, dist;
    static MapLocation new_ml;

    /*
     * BFS traversal of the map
     */
    boolean fillWithNeutral(int code){
        x = code/H; y = code%H;
        MapLocation ml = new MapLocation(x, y);
        Robot.rc.setIndicatorDot(ml, 0, 0, 255);

        //int b = Clock.getBytecodeNum();

        /*********************************************** NE ****************************************************/
        if (x < W1 && y < H1){
            // aux = code + diffNE;
            new_ml = ml.add(NORTHEAST);
            // TODO: precompute the difference?
            aux = code + diffNE;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }

            // switch((mapInfo[aux/4] >>> (4*(aux%4))) & 0xF){
            //     case 10:
            //     case 12: break;
            //     default:
            //         switch (vars[aux]&0xFFFF){
            //             case 0:
            //                 vars[aux] |= dist;
            //                 vars[queueIndexEnd++] |= (aux << 16);
            //                 break;
            //         }
            //         break;
            // }
        }

        /*********************************************** E ****************************************************/
        if (x < W1){
            new_ml = ml.add(EAST);
            aux = code + diffE;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** SE ****************************************************/
        if (x < W1 && y > 0){
            new_ml = ml.add(SOUTHEAST);
            aux = code + diffSE;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** N ****************************************************/
        if (y < H1){
            new_ml = ml.add(NORTH);
            aux = code + diffN;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** S ****************************************************/
        if (y > 0){
            new_ml = ml.add(SOUTH);
            aux = code + diffS;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** NW ****************************************************/
        if (x > 0 && y < H1){
            new_ml = ml.add(NORTHWEST);
            aux = code + diffNW;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** W ****************************************************/
        if (x > 0){
            new_ml = ml.add(WEST);
            aux = code + diffW;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }

        /*********************************************** SW ****************************************************/
        if (x > 0 && y > 0){
            new_ml = ml.add(SOUTHWEST);
            aux = code + diffSW;

            if (MapRecorder.getPassible(new_ml)){ // no wall
                switch (vars[aux]&0xFFFF){
                    case 0:
                        vars[aux] |= dist;
                        vars[queueIndexEnd++] |= ((aux) << 16);
                        break;
                }
            }
        }
        return true;
    }


    void runBFS(){
        if (reset) return;
        int code;
        // Debug.println("  TRYING TO GO " + Clock.getBytecodeNum() + " ");
        Debug.println("  TRYING TO GO " + Clock.getBytecodeNum() + " ");
        while(queueIndexEnd > queueIndexBeginning){
            if (Clock.getBytecodesLeft() < MAX_BYTECODE) return;
            //int b = Clock.getBytecodeNum();
            code = (vars[queueIndexBeginning] >>> 16)&0xFFFF;
            x = code/H; y = code%H;
            MapLocation ml = new MapLocation(x, y);

            if (!MapRecorder.getPassible(ml)){ // wall
                if (queueIndexBeginning > 0) {
                    // Debug.println("  RIP PATH  ");
                    ++queueIndexBeginning;
                    continue;
                }
                break;
            }

            // switch((mapInfo[code/4] >>> (4*(code%4))) & 0xF) {
            //     case 10:
            //     case 12:
            //         if (queueIndexBeginning > 0) {
            //             // Debug.println("  RIP PATH  ");
            //             ++queueIndexBeginning;
            //             continue;
            //         }
            //         break;
            // }

            dist = (vars[code]&0xFFFF) + 1;

            if (fillWithNeutral(code)) ++queueIndexBeginning;
            else return;
            // Debug.println("PATH_BC = " + (Clock.getBytecodeNum() - b));
        }
    }

}