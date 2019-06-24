#!/bin/bash
# A simple integer random number generator in the interval [1; 100]

if (( $# == 0 )); then
   echo "Usage: random-gen.sh <N>"
   exit 1
fi

N=$1

for (( i=1; i <= $N; i++)); do
   echo $[ ( $RANDOM % 100 )  + 1 ]
done

exit 0

