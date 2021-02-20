#include "daal.h"

namespace sofs20
{
    bool soCheckInodeAccess(int ih, int access)
    {
        return binCheckInodeAccess(ih, access);
    }

};
