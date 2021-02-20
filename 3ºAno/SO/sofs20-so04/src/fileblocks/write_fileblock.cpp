#include "fileblocks.h"
#include "bin_fileblocks.h"
#include "grp_fileblocks.h"

#include "core.h"
#include "devtools.h"

#include <string.h>
#include <inttypes.h>

namespace sofs20
{

    void soWriteFileBlock(int ih, uint32_t fbn, void *buf)
    {
        if (soBinSelected(332))
            binWriteFileBlock(ih, fbn, buf);
        else
            grpWriteFileBlock(ih, fbn, buf);
    }

};
