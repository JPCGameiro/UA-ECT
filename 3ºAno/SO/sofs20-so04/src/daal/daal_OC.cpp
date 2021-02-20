#include "daal.h"

#include "rawdisk.h"
#include "core.h"
#include "devtools.h"

#include <iostream>

namespace sofs20
{

    void soOpenDisk(const char *devname)
    {
        soProbe(SOPROBE_GREEN, 591, "%s(\"%s\")\n", __FUNCTION__, devname);

        soOpenRawDisk(devname);
        soOpenSuperblock();
        soOpenInodeTable();
        soOpenDataBlockPool();
        soOpenReferenceTable();

        SOSuperblock* sbp = soGetSuperblockPointer();
        if (sbp->mntstat < 0)
        {
            soProbe(SOPROBE_RED, 501, "  Not correctly unmounted\n");
            sbp->mntstat = -sbp->mntstat;
        }
        if (sbp->mntstat > 100)
        {
            soProbe(SOPROBE_RED, 501, "  Maximum mounts reached\n");
            sbp->mntstat = 0;
        }
        sbp->mntstat += -(sbp->mntstat + 1);

        soSaveSuperblock();
    }

    void soCloseDisk()
    {
        soProbe(SOPROBE_GREEN, 592, "%s()\n", __FUNCTION__);

        SOSuperblock* sbp = soGetSuperblockPointer();

        sbp->mntstat = - sbp->mntstat;
        soSaveSuperblock();

        soCloseReferenceTable();
        soCloseDataBlockPool();
        soCloseInodeTable();
        soCloseSuperblock();
        soCloseRawDisk();
    }
};
