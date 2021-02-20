#include "grp_mksofs.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <string.h>
#include <time.h>
#include <unistd.h>
#include <sys/stat.h>
#include <inttypes.h>

namespace sofs20
{
    void grpFillInodeTable(uint32_t itotal, bool date)
    {
        soProbe(604, "%s(%u)\n", __FUNCTION__, itotal);

        /* replace the following line with your code */
        //binFillInodeTable(itotal, date);


        //Total number of blocks
        uint32_t numBlocks = itotal/IPB;
        //Create IPB inodes   
        SOInode inodes[IPB];

        for(uint32_t blockNumber=1; blockNumber <= numBlocks; blockNumber++){
            
            for(uint32_t inodeNumber=0; inodeNumber < IPB; inodeNumber++){                
                //if is the first inode
                if(blockNumber == 1 && inodeNumber == 0){
                    inodes[inodeNumber].mode = S_IFDIR | 0755;  //The Lsbs are permissions(0755) and the Msbs type (directory)
                    inodes[inodeNumber].lnkcnt = 2;
                    inodes[inodeNumber].owner = getuid();       
                    inodes[inodeNumber].group = getgid();
                    inodes[inodeNumber].blkcnt = 1;
                    inodes[inodeNumber].size = ((2*BlockSize)/DPB);   //Must be equal to the size of a directory (root) == size of it's entrys (that are two . and ..)
                    if(date){
                        inodes[inodeNumber].atime = time(NULL);
                        inodes[inodeNumber].mtime = time(NULL);
                        inodes[inodeNumber].ctime = time(NULL);
                    }
                    else{
                        inodes[inodeNumber].atime = 0;
                        inodes[inodeNumber].mtime = 0;
                        inodes[inodeNumber].ctime = 0;
                    }
                }
                //any other inode
                else{
                    inodes[inodeNumber].mode = 0;
                    inodes[inodeNumber].lnkcnt = 0;
                    inodes[inodeNumber].owner = 0;
                    inodes[inodeNumber].group = 0;
                    inodes[inodeNumber].blkcnt = 0;
                    inodes[inodeNumber].size = 0;
                    
                    inodes[inodeNumber].atime = 0;
                    inodes[inodeNumber].mtime = 0;
                    inodes[inodeNumber].ctime = 0;                 
                }

                for(int i=0; i < N_DIRECT; i++)
                    inodes[inodeNumber].d[i] = BlockNullReference;
                    
                for(int i=0; i < N_INDIRECT; i++)
                    inodes[inodeNumber].i1[i] = BlockNullReference;
                    
                for(int i=0; i < N_DOUBLE_INDIRECT; i++)
                    inodes[inodeNumber].i2[i] = BlockNullReference;
                
                
                if(blockNumber == 1 && inodeNumber == 0)
                    inodes[inodeNumber].d[0] = 0;
            } 
            soWriteRawBlock(blockNumber, &inodes);
        }
    }
};

