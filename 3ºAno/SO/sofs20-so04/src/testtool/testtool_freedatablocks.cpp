#include "testtool.h"

#include "freedatablocks.h"

#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

using namespace sofs20;

/* ******************************************** */

static FILE *fin = stdin;

/* ******************************************** */
/* alloc data block */
void allocDataBlock(void)
{
    /* call function */
    uint32_t bn = soAllocDataBlock();

    /* print result */
    resultMsg("Data block number %u allocated\n", bn);
}

/* ******************************************** */
/* free file block */
void freeDataBlock(void)
{
    /* ask for block number */
    promptMsg("Data block number: ");
    uint32_t bn;
    fscanf(fin, "%u", &bn);
    fPurge(fin);

    /* call function */
    soFreeDataBlock(bn);

    /* print result */
    resultMsg("Data block number %u freed\n", bn);
}

/* ******************************************** */
/* deplete insertion cache */
void depleteInsertionCache(void)
{
    /* call function */
    soDepleteInsertionCache();

    /* print result */
    resultMsg("deplete of tail cache done\n");
}

/* ******************************************** */
/* replenish retrieval cache */
void replenishRetrievalCache(void)
{
    /* call function */
    soReplenishRetrievalCache();

    /* print result */
    resultMsg("replenish of Retrieval cache done\n");
}

/* ******************************************** */
