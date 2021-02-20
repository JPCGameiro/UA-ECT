#include "direntries.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"
#include "fileblocks.h"
#include "bin_direntries.h"

#include <string.h>
#include <errno.h>
#include <sys/stat.h>

namespace sofs20
{
    void grpRenameDirentry(int pih, const char *name, const char *newName)
    {
        soProbe(204, "%s(%d, %s, %s)\n", __FUNCTION__, pih, name, newName);

        /* replace the following line with your code */
        SOSuperblock* pointer_sb = soGetSuperblockPointer();
        uint16_t in = soGetInodeNumber(pih);
        if(in>=pointer_sb->itotal || in<0)
            throw SOException(EINVAL,__FUNCTION__);
    
        SOInode* ipointer = soGetInodePointer(pih);
        uint32_t block_idx = BlockNullReference;
        uint32_t entry_idx = 0;

        for(unsigned int i = 0; i<=ipointer -> size/BlockSize; i++) 
        {//percorre os blocos
            SODirentry entries[DPB];
            soReadFileBlock(pih,i,entries);

            for(unsigned int k=0; k < DPB; k++) // percorre as entries
            {
                if(strcmp(newName,entries[k].name) == 0) //se tiver uma entrada com nome igual manda exceção
                {
                    throw SOException(EEXIST,__FUNCTION__);
                }

                if(strcmp(name,entries[k].name) == 0)// encontrou a entrada
                {
                    entry_idx = k;
                    block_idx = i;
                }
            }
        }

        if(block_idx == BlockNullReference){// nao existe nenhuma entrada chamada 'name'
            throw SOException(ENOENT, __FUNCTION__);
        }
        SODirentry entries[DPB];
        soReadFileBlock(pih,block_idx,entries);
        strcpy(entries[entry_idx].name,newName);    //muda o nome
        soWriteFileBlock(pih,block_idx,entries);    //escreve
        //binRenameDirentry(pih, name, newName);
    }
};



