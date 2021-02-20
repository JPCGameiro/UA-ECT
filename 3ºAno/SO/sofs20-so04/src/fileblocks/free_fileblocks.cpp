#include "fileblocks.h"
#include "bin_fileblocks.h"
#include "grp_fileblocks.h"

#include "core.h"
#include "devtools.h"

#include <inttypes.h>
#include <errno.h>
#include <assert.h>

namespace sofs20
{

    void soFreeFileBlocks(int ih, uint32_t ffbn)
    {
        if (soBinSelected(303))
            binFreeFileBlocks(ih, ffbn);
        else
            grpFreeFileBlocks(ih, ffbn);
    }

};
