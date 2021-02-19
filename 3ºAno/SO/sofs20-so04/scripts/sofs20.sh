#!/bin/bash
# -----------------------------------------------------------------------------------

sofs20_setup(){
    # create some useful env variables
    cd "$(dirname ${BASH_SOURCE[0]})"
    export SOFS20_SCRIPTS="$(pwd)"
    cd ..
    export SOFS20_ROOT="$(pwd)"
    export SOFS20_BIN="$(pwd)/bin"
    
    export SOFS20_DISK="/tmp/dsk"

    # source bash tools
    source "$SOFS20_SCRIPTS/msg.sh"
    source "$SOFS20_SCRIPTS/basic.sh"
    source "$SOFS20_SCRIPTS/tt-tools.sh"

    # source custom functions
    source "$SOFS20_SCRIPTS/custom.sh"
}

# -----------------------------------------------------------------------------------

# bash command to resource bash tools in case they were edited
rs()
{
    source $SOFS20_SCRIPTS/sofs20.sh
}

sofs20_setup

# -----------------------------------------------------------------------------------

