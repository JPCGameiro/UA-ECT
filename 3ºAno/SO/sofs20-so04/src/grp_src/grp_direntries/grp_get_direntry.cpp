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
    uint16_t grpGetDirentry(int pih, const char *name)
    {
        soProbe(201, "%s(%d, %s)\n", __FUNCTION__, pih, name);

        /* replace the following line with your code */
        //return binGetDirentry(pih, name);

        //preconditions checks
        //soCheckInodeHandler(pih,__FUNCTION__); //causes problems in compilation
        
        if(strchr(name, '/') != nullptr ){
            return InodeNullReference;
        }

        SOInode* pi = soGetInodePointer(pih);
        SODirentry entries[DPB];

        // number of blocks with entries
        uint32_t dataBlkCnt = pi->size/BlockSize + 1;

        // goes through all blocks with entries
        for(uint32_t i = 0; i<dataBlkCnt;i++){
            uint32_t block = soGetFileBlock(pih, i);
            soReadDataBlock(block, entries);

            // search entries in a block
            for(uint32_t j=0; j<DPB;j++){
                // checks if entry is empty
                if(strcmp(entries[j].name, "")==0){
                    // entry is empty, as such, theres no more entries
                    break;
                }

                //checks if this is the entry we are looking for
                if(strcmp(entries[j].name,name)==0){
                    // this is the entry wen are looking for
                    // return the inode asssociated with th entry
                    printf("name: %s\n", entries[j].name);
                    return entries[j].in;
                }
            }
        }

        // the entry was not found
        return InodeNullReference;
    }
};

