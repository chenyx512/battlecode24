import sys
import os


def main():
    args = sys.argv

    if len(args) != 3:
        print("Usage: ./{} <source directory> <destination directory>")
        print("Usage: ./{} initialBot nextBot")
        return

    source = args[1]
    destination = args[2]

    print("Copying {} to {}".format(source, destination))

    # go to src directory
    # subprocess.run(["cd", "src"], shell=True)

    # copy folder
    os.system("cd src && cp -a {}/. {}/".format(source, destination))
    # subprocess.run(["cp", "-a", source + "/.", destination + "/"], shell=True)

    # go to destination directory
    # subprocess.run(["cd", destination], shell=True)

    # replace all instances of source
    os.system("cd src/{} && sed -i -e 's/{}/{}/g' $(find . -type f)".format(destination, source, destination))
    # subprocess.run(["sed", "-i", "'s/" + source + "/" + destination + "/g'", "*"], shell=True)

if __name__ == "__main__":
    main()