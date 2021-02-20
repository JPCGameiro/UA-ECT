#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <inttypes.h>
#include <string.h>
#include <iostream>

namespace sofs20
{
    uint32_t roundBUp(uint32_t);
    void grpFillReferenceTable(uint32_t ntotal, uint32_t itotal, uint32_t dbtotal)
    {
        soProbe(605, "%s(%u, %u, %u)\n", __FUNCTION__, ntotal, itotal, dbtotal);
        
        /* replace the following line with your code */
        //binFillReferenceTable(ntotal, itotal, dbtotal);
        
        //Numero total de inodes
        uint32_t iBlocks = itotal/IPB;
        //Numero de Blocks da refTable
        uint32_t Blockref = ntotal-1-iBlocks-dbtotal;
        //dbtotal = ntotal - iBlocks;
        uint32_t buffer[RPB];
        uint32_t nextRef = REF_CACHE_SIZE+1; 
        //uint32_t lastblock = ntotal-1-iBlocks-1;

        for (uint32_t i=0; i<Blockref; i++){
            if (i==Blockref - 1) {
                buffer[0] = BlockNullReference;
            }
            else{
                buffer[0] = i;
            }
            for (uint32_t j=0; j<RPB; j++) {
                if (nextRef == dbtotal) { 
                    for (uint32_t k=j; k<RPB; k++)
                    {
                        buffer[k] = BlockNullReference;
                    }
                    break;
                }
                else {
                    buffer[j] = nextRef;
                    nextRef++;
                }
                
            }
            soWriteRawBlock(dbtotal+ 1 + iBlocks+i, &buffer);
        }
        
    }
};

