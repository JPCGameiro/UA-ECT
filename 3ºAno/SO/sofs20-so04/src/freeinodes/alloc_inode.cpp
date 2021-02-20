/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freeinodes.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    uint16_t soAllocInode(uint32_t mode)
    {
        if (soBinSelected(401))
            return binAllocInode(mode);
        else
            return grpAllocInode(mode);
    }

};
