/**
 * \file
 * \brief Binary version of functions to manage the list of free inodes 
 *      and the list of free blocks
 * 
 *  \author Artur Pereira 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 *  \remarks Refer to the main \c freedatablocks header file for documentation
 *
 */

#ifndef __SOFS20_FREEDATAGROUPS_GROUP__
#define __SOFS20_FREEDATAGROUPS_GROUP__

#include <inttypes.h>

namespace sofs20
{
    uint32_t grpAllocDataBlock();

    void grpReplenishRetrievalCache();

    void grpFreeDataBlock(uint32_t bn);

    void grpDepleteInsertionCache();
};

#endif /* __SOFS20_FREEDATAGROUPS_GROUP__ */
