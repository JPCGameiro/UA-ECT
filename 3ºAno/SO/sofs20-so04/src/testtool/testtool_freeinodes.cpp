#include "testtool.h"

#include "freeinodes.h"

#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

using namespace sofs20;

/* ******************************************** */

static FILE *fin = stdin;

/* ******************************************** */
/* alloc inode */
static uint32_t iType[] = { S_IFREG, S_IFDIR, S_IFLNK };

static const char *msgType[] = { "file", "dir", "symlink" };

void allocInode(void)
{
    /* ask for type */
    promptMsg("Inode type (1 - file, 2 - dir, 3 - symlink): ");
    int t;
    fscanf(fin, "%d", &t);
    fPurge(fin);
    if (t < 1 || t > 3)
    {
        errorMsg("Wrong type: %d", t);
        return;
    }
    uint32_t type = iType[t - 1];

    /* ask for permissions */
    promptMsg("permissions in octal: ");
    uint32_t perm;
    fscanf(fin, "%o", &perm);
    fPurge(fin);
    if ((perm & ~0777) != 0)
    {
        errorMsg("Wrong permissions: %03o", perm);
        return;
    }

    /* call function */
    uint16_t in = soAllocInode(type | perm);

    /* print result */
    resultMsg("Inode number %u allocated (%s)\n", in, msgType[t - 1]);
}

/* ******************************************** */
/* free inode */
void freeInode(void)
{
    /* ask for inode number */
    promptMsg("Inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* call function */
    soFreeInode(in);
    /* print result */
    resultMsg("Inode number %u freed\n", in);
}

/* ******************************************** */
