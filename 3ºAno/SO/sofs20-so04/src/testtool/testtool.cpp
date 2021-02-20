/*
 *  \brief A test tool
 *  
 *  It provides a simple method to test separately the file system internal operations.
 *
 *  \author Artur Pereira - 2005-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <libgen.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <time.h>
#include <errno.h>
#include <stdarg.h>

#include "testtool.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"

using namespace sofs20;

/* quiet level 
 * 0 - print all messages
 * 1 - print only result messages
 * 2 - print nothing 
 */
int quiet = 0;

/* ******************************************** */
/* print help message */
static void printUsage(char *cmd_name)
{
    if (quiet > 0)
        return;

    printf("Sinopsis: %s [OPTIONS] supp-file\n"
           "  OPTIONS:\n"
           "  -q level    --- set quiet mode (default: 0)\n"
           "  -p num-num  --- set probe ID range (default: 0-999)\n"
           "  -A num-num  --- add range of IDs to probe configuration\n"
           "  -R num-num  --- remove range of IDs from probe configuration\n"
           "  -b          --- set bin configuration to 200-799\n"
           "  -g          --- set bin configuration to 0-0 (default)\n"
           "  -a num-num  --- add range of IDs to bin configuration\n"
           "  -r num-num  --- remove range of IDs from bin configuration\n"
           "  -h          --- print this help\n", cmd_name);
}

/* ******************************************** */
#include <map>
#include <string>
#include <iostream>
#include <stdexcept>
/* ******************************************** */
/* handling user choise */
typedef void (*handler) (void);
class Handler
{
public:
    std::map<std::string, handler> hdl;

    Handler()
    {
        /* core functions */
        hdl["sb"] = showBlock;
        hdl["s"] = showBlock;
        hdl["fd"] = formatDisk;
        hdl["f"] = formatDisk;
        hdl["cd"] = createDisk;
        hdl["c"] = createDisk;
        hdl["spi"] = setProbeIDs;
        hdl["api"] = addProbeIDs;
        hdl["rpi"] = removeProbeIDs;
        hdl["ppi"] = printProbeIDs;
        hdl["sbi"] = setBinIDs;
        hdl["abi"] = addBinIDs;
        hdl["rbi"] = removeBinIDs;
        hdl["pbi"] = printBinIDs;
        /* daal functions */
        /* freeinodes functions */
        hdl["ai"] = allocInode;
        hdl["fi"] = freeInode;
        /* freedatablocks functions */
        hdl["adb"] = allocDataBlock;
        hdl["fdb"] = freeDataBlock;
        hdl["rrc"] = replenishRetrievalCache;
        hdl["dic"] = depleteInsertionCache;
        /* inodeattr functions */
        hdl["cia"] = checkInodeAccess;
        hdl["sia"] = setInodeAccess;
        hdl["sis"] = setInodeSize;
        hdl["iil"] = incInodeLnkcnt;
        hdl["dil"] = decInodeLnkcnt;
        hdl["iilc"] = incInodeLnkcnt;
        hdl["dilc"] = decInodeLnkcnt;
        hdl["cog"] = changeInodeOwnership;
        hdl["cio"] = changeInodeOwnership;
        /* fileblocks functions */
        hdl["afb"] = allocFileBlock;
        hdl["ffb"] = freeFileBlocks;
        hdl["gfb"] = getFileBlock;
        hdl["rfb"] = readFileBlock;
        hdl["wfb"] = writeFileBlock;
        /* direntries functions */
        hdl["ade"] = addDirentry;
        hdl["dde"] = deleteDirentry;
        hdl["rde"] = renameDirentry;
        hdl["gde"] = getDirentry;
        hdl["tp"] = traversePath;
        hdl["cde"] = checkDirectoryEmptiness;
    }

    void exec(std::string & key)
    {
        try
        {
            hdl.at(key)();
        }
        catch (const std::out_of_range & err)
        {
            errorMsg("Invalid choice");
            if (quiet > 0)
                exit(EXIT_FAILURE);
        }
    }
};

class Menu
{
public:
    int quiet_mode;

    Menu() : quiet_mode(0)
    {
    }

