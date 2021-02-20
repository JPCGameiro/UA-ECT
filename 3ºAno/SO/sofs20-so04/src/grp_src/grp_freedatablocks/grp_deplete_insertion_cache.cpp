/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2016-2020
 */

#include "freedatablocks.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"

#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <iostream>
using namespace std;

namespace sofs20
{
    /* only fill the current block to its end */
    void grpDepleteInsertionCache(void)
    {
        soProbe(444, "%s()\n", __FUNCTION__);

        /* replace the following line with your code */
        //binDepleteInsertionCache();

        
        //Pointer to the Superblock
        SOSuperblock* sp = soGetSuperblockPointer();

        //If cache is not full nothing happens
        if(sp->insertion_cache.idx < REF_CACHE_SIZE)
            return;

        //Variable to Find the last block with references
        uint32_t lastBlock = sp->reftable.ref_idx + ((sp->reftable.count/RPB) % sp->rt_size);
        //Find the last reference 
        uint32_t lastRef = (sp->reftable.count % (sp->rt_size*RPB)) - lastBlock*RPB ;

        //Get a pointer to a reference block of the reference table.
        uint32_t* rp = soGetReferenceBlockPointer(lastBlock);

        //Variable to count the number of new references inserted in the reference table
        uint32_t cnt = 0;
        uint32_t j = 0;

        //Copy references from insertion cache to reference table
        for(uint32_t i = lastRef; i < RPB; i++){
            //All references (insertion_cache) where copied
            if(sp->insertion_cache.ref[0] == BlockNullReference) 
                break;
            else{
                //Copy the reference on position 0
                rp[i] = sp->insertion_cache.ref[0];
                cnt++;

                //Move all insertion cache references one position back
                j = 0;
                while(j != sp->insertion_cache.idx) {
                    sp->insertion_cache.ref[j] = sp->insertion_cache.ref[j+1];
                    j++;
                }

                //Update the insertion cache info
                sp->insertion_cache.ref[sp->insertion_cache.idx-1] = BlockNullReference;
                sp->insertion_cache.idx--;
            }
        }     
        
        //Update the number of not null references in the reference table
        sp->reftable.count += cnt;

        //Write new content in the reference table
        soSaveReferenceBlock();

        //Save SuperBlockMetaInfo
        soSaveSuperblock(); 

    }
};

