/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soRename(const char *path, const char *newPath)
    {
        if (soBinSelected(107))
            return binRename(path, newPath);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binRename(path, newPath);
    }

};
