/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soReaddir(const char *path, void *buf, int32_t pos)
    {
        if (soBinSelected(111))
            return binReaddir(path, buf, pos);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binReaddir(path, buf, pos);
    }

};
