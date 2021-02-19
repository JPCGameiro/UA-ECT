/**
 *  \file 
 *  \brief Display the contents of a block 
 *
 *      \li as hexadecimal data
 *      \li as ascii data
 *      \li as superblock data
 *      \li as inode data
 *      \li as block references
 *      \li as directory entries.
 *
 *  \author Artur Pereira - 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#ifndef __SOFS20_BLOCKVIEWS__
#define __SOFS20_BLOCKVIEWS__

#include "core.h"

#include <inttypes.h>

namespace sofs20
{
    /**
     *  \brief Display block as hexadecimal data.
     *
     *  \param buf pointer to a buffer with block contents
     *  \param off offset for the labels
     */
    void printBlockAsHex(void *buf, uint32_t off = 0x0);

    /**
     *  \brief Display block as ASCII data.
     *
     *  \param buf pointer to a buffer with block contents
     *  \param off offset for the labels
     */
    void printBlockAsAscii(void *buf, uint32_t off = 0x0);

    /**
     *  \brief Display block as superblock data.
     *
     *  \param buf pointer to a buffer with block contents
     */
    void printSuperblock(void *buf);

    /**
     *  \brief Display the block contents as inode data.
     *
     *  \param buf pointer to a buffer with block contents
     *  \param off offset for the labels (0 by default)
     *  \param showtimes flag to show/not show times (default true)
     */
    void printBlockOfInodes(void *buf, uint32_t off = 0x0, bool showtimes = true);

    /**
     *  \brief Display the block contents as direntry data.
     *
     *  \param buf pointer to a buffer with block contents
     *  \param off offset for the labels
     */
    void printBlockOfDirents(void *buf, uint32_t off = 0x0);

    /**
     *  \brief Display the block contents as reference data.
     *
     *  \param buf pointer to a buffer with block contents
     *  \param off offset for the labels
     */
    void printBlockOfRefs(void *buf, uint32_t off = 0x0);

};

#endif /* __SOFS20_BLOCKVIEWS__ */
