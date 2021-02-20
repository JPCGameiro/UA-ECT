#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{
    void grpFillSuperblock(const char *name, uint32_t ntotal, uint32_t itotal, uint32_t dbtotal)
    {
        soProbe(602, "%s(%s, %u, %u, %u)\n", __FUNCTION__, name, ntotal, itotal, dbtotal);

        SOSuperblock* buf = (SOSuperblock*) malloc(sizeof(SOSuperblock));

        uint32_t nBlkiN = itotal/16;

        buf->magic=0xFFFF;
        buf->version=VERSION_NUMBER;
        buf->mntstat=0;
        //buf->name = *name;
        memcpy(&(buf->name), name, 20);
        buf->ntotal = ntotal;
        buf->itotal = itotal;
        buf->ifree=itotal-1;
        buf->iidx=0;
        buf->ibitmap[0]=0x1;
        buf->dbp_start=1+nBlkiN;
        buf->dbtotal=dbtotal;
        buf->dbfree=dbtotal-1;
        buf->rt_start=1+nBlkiN+dbtotal;
        buf->rt_size=ntotal-buf->rt_start;

        buf->reftable.blk_idx=0;
        buf->reftable.ref_idx=0;
        if (buf->dbfree <= 68){
            buf->reftable.count=0;
        } else {
            buf->reftable.count=buf->dbfree-REF_CACHE_SIZE;
        }
        
        /* fill  retrieval cache */
        int32_t ndb =( dbtotal >REF_CACHE_SIZE) ? REF_CACHE_SIZE:dbtotal-1;
        for(int32_t i = REF_CACHE_SIZE-1;i>=0;i--){
            buf->retrieval_cache.ref[i] = (ndb>0) ? ndb:BlockNullReference;
            ndb--;
        }
        buf->retrieval_cache.idx=( dbtotal >REF_CACHE_SIZE) ? 0:REF_CACHE_SIZE-dbtotal+1;

        buf->insertion_cache.idx=0;
        /* fill insertion cache */ 
        for(uint32_t i = 0; i<REF_CACHE_SIZE;i++){
            buf->insertion_cache.ref[i]=BlockNullReference;
        }
        
        soWriteRawBlock(0, buf);

        free(buf);

        /* replace the following line with your code */
        //binFillSuperblock(name, ntotal, itotal, dbtotal);
    }
};

