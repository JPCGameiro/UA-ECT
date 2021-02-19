/*
 * An implementation of inc_mod suffering from race conditions
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#include "bwdelay.h"
#include "process.h"

#include <stdlib.h>
#include <stdio.h>
#include <sys/shm.h>

const long key = 0x1111L;

/* time delay length */
#define INC_TIME 100

/* Internal data structure */
typedef struct
{
    int var1, var2;
} SharedDataType;

static int shmid = -1;
static SharedDataType * p = NULL;

/* manipulation functions */

void v_create()
{
    /* create the shared memory */
    shmid = pshmget(key, sizeof(SharedDataType), 0600 | IPC_CREAT | IPC_EXCL);
}

void v_connect()
{
    /* get access to the shared memory */
    shmid = pshmget(key, 0, 0);

    /* attach shared memory to process addressing space */ 
    p = (SharedDataType*)pshmat(shmid, NULL, 0);
}

void v_destroy()
{
    /* detach shared memory from process addressing space */
    pshmdt(p);
    p = NULL;

    /* ask OS to destroy the shared memory */
    pshmctl(shmid, IPC_RMID, NULL);
    shmid = -1;
}

/* set shared data with new values */
void v_set(int value1, int value2)
{
	p->var1 = value1;
	p->var2 = value2;
}

/* get current values of shared data */
void v_get(int * value1p, int * value2p)
{
	*value1p = p->var1;
	*value2p = p->var2;
}

/* increment both variables of the shared data */
void v_inc(void)
{
    /* v1 is incremented in a way to promote the occurrence of race conditions */
    int v1 = p->var1 + 1;
    bwRandomDelay(INC_TIME);
    p->var1 = v1;

    /* v2 is incremented in the normal way; however race conditions can still appear */
    p->var2++;
}

