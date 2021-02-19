/**
 *  \file 
 *  \brief Binary version of functions to manage directories and directory entries
 *
 *  \author Artur Pereira 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2012-2015
 *
 */

#ifndef __SOFS20_DIRENTRIES_BIN__
#define __SOFS20_DIRENTRIES_BIN__

#include <inttypes.h>

namespace sofs20
{
    uint16_t binTraversePath(char *path);

    uint16_t binGetDirentry(int pih, const char *name);

    void binAddDirentry(int pih, const char *name, uint16_t cin);

    uint16_t binDeleteDirentry(int pih, const char *name);

    void binRenameDirentry(int pih, const char *name, const char *newName);

    bool binCheckDirEmpty(int ih);
};

#endif /* __SOFS20_DIRENTRIES_BIN__ */
