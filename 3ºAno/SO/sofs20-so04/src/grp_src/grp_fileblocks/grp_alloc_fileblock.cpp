#include "fileblocks.h"

#include "freedatablocks.h"
#include "daal.h"
#include "core.h"
#include "devtools.h"

#include <errno.h>

#include <iostream>

namespace sofs20
{

//#if false
    static uint32_t grpAllocIndirectFileBlock(SOInode * ip, uint32_t afbn);
    static uint32_t grpAllocDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn);
//#endif

    uint32_t i1_size = N_INDIRECT * RPB;
    uint32_t i2_size = N_DOUBLE_INDIRECT * (RPB * RPB);

    /* ********************************************************* */

    uint32_t grpAllocFileBlock(int ih, uint32_t fbn)
    {
        soProbe(302, "%s(%d, %u)\n", __FUNCTION__, ih, fbn);

        /* replace the following two lines with your code */
        //return binAllocFileBlock(ih, fbn);

        if (fbn >= N_DIRECT + i1_size + i2_size)
        {
            throw SOException(EINVAL, __FUNCTION__);
        }
        
        SOInode *ip = soGetInodePointer(ih);
        uint32_t dataBlock_ref;

        if (fbn < N_DIRECT){
            dataBlock_ref = soAllocDataBlock();
            ip->blkcnt += 1;
            ip->d[fbn] = dataBlock_ref;
        }
        else if (fbn < N_DIRECT + i1_size){
            dataBlock_ref = grpAllocIndirectFileBlock(ip, fbn);
        }
        else{
            dataBlock_ref = grpAllocDoubleIndirectFileBlock(ip, fbn);
        }

        soSaveInode(ih);
        return dataBlock_ref;

    }

    /* ********************************************************* */

//#if false
    /*
    */
    static uint32_t grpAllocIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(302, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* replace the following two lines with your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
        //return 0;

        uint32_t block_ref;
        uint32_t i1_idx = (afbn - N_DIRECT) / RPB;
        uint32_t rdb_idx = (afbn - N_DIRECT) - (RPB * i1_idx);
        uint32_t dataBlock_ref;
        uint32_t rdb_refs[RPB];

        if(ip->i1[i1_idx] == BlockNullReference){
            block_ref = soAllocDataBlock();
            ip->blkcnt += 1;
            ip->i1[i1_idx] = block_ref;

            for (unsigned long i = 0; i < RPB; i++) {
                rdb_refs[i] = BlockNullReference;
            }
            
        }
        else{
            block_ref = ip->i1[i1_idx];
            soReadDataBlock(block_ref, rdb_refs);
        }
        dataBlock_ref = soAllocDataBlock();
        ip->blkcnt += 1;
        rdb_refs[rdb_idx] = dataBlock_ref;
        
        return dataBlock_ref;

    }
//#endif

    /* ********************************************************* */

//#if false
    /*
    */
    static uint32_t grpAllocDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(302, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* replace the following two lines with your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
        //return 0;

        uint32_t i2_block_ref, i1_block_ref;
        uint32_t i2_idx = (afbn - N_DIRECT - i1_size) / (RPB * RPB);
        uint32_t i1_idx = ((afbn - N_DIRECT - i1_size) - (RPB * RPB * i2_idx)) / RPB;
        uint32_t rdb_idx = ((afbn - N_DIRECT - i1_size) - (RPB * RPB * i2_idx)) - (RPB * i1_idx);
        uint32_t dataBlock_ref;
        uint32_t i1_refs[RPB], rdb_refs[RPB];

        if (ip->i2[i2_idx] == BlockNullReference){
            i2_block_ref = soAllocDataBlock();
            ip->blkcnt += 1;
            ip->i2[i2_idx] = i2_block_ref;
            for(unsigned long i=0; i< RPB; i++){
                i1_refs[i] = BlockNullReference;
            }
        }
        else{
            i2_block_ref = ip->i2[i2_idx];
            soReadDataBlock(i2_block_ref, i1_refs);
        }

        if(i1_refs[i1_idx] == BlockNullReference){
            i1_block_ref = soAllocDataBlock();
            ip->blkcnt += 1;
            i1_refs[i1_idx] = i1_block_ref;
            for(unsigned long i=0; i < RPB; i++){
                rdb_refs[i] = BlockNullReference;
            }
        }
        else{
            i1_block_ref = i1_refs[i1_idx];
            soReadDataBlock(i1_block_ref, rdb_refs);
        }

        dataBlock_ref = soAllocDataBlock();
        ip->blkcnt += 1;
        rdb_refs[rdb_idx] = dataBlock_ref;

        soWriteDataBlock(i1_block_ref, rdb_refs);
        soWriteDataBlock(i2_block_ref, i1_refs);
        return dataBlock_ref;
    }
//#endif
};
 
