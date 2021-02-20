/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2016-2020
 * 
 *  Free the referenced data block.details The reference of the freed data block is inserted into the list of free data blocks,
 *  in the insertion cache:


    -If the insertion cache is full, the deplete function must be called first.
    -The reference must be inserted at the first empty cell.
    -The data block meta data fields in the superblock must be updated.

    Precondition
    -bn must represent a valid data block number

    Exceptions
    -EINVAL	if data block number is not valid.

    Parameters
    -[in]	bn	the number (reference) of the data block to be freed

    Remarks
    -When calling a function of any layer, the version with prefix so should be used, not the one with prefix grp or bin.
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
    void grpFreeDataBlock(uint32_t bn)
    {
        soProbe(442, "%s(%u)\n", __FUNCTION__, bn);

        /* replace the following line with your code */
        //binFreeDataBlock(bn);

        //superblock pointer
        soOpenSuperblock();
        SOSuperblock* sb = soGetSuperblockPointer();

        // check if bn is a valid data block
        if(bn < 0 || bn >= sb->dbtotal) {
            throw SOException(EINVAL, __FUNCTION__);
        }

        //check if insertion cache is full
        if(sb->insertion_cache.idx >= REF_CACHE_SIZE) {
            soDepleteInsertionCache();
        }

        // data blocknumber is inserted in insertion cache
        sb->insertion_cache.ref[sb->insertion_cache.idx] = bn;
        sb->insertion_cache.idx += 1;

        //superblock datablockpool metadata update
        sb->dbfree += 1;
        soSaveSuperblock();
        
    }
};

