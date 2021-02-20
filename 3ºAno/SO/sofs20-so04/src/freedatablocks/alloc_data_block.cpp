/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freedatablocks.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    uint32_t soAllocDataBlock()
    {
        if (soBinSelected(441))
            return binAllocDataBlock();
        else
            return grpAllocDataBlock();
    }

};
