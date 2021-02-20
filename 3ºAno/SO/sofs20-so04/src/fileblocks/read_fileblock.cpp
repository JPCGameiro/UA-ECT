#include "fileblocks.h"
#include "bin_fileblocks.h"
#include "grp_fileblocks.h"

#include "core.h"
#include "devtools.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{

    void soReadFileBlock(int ih, uint32_t fbn, void *buf)
    {
        if (soBinSelected(331))
            binReadFileBlock(ih, fbn, buf);
        else
            grpReadFileBlock(ih, fbn, buf);
    }

};
