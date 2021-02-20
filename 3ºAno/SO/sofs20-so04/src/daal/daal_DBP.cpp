#include "daal.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"

#include <inttypes.h>

namespace sofs20
{
    /* ***************************************** */

    void soOpenDataBlockPool()
    {
        binOpenDataBlockPool();
    }

    /* ***************************************** */

    void soCloseDataBlockPool()
    {
        binCloseDataBlockPool();
    }

    /* ***************************************** */

    void soReadDataBlock(uint32_t bn, void *buf)
    {
        binReadDataBlock(bn, buf);
    }

    /* ***************************************** */

    void soWriteDataBlock(uint32_t bn, void *buf)
    {
        binWriteDataBlock(bn, buf);
    }

    /* ***************************************** */
};
