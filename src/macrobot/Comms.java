/* Comms.java is generated by gen_comms.py from CommsTemplate.java. 
DO NOT MODIFY THIS FILE DIRECTLY */
package macrobot;

import battlecode.common.*;

public class Comms extends RobotPlayer {
    private static int buf0,buf1,buf2,buf3,buf4,buf5,buf6,buf7,buf8,buf9,buf10,buf11,buf12,buf13,buf14,buf15,buf16,buf17,buf18,buf19,buf20,buf21,buf22,buf23,buf24,buf25,buf26,buf27,buf28,buf29,buf30,buf31,buf32,buf33,buf34,buf35,buf36,buf37,buf38,buf39,buf40,buf41,buf42,buf43,buf44,buf45,buf46,buf47,buf48,buf49,buf50,buf51,buf52,buf53,buf54,buf55,buf56,buf57,buf58,buf59,buf60,buf61,buf62,buf63;
    private static int dirty0,dirty1,dirty2,dirty3,dirty4,dirty5,dirty6,dirty7,dirty8,dirty9,dirty10,dirty11,dirty12,dirty13,dirty14,dirty15,dirty16,dirty17,dirty18,dirty19,dirty20,dirty21,dirty22,dirty23,dirty24,dirty25,dirty26,dirty27,dirty28,dirty29,dirty30,dirty31,dirty32,dirty33,dirty34,dirty35,dirty36,dirty37,dirty38,dirty39,dirty40,dirty41,dirty42,dirty43,dirty44,dirty45,dirty46,dirty47,dirty48,dirty49,dirty50,dirty51,dirty52,dirty53,dirty54,dirty55,dirty56,dirty57,dirty58,dirty59,dirty60,dirty61,dirty62,dirty63;

    public final static int HQ_SLOTS = 3;
    public final static int SYMMETRY_SLOTS = 1;
    public final static int MYFLAGS_SLOTS = 3;
    public final static int OPPFLAGS_SLOTS = 3;

    public static void pull() throws GameActionException {
        buf0 = rc.readSharedArray(0);
        buf1 = rc.readSharedArray(1);
        buf2 = rc.readSharedArray(2);
        buf3 = rc.readSharedArray(3);
        buf4 = rc.readSharedArray(4);
        buf5 = rc.readSharedArray(5);
        buf6 = rc.readSharedArray(6);
        buf7 = rc.readSharedArray(7);
        buf8 = rc.readSharedArray(8);
        buf9 = rc.readSharedArray(9);
        buf10 = rc.readSharedArray(10);
        buf11 = rc.readSharedArray(11);
        buf12 = rc.readSharedArray(12);
        buf13 = rc.readSharedArray(13);
        buf14 = rc.readSharedArray(14);
        buf15 = rc.readSharedArray(15);
        buf16 = rc.readSharedArray(16);
        buf17 = rc.readSharedArray(17);
        buf18 = rc.readSharedArray(18);
        buf19 = rc.readSharedArray(19);
        buf20 = rc.readSharedArray(20);
        buf21 = rc.readSharedArray(21);
        buf22 = rc.readSharedArray(22);
        buf23 = rc.readSharedArray(23);
        buf24 = rc.readSharedArray(24);
        buf25 = rc.readSharedArray(25);
        buf26 = rc.readSharedArray(26);
        buf27 = rc.readSharedArray(27);
        buf28 = rc.readSharedArray(28);
        buf29 = rc.readSharedArray(29);
        buf30 = rc.readSharedArray(30);
        buf31 = rc.readSharedArray(31);
        buf32 = rc.readSharedArray(32);
        buf33 = rc.readSharedArray(33);
        buf34 = rc.readSharedArray(34);
        buf35 = rc.readSharedArray(35);
        buf36 = rc.readSharedArray(36);
        buf37 = rc.readSharedArray(37);
        buf38 = rc.readSharedArray(38);
        buf39 = rc.readSharedArray(39);
        buf40 = rc.readSharedArray(40);
        buf41 = rc.readSharedArray(41);
        buf42 = rc.readSharedArray(42);
        buf43 = rc.readSharedArray(43);
        buf44 = rc.readSharedArray(44);
        buf45 = rc.readSharedArray(45);
        buf46 = rc.readSharedArray(46);
        buf47 = rc.readSharedArray(47);
        buf48 = rc.readSharedArray(48);
        buf49 = rc.readSharedArray(49);
        buf50 = rc.readSharedArray(50);
        buf51 = rc.readSharedArray(51);
        buf52 = rc.readSharedArray(52);
        buf53 = rc.readSharedArray(53);
        buf54 = rc.readSharedArray(54);
        buf55 = rc.readSharedArray(55);
        buf56 = rc.readSharedArray(56);
        buf57 = rc.readSharedArray(57);
        buf58 = rc.readSharedArray(58);
        buf59 = rc.readSharedArray(59);
        buf60 = rc.readSharedArray(60);
        buf61 = rc.readSharedArray(61);
        buf62 = rc.readSharedArray(62);
        buf63 = rc.readSharedArray(63);
    }

