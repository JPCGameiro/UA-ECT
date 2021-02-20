#include "direntries.h"
#include "bin_direntries.h"
#include "grp_direntries.h"

#include "core.h"
#include "devtools.h"

#include <errno.h>
#include <string.h>
#include <sys/stat.h>

namespace sofs20
{

    uint16_t soDeleteDirentry(int pih, const char *name)
    {
        if (soBinSelected(203))
            return binDeleteDirentry(pih, name);
        else
            return grpDeleteDirentry(pih, name);
    }

};
