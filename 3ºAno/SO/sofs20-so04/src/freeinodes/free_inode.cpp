/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freeinodes.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    void soFreeInode(uint16_t in)
    {
        if (soBinSelected(402))
            binFreeInode(in);
        else
            grpFreeInode(in);
    }

};
