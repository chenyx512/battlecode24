package bfs;

import battlecode.common.Clock;
import battlecode.common.MapLocation;

public class BFSGreedy {
    BFS trueBFS;

    boolean reset = true;
    boolean init = false;

    int queueIndexBeginning = 0;
    int queueIndexEnd = 0;

    int[] vars;

    static int[] mapInfo;
    static int H, W;

    static int H1, W1;

    // static int diffE, diffNE, diffN, diffNW, diffW, diffSW, diffS, diffSE;
    static MapLocation diffE = new MapLocation(1, 0);
    static MapLocation diffNE = new MapLocation(1, 1);
    static MapLocation diffN = new MapLocation(0, 1);
    static MapLocation diffNW = new MapLocation(-1, 1);
    static MapLocation diffW = new MapLocation(-1, 0);
    static MapLocation diffSW = new MapLocation(-1, -1);
    static MapLocation diffS = new MapLocation(0, -1);
    static MapLocation diffSE = new MapLocation(1, -1);

    static final int MAX_BYTECODE = 1200;

    BFSGreedy(BFS trueBFS){
        this.trueBFS = trueBFS;
        H = MapData.H;
        W = MapData.W;

        this.mapInfo = MapData.mapInfo;

        H1 = H - 1;
        W1 = W - 1;

        // diffE = H;
        // diffNE = H+1;
        // diffN = 1;
        // diffNW = 1-H;
        // diffW = -H;
        // diffSW = -H-1;
        // diffS = -1;
        // diffSE = H-1;
    }

    void init(){
        init = true;
        vars = new int[trueBFS.vars.length];
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
        Constants.indicatorString += "RESETTINGGGG ";
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
        Robot.rc.setIndicatorDot(new MapLocation(x,y), 0, 0, 255);

        //int b = Clock.getBytecodeNum();

        /*********************************************** NE ****************************************************/
        if (x < W1 && y < H1){
            // aux = code + diffNE;
            new_ml = ml.add(diffNE);
            // TODO: precompute the difference?
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
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
            new_ml = ml.add(diffE);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** SE ****************************************************/
        if (x < W1 && y > 0){
            new_ml = ml.add(diffSE);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** N ****************************************************/
        if (y < H1){
            new_ml = ml.add(diffN);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** S ****************************************************/
        if (y > 0){
            new_ml = ml.add(diffS);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** NW ****************************************************/
        if (x > 0 && y < H1){
            new_ml = ml.add(diffNW);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** W ****************************************************/
        if (x > 0){
            new_ml = ml.add(diffW);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }

        /*********************************************** SW ****************************************************/
        if (x > 0 && y > 0){
            new_ml = ml.add(diffSW);
            aux = new_ml.x*H + new_ml.y;

            switch (MapRecorder.getPassible(new_ml)){
                case true:
                    switch (vars[aux]&0xFFFF){
                        case 0:
                            vars[aux] |= dist;
                            vars[queueIndexEnd++] |= ((aux) << 16);
                            break;
                    }
                    break;
                case false: // wall
                    break;
            }
        }
        return true;
    }


    void runBFS(){
        if (reset) return;
        int code;
        Constants.indicatorString += "  TRYING TO GO " + Clock.getBytecodeNum() + " ";
        while(queueIndexEnd > queueIndexBeginning){
            if (Clock.getBytecodesLeft() < MAX_BYTECODE) return;
            //int b = Clock.getBytecodeNum();
            includeNeutral = true;
            code = (vars[queueIndexBeginning] >>> 16)&0xFFFF;

            switch (MapRecorder.getPassible(ml)){
                case true:
                    break;
                case false: // wall
                    if (queueIndexBeginning > 0) {
                        //Constants.indicatorString += "  RIP PATH  ";
                        ++queueIndexBeginning;
                        continue;
                    }
                    break;
            }

            // switch((mapInfo[code/4] >>> (4*(code%4))) & 0xF) {
            //     case 10:
            //     case 12:
            //         if (queueIndexBeginning > 0) {
            //             //Constants.indicatorString += "  RIP PATH  ";
            //             ++queueIndexBeginning;
            //             continue;
            //         }
            //         break;
            // }

            dist = (vars[code]&0xFFFF) + 1;

            if (fillWithNeutral(code)) ++queueIndexBeginning;
            else return;
            //Constants.indicatorString += "PATH_BC = " + (Clock.getBytecodeNum() - b);
        }
    }

}