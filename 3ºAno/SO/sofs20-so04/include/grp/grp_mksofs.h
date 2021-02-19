/**
 *  \file
 *  \brief Student version of the \b sofs20 formatting functions.
 *
 *  \author Artur Pereira - 2019-2020
 *
 *  \remarks See the main \c mksofs header file for documentation
 */

#ifndef __SOFS20_MKSOFS_GROUP__
#define __SOFS20_MKSOFS_GROUP__

#include <inttypes.h>

namespace sofs20
{
    void grpComputeStructure(uint32_t ntotal, uint32_t & itotal, uint32_t & dbtotal);

    void grpFillSuperblock(const char *name, uint32_t ntotal, uint32_t itotal, uint32_t dbtotal);

    void grpFillInodeTable(uint32_t itotal, bool date = true);

    void grpFillRootDir(uint32_t itotal);

    void grpFillReferenceTable(uint32_t ntotal, uint32_t itotal, uint32_t dbtotal);

    void grpResetFreeDataBlocks(uint32_t ntotal, uint32_t itotal, uint32_t dbtotal);
};

#endif /* __SOFS20_MKSOFS_GROUP__ */
