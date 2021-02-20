/* ******************************************** */
#include "testtool.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"

#include <string.h>

using namespace sofs20;

/* ******************************************** */

static FILE *fin = stdin;

/* ******************************************** */

/* get inode permissions */
void setInodeSize()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* ask for new size */
    promptMsg("size in bytes: ");
    uint32_t size;
    fscanf(fin, "%u", &size);
    fPurge(fin);

    /* call function */
    int ih = soOpenInode(in);
    SOInode *ip = soGetInodePointer(ih);
    ip->size = size;

    /* print result */
    printf("Size of inode #%u set to %u\n", in, size);

    /* save and close inode */
    soSaveInode(ih);
    soCloseInode(ih);
}

/* ******************************************** */

/* get inode permissions */
void setInodeAccess()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

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
    int ih = soOpenInode(in);
    SOInode *ip = soGetInodePointer(ih);
    ip->mode &= ~0777;
    ip->mode |= perm;

    /* print result */
    //printInode(ip, in);

    /* save and close inode */
    soSaveInode(ih);
    soCloseInode(ih);
}

/* ******************************************** */
/* change the owner and group of an inode */
void changeInodeOwnership()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* ask for owner id */
    promptMsg("owner id: ");
    uint32_t own_id;
    fscanf(fin, "%u", &own_id);
    fPurge(fin);

    /* ask for group id */
    promptMsg("group id: ");
    uint32_t grp_id;
    fscanf(fin, "%u", &grp_id);
    fPurge(fin);

    /* change ownership */
    int ih = soOpenInode(in);
    SOInode *ip = soGetInodePointer(ih);
    ip->owner = own_id;
    ip->group = grp_id;
    soSaveInode(ih);
    soCloseInode(ih);
}

/* ******************************************** */
/* check inode permissions */
void checkInodeAccess()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* ask for access mode */
    promptMsg("requested access in octal: ");
    uint16_t access;
    fscanf(fin, "%ho", &access);
    fPurge(fin);

    /* call function */
    int ih = soOpenInode(in);
    bool granted = soCheckInodeAccess(ih, access);
    soCloseInode(ih);

    /* print result */
    resultMsg("access %0s\n", granted ? "granted" : "denied");
}

/* ******************************************** */
/* dec inode refcount */
void decInodeLnkcnt()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* perform operation */
    int ih = soOpenInode(in);
    SOInode *ip = soGetInodePointer(ih);
    uint32_t rc = --ip->lnkcnt;
    soSaveInode(ih);
    soCloseInode(ih);

    /* print result */
    resultMsg("link count = %hu\n", rc);
}

/* ******************************************** */
/* inc inode refcount */
void incInodeLnkcnt()
{
    /* ask for inode number */
    promptMsg("inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* perform operation */
    int ih = soOpenInode(in);
    SOInode *ip = soGetInodePointer(ih);
    uint32_t rc = ++ip->lnkcnt;
    soSaveInode(ih);
    soCloseInode(ih);

    /* print result */
    resultMsg("link count = %hu\n", rc);
}
