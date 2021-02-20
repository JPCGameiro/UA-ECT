#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

/*Fill with zeros the free data blocks.

The following nust be considered:

All data blocks, but the one used by the root directory, must be filled with pattern 0x0.
Difficulty: 1
Parameters
[in]	ntotal	Total number of blocks in the device
[in]	itotal	Total number of inodes
[in]	dbtotal	Total number of data blocks
Note
Access to disk must be done using soWriteRawBlock function
*/

namespace sofs20
{
    void grpResetFreeDataBlocks(uint32_t ntotal, uint32_t itotal, uint32_t dbtotal)
    {
        soProbe(607, "%s(%u, %u, %u)\n", __FUNCTION__, ntotal, itotal, dbtotal);

        /* replace the following line with your code */
        //binResetFreeDataBlocks(ntotal, itotal, dbtotal);

        uint32_t InodesBlocks = itotal/IPB;
        uint32_t buf[BlockSize];

        for(uint32_t i=0; i < BlockSize; i++)
            buf[i] = 0x0;
        
        for(uint32_t i = InodesBlocks + 2; i <InodesBlocks + 1 + dbtotal; i++)
            soWriteRawBlock(i, buf);   

    }
};

