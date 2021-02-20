# The **sofs20** file system supporting software

******
******

## Prerequisites

On Ubuntu you need the following packages installed: 
_build-essential_, _cmake_, _doxygen_, _libfuse-dev_, and _git_.

```
sudo apt install build-essential cmake doxygen libfuse-dev git
```

In other Linux distributions you need equivalent packages installed.

******

## Cloning the repo

In a directory of your choice, clone the project to your computer

```
cd «directory-of-your-choice»
git clone https://to-be-defined/XXXX
```

where **XXXXX** must be your project id.

******

## Compiling the code

In a terminal, enter the **build** directory of your project

```
cd XXXXX/sofs20/build
```

Then compile the code

```
cmake ../src
make
```

If you prefer ninja, instead of make

```
cmake -G Ninja ../src
ninja
```

******

## Generating documentation

The code is documented in **doxygen**. So, you can easily generate **html** documentation pages.

```
cd XXXXX/sofs20/doc
doxygen
firefox html/index.html &
```

Of course, you can change firefox by your favourite browser.

******

## Editable source files

When editing your code, take into attention the following:

- Folder **src/grp_src** is the only one containing source code to be edited by students.

- Only files with termination **.cpp** are to be edited.

- There is a single function per file, with the exception of proposals for internal auxiliary functions in some cases.

- Please do not change the signature of the functions, nor delete the call to soProbe.

- We assume that only files with termination **.cpp** inside **src/grp_src** subfolders are editable by the groups. Thus, any changes to the other files can produce indesirable behavior during our tests, since we will use our version of them.

******

## Testing the code

The following sequence of commands, where XXXXX is your project's id, allows you to mount a **sofs** file system

```
cd XXXXX/sofs20/bin
./createDisk /tmp/dsk 1000      # /tmp/dsk will be a disk with 1000 blocks
./mksofs /tmp/dsk               # format the disk as a sofs20 file system
mkdir /tmp/mnt                  # our mount point
./sofsmount /tmp/dsk /tmp/mnt   # mount the disk in the mount point
```
Now, everything created inside the mount point will be stored in disk (the /tmp/dsk file). You can use the **showblock** tool to check that out.

******

## Script files

We encourage the use o bash script functions to facilitate the test of your code.
In folder **scripts**, 4 files were added to propose an approach on how to create and use these functions:

- **sofs20.sh** is the entry point, creating some required environment variables and sourcing the other script files.

- **msg.sh** just declare 3 auxiliary functions.

- **basic.sh** declare functions to create a sofs20 disk (c), format it (f), call the showblock tool (s), call the testtool tool (tt), and build your code (m).

- **tt-tools** declare functions interfacing with the testool. These are incomplete.

To activate these functions, just execute command **source «path-to-sofs20.sh»**, where «path-to-sofs20.sh» is a absolute or relative path to file **sofs20.sh**.

