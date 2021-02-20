#include "fileblocks.h"

#include "daal.h"
#include "core.h"
#include "devtools.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{
    void grpWriteFileBlock(int ih, uint32_t fbn, void *buf)
    {
        soProbe(332, "%s(%d, %u, %p)\n", __FUNCTION__, ih, fbn, buf);
        SOSuperblock* pointer_sb = soGetSuperblockPointer();
        int16_t in = soGetInodeNumber(ih);
        if(in>=pointer_sb->itotal || in<0)
            throw SOException(EINVAL,__FUNCTION__);

        uint32_t b = soGetFileBlock(ih,fbn);

        if(b == BlockNullReference || b == InodeNullReference)
        {
            b = soAllocFileBlock(ih,fbn);
        }
        
        soWriteDataBlock(b,buf);
        //binWriteFileBlock(ih, fbn, buf);
    }
};

