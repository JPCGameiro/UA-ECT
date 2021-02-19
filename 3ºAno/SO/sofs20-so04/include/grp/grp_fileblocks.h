/**
 *  \file 
 *  \brief Group version of function to manage file blocks
 *
 *  \author Artur Pereira - 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 *  \remarks Refer to the main \c fileblocks header file for documentation
 */

#ifndef __SOFS20_FILEBLOCKS_GROUP__
#define __SOFS20_FILEBLOCKS_GROUP__

#include <inttypes.h>

namespace sofs20
{
    uint32_t grpGetFileBlock(int ih, uint32_t fbn);

    uint32_t grpAllocFileBlock(int ih, uint32_t fbn);

    void grpFreeFileBlocks(int ih, uint32_t ffbn);

    void grpReadFileBlock(int ih, uint32_t fbn, void *buf);

    void grpWriteFileBlock(int ih, uint32_t fbn, void *buf);
};

#endif /* __SOFS20_FILEBLOCKS_GROUP__ */
