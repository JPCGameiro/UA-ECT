/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freedatablocks.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    void soDepleteInsertionCache(void)
    {
        if (soBinSelected(444))
            binDepleteInsertionCache();
        else
            grpDepleteInsertionCache();
    }

};
