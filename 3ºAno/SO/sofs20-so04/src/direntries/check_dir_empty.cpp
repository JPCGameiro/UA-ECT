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

    bool soCheckDirEmpty(int ih)
    {
        if (soBinSelected(205))
            return binCheckDirEmpty(ih);
        else
            return grpCheckDirEmpty(ih);
    }

};
