/**
 *  \file 
 *  \brief Definition of the superblock data type.
 *
 *  \author Artur Pereira - 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#ifndef __SOFS20_SUPERBLOCK__
#define __SOFS20_SUPERBLOCK__

#include "constants.h"

#include <inttypes.h>

namespace sofs20
{
    /** 
     * \defgroup superblock superblock
     * \ingroup core
     * \brief The SOSuperblock data type
     * @{ 
     */

/** \brief sofs20 magic number */
#define MAGIC_NUMBER 0x50F5

/** \brief sofs20 version number */
#define VERSION_NUMBER 0x20

/** \brief maximum length of volume name */
#define PARTITION_NAME_LEN 19

/** \brief reference cache size */
#define REF_CACHE_SIZE 68

    /** \brief Definition of the superblock data type. 
     */
    struct SOSuperblock
    {

        /** \brief magic number - file system identification number */
        uint16_t magic;

        /** \brief version number */
        uint8_t version;

        /** \brief mount status and mount count 
         * - if positive it means properly unmounted
         * - if negative on mount, file system check should be done
         * - absolute value represents the number of mounts
         */
        int8_t mntstat;

        /** \brief volume name */
        char name[PARTITION_NAME_LEN + 1];

        /** \brief total number of blocks in the device */
        uint32_t ntotal;

        /* Inode table's metadata */

        /** \brief total number of inodes */
        uint32_t itotal;

        /** \brief number of free inodes */
        uint32_t ifree;

        /** \brief bit index of last allocated inode 
         * - This value is the same as the number of the last inode allocated 
         */
        uint32_t iidx;

        /** \brief bitmap representing inode allocation states 
         * - All inodes are represented, including inode number 0, which is never free
         * - Inode 0 is represented by bit 0 (LSB) of ibitmap[0], and so forth
         * - A 0 means the corresponding inode is free, and 1 it is in-use.
         */
        uint32_t ibitmap[MAX_INODES/32];


        /* Data blocks' metadata */

        /** \brief physical number of the block where the data block pool starts */
        uint32_t dbp_start;

        /** \brief total number of data blocks */
        uint32_t dbtotal;

        /** \brief number of free data blocks */
        uint32_t dbfree;

        /* Reference table's metadata */

        /** \brief physical number of the block where the reference table starts */
        uint32_t rt_start;

        /** \brief number of blocks the reference table comprises */
        uint32_t rt_size;

        /** \brief The reference table control structure */
        struct ReferenceTable
        {
            /** \brief index, within the reference table, of the first block with references */
            uint32_t blk_idx; 
            /** \brief index of first cell with references, within the previous block */
            uint32_t ref_idx;
            /** \brief total number of not null references in the reference table */
            uint32_t count;
        };

        /** \brief The reference table control structure */
        ReferenceTable reftable;

        /** \brief cache of references to free data blocks */
        struct ReferenceCache
        {
            /** \brief index of first free/occupied cell */
            uint32_t idx;
            /** \brief the cache itself */
            uint32_t ref[REF_CACHE_SIZE];
        };

        /** \brief retrieval cache of references to free data blocks */
        ReferenceCache retrieval_cache;

        /** \brief insertion cache of references to free data blocks */
        ReferenceCache insertion_cache;

    };

    /** @} */

};

#endif /*__SOFS20_SUPERBLOCK__ */
