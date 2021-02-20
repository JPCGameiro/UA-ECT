/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soLink(const char *path, const char *newPath)
    {
        if (soBinSelected(104))
            return binLink(path, newPath);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binLink(path, newPath);
    }

};
