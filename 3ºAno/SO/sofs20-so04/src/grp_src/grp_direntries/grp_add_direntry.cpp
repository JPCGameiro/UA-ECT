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
    void grpAddDirentry(int pih, const char *name, uint16_t cin)
    {
        soProbe(202, "%s(%d, %s, %u)\n", __FUNCTION__, pih, name, cin);

        /* replace the following line with your code */
        //binAddDirentry(pih, name, cin);
        
        //inode parent
        SOInode* ind = soGetInodePointer(pih); 
        //buffer with direntries
        SODirentry dir[DPB];
        //fileblock number
        uint32_t fbn = ind->size/BlockSize;

        //read inode FB
        soReadFileBlock(pih, fbn, dir);

        //if inode nill alloc it
        if(soGetFileBlock(pih, fbn) == BlockNullReference){
            soAllocFileBlock(pih, fbn);
            for(uint32_t i=0; i<DPB; i++){
                dir[i].in = 0;
                strcpy(dir[i].name, "");
            }
        }

        for(uint32_t idx=0; idx<DPB; idx++){
            //Add a new entry to the parent directory
            if(strcmp(dir[idx].name, "")==0){
                dir[idx].in = cin;
                strcpy(dir[idx].name, name);
                break;
            }
        }
        //write FB
        soWriteFileBlock(pih, fbn, dir);
        //The inode fields must be updated
        ind->size+= sizeof(SODirentry);
        soSaveInode(pih);
    }
};

