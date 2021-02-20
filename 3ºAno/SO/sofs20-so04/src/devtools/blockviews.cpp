
/*
 *  \author Artur Pereira - 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2012
 */

#define  __STDC_FORMAT_MACROS
#include <inttypes.h>
#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <sys/stat.h>
#include <string.h>
#include <stdlib.h>

#include <iostream>

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    /* ********************************************************* */

    void printBlockAsHex(void *buf, uint32_t off)
    {
        /* cast buf to appropriate type */
        unsigned char *byte = (unsigned char *)buf;

        /* print cluster */
        for (uint32_t i = 0; i < BlockSize; i++)
        {
            if ((i & 0x1f) == 0)
                printf("%4.4x:", i + off);
            /* print byte */
            printf(" %.2x", byte[i]);
            /* terminate present line, if required */
            if ((i & 0x1f) == 0x1f)
                printf("\n");
        }
    }

    /* ********************************************************* */

    void printBlockAsAscii(void *buf, uint32_t off)
    {
        /* cast buf to appropriate type */
        unsigned char *c = (unsigned char *)buf;

        /* print cluster */
        char line[256];         /* line to be printed */
        char *p_line = line;    /* pointer to a character in the line */
        for (uint32_t i = 0; i < BlockSize; i++)
        {
            if ((i & 0x1f) == 0)
            {
                printf("%4.4d:", i + off);
                p_line = line;
            }
            /* add character to the line */
            switch (c[i])
            {
                case '\a':
                    p_line += sprintf(p_line, " \\a");
                    break;
                case '\b':
                    p_line += sprintf(p_line, " \\b");
                    break;
                case '\f':
                    p_line += sprintf(p_line, " \\f");
                    break;
                case '\n':
                    p_line += sprintf(p_line, " \\n");
                    break;
                case '\r':
                    p_line += sprintf(p_line, " \\r");
                    break;
                case '\t':
                    p_line += sprintf(p_line, " \\t");
                    break;
                case '\v':
                    p_line += sprintf(p_line, " \\v");
                    break;
                default:
                    if ((c[i] >= ' ') && (c[i] != 0x7F) && (c[i] != 0x8F))
                        p_line += sprintf(p_line, " %c ", c[i]);
                    else
                        p_line += sprintf(p_line, " %.2x", c[i]);
            }
            /* terminate and print present line, if required */
            if ((i & 0x1f) == 0x1f)
            {
                *p_line = '\0';
                printf("%s\n", line);
            }
        }
    }

    /* ********************************************************* */

    void printSuperblock(void *buf)
    {
        /* cast buf to appropriate type */
        SOSuperblock *sbp = (SOSuperblock *) buf;

        /* header */
        printf("Header:\n");
        printf("  Magic number: 0x%04x\n", sbp->magic);
        printf("  Version number: 0x%02x\n", sbp->version);
        printf("  Volume name: \"%-s\"\n", sbp->name);
        printf("  Properly unmounted: %s\n", (sbp->mntstat >= 0) ? "yes" : "no");
        printf("  Number of mounts: %u\n", abs(sbp->mntstat));
        printf("  Total number of blocks in the device: %u\n", sbp->ntotal);
        //printf("\n");

        /* ----------------------------------------------------- */

        /* inodes' metadata */
        printf("Inodes' metadata:\n");
        printf("  Total number of inodes: %u\n", sbp->itotal);
        printf("  Number of free inodes: %u\n", sbp->ifree);
        printf("  Last allocated inode: %u\n", sbp->iidx);
        printf("  Inode allocation bitmap:\n");
        for (uint32_t i = 0; i < MAX_INODES/32; i++)
        {
            if ((i % 10) == 0)
                printf("    0x%08x", sbp->ibitmap[i]);
            else if ((i % 10) == 9)
                printf(" 0x%08x\n", sbp->ibitmap[i]);
            else
                printf(" 0x%08x", sbp->ibitmap[i]);
        }

        /* ----------------------------------------------------- */

        /* blocks' metadata */
        printf("Data blocks' metadata:\n");
        printf("  First block of the data block pool: %u\n", sbp->dbp_start);
        printf("  Total number of data blocks: %u\n", sbp->dbtotal);
        printf("  Number of free data blocks: %u\n", sbp->dbfree);

        printf("Reference table's metadata:\n");
        printf("  First block of the reference table: %u\n", sbp->rt_start);
        printf("  Number of blocks of the reference table: %u\n", sbp->rt_size);
        printf("  Index of first block with references: %u\n", sbp->reftable.blk_idx);
        printf("  Index of first cell, within first block, with references: %u\n", sbp->reftable.ref_idx);
        printf("  Number of references in reference table: %u\n", sbp->reftable.count);

        /* ----------------------------------------------------- */

        /* Retrieval cache' contents */
        printf("Retrieval cache:\n");
        printf("  Index of the first occupied cache position: ");
        if (sbp->retrieval_cache.idx == REF_CACHE_SIZE)
            printf("%u\n", sbp->retrieval_cache.idx);
        else
            printf("%u\n", sbp->retrieval_cache.idx);
        printf("  Cache contents:");
        for (uint32_t i = 0; i < REF_CACHE_SIZE; i++)
        {
            if ((i % 10) == 0)
                printf("\n    ");
            if (sbp->retrieval_cache.ref[i] == BlockNullReference)
                printf(" (nil)");
            else
                printf(" %5u", sbp->retrieval_cache.ref[i]);
        }
        printf("\n");

        printf("Insertion cache:\n");
        printf("  Index of the first empty cache position: ");
        if (sbp->insertion_cache.idx == REF_CACHE_SIZE)
            printf("%u\n", sbp->insertion_cache.idx);
        else
            printf("%u\n", sbp->insertion_cache.idx);
        printf("  Cache contents:");
        for (uint32_t i = 0; i < REF_CACHE_SIZE; i++)
        {
            if ((i % 10) == 0)
                printf("\n    ");
            if (sbp->insertion_cache.ref[i] == BlockNullReference)
                printf(" (nil)");
            else
                printf(" %u", sbp->insertion_cache.ref[i]);
        }
        printf("\n");

        /* ----------------------------------------------------- */

    }

    /* ********************************************************* */

    /* \brief Bit pattern description of the mode field in the inode data type */
    static const char *inodetypes[] = {
        "free",
        "INVALID_001",
        "INVALID-002",
        "INVALID_003",
        "directory",
        "INVALID-005",
        "INVALID-006",
        "INVALID_007",
        "regular file",
        "INVALID_011",
        "symlink",
        "INVALID_013",
        "INVALID-014",
        "INVALID-015",
        "INVALID-016",
        "INVALID-017"
    };

    static void printInode(void *buf, uint16_t in, bool showtimes = true)
    {
        SOInode *ip = (SOInode *) buf;

        /* print inode number */
        printf("Inode #");
        if (in == InodeNullReference)
            printf("(nil)\n");
        else
            printf("%u\n", in);

        /* decouple and print mode field */
        uint32_t typebits = (ip->mode & S_IFMT) >> 12;
        printf("type = %s, ", inodetypes[typebits]);

        uint32_t permbits = ip->mode & (S_IRWXU | S_IRWXG | S_IRWXO);
        char perm[10] = "rwxrwxrwx";    /* access permissions array */
        for (int i = 0; i < 9; i++)
        {
            if ((permbits % 2) == 0)    // LSB is zero ?
            {
                perm[8 - i] = '-';
            }
            permbits >>= 1;
        }
        printf("permissions = %s, ", perm);

        /* print reference count */
        printf("lnkcnt = %" PRIu16 ", ", ip->lnkcnt);

        /* print owner and group IDs */
        printf("owner = %u, group = %u\n", ip->owner, ip->group);

        /* print file size in bytes and in clusters */
        printf("size in bytes = %u, block count = %u\n", ip->size, ip->blkcnt);

        if (showtimes)
        {
            char timebuf[30];
            time_t t = ip->atime;
            ctime_r(&t, timebuf);
            timebuf[strlen(timebuf) - 1] = '\0';
            printf("atime = %s, ", timebuf);
            t = ip->mtime;
            ctime_r(&t, timebuf);
            timebuf[strlen(timebuf) - 1] = '\0';
            printf("mtime = %s, ", timebuf);
            t = ip->ctime;
            ctime_r(&t, timebuf);
            timebuf[strlen(timebuf) - 1] = '\0';
            printf("ctime = %s\n", timebuf);
        }

        /* print direct references */
        printf(" d[*] = {");
        for (int i = 0; i < N_DIRECT; i++)
        {
            if (i > 0)
                printf(" ");
            if (ip->d[i] == BlockNullReference)
                printf("(nil)");
            else
                printf("%" PRIu32 "", ip->d[i]);
        }
        printf("},  ");

        /* print single indirect references */
        printf("i1[*] = {");
        for (int i = 0; i < N_INDIRECT; i++)
        {
            if (i > 0)
                printf(" ");
            if (ip->i1[i] == BlockNullReference)
                printf("(nil)");
            else
                printf("%" PRIu32 "", ip->i1[i]);
        }
        printf("},  ");

        /* print double indirect reference */
        printf("i2[*] = {");
        for (int i = 0; i < N_DOUBLE_INDIRECT; i++)
        {
            if (i > 0)
                printf(" ");
            if (ip->i2[i] == BlockNullReference)
                printf("(nil)");
            else
                printf("%" PRIu32 "", ip->i2[i]);
        }
        printf("}\n");

        printf("----------------\n");
    }

    /* ********************************************************* */

    void printBlockOfInodes(void *buf, uint32_t off, bool showtimes)
    {
        /* cast buf to appropriate type */
        SOInode *inode = (SOInode *) buf;

        /* treat each inode stored in the block separately */
        for (uint32_t i = 0; i < IPB; i++)
            printInode(&inode[i], i + off, showtimes);
    }

    /* ********************************************************* */

    void printBlockOfDirents(void *buf, uint32_t off)
    {
        /* get dirents per cluster */
        uint32_t dpb = BlockSize / sizeof (SODirentry);

        /* cast buf to appropriate type */
        SODirentry *dir = (SODirentry *) buf;

        /* print */
        for (uint32_t i = 0; i < dpb; i++)
        {
            printf("%-*.*s ", SOFS20_FILENAME_LEN, SOFS20_FILENAME_LEN, dir[i].name);
            if (dir[i].in == InodeNullReference)
                printf("(nil)\n");
            else
                printf("%.10" PRIu32 "\n", dir[i].in);
        }
    }

    /* ********************************************************* */

    void printBlockOfRefs(void *buf, uint32_t off)
    {
        /* get refs per block */
        uint32_t rpb = BlockSize / sizeof (uint32_t);

        /* cast buf to appropriate type */
        uint32_t *ref = (uint32_t *) buf;

        for (uint32_t i = 0; i < rpb; i++)
        {
            if ((i & 0x07) == 0)
                printf("%4.4d:", i + off);
            /* print reference to a cluster */
            if (ref[i] == BlockNullReference)
                printf("   (nil)   ");
            else
                printf(" %.10" PRIu32, ref[i]);
            /* terminate present line, if required */
            if (((i & 0x07) == 0x07) || (i == (rpb - 1)))
                printf("\n");
        }
    }

    /* ********************************************************* */

};
