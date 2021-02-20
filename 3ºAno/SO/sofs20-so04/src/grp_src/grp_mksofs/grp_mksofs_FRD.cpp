#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{
    /*
       filling in the contents of the root directory:
       the first 2 entries are filled in with "." and ".." references
       the other entries are empty.
       If rdsize is 2, a second block exists and should be filled as well.
       */
    void grpFillRootDir(uint32_t itotal)
    {
        soProbe(606, "%s(%u)\n", __FUNCTION__, itotal);

        /* replace the following line with your code */
        //binFillRootDir(itotal);

        //buffer of direntries
        SODirentry dir[DPB];

        //The remaining of the data block must be cleaned, filled with pattern 0x0
        for(uint32_t i=2; i<DPB;i++){
            dir[i].in = InodeNullReference;
            strcpy(dir[i].name, "0x0");
        }
        memset(dir,0,BlockSize);

        //It contains two entries, filled in with "." and "..", both pointing to inode 0
        dir[0].in = 0;
        strcpy(dir[0].name, "."); 
        dir[1].in = 0;
        strcpy(dir[1].name, "..");

        soWriteRawBlock(1+itotal/IPB, &dir);
    }
};

