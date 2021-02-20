/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soMkdir(const char *path, mode_t mode)
    {
        if (soBinSelected(102))
            return binMkdir(path, mode);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binMkdir(path, mode);
    }

};
