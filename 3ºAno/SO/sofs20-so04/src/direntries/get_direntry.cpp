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

    uint16_t soGetDirentry(int pih, const char *name)
    {
        if (soBinSelected(201))
            return binGetDirentry(pih, name);
        else
            return grpGetDirentry(pih, name);
    }

};
