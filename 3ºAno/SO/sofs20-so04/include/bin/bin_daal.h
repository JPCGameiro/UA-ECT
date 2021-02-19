/**
 * \file
 * \brief Binary version of the disk abstraction layer module
 */

#ifndef __SOFS20_BIN_DAAL__
#define __SOFS20_BIN_DAAL__

#include <inttypes.h>

#include "superblock.h"
#include "inode.h"

namespace sofs20
{
    /* ***************************************** */

    void binOpenSuperblock();

    void binCloseSuperblock();

    void binSaveSuperblock();

    SOSuperblock *binGetSuperblockPointer();

    /* ***************************************** */

    void binOpenInodeTable();

    void binCloseInodeTable();

    int binOpenInode(uint16_t in);

    SOInode *binGetInodePointer(int ih);

    void binSaveInode(int ih);

    void binCloseInode(int ih);

    uint16_t binGetInodeNumber(int ih);

    /* ***************************************** */

    bool binCheckInodeAccess(int ih, int access);

    /* ***************************************** */

    void binOpenDataBlockPool();

    void binCloseDataBlockPool();

    void binReadDataBlock(uint32_t bn, void *buf);

    void binWriteDataBlock(uint32_t bn, void *buf);

    /* ***************************************** */

    void binOpenReferenceTable();

    void binCloseReferenceTable();

    uint32_t* binGetReferenceBlockPointer(uint32_t rbn);

    void binSaveReferenceBlock();
};


#endif /* __SOFS20_BIN_DAAL__ */
