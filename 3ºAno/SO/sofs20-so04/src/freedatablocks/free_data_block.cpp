/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freedatablocks.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    void soFreeDataBlock(uint32_t bn)
    {
        if (soBinSelected(442))
            binFreeDataBlock(bn);
        else
            grpFreeDataBlock(bn);
    }

};
