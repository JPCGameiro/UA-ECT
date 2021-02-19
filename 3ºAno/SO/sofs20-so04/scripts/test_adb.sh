#!/bin/bash

cd $SOFS20_SCRIPTS
source tt-tools.sh
source sofs20.sh

if [ -z "$1" ]  #No arguments
then
    echo "Usage: test_adb.sh <<num of datablocks>>"
else    #One argument were given
    for i in $(seq 1 $1)
    do
        ttadb -g #Allocate datablock
    done
fi