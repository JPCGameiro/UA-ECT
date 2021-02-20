
#include "freeinodes.h"

#include <stdio.h>
#include <errno.h>
#include <inttypes.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <math.h>

#include "core.h"
#include "devtools.h"
#include "daal.h"

namespace sofs20
{
    void grpFreeInode(uint16_t in)
    {
        soProbe(402, "%s(%u)\n", __FUNCTION__, in);
        
        /* replace the following line with your code */
        SOSuperblock* pointer_sb = soGetSuperblockPointer();
        if(in>=pointer_sb->itotal || in<0)
            throw SOException(EINVAL,__FUNCTION__);
    
        int in_inodehandler = soOpenInode(in);
        SOInode* pointer_inode = soGetInodePointer(in_inodehandler);

        pointer_inode->mode = 0;     
        pointer_inode->lnkcnt = 0;
        pointer_inode->owner = 0;
        pointer_inode->group = 0;
        pointer_inode->size = 0;
        pointer_inode->blkcnt = 0;
        pointer_inode->atime = 0;
        pointer_inode->mtime = 0;
        pointer_inode->ctime = 0;

        for(int i=0; i < N_DIRECT; i++)
            pointer_inode->d[i] = BlockNullReference;
                    
        for(int i=0; i < N_INDIRECT; i++)
            pointer_inode->i1[i] = BlockNullReference;
                    
        for(int i=0; i < N_DOUBLE_INDIRECT; i++)
            pointer_inode->i2[i] = BlockNullReference;


        //Posição do bitmap a aceder
        uint16_t pos = (uint16_t)floor(in / 32);
        //Bit da posição a aceder
        uint32_t bit = ~(0x00000001 << (in % 32));
        pointer_sb->ibitmap[pos] = pointer_sb->ibitmap[pos] & bit;
        
        
        pointer_sb->ifree = pointer_sb->ifree + 1;

        soSaveInode(in_inodehandler);
        soSaveSuperblock(); 
                
        
        //binFreeInode(in);
    }
};
