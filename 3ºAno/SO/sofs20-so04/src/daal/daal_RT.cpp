#include "daal.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{
    /* ***************************************** */

    void soOpenReferenceTable()
    {
        binOpenReferenceTable();
    }

    /* ***************************************** */

    void soCloseReferenceTable()
    {
        binCloseReferenceTable();
    }

    /* ***************************************** */

    void soSaveReferenceBlock()
    {
        binSaveReferenceBlock();
    }

    /* ***************************************** */

    uint32_t *soGetReferenceBlockPointer(uint32_t rbn)
    {
        return binGetReferenceBlockPointer(rbn);
    }

    /* ***************************************** */
};
