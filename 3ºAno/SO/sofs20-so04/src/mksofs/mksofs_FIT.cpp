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
    void soFillInodeTable(uint32_t itotal, bool date)
    {
        if (soBinSelected(604))
            binFillInodeTable(itotal, date);
        else
            grpFillInodeTable(itotal, date);
    }

};
