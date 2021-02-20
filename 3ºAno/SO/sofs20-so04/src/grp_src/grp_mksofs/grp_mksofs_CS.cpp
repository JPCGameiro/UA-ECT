#include "grp_mksofs.h"

#include "core.h"
#include "devtools.h"
#include "bin_mksofs.h"

#include <inttypes.h>
#include <iostream>
#include <math.h>

namespace sofs20
{
    uint32_t roundUp(int n, int mul)
    {
    if(mul == 0) 
        return n;
    int rem = n % mul;
    if(rem == 0) 
        return n;
    return n + mul - rem;
    }

    void grpComputeStructure(uint32_t ntotal, uint32_t & itotal, uint32_t & dbtotal)
    {
        soProbe(601, "%s(%u, %u, ...)\n", __FUNCTION__, ntotal, itotal);

        /* replace the following line with your code */ 
        
        uint32_t maxitotal = ceil(ntotal/8);
        uint32_t minitotal = IPB;
        

        if(itotal<minitotal || itotal > maxitotal || itotal == 0) 
            itotal = ntotal/16;

        if( itotal % IPB != 0)
            itotal = roundUp(itotal,IPB);  
        
        if( itotal % 32 != 0)
            itotal = roundUp(itotal,32);

        uint32_t nrefblocks = 0;
        
        dbtotal = ntotal - 2 - itotal/IPB;
        
        if(dbtotal > REF_CACHE_SIZE){
            nrefblocks = (ntotal- 2 - (itotal/IPB))/RPB;

            dbtotal -= nrefblocks;

            uint32_t nblocksrem = ntotal - 2 - nrefblocks - itotal/IPB - dbtotal;
            if(nblocksrem == 1)
                itotal += IPB;
        }
        else dbtotal++;


        //binComputeStructure(ntotal, itotal, dbtotal);
    }
};

