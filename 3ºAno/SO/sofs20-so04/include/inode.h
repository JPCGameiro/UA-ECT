/**
 *  \file
 *  \brief Definition of the inode data type.
 *
 *  \author Artur Pereira - 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#ifndef __SOFS20_INODE__
#define __SOFS20_INODE__

#include <inttypes.h>

namespace sofs20
{

    /**
     * \defgroup inode inode
     * \ingroup core
     * \brief The \c SOInode data type
     * @{
     */

    /** \brief number of direct data block references in the inode */
#define N_DIRECT 4

    /** \brief number of indirect data block references in the inode */
#define N_INDIRECT 3

    /** \brief number of double indirect data block references in the inode */
#define N_DOUBLE_INDIRECT 1

    /** \brief Definition of the inode data type. */
    struct SOInode
    {
        /** \brief inode mode: it stores the file type and permissions.
         *  \see man 7 inode
         */
        uint16_t mode;

        /** \brief link count: number of hard links (directory entries) pointing to the inode */
        uint16_t lnkcnt;

        /** \brief user ID of the file owner */
        uint32_t owner;

        /** \brief group ID of the file owner */
        uint32_t group;

        /** \brief file size in bytes: */
        uint32_t size;

        /** \brief block count: total number of blocks used by the file 
         * - it includes data and reference blocks */
        uint32_t blkcnt;

        /** \brief access time: time of last access to file data or meta-data */
        uint32_t atime;

        /** \brief modification time: time of last change to file data */
        uint32_t mtime;

        /** \brief change time: time of last change to file meta-data */
        uint32_t ctime;

        /** \brief direct references to the first data blocks with file's data */
        uint32_t d[N_DIRECT];

        /** \brief references to block(s) that extend the \c d array */
        uint32_t i1[N_INDIRECT];

        /** \brief references to block(s) that extends the \c i1 array */
        uint32_t i2[N_DOUBLE_INDIRECT];
    };

    /** @} */

};

#endif /* __SOFS20_INODE__ */
