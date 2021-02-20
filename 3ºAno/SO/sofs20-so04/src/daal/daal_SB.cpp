#include "daal.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{
    /* ***************************************** */

    void soOpenSuperblock()
    {
        binOpenSuperblock();
    }

    /* ***************************************** */

    void soSaveSuperblock()
    {
        binSaveSuperblock();
    }

    /* ***************************************** */

    void soCloseSuperblock()
    {
        binCloseSuperblock();
    }

    /* ***************************************** */

    SOSuperblock *soGetSuperblockPointer()
    {
        return binGetSuperblockPointer();
    }

    /* ***************************************** */
};
