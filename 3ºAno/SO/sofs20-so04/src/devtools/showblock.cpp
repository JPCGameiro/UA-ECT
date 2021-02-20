/**
 *  \defgroup showblock showblock
 *  \ingroup tools
 *  \brief The \b sofs20 show block program.
 * 
 *  \details 
 *      It allows to display the contents of a range of blocks as a given type.<br/>
 *      Possible types are: hexadecimal, ASCII, superblock, inodes, direntries, and references.
 *
 *  \author Artur Pereira - 2006-2009, 2016-2020
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
#include <errno.h>

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"

/*
 * print help message
 */
static void printUsage(char *cmd_name)
{
    printf("Sinopsis: %s [ OPTION ] supp-file\n"
           "  OPTIONS:\n"
           "  -x range   --- show block(s) as hexadecimal byte data\n"
           "  -a range   --- show block(s) as ascii byte data\n"
           "  -s range   --- show block(s) as superblock data\n"
           "  -i range   --- show block(s) as inode entries\n"
           "  -d range   --- show block(s) as directory entries\n"
           "  -r range   --- show block(s) as references\n"
           "  -D         --- debug mode\n"
           "  -h         --- print this help\n", cmd_name);
}

/* print error message */
static void printError(int errcode, char *cmd_name)
{
    fprintf(stderr, "%s: error #%d - %s.\n", cmd_name, errcode, strerror(errcode));
}

/* The main function */

using namespace sofs20;

int main(int argc, char *argv[])
{
    /* process command line options */
    int opt;
    int sopt = '_';
    const char *range = "0";

    while ((opt = getopt(argc, argv, "x:a:s:i:d:r:Dh")) != -1)
    {
        switch (opt)
        {
            case 'x':        /* show block contents as hexadecimal data */
            case 'a':        /* show block contents as ascii data */
            case 's':        /* show block contents as superblock data */
            case 'i':        /* show block contents as inode entries */
            case 'd':        /* show block contents as directory entries */
            case 'r':        /* show block contents as cluster references */
            {
                if (sopt != '_')
                {
                    fprintf(stderr, "%s: Too many options.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                range = optarg;
                sopt = opt;
                break;
            }
            case 'D':
            {
                soProbeOpen(stdout, 0, 1000);
                break;
            }
            case 'h':
            {
                printUsage(basename(argv[0]));
                return EXIT_SUCCESS;
            }
            default:
            {
                fprintf(stderr, "%s: It should not have happened.\n", basename(argv[0]));
                printUsage(basename(argv[0]));
                return EXIT_FAILURE;
            }
        }
    }

    /* check existence of mandatory argument: storage device name */
    if ((argc - optind) != 1)
    {
        fprintf(stderr, "%s: Wrong number of mandatory arguments.\n", basename(argv[0]));
        printUsage(basename(argv[0]));
        return EXIT_FAILURE;
    }

    /* if no selected option is given, assume '-s 0' */
    if (sopt == '_')
        sopt = 's';

    /* open a direct communication channel with the storage device */
    uint32_t nb;
    try
    {
        soOpenRawDisk(argv[optind], &nb);
    }
    catch (int err)
    {
        printError(err, basename(argv[0]));
        return EXIT_FAILURE;
    }

    /* check range */
    uint32_t i1 = 0, i2 = 0;
    unsigned int n1 = 0, n2 = 0;
    sscanf(range, "%u%n-%u%n", &i1, &n1, &i2, &n2);
    if (n1 == strlen(range))
    {
        i2 = i1;
    }
    else if (n2 != strlen(range) || i2 < i1 || i2 >= nb)
    {
        fprintf(stderr, "%s: Wrong range format.\n", basename(argv[0]));
        printUsage(basename(argv[0]));
        return EXIT_FAILURE;
    }

    /* process request */
    char buf[BlockSize];
    uint32_t off = 0;
    bool first_time = true;
    for (uint32_t i = i1; i <= i2; i++, first_time = false)
    {
        try
        {
            soReadRawBlock(i, buf);
        }
        catch (int err)
        {
            printError(err, basename(argv[0]));
            return EXIT_FAILURE;
        }
        switch (sopt)
        {
            case 'x':
                if (not first_time) printf("---\n");
                printBlockAsHex(buf, off);
                off += BlockSize;
                break;
            case 'a':
                if (not first_time) printf("---\n");
                printBlockAsAscii(buf, off);
                off += BlockSize;
                break;
            case 'i':
                printBlockOfInodes(buf, off);
                off += IPB;
                break;
            case 'd':
                if (not first_time) printf("---\n");
                printBlockOfDirents(buf, off);
                off += DPB;
                break;
            case 'r':
                if (not first_time) printf("---\n");
                printBlockOfRefs(buf, off);
                off += RPB;
                break;
            case 's':
                printSuperblock(buf);
                break;
        }
    }

    /* close the communication channel with the storage device */
    try
    {
        soCloseRawDisk();
    }
    catch (int err)
    {
        printError(err, basename(argv[0]));
        return EXIT_FAILURE;
    }

    /* that's all */
    return EXIT_SUCCESS;

}                               /* end of main */
