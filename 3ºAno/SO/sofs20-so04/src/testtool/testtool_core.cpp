#include "testtool.h"

#include "core.h"
#include "devtools.h"
#include "daal.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

using namespace sofs20;

/* ******************************************** */

const char *devname = NULL;

char *progDir = NULL;           /* this program's directory */

static FILE *fin = stdin;

/* ******************************************** */
/* still not implemented */
void notImplemented(void)
{
    fprintf(stderr, "\e[02;41m==>\e[0m ");
    fprintf(stderr, "Option still not implemented.\n");
}

/* ******************************************** */
/* set bin IDs */
void setBinIDs()
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    soBinSetIDs(n1, n2);
}

/* ******************************************** */
/* add bin IDs */
void addBinIDs()
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    soBinAddIDs(n1, n2);
}

/* ******************************************** */
/* remove bin IDs */
void removeBinIDs()
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    soBinRemoveIDs(n1, n2);
}

/* ******************************************** */
/* print bin IDs */
void printBinIDs()
{
    notImplemented();
}

/* ******************************************** */
/* set probe IDs */
void setProbeIDs(void)
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    if (n1 > n2)
    {
        errorMsg("Upper ID can not be less than the lower one");
        return;
    }

    /* activate new IDs */
    soProbeSetIDs(n1, n2);
}

/* ******************************************** */
/* add probe IDs */
void addProbeIDs(void)
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    if (n1 > n2)
    {
        errorMsg("Upper ID can not be less than the lower one");
        return;
    }

    /* activate new IDs */
    soProbeAddIDs(n1, n2);
}

/* ******************************************** */
/* remove probe IDs */
void removeProbeIDs(void)
{
    /* ask for lower ID */
    promptMsg("Lower ID: ");
    int n1;
    fscanf(fin, "%d", &n1);
    fPurge(fin);
    if (n1 < 0)
    {
        errorMsg("Wrong number: %d", n1);
        return;
    }

    /* ask for upper ID */
    promptMsg("Upper ID: ");
    int n2;
    fscanf(fin, "%d", &n2);
    fPurge(fin);
    if (n2 < 0)
    {
        errorMsg("Wrong number: %d", n2);
        return;
    }

    if (n1 > n2)
    {
        errorMsg("Upper ID can not be less than the lower one");
        return;
    }

    /* activate new IDs */
    soProbeRemoveIDs(n1, n2);
}

/* ******************************************** */
/* print probe IDs */
void printProbeIDs()
{
    notImplemented();
}

/* ******************************************** */
/* create disk */
void createDisk(void)
{
    notImplemented();
    return;

    /* ask for number of blocks */
    promptMsg("Number of blocks: ");
    int n;
    fscanf(fin, "%d", &n);
    fPurge(fin);
    if (n < 3)
    {
        errorMsg("Wrong number: %d", n);
        return;
    }

    /* create disk */
}

/* ******************************************** */
/* format disk */
void formatDisk(void)
{
    /* ask for number of inodes */
    promptMsg("Number of inodes: ");
    int n;
    fscanf(fin, "%d", &n);
    fPurge(fin);
    if (n < 0)
    {
        errorMsg("Wrong number: %d", n);
        return;
    }

    /* close disk */
    try
    {
        soCloseDisk();
    }
    catch (SOException & err)
    {
        errnoMsg(err.en, err.msg);
        exit(EXIT_FAILURE);
    }

    /* format disk */
    char cmd[1000];
    sprintf(cmd, "%s/mksofs -b %s -i %d", progDir, devname, n);
    system(cmd);

    /* reopen disk */
    try
    {
        soOpenDisk(devname);
    }
    catch (SOException & err)
    {
        errnoMsg(err.en, err.msg);
        exit(EXIT_FAILURE);
    }

    /* wait for ENTER */
return;

    if (quiet > 0)
        return;
    promptMsg("\nPress ENTER to continue");
    fscanf(fin, "%*[^\n]");
    fPurge(fin);
}

/* ******************************************** */
/* show block */
void showBlock(void)
{
    /* ask for format */
    promptMsg("Display format (s, i, r, d, x, a): ");
    char t;
    fscanf(fin, "%c", &t);
    fPurge(fin);
    if (strchr("sirdxa", t) == NULL)
    {
        errorMsg("Wrong format\n");
        return;
    }

    /* ask for range */
    char range[100];
    promptMsg("block range: ");
    fscanf(fin, "%s", range);
    fPurge(fin);

    /* call showblock */
    char cmd[1000];
    sprintf(cmd, "%s/showblock %s -%c %s", progDir, devname, t, range);
    system(cmd);

    /* wait for ENTER */
return;

    if (quiet > 0)
        return;
    promptMsg("\nPress ENTER to continue");
    fscanf(fin, "%*[^\n]");
    fPurge(fin);
}

/* ******************************************** */