    public static void push() throws GameActionException {
        switch (dirty0) {case 1: rc.writeSharedArray(0, buf0); dirty0 = 0;}
        switch (dirty1) {case 1: rc.writeSharedArray(1, buf1); dirty1 = 0;}
        switch (dirty2) {case 1: rc.writeSharedArray(2, buf2); dirty2 = 0;}
        switch (dirty3) {case 1: rc.writeSharedArray(3, buf3); dirty3 = 0;}
        switch (dirty4) {case 1: rc.writeSharedArray(4, buf4); dirty4 = 0;}
        switch (dirty5) {case 1: rc.writeSharedArray(5, buf5); dirty5 = 0;}
        switch (dirty6) {case 1: rc.writeSharedArray(6, buf6); dirty6 = 0;}
        switch (dirty7) {case 1: rc.writeSharedArray(7, buf7); dirty7 = 0;}
        switch (dirty8) {case 1: rc.writeSharedArray(8, buf8); dirty8 = 0;}
        switch (dirty9) {case 1: rc.writeSharedArray(9, buf9); dirty9 = 0;}
        switch (dirty10) {case 1: rc.writeSharedArray(10, buf10); dirty10 = 0;}
        switch (dirty11) {case 1: rc.writeSharedArray(11, buf11); dirty11 = 0;}
        switch (dirty12) {case 1: rc.writeSharedArray(12, buf12); dirty12 = 0;}
        switch (dirty13) {case 1: rc.writeSharedArray(13, buf13); dirty13 = 0;}
        switch (dirty14) {case 1: rc.writeSharedArray(14, buf14); dirty14 = 0;}
        switch (dirty15) {case 1: rc.writeSharedArray(15, buf15); dirty15 = 0;}
        switch (dirty16) {case 1: rc.writeSharedArray(16, buf16); dirty16 = 0;}
        switch (dirty17) {case 1: rc.writeSharedArray(17, buf17); dirty17 = 0;}
        switch (dirty18) {case 1: rc.writeSharedArray(18, buf18); dirty18 = 0;}
        switch (dirty19) {case 1: rc.writeSharedArray(19, buf19); dirty19 = 0;}
        switch (dirty20) {case 1: rc.writeSharedArray(20, buf20); dirty20 = 0;}
        switch (dirty21) {case 1: rc.writeSharedArray(21, buf21); dirty21 = 0;}
        switch (dirty22) {case 1: rc.writeSharedArray(22, buf22); dirty22 = 0;}
        switch (dirty23) {case 1: rc.writeSharedArray(23, buf23); dirty23 = 0;}
        switch (dirty24) {case 1: rc.writeSharedArray(24, buf24); dirty24 = 0;}
        switch (dirty25) {case 1: rc.writeSharedArray(25, buf25); dirty25 = 0;}
        switch (dirty26) {case 1: rc.writeSharedArray(26, buf26); dirty26 = 0;}
        switch (dirty27) {case 1: rc.writeSharedArray(27, buf27); dirty27 = 0;}
        switch (dirty28) {case 1: rc.writeSharedArray(28, buf28); dirty28 = 0;}
        switch (dirty29) {case 1: rc.writeSharedArray(29, buf29); dirty29 = 0;}
        switch (dirty30) {case 1: rc.writeSharedArray(30, buf30); dirty30 = 0;}
        switch (dirty31) {case 1: rc.writeSharedArray(31, buf31); dirty31 = 0;}
        switch (dirty32) {case 1: rc.writeSharedArray(32, buf32); dirty32 = 0;}
        switch (dirty33) {case 1: rc.writeSharedArray(33, buf33); dirty33 = 0;}
        switch (dirty34) {case 1: rc.writeSharedArray(34, buf34); dirty34 = 0;}
        switch (dirty35) {case 1: rc.writeSharedArray(35, buf35); dirty35 = 0;}
        switch (dirty36) {case 1: rc.writeSharedArray(36, buf36); dirty36 = 0;}
        switch (dirty37) {case 1: rc.writeSharedArray(37, buf37); dirty37 = 0;}
        switch (dirty38) {case 1: rc.writeSharedArray(38, buf38); dirty38 = 0;}
        switch (dirty39) {case 1: rc.writeSharedArray(39, buf39); dirty39 = 0;}
        switch (dirty40) {case 1: rc.writeSharedArray(40, buf40); dirty40 = 0;}
        switch (dirty41) {case 1: rc.writeSharedArray(41, buf41); dirty41 = 0;}
        switch (dirty42) {case 1: rc.writeSharedArray(42, buf42); dirty42 = 0;}
        switch (dirty43) {case 1: rc.writeSharedArray(43, buf43); dirty43 = 0;}
        switch (dirty44) {case 1: rc.writeSharedArray(44, buf44); dirty44 = 0;}
        switch (dirty45) {case 1: rc.writeSharedArray(45, buf45); dirty45 = 0;}
        switch (dirty46) {case 1: rc.writeSharedArray(46, buf46); dirty46 = 0;}
        switch (dirty47) {case 1: rc.writeSharedArray(47, buf47); dirty47 = 0;}
        switch (dirty48) {case 1: rc.writeSharedArray(48, buf48); dirty48 = 0;}
        switch (dirty49) {case 1: rc.writeSharedArray(49, buf49); dirty49 = 0;}
        switch (dirty50) {case 1: rc.writeSharedArray(50, buf50); dirty50 = 0;}
        switch (dirty51) {case 1: rc.writeSharedArray(51, buf51); dirty51 = 0;}
        switch (dirty52) {case 1: rc.writeSharedArray(52, buf52); dirty52 = 0;}
        switch (dirty53) {case 1: rc.writeSharedArray(53, buf53); dirty53 = 0;}
        switch (dirty54) {case 1: rc.writeSharedArray(54, buf54); dirty54 = 0;}
        switch (dirty55) {case 1: rc.writeSharedArray(55, buf55); dirty55 = 0;}
        switch (dirty56) {case 1: rc.writeSharedArray(56, buf56); dirty56 = 0;}
        switch (dirty57) {case 1: rc.writeSharedArray(57, buf57); dirty57 = 0;}
        switch (dirty58) {case 1: rc.writeSharedArray(58, buf58); dirty58 = 0;}
        switch (dirty59) {case 1: rc.writeSharedArray(59, buf59); dirty59 = 0;}
        switch (dirty60) {case 1: rc.writeSharedArray(60, buf60); dirty60 = 0;}
        switch (dirty61) {case 1: rc.writeSharedArray(61, buf61); dirty61 = 0;}
        switch (dirty62) {case 1: rc.writeSharedArray(62, buf62); dirty62 = 0;}
        switch (dirty63) {case 1: rc.writeSharedArray(63, buf63); dirty63 = 0;}
    }


