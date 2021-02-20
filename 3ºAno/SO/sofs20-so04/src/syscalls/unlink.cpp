/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soUnlink(const char *path)
    {
        if (soBinSelected(105))
            return binUnlink(path);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binUnlink(path);
    }

};
