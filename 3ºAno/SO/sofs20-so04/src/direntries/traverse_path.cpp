#include "direntries.h"
#include "bin_direntries.h"
#include "grp_direntries.h"

#include "core.h"
#include "devtools.h"

#include <errno.h>
#include <string.h>
#include <libgen.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>

namespace sofs20
{

    uint16_t soTraversePath(char *path)
    {
        if (soBinSelected(221))
            return binTraversePath(path);
        else
            return grpTraversePath(path);
    }

};
