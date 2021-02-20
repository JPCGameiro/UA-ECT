/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soRmdir(const char *path)
    {
        if (soBinSelected(106))
            return binRmdir(path);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binRmdir(path);
    }

};
