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
    uint16_t grpDeleteDirentry(int pih, const char *name)
    {
        soProbe(203, "%s(%d, %s)\n", __FUNCTION__, pih, name);

        /* replace the following line with your code */
        //return binDeleteDirentry(pih, name);

        //Get the pointer to the parent inode
        SOInode* pi = soGetInodePointer(pih);

        //Number of blocks used by the inode
        uint32_t iBlocks = pi->size/BlockSize;
        //Buffer to store direntries of a block
        SODirentry buf[DPB];
        //Buffer to store the direntries of last block with direntries
        SODirentry lastBlock[DPB];
        //Child inode number (will be returned)
        uint16_t child = 0;
        //Used to store the last direntry
        uint32_t lastDir = 0;

        //Check if last block was updated or not
        int check = 0;
        int entryFound = 0;


        //ReadFileBlock value of last block
        soReadFileBlock(pih, iBlocks, lastBlock);
        
        //Finding the last direntry
        for(uint32_t i=0; i < DPB; i++){
            if(strcmp(lastBlock[i].name, "") == 0){
                i == 0 ? lastDir = DPB-1 : lastDir = i-1;
                break;
            }
        }
        if(lastDir == DPB-1){
            iBlocks--;
            soReadFileBlock(pih, iBlocks, lastBlock);
        }


        for(uint32_t i = 0; i <= iBlocks; i++){
            //Read File block content to buf
            soReadFileBlock(pih, i, buf);

            for(uint32_t j = 0; j < DPB; j++){
                //Direntry 'name' found
                if(strcmp(buf[j].name, name) == 0){
                    entryFound = 1;
                    child = buf[j].in;

                    //If position was the last of the last block
                    if(strcmp(buf[j].name, lastBlock[lastDir].name) == 0){
                        strcpy(buf[j].name, "");
                        buf[j].in = 0;
                        
                        //If block became empty
                        if(j==0){
                            soWriteFileBlock(pih,i,buf);
                            check = 1;
                            soFreeFileBlocks(pih, i);
                            break;
                        }
                    }
                    //If position was not the last of them all
                    else{
                        strcpy(buf[j].name, lastBlock[lastDir].name);
                        buf[j].in = lastBlock[lastDir].in;

                        //If position was in the last block an update must be done on the lastBlock buffer
                        if(i == iBlocks){
                            strcpy(lastBlock[j].name, buf[j].name);
                            lastBlock[j].in = buf[j].in;
                        }
                    }
                    soWriteFileBlock(pih,i,buf);

                    break;
                }
            }
        }

        //Update the last block if necessary (using lastBlock buffer)
        if(check == 0 && entryFound == 1){
            lastBlock[lastDir].in = 0;
            strcpy(lastBlock[lastDir].name, "");
            soWriteFileBlock(pih, iBlocks, lastBlock);
            //If lastBlock is empty must be freed
            if(lastDir == 0)
                soFreeFileBlocks(pih, iBlocks);
            check = 1;
        }

        //Size of inode is only updated if an entry was found
        if(entryFound == 1)
            pi->size -= sizeof(SODirentry);


        soSaveInode(pih);        
        return child; 
    }
};
