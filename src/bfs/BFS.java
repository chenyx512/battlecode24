package bfs;

import battlecode.common.Clock;
import battlecode.common.MapLocation;

public class BFS {
    int queueIndexBeginning = 0;
    int queueIndexEnd = 1;

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

    boolean reset = false;

    MapLocation start = null;

    // constructor
    BFS(MapLocation start){
        H = MapData.H;
        W = MapData.W;

        // TODO: more efficient initialization
        vars = new int[H*W];
        this.mapInfo = MapData.mapInfo;
        vars[0] |= ((start.x*H + start.y) << 16);
        vars[start.x*H + start.y] |= 1;

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

    /*
     * Queues reset and sets new start location
     */
    void reset(MapLocation newStart){
        this.start = newStart;
        reset = true;
    }

    /*
     * Applies reset
     */
    void applyReset(){
        vars = new int[H*W];
        vars[0] |= ((start.x*H + start.y) << 16); // store start location code in the first 16 bits of vars[0]
        vars[start.x*H + start.y] |= 1;
        reset = false;
        queueIndexBeginning = 0;
        queueIndexEnd = 1;
    }

    /*
     * Returns the distance from maplocation m to the target location
     */
    int getDistance(MapLocation m){
        int aux = m.x*H + m.y;
        return (vars[aux] & 0xFFFF);
    }

    /*
     * Returns the first location to be traversed in the queue
     */
    MapLocation getFront(){
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
                // TODO: consider not visited vs passible
            }

            // switch((mapInfo[aux/4] >>> (4*(aux%4))) & 0xF){
            //     case 0: return false; // not visited yet
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
        int code;
        while(queueIndexEnd > queueIndexBeginning){
            if (Clock.getBytecodesLeft() < MAX_BYTECODE) return;
            //int b = Clock.getBytecodeNum();

            code = (vars[queueIndexBeginning] >>> 16)&0xFFFF;
            x = code/H; y = code%H;
            ml = new MapLocation(x, y);

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
            //     case 0:
            //         Robot.rc.setIndicatorDot(new MapLocation(x, y), 255, 0, 0);
            //         return;
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