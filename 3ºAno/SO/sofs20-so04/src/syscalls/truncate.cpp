/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"
#include "core.h"
#include "devtools.h"

namespace sofs20
{

    int soTruncate(const char *path, off_t length)
    {
        if (soBinSelected(110))
            return binTruncate(path, length);
        else
            /* replace prefix bin with grp if you implement this syscall */
            return binTruncate(path, length);
    }

};
