#include "direntries.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"
#include "fileblocks.h"
#include "direntries.h"
#include "bin_direntries.h"

#include <errno.h>
#include <string.h>
#include <libgen.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>

namespace sofs20
{
    uint16_t grpTraversePath(char *path)
    {
        soProbe(221, "%s(%s)\n", __FUNCTION__, path);

        /* replace the following line with your code */
        //return binTraversePath(path);

        char path_copy[strlen(path)+1];
        strcpy(path_copy, path);


        //check if path is root
        if(strcmp(path, (char*) "/")==0) {
            return 0;
        }

        // get parent inode //
        //parent path inode
        uint16_t parenteInode = soTraversePath(dirname(path_copy));
        
        if(parenteInode == InodeNullReference){
            throw SOException(ENOTDIR, __FUNCTION__);
        }

        // get child node
        int pih = soOpenInode(parenteInode);
        uint16_t childInode = soGetDirentry(pih, basename(path));
        soCloseInode(pih);

        if(childInode == InodeNullReference){
            throw SOException(ENOTDIR, __FUNCTION__);
        }

        int cih = soOpenInode(childInode);
        SOInode* cin = soGetInodePointer(cih);

        // CHECK IF DIR
        if (!S_ISDIR(cin->mode)) {
            soCloseInode(cih);
            throw SOException(ENOTDIR, __FUNCTION__);
        }

        //check permisions
        if ((cin->owner == getuid() || cin->group == getgid()) && soCheckInodeAccess(cih, X_OK)) {
            soCloseInode(cih);
            return childInode;
        } else {
            soCloseInode(cih);
            throw SOException(EACCES, __FUNCTION__);
        }
    }
};

