# https://github.com/jmerle/battlecode-2023/blob/master/scripts/compare_full.py
import re
import signal
import subprocess
import sys
from argparse import ArgumentParser
from datetime import datetime
from multiprocessing import Pool, Value
from pathlib import Path
from typing import Any

NUM_CORES = 6
assert NUM_CORES % 2 == 0
NUM_CORES_PER_SIDE = NUM_CORES // 2

early_maps = 'DefaultSmall DefaultMedium DefaultLarge DefaultHuge'.split()
sprint1_maps = 'AceOfSpades Alien Ambush Battlecode24 BigDucksBigPond Canals CH3353C4K3F4CT0RY Duck Fountain Hockey MazeRunner Rivers Snake Soccer SteamboatMickey Yinyang'.split()
sprint2_maps = ['BedWars',
                'Bunkers',
                'Checkered',
                'Diagonal',
                'Divergent',
                'EndAround',
                'FloodGates',
                'Foxes',
                'Fusbol',
                'GaltonBoard',
                'HeMustBeFreed',
                'Intercontinental',
                'Klein',
                'QueenOfHearts',
                'QuestionableChess',
                'Racetrack',
                'Rainbow',
                'TreeSearch',]
maps = early_maps + sprint1_maps + sprint2_maps

def partition_list(maps, n):
    if n <= 0:
        raise ValueError("Number of partitions must be greater than 0")

    # The length of each partition
    partition_length, remainder = divmod(len(maps), n)

    # Create the partitions
    partitions = []
    start = 0
    for i in range(n):
        # Add an extra element to some of the partitions to distribute the remainder
        end = start + partition_length + (1 if i < remainder else 0)
        partitions.append(maps[start:end])
        start = end

    return partitions

def run_matches(player1: str, player2: str, mymaps: list[str], timestamp: str) -> dict[str, Any]:
    result = {
        "player1": player1,
        "player2": player2
    }

    winners_by_map = {}
    current_map = None
    current_result = None

    args = [
        str(Path(__file__).parent / "gradlew"),
        "run",
        f"-PteamA={player1}",
        f"-PteamB={player2}",
        f"-Pmaps={','.join(mymaps)}",
        "-Pverbose=false",
        # f"-PreplayPath=replays/run-{timestamp}-%TEAM_A%-vs-%TEAM_B%.bc24"
    ]

    proc = subprocess.Popen(args, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)

    lines = []
    while True:
        line = proc.stdout.readline()
        if not line:
            break

        line = line.decode("utf-8").rstrip()
        lines.append(line)

        map_match = re.search(r"[^ ]+ vs\. [^ ]+ on ([^ ]+)", line)
        if map_match is not None:
            current_map = map_match[1]
        result_match = re.search(r"([^ ]+) \([AB]\) wins \(round (\d+)\)", line)
        if result_match is not None:
            current_result = result_match
        reason = re.search(r"Reason: (.*)", line)
        if reason is not None:
            reason = reason[1]
            if 'resigned' in reason:
                reason = 'resign!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!'
            global counter
            with counter.get_lock():
                counter.value += 1
                current_match = counter.value

            total_matches = len(maps) * 2
            prefix = f"[{str(current_match).rjust(len(str(total_matches)))}/{total_matches}]"

            winner_color = "red" if current_result[1] == player1 else "blue"

            print(f"{prefix} {current_result[1]} wins in {current_result[2]} rounds as {winner_color} on {current_map}: {reason}")
            winners_by_map[current_map] = current_result[1]

    if proc.wait() != 0:
        result["type"] = "error"
        result["message"] = "\n".join(lines)
        return result

    result["type"] = "success"
    result["winners"] = winners_by_map
    return result

def main() -> None:
    parser = ArgumentParser(description="Compare the performance of two players.")
    parser.add_argument("player1", help="name of the first player")
    parser.add_argument("player2", help="name of the second player")

    args = parser.parse_args()

    signal.signal(signal.SIGINT, lambda a, b: sys.exit(1))

    timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")

    global counter
    counter = Value("i", 0)

    print(f"Running {len(maps) * 2} matches")

    maps_partition = partition_list(maps, NUM_CORES_PER_SIDE)
    pool_args = [(args.player1, args.player2, m, timestamp) for m in maps_partition] + [(args.player2, args.player1, m, timestamp) for m in maps_partition]
    with Pool(NUM_CORES) as pool:
        results = pool.starmap(run_matches, pool_args)

    if any(r["type"] == "error" for r in results):
        for r in results:
            if r["type"] == "error":
                print(f"{r['player1']} versus {r['player2']} failed with the following error:")
                print(r["message"])
        sys.exit(1)

    map_winners = {}

    player1_wins = 0
    player2_wins = 0

    for r in results:
        for map, winner in r["winners"].items():
            if map in map_winners and map_winners[map] != winner:
                map_winners[map] = "Tied"
            else:
                map_winners[map] = winner

            if winner == args.player1:
                player1_wins += 1
            else:
                player2_wins += 1

    tied_maps = [k for k, v in map_winners.items() if v == "Tied"]
    player1_superior_maps = [k for k, v in map_winners.items() if v == args.player1]
    player2_superior_maps = [k for k, v in map_winners.items() if v == args.player2]

    if len(tied_maps) > 0:
        print(f"Tied maps ({len(tied_maps)}):")
        for map in tied_maps:
            print(f"- {map}")
    else:
        print(f"There are no tied maps")

    if len(player1_superior_maps) > 0:
        print(f"Maps {args.player1} wins on as both red and blue ({len(player1_superior_maps)}):")
        for map in player1_superior_maps:
            print(f"- {map}")
    else:
        print(f"There are no maps {args.player1} wins on as both red and blue")

    if len(player2_superior_maps) > 0:
        print(f"Maps {args.player2} wins on as both red and blue ({len(player2_superior_maps)}):")
        for map in player2_superior_maps:
            print(f"- {map}")
    else:
        print(f"There are no maps {args.player2} wins on as both red and blue")

    print(f"{args.player1} wins: {player1_wins} ({player1_wins / (player1_wins + player2_wins) * 100:,.2f}% win rate)")
    print(f"{args.player2} wins: {player2_wins} ({player2_wins / (player1_wins + player2_wins) * 100:,.2f}% win rate)")

if __name__ == "__main__":
    main()
