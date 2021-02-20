/*
 *  \authur Artur Pereira - 2009-2020
 */

#include "freedatablocks.h"

#include "core.h"
#include "devtools.h"

namespace sofs20
{

    void soReplenishRetrievalCache(void)
    {
        if (soBinSelected(443))
            binReplenishRetrievalCache();
        else
            grpReplenishRetrievalCache();
    }

};
