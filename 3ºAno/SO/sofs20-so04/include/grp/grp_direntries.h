/**
 *  \file 
 *  \brief Student version of the functions to manage directory entries
 *
 *  \author Artur Pereira 2008-2009, 2016-2020
 *
 *  \remarks See the main \c direntries header file for documentation
 */

#ifndef __SOFS20_DIRENTRIES_GROUP__
#define __SOFS20_DIRENTRIES_GROUP__

#include <inttypes.h>

namespace sofs20
{
    uint16_t grpTraversePath(char *path);

    uint16_t grpGetDirentry(int pih, const char *name);

    void grpAddDirentry(int pih, const char *name, uint16_t cin);

    uint16_t grpDeleteDirentry(int pih, const char *name);

    void grpRenameDirentry(int pih, const char *name, const char *newName);

    bool grpCheckDirEmpty(int ih);
};

#endif /* __SOFS20_DIRENTRIES_GROUP__ */
