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
    void soFillSuperblock(const char *name, uint32_t ntotal, uint32_t itotal, uint32_t dbtotal)
    {
        if (soBinSelected(602))
            binFillSuperblock(name, ntotal, itotal, dbtotal);
        else
            grpFillSuperblock(name, ntotal, itotal, dbtotal);
    }

};
