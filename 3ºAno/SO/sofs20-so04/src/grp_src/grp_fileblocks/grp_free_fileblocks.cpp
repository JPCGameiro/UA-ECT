#include "fileblocks.h"

#include "freedatablocks.h"
#include "daal.h"
#include "core.h"
#include "devtools.h"

#include <inttypes.h>
#include <errno.h>
#include <assert.h>

namespace sofs20
{

    /* free all blocks between positions ffbn and RPB - 1
     * existing in the block of references given by i1.
     * Return true if, after the operation, all references become BlockNullReference.
     * It assumes i1 is valid.
     */
    static bool grpFreeIndirectFileBlocks(SOInode * ip, uint32_t i1, uint32_t ffbn);

    /* free all blocks between positions ffbn and RPB**2 - 1
     * existing in the block of indirect references given by i2.
     * Return true if, after the operation, all references become BlockNullReference.
     * It assumes i2 is valid.
     */
    static bool grpFreeDoubleIndirectFileBlocks(SOInode * ip, uint32_t i2, uint32_t ffbn);

    /**
     * @brief frees and derefences the data blocks references in a block f.
     * 
     * @param db datapool blocck with references to data blocks.
     * @param ffbn first block to be cleared
     * @return true after all blocks from ffbn to RPB-1 are freed and references cleared
     * @return false 
     */
    bool grpFreeRefedBlocks(uint32_t db, uint32_t ffbn);

    /* ********************************************************* */

    void grpFreeFileBlocks(int ih, uint32_t ffbn)
    {
        soProbe(303, "%s(%d, %u)\n", __FUNCTION__, ih, ffbn);

        //soCheckInodeHandler(ih, __FUNCTION__);

        //get inode
        SOInode* in = soGetInodePointer(ih);

        //free directly referenced blocks
        for(uint32_t i = 0; i< N_DIRECT;i++){
            if(i>=ffbn){
                soFreeDataBlock[in->d[i]];
            }
        }

        //free indirectly referenced blocks
        if(ffbn < N_DIRECT + N_INDIRECT * RPB) {
            grpFreeIndirectFileBlocks(in,0,ffbn-N_DIRECT);
        } 
        
        //free double indirectly referenced blocks
        
        if(ffbn < N_DIRECT + N_INDIRECT * RPB * RPB){
            ffbn -= (N_DIRECT + N_INDIRECT*RPB);
            grpFreeDoubleIndirectFileBlocks(in,0,ffbn);
        }
        
        /* replace the following line with your code */
        //binFreeFileBlocks(ih, ffbn);
    }

    /* ********************************************************* */

    static bool grpFreeIndirectFileBlocks(SOInode * ip, uint32_t i1, uint32_t ffbn)
    {
        soProbe(303, "%s(..., %u, %u)\n", __FUNCTION__, i1, ffbn);

        if(i1 >= N_INDIRECT) {
            return true;
        }

        // get datpool bloxk with references
        uint32_t data_blk = ip->i1[i1];

        // check if ffnb refers to current block and that current block is valid
        if(ffbn < RPB && data_blk!=BlockNullReference) {

            // free references in current block
            grpFreeRefedBlocks(data_blk, ffbn);

            // if ffbn == 0: this block got all of its references cleared
            // so can be freed
            if(ffbn==0){
                soFreeDataBlock(data_blk);
                ip->i1[i1]=BlockNullReference;
            }

            // if ffbn fell on current block means ll following blocks have to be cleared: ffbn=0.
            ffbn = 0;

        } else {
            ffbn-=RPB;
        }

        return grpFreeIndirectFileBlocks(ip, i1+1, ffbn);

        /* replace the following line with your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
    }

    static bool grpFreeDoubleIndirectFileBlocks(SOInode * ip, uint32_t i2, uint32_t ffbn)
    {
        soProbe(303, "%s(..., %u, %u)\n", __FUNCTION__, i2, ffbn);

        if(i2 >= N_DOUBLE_INDIRECT) {
            return true;
        }

        //datapool block with double indirect rferences
        uint32_t blk = ip->i2[i2];        

        // index corresponding to first indirect block with d[ffbn]
        uint32_t i2BlkIdx = ffbn/RPB;

        // this block only addresses RPB^2 references
        // if ffbn does doesn adrress this block no clearing necessary
        if(i2BlkIdx < RPB) {
            // references to indirect blocks in the double indirect block
            uint32_t refs2[RPB];
            soReadDataBlock(blk, refs2);

            // index for first data block within indirect block
            uint32_t i1BlkIdx = ffbn%RPB;

            for(uint32_t i = i2BlkIdx; i<RPB; i++){
                if(refs2[i] != BlockNullReference){
                    grpFreeRefedBlocks(refs2[i], i1BlkIdx);
                    // if ffbn==0 all references in block were freed so the block itself can be freed
                    if(i1BlkIdx==0){
                        soFreeDataBlock(refs2[i]);
                        refs2[i] = BlockNullReference;
                    }
                    i1BlkIdx=0;
                }
            }

            //update references
            soWriteDataBlock(blk, refs2);

            // if ffbn==0 current block had all its referencess cleared
            // it can also be freed
            if(ffbn==0){
                soFreeDataBlock(blk);
                ip->i2[i2] =  BlockNullReference;
            }

            // all references in the next blocks need to be freed
            ffbn = 0;
        } else {
            ffbn -= RPB*RPB;
        }

        return grpFreeDoubleIndirectFileBlocks(ip, i2+1,ffbn);

        /* replace the following line with your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
    }

    bool grpFreeRefedBlocks(uint32_t db, uint32_t ffbn) {
        // check if ffbn corresponds to any block referenced in db
        if(ffbn < RPB) {

            //references o data blcks saved in db
            uint32_t refs[RPB];
            soReadDataBlock(db, refs);

            // iteration over all references in db
            for(uint32_t i = ffbn; i < RPB; i++){
                uint32_t r = refs[i];
                if (r != BlockNullReference)
                {
                    // free referenced data block
                    soFreeDataBlock(r);
                    // clear reference to data block
                    refs[i] = BlockNullReference;
                }
            }

            // update block of references
            soWriteDataBlock(db, refs);
        }

        return true;
    }
};

