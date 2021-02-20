/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soRead(const char *path, void *buf, uint32_t count, int32_t pos)
    {
        if (soBinSelected(108))
            return binRead(path, buf, count, pos);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binRead(path, buf, count, pos);
    }

};
