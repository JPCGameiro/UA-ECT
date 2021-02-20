#include "fileblocks.h"

#include "daal.h"
#include "core.h"
#include "devtools.h"

#include <errno.h>

namespace sofs20
{
    /* ********************************************************* */

//#if false
    static uint32_t grpGetIndirectFileBlock(SOInode * ip, uint32_t fbn);
    static uint32_t grpGetDoubleIndirectFileBlock(SOInode * ip, uint32_t fbn);
//#endif

    /* ********************************************************* */

    uint32_t grpGetFileBlock(int ih, uint32_t fbn)
    {
        soProbe(301, "%s(%d, %u)\n", __FUNCTION__, ih, fbn);

        /* replace the following line with your code */
        //return binGetFileBlock(ih, fbn);

        //check if fbn is valid
        if(N_DIRECT+N_INDIRECT*RPB+N_DOUBLE_INDIRECT*RPB*RPB < fbn) throw SOException(EINVAL, __FUNCTION__);
        //pointer to inode
        SOInode* ind = soGetInodePointer(ih);
        
        uint32_t datablockNumber;
        if(fbn<N_DIRECT) datablockNumber = ind->d[fbn];
        else if(fbn<N_DIRECT+N_INDIRECT*RPB) datablockNumber = grpGetIndirectFileBlock(ind,fbn-N_DIRECT);
        else datablockNumber = grpGetDoubleIndirectFileBlock(ind, fbn-N_DIRECT-N_INDIRECT*RPB);
        
        return datablockNumber;
    }

    /* ********************************************************* */

//#if false
    static uint32_t grpGetIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(301, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* replace the following two lines with your code */
        //throw SOException(ENOSYS, __FUNCTION__); 
        //return 0;
        
        uint32_t refBlock[RPB];
        if(ip->i1[afbn/RPB]== BlockNullReference) return BlockNullReference;
        soReadDataBlock(ip->i1[afbn/RPB],&refBlock);
        return refBlock[afbn%RPB];
    }
//#endif

    /* ********************************************************* */

//#if false
    static uint32_t grpGetDoubleIndirectFileBlock(SOInode * ip, uint32_t afbn)
    {
        soProbe(301, "%s(%d, ...)\n", __FUNCTION__, afbn);

        /* replace the following two lines with your code */
        //throw SOException(ENOSYS, __FUNCTION__);
        //return 0;

        uint32_t refBlock2[RPB];
        if(ip->i2[afbn/(RPB*RPB)]==BlockNullReference) return BlockNullReference;
        soReadDataBlock(ip->i2[afbn/(RPB*RPB)], &refBlock2);

        uint32_t refBlock1[RPB];
        if(refBlock2[afbn/RPB%RPB]==BlockNullReference) return BlockNullReference;
        soReadDataBlock(refBlock2[afbn/RPB%RPB] ,&refBlock1);

        return refBlock1[afbn%RPB%RPB];
    }
//#endif
};