    void printMenu(void)
    {
        if (quiet > 0)
            return;

        printf
            ("+===============================================================================+\n"
             "|                               testing  functions                              |\n"
             "+===============================================================================+\n"
             "|   q       - exit                      |  cd       - Create Disk               |\n"
             "|  fd       - Format disk               |  sb       - Show Block                |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "| spi       - Set Probe Ids             | ppi       - Print Probe Ids           |\n"
             "| api       - Add Probe Ids             | rpi       - Remove Probe Ids          |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "| sbi       - Set Bin Ids               | pbi       - Print Bin Ids             |\n"
             "| abi       - Add Bin Ids               | rbi       - Remove Bin Ids            |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "| cia [555] - Check Inode Access        | sia       - Set Inode Access          |\n"
             "| iil       - Increment Inode Lnkcnt    | dil       - Decrement Inode Lnkcnt    |\n"
             "| cio       - Change Inode Ownership    | sis       - Set Inode Size            |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "|  ai [401] - Alloc Inode               |  fi [402] - Free Inode                |\n"
             "| adb [441] - Alloc Data Block          | fdb [442] - Free Data Block           |\n"
             "| rrc [443] - Replenish Retrieval cache | dic [444] - Deplete Insertion cache   |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "| gfb [301] - Get File Block            |                                       |\n"
             "| afb [302] - Alloc File Block          | ffb [303] - Free File Blocks          |\n"
             "| rfb [331] - Read File Block           | wfb [332] - Write File Block          |\n"
             "+---------------------------------------+---------------------------------------+\n"
             "| gde [201] - Get Dir Entry             | ade [202] - Add Dir Entry             |\n"
             "| dde [203] - Delete Dir Entry          | rde [204] - Rename Dir Entry          |\n"
             "| cde [205] - Check Directory Emptiness |  tp [221] - Traverse Path             |\n"
             "+===============================================================================+\n");
    }

    std::string & readChoice()
    {
        promptMsg("\nYour command: ");
        static std::string line;
        getline(std::cin, line);
        return line;
    }
};

/* ******************************************** */
/* The main function */
int main(int argc, char *argv[])
{
    int exit_result = EXIT_SUCCESS; // last command result
    char *progName = basename(argv[0]); // must be called before dirname!
    progDir = dirname(argv[0]);

    /* ... */
    Menu menu;
    Handler handler;

    /* open probing system, all */
    soProbeOpen(stdout, 0, 999);

    /* process command line options */
    int opt;
    while ((opt = getopt(argc, argv, "p:A:R:q:bga:r:h")) != -1)
    {
        switch (opt)
        {
            case 'p':          /* set ID range to probing system */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'p' option.\n", progName);
                    printUsage(progName);
                    return EXIT_FAILURE;
                }
                soProbeSetIDs(lower, upper);
                break;
            }
            case 'A':          /* add IDs to probe conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'A' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soProbeAddIDs(lower, upper);
                break;
            }
            case 'R':          /* remove IDs from probe conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d-%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'R' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soProbeRemoveIDs(lower, upper);
                break;
            }
            case 'q':          /* quiet mode */
            {
                quiet = atoi(optarg);
                if (quiet < 0)
                    quiet = 0;
                else if (quiet > 2)
                    quiet = 2;
                break;
            }
            case 'b':          /* set binary mode: all functios binary */
            {
                soBinSetIDs(200, 799);;
                break;
            }
            case 'g':          /* set work mode: nobinary functios */
            {
                soBinSetIDs(0, 0);;
                break;
            }
            case 'a':          /* add IDs to bynary conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'a' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soBinAddIDs(lower, upper);
                break;
            }
            case 'r':          /* remove IDs from bynary conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d-%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'r' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soBinRemoveIDs(lower, upper);
                break;
            }
            case 'h':          /* help mode */
            {
                printUsage(progName);
                return EXIT_SUCCESS;
            }
            default:
            {
                errorMsg("%s: Wrong option.\n", progName);
                printUsage(progName);
                return EXIT_FAILURE;
            }
        }
    }

    /* check existence of mandatory argument: storage device name */
    if ((argc - optind) != 1)
    {
        fprintf(stderr, "%s: Wrong number of mandatory arguments.\n", progName);
        printUsage(progName);
        return EXIT_FAILURE;
    }
    devname = argv[optind];

    /* open disk */
    try
    {
        soOpenDisk(devname);
    }
    catch (SOException & err)
    {
        errnoMsg(err.en, err.msg);
        return EXIT_FAILURE;
    }

    /* process the command */
    while (true)
    {
        menu.printMenu();
        std::string & opt = menu.readChoice();
        if (opt == "q" || opt == "0")
            break;

        try
        {
            handler.exec(opt);
            exit_result = EXIT_SUCCESS;
        }
        catch (SOException & err)
        {
            errnoMsg(err.en, err.what());
            exit_result = EXIT_FAILURE;
        }
    }

    /* close the unbuffered communication channel with the storage device */
    try
    {
        soCloseDisk();
    }
    catch (SOException & err)
    {
        errnoMsg(err.en, err.msg);
        exit_result = EXIT_FAILURE;
    }

    /* that's all */
    promptMsg("Bye!\n");
    return exit_result;
}                               /* end of main */
