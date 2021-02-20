#include "daal.h"

#include "core.h"
#include "devtools.h"

#include <inttypes.h>

namespace sofs20
{
    /* ************************************** */

    void soOpenInodeTable()
    {
        binOpenInodeTable();
    }

    /* ***************************************** */

    void soCloseInodeTable()
    {
        binCloseInodeTable();
    }

    /* ************************************** */

    int soOpenInode(uint16_t in)
    {
        return binOpenInode(in);
    }

    /* ************************************** */

    void soSaveInode(int ih)
    {
        binSaveInode(ih);
    }

    /* ************************************** */

    void soCloseInode(int ih)
    {
        binCloseInode(ih);
    }

    /* ************************************** */

    SOInode *soGetInodePointer(int ih)
    {
        return binGetInodePointer(ih);
    }

    /* ************************************** */

    uint16_t soGetInodeNumber(int ih)
    {
        return binGetInodeNumber(ih);
    }

    /* ************************************** */

};
