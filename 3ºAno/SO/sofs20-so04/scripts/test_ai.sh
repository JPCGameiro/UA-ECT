#!/bin/bash

cd $SOFS20_SCRIPTS
source tt-tools.sh
source sofs20.sh

#Allocs $1 inodes
#If arg2 == 1(file), 2(dir), 3(symlink)

if [ -z "$1" ] | [ -z "$2"] #No arguments
then
    echo "Usage: test_ai.sh <<inode type: 1(file), 2(dir), 3(symlink)>>"
else
    for i in $(seq 1 $1)
    do
        ttai $2 -g #Alloc $1 inodes with $2 type
    done
fi