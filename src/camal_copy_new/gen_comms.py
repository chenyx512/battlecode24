# Modified from https://github.com/maxwelljones14/BattleCode2023/blob/main/gen_comms.py
# Modified from smite's Battlecode2022 bot located here
# https://github.com/mvpatel2000/Battlecode2022/blob/main/scripts/generate_comms_handler.py

#! /usr/bin/env python3

from pathlib import Path
import sys

SCHEMA = {
    'sync': {
        'slots': 1,
        'bits': {
            'id': 13,
        }
    },
    'HQ': {
        'slots': 3,
        'bits': {
            'loc': 12,
            'dis2next': 8,
            'congestRound': 5,
        }
    },
    'symmetry': {
        'slots': 1,
        'bits': {
            'sym': 3,
        }
    },
    'myflags': {
        'slots': 3,
        'bits': {
            'id': 12,
            'exists': 1,
            'loc': 12,
            'original_loc': 12,
            'assigned': 6,
            'distress': 1,
        }
    },
    'oppflags': {
        'slots': 3,
        'bits': {
            'id': 12,
            'exists': 1,
            'loc': 12,
            'original_loc': 12,
            'escort_loc': 12,
            'confirmed': 1, # whether the flag's broadcast location is correctly set
            'carried': 1,
            'assigned': 6,
        }
    },
    'myTeam': {
        'slots': 1,
        'bits': {
            'cnt': 6,
        }
    },
    'oppTeam': {
        'slots': 1,
        'bits': {
            'cnt': 6,
        }
    },
    'enemy': {
        'slots': 3,
        'bits': {
            'loc': 12,
        }
    },
}

def gen_constants():
    out = """"""
    for datatype in SCHEMA:
        out += f"""
    public final static int {datatype.upper()}_SLOTS = {SCHEMA[datatype]['slots']};"""
    return out+"\n"

