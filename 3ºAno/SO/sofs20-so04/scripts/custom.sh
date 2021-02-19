#!/bin/bash

# aloca N blocos de dados, N como argumento, recurrendo a tt-adb()
function ccadb() {
    helpmsg="ccadb < Number of Blocks > [ OPTIONS ]\n"
        helpmsg+="  Alloc data block on disk $SOFS20_DISK\n"
        helpmsg+="OPTIONS:\n"
        helpmsg+="  -b           --- use binary version\n"
        helpmsg+="  -g           --- use group version\n"
        helpmsg+="  -p           --- probe this function\n"
        helpmsg+="  -v           --- probe all functions\n"
        helpmsg+="  -h           --- this help"

        n=$1
        shift 1

        local ttoptions="-b" verbose=0
        while [[ $# -gt 0 ]]
        do
            case $1 in 
                "-b"|"-g"|"-p"|"-v") # testtool options are the same
                    ttoptions+=" $1"
                    shift 1
                    ;;
                "-h") # help message
                    InfoMessage "$helpmsg"
                    return
                    ;;
                *) # some thing wrong
                    ErrorMessage "Wrong arguments: \"$@\""
                    InfoMessage "$helpmsg"
                    break
                    ;;
            esac
        done

        while [[ $n -gt 0 ]] 
        do 
            ttadb $ttoptions
            n=$((n-1))
        done        
}

# frees N blocos de dados, N como argumento, recurrendo a tt-fdb()
function ccfdb() {
    helpmsg="ccfdb < Number of Blocks > [ OPTIONS ]\n"
        helpmsg+="  Alloc data block on disk $SOFS20_DISK\n"
        helpmsg+="OPTIONS:\n"
        helpmsg+="  -b           --- use binary version\n"
        helpmsg+="  -g           --- use group version\n"
        helpmsg+="  -p           --- probe this function\n"
        helpmsg+="  -v           --- probe all functions\n"
        helpmsg+="  -h           --- this help"

        n=$1
        shift 1

        local ttoptions="-b" verbose=0
        while [[ $# -gt 0 ]]
        do
            case $1 in 
                "-b"|"-g"|"-p"|"-v") # testtool options are the same
                    ttoptions+=" $1"
                    shift 1
                    ;;
                "-h") # help message
                    InfoMessage "$helpmsg"
                    return
                    ;;
                *) # some thing wrong
                    ErrorMessage "Wrong arguments: \"$@\""
                    InfoMessage "$helpmsg"
                    break
                    ;;
            esac
        done

        while [[ $n -gt 0 ]] 
        do 
            ttfdb $n
            n=$((n-1))
        done        
}

# empties one full block of the reference table to the retrival cache
# can output superblock status after each transfer to file
function ccerb() {
    helpmsg="ttadb < Number of Blocks > [ OPTIONS ]\n"
        helpmsg+="  Alloc data block on disk $SOFS20_DISK\n"
        helpmsg+="OPTIONS:\n"
        helpmsg+="  -b           --- use binary version\n"
        helpmsg+="  -g           --- use group version\n"
        helpmsg+="  -o           --- after each empy and refill of ret cache, outputs superblock status to file\n"
        helpmsg+="  -h           --- this help"

    local ttoptions="-b"
    local out=0 out_name=""
    while [[ $# -gt 0 ]]
    do
        case $1 in 
            "-b") # testtool options are the same
                out_name="sb_bin"
                ttoptions+=" $1"
                shift 1
                ;;
            "-g") # testtool options are the same
                out_name="sb_grp"
                ttoptions+=" $1"
                shift 1
                ;;
            "-o")
                out=1
                shift 1
                ;;
            "-h") # help message
                InfoMessage "$helpmsg"
                return
                ;;
            *) # some thing wrong
                ErrorMessage "Wrong arguments: \"$@\""
                InfoMessage "$helpmsg"
                break
                ;;
        esac
    done

    #reset output file
    s > $out_name

    for i in {1..4}
    do
        ccadb 68
        ttrrc $ttoptions
        if [ $out -eq 1 ]
        then
            -------------------------- >> $out_name
            s >> $out_name
        fi
    done

}

# show inode information
function ccsi(){
    local inode=$1
    shift 1

    let "head = ($inode + 1) * 6"
    let "tail = 6"
    
    echo "----------------"
    s -i $inode | head -n $head | tail -n $tail
}

# 'concat' de c() e f(-b), default: default c(): 1000 blocos 
function ccc(){
    c $1
    f -b
}