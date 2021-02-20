#include "fileblocks.h"
#include "bin_fileblocks.h"
#include "grp_fileblocks.h"

#include "core.h"
#include "devtools.h"

#include <errno.h>

#include <iostream>

namespace sofs20
{
    uint32_t soAllocFileBlock(int ih, uint32_t fbn)
    {
        if (soBinSelected(302))
            return binAllocFileBlock(ih, fbn);
        else
            return grpAllocFileBlock(ih, fbn);
    }

};
