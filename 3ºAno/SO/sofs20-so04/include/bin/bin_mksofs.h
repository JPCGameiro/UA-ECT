/**
 *  \file
 *  \brief Binary version of the \b sofs20 formatting functions.
 *
 *  \author Artur Pereira - 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#ifndef __SOFS20_MKSOFS_BIN__
#define __SOFS20_MKSOFS_BIN__

#include <inttypes.h>

namespace sofs20
{
    void binComputeStructure(uint32_t ntotal, uint32_t & itotal, uint32_t & bref);

    void binFillSuperblock(const char *name, uint32_t ntotal, uint32_t itotal, uint32_t bref);

    void binFillInodeTable(uint32_t itotal, bool date = true);

    void binFillRootDir(uint32_t itotal);

    void binFillReferenceTable(uint32_t ntotal, uint32_t itotal, uint32_t bref);

    void binResetFreeDataBlocks(uint32_t ntotal, uint32_t itotal, uint32_t bref);
};

#endif /* __SOFS20_MKSOFS_BIN__ */
