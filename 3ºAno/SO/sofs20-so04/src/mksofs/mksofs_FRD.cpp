/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "mksofs.h"

#include "core.h"
#include "devtools.h"

#include <inttypes.h>

namespace sofs20
{
    /* see mksofs.h for a description */
    void soFillRootDir(uint32_t itotal)
    {
        if (soBinSelected(606))
            binFillRootDir(itotal);
        else
            grpFillRootDir(itotal);
    }

};
