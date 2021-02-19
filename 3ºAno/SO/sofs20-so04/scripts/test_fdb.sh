#!/bin/bash

cd $SOFS20_SCRIPTS
source tt-tools.sh
source sofs20.sh

#if no arguments are given frees the data blocks 1-68
#if one argument is given frees the $1 data block 68 times
#if two arguments are given frees the $1 data block $2 times


if [ -z "$1" ]  #No arguments
then
    for i in $(seq 1 68)
    do
        ttfdb $i -g

    done
elif [ -z "$2" ] #One argument was given
then
    for i in $(seq 1 68)
    do
        ttfdb $1 -g

    done
else    #Two arguments were given
    for i in $(seq 1 $2)
    do
        ttfdb $1 -g

    done
fi
