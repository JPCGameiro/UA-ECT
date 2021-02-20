/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2009-2020
 */

#include "freedatablocks.h"

#include <stdio.h>
#include <errno.h>
#include <inttypes.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>

#include "core.h"
#include "devtools.h"
#include "daal.h"

namespace sofs20
{
    uint32_t grpAllocDataBlock()
    {
        soProbe(441, "%s()\n", __FUNCTION__);

        /* replace the following line with your code */
        //return binAllocDataBlock();

        //A data block reference is retrieved from the retrieval cache
        SOSuperblock* sb = soGetSuperblockPointer();

        //If the retrieval cache is empty, the replenish function must be called first
        if(sb->retrieval_cache.idx == REF_CACHE_SIZE){
            //if there are no free data blocks
            if(sb->reftable.count==0) throw SOException(ENOSPC, __FUNCTION__);
            soReplenishRetrievalCache();
        }

        //The first reference in the retrieval cache must be retrieved and returned
        uint32_t db = sb->retrieval_cache.ref[sb->retrieval_cache.idx];

        //The data block meta data fields in the superblock must be updated
        sb->retrieval_cache.ref[sb->retrieval_cache.idx] = BlockNullReference;
        sb->retrieval_cache.idx++;
        soSaveSuperblock();

        return db;
    }
};

