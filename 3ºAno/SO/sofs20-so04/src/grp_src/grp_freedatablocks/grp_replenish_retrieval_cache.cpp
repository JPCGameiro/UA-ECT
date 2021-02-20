
/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2016-2020
 */

#include "freedatablocks.h"

#include <string.h>
#include <errno.h>
#include <iostream>

#include "core.h"
#include "devtools.h"
#include "daal.h"

namespace sofs20
{
    void grpReplenishRetrievalCache(void)
    {
        soProbe(443, "%s()\n", __FUNCTION__);

        SOSuperblock* sb = soGetSuperblockPointer();

        /* Check if Retrieval Cache is empty */
        if(sb->retrieval_cache.idx!=68){
            /* Cache not empty */
            return;
        }

        /*check if reference table has references */
        if (sb->reftable.count > 0)
        {
            /* get references from reference table */

            uint32_t blk_idx = sb->reftable.blk_idx; 
            uint32_t ref_idx = sb->reftable.ref_idx;
            /* Number of cells left on the block */
            uint32_t refs_blk = RPB - ref_idx;

            uint32_t* blck = soGetReferenceBlockPointer(blk_idx);

            /* number of 'readable' refs */ 
            uint32_t refs_av = (sb->reftable.count < refs_blk)? sb->reftable.count:refs_blk;

            int32_t i = 0;
            if(refs_av < 68){
                i = REF_CACHE_SIZE - refs_av;
            }

            /* update cache idx */
            sb->retrieval_cache.idx=i;

            while(i<68 && ref_idx < RPB){
                
                sb->retrieval_cache.ref[i] = *(blck + ref_idx);

                /*update reference table*/
                *(blck + ref_idx) = BlockNullReference;
                sb->reftable.count-=1;

                i++;
                ref_idx++;
            }

            /* update supervlock values */
            if(ref_idx==RPB){
                /* update bck_idx */
                uint32_t rt_end = sb->rt_start+sb->rt_size;
                sb->reftable.blk_idx = (blk_idx == rt_end)? sb->rt_start:(blk_idx+1);

                sb->reftable.ref_idx = 0;
            } else {
                sb->reftable.ref_idx = ref_idx;
            }

            // checks if reference table is empty after getting values from it
            // if empty reset indexes
            if(sb->reftable.count==0){
                sb->reftable.blk_idx = 0;
                sb->reftable.ref_idx = 0;
            }
        }
        /* check if Insertion Cache has references */
        else if(sb->insertion_cache.idx>0)
        {
            //  insertion cache has references

            // calculate the index where to start fill retrival cache 
            // number of references in insertion cache -> start index
            uint32_t idx_st = REF_CACHE_SIZE - sb->insertion_cache.idx;

            for(int32_t i = idx_st; i<68;i++){
                // copy refs from insertion cache from oler to newer
                sb->retrieval_cache.ref[i] = sb->insertion_cache.ref[i];
                //update insertion cache position with null reference
                sb->insertion_cache.ref[i] = BlockNullReference;
            }

            // update retrival cache index in superblock 
            sb->retrieval_cache.idx=idx_st;

        } 
        /*there are no references available */
        else {
            return;
        }

        /* save suberblock **/
        soSaveSuperblock();

        /* replace the following line with your code */
        //binReplenishRetrievalCache();
    }
};


