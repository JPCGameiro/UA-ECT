#!/bin/bash

cd $SOFS20_SCRIPTS
source tt-tools.sh
source sofs20.sh

#Adds arg1 direntries to inode number 0
#dir entries names will be 1, 2, 3, ....
#their child inodes will be 1, 2, 3, ...

if [ -z "$1" ]  #No arguments
then
    echo "Usage: test_ade.sh <<num of direntries>>"
else    #One argument were given
    c   #Create a new disk
    f   #Format the disk
    for i in $(seq 1 $1)
    do
        ttade 0 $i $i -b #Add the direntries to inode 0
    done
fi