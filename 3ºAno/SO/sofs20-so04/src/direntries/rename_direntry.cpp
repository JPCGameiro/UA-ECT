#include "direntries.h"
#include "bin_direntries.h"
#include "grp_direntries.h"

#include "core.h"
#include "devtools.h"

#include <string.h>
#include <errno.h>
#include <sys/stat.h>

namespace sofs20
{

    void soRenameDirentry(int pih, const char *name, const char *newName)
    {
        if (soBinSelected(204))
            return binRenameDirentry(pih, name, newName);
        else
            return grpRenameDirentry(pih, name, newName);
    }

};
