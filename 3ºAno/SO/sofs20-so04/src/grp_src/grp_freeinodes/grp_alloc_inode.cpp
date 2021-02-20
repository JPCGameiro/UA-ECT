/*
 *  \author António Rui Borges - 2012-2015
 *  \authur Artur Pereira - 2016-2020
 */

#include "freeinodes.h"

#include <stdio.h>
#include <errno.h>
#include <inttypes.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <math.h>

#include <iostream>


#include "core.h"
#include "devtools.h"
#include "daal.h"

namespace sofs20
{
    uint16_t grpAllocInode(uint32_t mode)
    {
        soProbe(401, "%s(0x%x)\n", __FUNCTION__, mode);

        /* replace the following line with your code */
        //return binAllocInode(mode);

        //Verificar se o tipo é válido
        if(!(S_ISDIR(mode) || S_ISREG(mode) || S_ISLNK(mode))){
            throw SOException(EINVAL,__FUNCTION__);
        }

        SOSuperblock *spb = soGetSuperblockPointer();
        //Verificar se existem inodes livres
        if(spb->ifree == 0){
            throw SOException(ENOSPC,__FUNCTION__);
        }
        
        //libertar o espaço em memoria
        spb->ifree--;

        //O inode a abrir é o primeiro que estiver a 0 a seguir ao iidx (no bitmap)

        
        uint16_t in = spb->iidx+1;
        while(true){
            //Posição do bitmap a aceder
            uint16_t pos = (uint16_t)floor(in / 32);
            //Bit da posição a aceder
            uint32_t bit = 0x00000001 << (in % 32);

            //Primeiro bit a zero encontrado
            if((spb->ibitmap[pos] & bit) == 0x00000000){
                //atualizar valor no bitmap
                spb->ibitmap[pos] = spb->ibitmap[pos] | bit;
                spb->iidx = in;
                break;
            }
            else
                in++;
        } 

        uint32_t inode_handler = soOpenInode(in);
        //inicializar o inode
        SOInode* pih = soGetInodePointer(inode_handler);
        
        //set up inode
        pih->mode = mode;
        pih->lnkcnt = 0;
        pih->owner = getuid();
        pih->group = getgid();
        pih->size = 0;
        pih->blkcnt = 0;
        pih->ctime = time(NULL);
        pih->mtime = time(NULL);
        pih->atime = time(NULL);
        int i;
        for(i = 0; i < N_DIRECT; i++){
            pih->d[i] = BlockNullReference; //put all direct references to the data array pointing null
        }
        for(i = 0; i < N_INDIRECT; i++){
            pih->i1[i] = BlockNullReference; //put all indirect references to the data array pointing null
        }
        for(i = 0;i < N_DOUBLE_INDIRECT;i++){
            pih->i2[i] = BlockNullReference;  //put double-indirect references to the data array pointing null
        } 


        //save all changes
        soSaveInode(inode_handler);
        soCloseInode(inode_handler);
        soSaveSuperblock();
        
        //return número do inode alocado
        return in;
    }
};
