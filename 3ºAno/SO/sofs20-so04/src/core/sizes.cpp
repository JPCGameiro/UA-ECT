#include <iostream>

#include "core.h"
#include "devtools.h"

using namespace std;
using namespace sofs20;

int main(void)
{
    cout <<
        "BlockSize: " << BlockSize << endl <<
        "sizeof(SOSuperblock): " << sizeof(SOSuperblock) << endl <<
        "sizeof(SOInode): " << sizeof(SOInode) << endl <<
        "sizeof(SODirentry): " << sizeof(SODirentry) << endl <<
        "IPB (inodes per block): " << IPB << endl <<
        "DPB (direntries per block): " << DPB << endl <<
        "RPB references per block): " << RPB << endl;

    return 0;
}
