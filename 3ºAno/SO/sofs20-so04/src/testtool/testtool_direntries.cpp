#include "testtool.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"
#include "direntries.h"

#include <string.h>

using namespace sofs20;

/* ******************************************** */

static FILE *fin = stdin;

/* ******************************************** */
/* check directory emptiness */
void checkDirectoryEmptiness()
{
    /* ask for inode number */
    promptMsg("Parent inode number: ");
    uint16_t in;
    fscanf(fin, "%hu", &in);
    fPurge(fin);

    /* open parent inode */
    int ih = soOpenInode(in);

    /* call function */
    bool is_empty = soCheckDirEmpty(ih);

    /* print result */
    if (is_empty)
        resultMsg("Directory is empty\n");
    else
        resultMsg("Directory is NOT empty\n");
}

/* ******************************************** */
/* get direntry */
void getDirentry()
{
    /* ask for inode number */
    promptMsg("Parent inode number: ");
    uint16_t pin;
    fscanf(fin, "%hu", &pin);
    fPurge(fin);

    /* ask for direntry name */
    promptMsg("Direntry name: ");
    char name[100];
    name[0] = '\0';
    fscanf(fin, "%[^\n]", name);
    fPurge(fin);

    /* open parent inode */
    int pih = soOpenInode(pin);

    /* call function */
    uint16_t cin = soGetDirentry(pih, name);

    /* close inode */
    soCloseInode(pih);

    /* print result */
    if (cin == InodeNullReference)
        resultMsg("Entry \"%s\" does not exist\n", name);
    else
        resultMsg("Child inode number = %u\n", cin);
}

/* ******************************************** */
/* add direntry */
void addDirentry()
{
    /* ask for parent inode number */
    promptMsg("Parent inode number: ");
    uint16_t pin;
    fscanf(fin, "%hu", &pin);
    fPurge(fin);

    /* ask for direntry name */
    promptMsg("Direntry name: ");
    char name[100];
    name[0] = '\0';
    fscanf(fin, "%[^\n]", name);
    fPurge(fin);

    /* ask for child inode number */
    promptMsg("Child inode number: ");
    uint16_t cin;
    fscanf(fin, "%hu", &cin);
    fPurge(fin);

    /* open parent and child inodes */
    int pih = soOpenInode(pin);

    /* call function */
    soAddDirentry(pih, name, cin);

    /* close inodes */
    soSaveInode(pih);
    soCloseInode(pih);

    /* print result */
    resultMsg("Direntry added.\n");
}

/* ******************************************** */
/* rename direntry */
void renameDirentry()
{
    /* ask for parent inode number */
    promptMsg("Parent inode number: ");
    uint16_t pin;
    fscanf(fin, "%hu", &pin);
    fPurge(fin);

    /* ask for direntry name */
    promptMsg("Direntry name: ");
    char name[100];
    name[0] = '\0';
    fscanf(fin, "%[^\n]", name);
    fPurge(fin);

    /* ask for new direntry name */
    promptMsg("New direntry name: ");
    char newname[100];
    newname[0] = '\0';
    fscanf(fin, "%[^\n]", newname);
    fPurge(fin);

    /* open parent inode */
    int pih = soOpenInode(pin);

    /* call function */
    soRenameDirentry(pih, name, newname);

    /* close inode */
    soSaveInode(pih);
    soCloseInode(pih);

    /* print result */
    resultMsg("Direntry renamed.\n");
}

/* ******************************************** */
/* delete direntry */
void deleteDirentry()
{
    /* ask for inode number */
    promptMsg("Parent inode number: ");
    uint16_t pin;
    fscanf(fin, "%hu", &pin);
    fPurge(fin);

    /* ask for direntry name */
    promptMsg("Direntry name: ");
    char name[100];
    name[0] = '\0';
    fscanf(fin, "%[^\n]", name);
    fPurge(fin);

#if 0
    /* ask for clean or non clean deletion */
    promptMsg("Clean entry (y/N): ");
    char answer[100];
    answer[0] = '\0';
    fscanf(fin, "%[^\n]", answer);
    bool clean = (strcasecmp(answer, "y") == 0) || (strcasecmp(answer, "yes") == 0);

    fPurge(fin);
#endif

    /* open parent inode */
    int pih = soOpenInode(pin);

    /* call function */
    uint16_t cin = soDeleteDirentry(pih, name);

    /* close inode */
    soSaveInode(pih);
    soCloseInode(pih);

    /* print result */
    resultMsg("Child inode number = %u\n", cin);
}

/* ******************************************** */
/* traverse path */
void traversePath()
{
    /* ask for PATH */
    promptMsg("path: ");
    char path[500];
    path[0] = '\0';
    fscanf(fin, "%[^\n]", path);
    fPurge(fin);

    /* call function */
    uint16_t in = soTraversePath(path);

    /* print result */
    resultMsg("inode number = %u\n", in);
}