def gen():
    out = """"""""
    bits_so_far = 0
    for datatype in SCHEMA:
        datatype_bits = sum(SCHEMA[datatype]['bits'].values())
        prefix_bits = 0

        for attribute in SCHEMA[datatype]['bits']:
            if attribute == 'all': # unused since it may be too big
                attribute_bits = datatype_bits
                prefix_bits = 0
            else:
                attribute_bits = SCHEMA[datatype]['bits'][attribute]

            # read function
            rets = []
            for idx in range(SCHEMA[datatype]['slots']):
                start_bit = bits_so_far + datatype_bits*idx + prefix_bits
                # we want to read attribute_bits starting from start_bit
                start_int = start_bit // 16
                rem = start_bit % 16
                end_int = (start_bit + attribute_bits - 1) // 16
                ret = ""
                if start_int == end_int:
                    bitstring = '1' * attribute_bits + '0' * (16 - attribute_bits - rem)
                    ret = f"(buf{start_int} & {int(bitstring, 2)}) >>> {(16 - attribute_bits - rem)}"
                else:
                    part1_bitstring = '1' * (16 - rem)
                    part2_bitstring = '1' * (attribute_bits + rem - 16) + '0' * (32 - attribute_bits - rem)
                    ret = f"((buf{start_int} & {int(part1_bitstring, 2)}) << {(attribute_bits + rem - 16)}) + ((buf{end_int} & {int(part2_bitstring, 2)}) >>> {(32 - attribute_bits - rem)})"
                rets.append(ret)

            if SCHEMA[datatype]['slots'] == 1:
                out += f"""
    public static int read{capitalize(datatype)}{capitalize(attribute)}() throws GameActionException {{
        return {rets[0]};
    }}
"""
            else:
                out += f"""
    public static int read{capitalize(datatype)}{capitalize(attribute)}(int idx) throws GameActionException {{
        switch (idx) {{"""
                for idx, ret in enumerate(rets):
                    out += f"""
            case {idx}: return {ret};"""
                out += f"""
            default:
                Debug.failFast("Comm read param not in range"); return -1;
        }}
    }}
"""

            # write function
            writes = []
            for idx in range(SCHEMA[datatype]['slots']):
                start_bit = bits_so_far + datatype_bits*idx + prefix_bits
                # we want to write attribute_bits starting from start_bit
                start_int = start_bit // 16
                rem = start_bit % 16
                end_int = (start_bit + attribute_bits - 1) // 16
                write = []
                if start_int == end_int:
                    bitstring = '1' * rem + '0' * attribute_bits + '1' * (16 - attribute_bits - rem)
                    write.append(f"buf{start_int} = (buf{start_int} & {int(bitstring, 2)}) | (value << {(16 - attribute_bits - rem)})")
                    write.append(f"dirty{start_int} = 1")
                else:
                    part1_bitstring = '1' * rem + '0' * (16 - rem)
                    part2_bitstring = '0' * (attribute_bits + rem - 16) + '1' * (32 - attribute_bits - rem)
                    value1_bitstring = '1' * (16 - rem) + '0' * (attribute_bits + rem - 16)
                    value2_bitstring = '1' * (attribute_bits + rem - 16)
                    write.append(f"buf{start_int} = (buf{start_int} & {int(part1_bitstring, 2)}) | ((value & {int(value1_bitstring, 2)}) >>> {(attribute_bits + rem - 16)})")
                    write.append(f"dirty{start_int} = 1")
                    write.append(f"buf{end_int} = (buf{end_int} & {int(part2_bitstring, 2)}) | ((value & {int(value2_bitstring, 2)}) << {(32 - attribute_bits - rem)})")
                    write.append(f"dirty{end_int} = 1")
                writes.append(write)

            if SCHEMA[datatype]['slots'] == 1:
                out += f"""
    public static void write{capitalize(datatype)}{capitalize(attribute)}(int value) throws GameActionException {{"""
                out += f"""Debug.betterAssert(value >= 0 && value < {1 << attribute_bits}, "write value out of range");"""
                for w in writes[0]:
                    out += f"""
        {w};"""
                out += f"""
    }}
"""
            else:
                out += f"""
    public static void write{capitalize(datatype)}{capitalize(attribute)}(int idx, int value) throws GameActionException {{"""
                out += f"""Debug.betterAssert(value >= 0 && value < {1 << attribute_bits}, "write value out of range");"""
                out += """
        switch (idx) {"""
                for idx, write in enumerate(writes):
                    out += f"""
            case {idx}:"""
                    for w in write:
                        out += f"""
                {w};"""
                    out += f"""
                break;"""
                out += f"""
            default:
                Debug.failFast("Comm write param not in range"); 
        }}
    }}
"""

            prefix_bits += attribute_bits

        bits_so_far += datatype_bits * SCHEMA[datatype]['slots']
    # remove redundant shifts
    out = out.replace(" >>> 0", "")
    out = out.replace(" << 0", "")
    print("Total bit usage: " + str(bits_so_far))
    return out

def capitalize(s):
    return ''.join(x.capitalize() for x in s.split('_'))


if __name__ == '__main__':
    # Check num bits
    total_bits = sum(sum(SCHEMA[datatype]['bits'].values()) for datatype in SCHEMA)
    if total_bits > 1024:
        raise Exception("Too many bits")

    # package_name = len(sys.argv) > 1 and sys.argv[1] or 'camal_copy_new'
    package_name = 'camal_copy_new'
    template_file = Path('./CommsTemplate.java')
    out_file = Path('./Comms.java')

    with open(template_file, 'r') as t:
        with open(out_file, 'w') as f:
            f.write("""/* Comms.java is generated by gen_comms.py from CommsTemplate.java. \nDO NOT MODIFY THIS FILE DIRECTLY */\n""")
            for line in t:
                if 'package camal_copy_new;' in line:
                    f.write(f"package {package_name};\n")
                elif 'CommsTemplate' in line:
                    f.write(line.replace('CommsTemplate', 'Comms'))
                elif '// MAIN READ AND WRITE METHODS' in line:
                    f.write(gen())
                elif '// CONSTS' in line:
                    f.write(gen_constants())
                else:
                    f.write(line)