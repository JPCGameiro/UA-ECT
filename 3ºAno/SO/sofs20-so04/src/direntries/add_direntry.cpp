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

    void soAddDirentry(int pih, const char *name, uint16_t cin)
    {
        if (soBinSelected(202))
            return binAddDirentry(pih, name, cin);
        else
            return grpAddDirentry(pih, name, cin);
    }

};
