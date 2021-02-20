#include "direntries.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"
#include "fileblocks.h"
#include "bin_direntries.h"

#include <errno.h>
#include <string.h>
#include <sys/stat.h>

namespace sofs20
{
    bool grpCheckDirEmpty(int ih)
    {
        soProbe(205, "%s(%d)\n", __FUNCTION__, ih);

        /* replace the following line with your code */
        // return binCheckDirEmpty(ih);
        SOInode* pih = soGetInodePointer(ih);   // inode handler pointer

        for (uint32_t i = 0; i <= pih->size/BlockSize; i++)
        {
            SODirentry dir[DPB];
            soReadFileBlock(ih, i, dir);
            for (uint32_t j = 2; j < DPB; j++)
            {
                if (strcmp(dir[j].name, "\0") != 0) // verify if empty
                {
                    return false;
                } 
            }
        }
        
        return true;
    }
};

