/**
 * \file
 * \brief Binary version of functions to manage the list of free inodes 
 *      and the list of free blocks
 * 
 *  \author Artur Pereira 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 *  \remarks Refer to the main \c freeinodes header file for documentation
 *
 */

#ifndef __SOFS20_FREEINODES_GROUP__
#define __SOFS20_FREEINODES_GROUP__

#include <inttypes.h>

namespace sofs20
{
    uint16_t grpAllocInode(uint32_t mode);

    void grpFreeInode(uint16_t in);
};

#endif /* __SOFS20_FREEINODES_GROUP__ */
