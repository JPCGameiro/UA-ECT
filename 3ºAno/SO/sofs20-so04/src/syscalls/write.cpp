/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{
    int soWrite(const char *path, void *buf, uint32_t count, int32_t pos)
    {
        if (soBinSelected(109))
            return binWrite(path, buf, count, pos);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binWrite(path, buf, count, pos);
    }

};