    public static int readHqLoc(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf0 & 65520) >>> 4;
            case 1: return ((buf0 & 15) << 8) + ((buf1 & 65280) >>> 8);
            case 2: return ((buf1 & 255) << 4) + ((buf2 & 61440) >>> 12);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeHqLoc(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf0 = (buf0 & 15) | (value << 4);
                dirty0 = 1;
                break;
            case 1:
                buf0 = (buf0 & 65520) | ((value & 3840) >>> 8);
                dirty0 = 1;
                buf1 = (buf1 & 255) | ((value & 255) << 8);
                dirty1 = 1;
                break;
            case 2:
                buf1 = (buf1 & 65280) | ((value & 4080) >>> 4);
                dirty1 = 1;
                buf2 = (buf2 & 4095) | ((value & 15) << 12);
                dirty2 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readSymmetrySym() throws GameActionException {
        return (buf2 & 3584) >>> 9;
    }

    public static void writeSymmetrySym(int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 8, "write value out of range");
        buf2 = (buf2 & 61951) | (value << 9);
        dirty2 = 1;
    }

    public static int readMyflagsId(int idx) throws GameActionException {
        switch (idx) {
            case 0: return ((buf2 & 511) << 3) + ((buf3 & 57344) >>> 13);
            case 1: return (buf5 & 8190) >>> 1;
            case 2: return ((buf7 & 1) << 11) + ((buf8 & 65504) >>> 5);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsId(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf2 = (buf2 & 65024) | ((value & 4088) >>> 3);
                dirty2 = 1;
                buf3 = (buf3 & 8191) | ((value & 7) << 13);
                dirty3 = 1;
                break;
            case 1:
                buf5 = (buf5 & 57345) | (value << 1);
                dirty5 = 1;
                break;
            case 2:
                buf7 = (buf7 & 65534) | ((value & 2048) >>> 11);
                dirty7 = 1;
                buf8 = (buf8 & 31) | ((value & 2047) << 5);
                dirty8 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readMyflagsExists(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf3 & 4096) >>> 12;
            case 1: return (buf5 & 1);
            case 2: return (buf8 & 16) >>> 4;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsExists(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 2, "write value out of range");
        switch (idx) {
            case 0:
                buf3 = (buf3 & 61439) | (value << 12);
                dirty3 = 1;
                break;
            case 1:
                buf5 = (buf5 & 65534) | (value);
                dirty5 = 1;
                break;
            case 2:
                buf8 = (buf8 & 65519) | (value << 4);
                dirty8 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readMyflagsLoc(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf3 & 4095);
            case 1: return (buf6 & 65520) >>> 4;
            case 2: return ((buf8 & 15) << 8) + ((buf9 & 65280) >>> 8);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsLoc(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf3 = (buf3 & 61440) | (value);
                dirty3 = 1;
                break;
            case 1:
                buf6 = (buf6 & 15) | (value << 4);
                dirty6 = 1;
                break;
            case 2:
                buf8 = (buf8 & 65520) | ((value & 3840) >>> 8);
                dirty8 = 1;
                buf9 = (buf9 & 255) | ((value & 255) << 8);
                dirty9 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readMyflagsOriginalLoc(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf4 & 65520) >>> 4;
            case 1: return ((buf6 & 15) << 8) + ((buf7 & 65280) >>> 8);
            case 2: return ((buf9 & 255) << 4) + ((buf10 & 61440) >>> 12);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsOriginalLoc(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf4 = (buf4 & 15) | (value << 4);
                dirty4 = 1;
                break;
            case 1:
                buf6 = (buf6 & 65520) | ((value & 3840) >>> 8);
                dirty6 = 1;
                buf7 = (buf7 & 255) | ((value & 255) << 8);
                dirty7 = 1;
                break;
            case 2:
                buf9 = (buf9 & 65280) | ((value & 4080) >>> 4);
                dirty9 = 1;
                buf10 = (buf10 & 4095) | ((value & 15) << 12);
                dirty10 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readMyflagsAssigned(int idx) throws GameActionException {
        switch (idx) {
            case 0: return ((buf4 & 15) << 2) + ((buf5 & 49152) >>> 14);
            case 1: return (buf7 & 252) >>> 2;
            case 2: return (buf10 & 4032) >>> 6;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsAssigned(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 64, "write value out of range");
        switch (idx) {
            case 0:
                buf4 = (buf4 & 65520) | ((value & 60) >>> 2);
                dirty4 = 1;
                buf5 = (buf5 & 16383) | ((value & 3) << 14);
                dirty5 = 1;
                break;
            case 1:
                buf7 = (buf7 & 65283) | (value << 2);
                dirty7 = 1;
                break;
            case 2:
                buf10 = (buf10 & 61503) | (value << 6);
                dirty10 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readMyflagsDistress(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf5 & 8192) >>> 13;
            case 1: return (buf7 & 2) >>> 1;
            case 2: return (buf10 & 32) >>> 5;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeMyflagsDistress(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 2, "write value out of range");
        switch (idx) {
            case 0:
                buf5 = (buf5 & 57343) | (value << 13);
                dirty5 = 1;
                break;
            case 1:
                buf7 = (buf7 & 65533) | (value << 1);
                dirty7 = 1;
                break;
            case 2:
                buf10 = (buf10 & 65503) | (value << 5);
                dirty10 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsId(int idx) throws GameActionException {
        switch (idx) {
            case 0: return ((buf10 & 31) << 7) + ((buf11 & 65024) >>> 9);
            case 1: return ((buf13 & 255) << 4) + ((buf14 & 61440) >>> 12);
            case 2: return ((buf16 & 2047) << 1) + ((buf17 & 32768) >>> 15);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsId(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf10 = (buf10 & 65504) | ((value & 3968) >>> 7);
                dirty10 = 1;
                buf11 = (buf11 & 511) | ((value & 127) << 9);
                dirty11 = 1;
                break;
            case 1:
                buf13 = (buf13 & 65280) | ((value & 4080) >>> 4);
                dirty13 = 1;
                buf14 = (buf14 & 4095) | ((value & 15) << 12);
                dirty14 = 1;
                break;
            case 2:
                buf16 = (buf16 & 63488) | ((value & 4094) >>> 1);
                dirty16 = 1;
                buf17 = (buf17 & 32767) | ((value & 1) << 15);
                dirty17 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsExists(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf11 & 256) >>> 8;
            case 1: return (buf14 & 2048) >>> 11;
            case 2: return (buf17 & 16384) >>> 14;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsExists(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 2, "write value out of range");
        switch (idx) {
            case 0:
                buf11 = (buf11 & 65279) | (value << 8);
                dirty11 = 1;
                break;
            case 1:
                buf14 = (buf14 & 63487) | (value << 11);
                dirty14 = 1;
                break;
            case 2:
                buf17 = (buf17 & 49151) | (value << 14);
                dirty17 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsLoc(int idx) throws GameActionException {
        switch (idx) {
            case 0: return ((buf11 & 255) << 4) + ((buf12 & 61440) >>> 12);
            case 1: return ((buf14 & 2047) << 1) + ((buf15 & 32768) >>> 15);
            case 2: return (buf17 & 16380) >>> 2;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsLoc(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf11 = (buf11 & 65280) | ((value & 4080) >>> 4);
                dirty11 = 1;
                buf12 = (buf12 & 4095) | ((value & 15) << 12);
                dirty12 = 1;
                break;
            case 1:
                buf14 = (buf14 & 63488) | ((value & 4094) >>> 1);
                dirty14 = 1;
                buf15 = (buf15 & 32767) | ((value & 1) << 15);
                dirty15 = 1;
                break;
            case 2:
                buf17 = (buf17 & 49155) | (value << 2);
                dirty17 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsOriginalLoc(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf12 & 4095);
            case 1: return (buf15 & 32760) >>> 3;
            case 2: return ((buf17 & 3) << 10) + ((buf18 & 65472) >>> 6);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsOriginalLoc(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 4096, "write value out of range");
        switch (idx) {
            case 0:
                buf12 = (buf12 & 61440) | (value);
                dirty12 = 1;
                break;
            case 1:
                buf15 = (buf15 & 32775) | (value << 3);
                dirty15 = 1;
                break;
            case 2:
                buf17 = (buf17 & 65532) | ((value & 3072) >>> 10);
                dirty17 = 1;
                buf18 = (buf18 & 63) | ((value & 1023) << 6);
                dirty18 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsConfirmed(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf13 & 32768) >>> 15;
            case 1: return (buf15 & 4) >>> 2;
            case 2: return (buf18 & 32) >>> 5;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsConfirmed(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 2, "write value out of range");
        switch (idx) {
            case 0:
                buf13 = (buf13 & 32767) | (value << 15);
                dirty13 = 1;
                break;
            case 1:
                buf15 = (buf15 & 65531) | (value << 2);
                dirty15 = 1;
                break;
            case 2:
                buf18 = (buf18 & 65503) | (value << 5);
                dirty18 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsCarried(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf13 & 16384) >>> 14;
            case 1: return (buf15 & 2) >>> 1;
            case 2: return (buf18 & 16) >>> 4;
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsCarried(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 2, "write value out of range");
        switch (idx) {
            case 0:
                buf13 = (buf13 & 49151) | (value << 14);
                dirty13 = 1;
                break;
            case 1:
                buf15 = (buf15 & 65533) | (value << 1);
                dirty15 = 1;
                break;
            case 2:
                buf18 = (buf18 & 65519) | (value << 4);
                dirty18 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    public static int readOppflagsAssigned(int idx) throws GameActionException {
        switch (idx) {
            case 0: return (buf13 & 16128) >>> 8;
            case 1: return ((buf15 & 1) << 5) + ((buf16 & 63488) >>> 11);
            case 2: return ((buf18 & 15) << 2) + ((buf19 & 49152) >>> 14);
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }
    }

    public static void writeOppflagsAssigned(int idx, int value) throws GameActionException {Debug.betterAssert(value >= 0 && value < 64, "write value out of range");
        switch (idx) {
            case 0:
                buf13 = (buf13 & 49407) | (value << 8);
                dirty13 = 1;
                break;
            case 1:
                buf15 = (buf15 & 65534) | ((value & 32) >>> 5);
                dirty15 = 1;
                buf16 = (buf16 & 2047) | ((value & 31) << 11);
                dirty16 = 1;
                break;
            case 2:
                buf18 = (buf18 & 65520) | ((value & 60) >>> 2);
                dirty18 = 1;
                buf19 = (buf19 & 16383) | ((value & 3) << 14);
                dirty19 = 1;
                break;
            default:
                Debug.failFast("Comm read param not in range"); 
        }
    }

    // BUFFER POOL READ AND WRITE METHODS

}