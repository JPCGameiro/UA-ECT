/*
 *  @brief A simple FIFO, whose elements are pairs of integers,
 *      one being the id of the producer and the other the value produced
 *
 * @remarks safe, non busy waiting version
 *
 *  The following operations are defined:
 *     \li insertion of a value
 *     \li retrieval of a value.
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <errno.h>
#include <sys/shm.h>
#include <sys/sem.h>

#include "fifo.h"
#include "delays.h"
#include "process.h"

/** \brief internal storage size of <em>FIFO memory</em> */
#define  FIFOSZ         5

/** \brief resource key */
const long key = 0x1111L;

/*
 *  \brief Shared data structure.
 */
typedef struct ITEM
{
    unsigned int id;     ///< id of the producer
    unsigned int value;  ///< value stored
} ITEM;

typedef struct FIFO
{ 
    int semid;         ///< syncronization semaphore
    unsigned int ii;   ///< point of insertion
    unsigned int ri;   ///< point of retrieval
    unsigned int cnt;  ///< number of items stored
    ITEM slot[FIFOSZ];  ///< storage memory
} FIFO;

/** \brief internal storage region of FIFO type */
static int shmid = -1;
static FIFO * fifo = NULL;

/* index of access, full and empty semaphores */
#define ACCESS 0
#define FULLNESS 1
#define EMPTINESS 2

/* ************************************************* */

void down(unsigned short index)
{
    struct sembuf op = {index, -1, 0};
    psemop(fifo->semid, &op, 1);
}

/* ************************************************* */

void up(unsigned short index)
{
    struct sembuf op = {index, 1, 0};
    psemop(fifo->semid, &op, 1);
}

/* ************************************************* */

/* Initialization of the FIFO */
void fifoCreate(void)
{
    /* create the shared memory */
    shmid = pshmget(key, sizeof(FIFO), 0600 | IPC_CREAT | IPC_EXCL);

    /*  attach shared memory to process addressing space */
    fifo = (FIFO*)pshmat(shmid, NULL, 0);

    /* init fifo */
    unsigned int i;
    for (i = 0; i < FIFOSZ; i++)
    {
        fifo->slot[i].id = 99999999;
        fifo->slot[i].value = 99999999;
    }
    fifo->ii = fifo->ri = 0;
    fifo->cnt = 0;

    /* create access, full and empty semaphores */
    fifo->semid = psemget(key, 3, 0600 | IPC_CREAT | IPC_EXCL);

    /* init semaphores */
    for (i = 0; i < FIFOSZ; i++)
    {
        up(EMPTINESS);
    }
    up(ACCESS);
}

/* ************************************************* */

void fifoConnect()
{
    /* get access to the shared memory */
    shmid = pshmget(key, 0, 0);

    /* attach shared memory to process addressing space */ 
    fifo = (FIFO*)pshmat(shmid, NULL, 0);
}

/* ************************************************* */

void fifoDisconnect()
{
    /* detach shared memory from process addressing space */
    if (fifo == NULL) return;
    pshmdt(fifo);
    fifo = NULL;
}

/* ************************************************* */

void fifoDestroy()
{
    /* destroy semaphore set */
    psemctl(fifo->semid, 0, IPC_RMID, NULL);

    /* detach shared memory from process addressing space */
    if (fifo != NULL)
    {
        pshmdt(fifo);
        fifo = NULL;
    }

    /* ask OS to destroy the shared memory */
    pshmctl(shmid, IPC_RMID, NULL);
    shmid = -1;
}

/* ************************************************* */

/* Insertion of a pait <id, value> into the FIFO  */
void fifoIn(unsigned int id, unsigned int value)
{
    /* decrement emptiness, blocking if full */
    down(EMPTINESS);

    /* lock access */
    down(ACCESS);

    /* Insert pair */
    fifo->slot[fifo->ii].value = value;
    gaussianDelay(1, 0.5);
    fifo->slot[fifo->ii].id = id;
    fifo->ii = (fifo->ii + 1) % FIFOSZ;
    fifo->cnt++;

    /* unlock access and increment fullness */
    up(ACCESS);
    up(FULLNESS);
}

/* ************************************************* */

/* Retrieval of a pair <id, value> from the FIFO */

void fifoOut (unsigned int * idp, unsigned int * valuep)
{
    /* decrement fullness, blocking if full */
    down(FULLNESS);

    /* lock access */
    down(ACCESS);

    /* Retrieve pair */
    *valuep = fifo->slot[fifo->ri].value;
    fifo->slot[fifo->ri].value = 99999999;
    *idp = fifo->slot[fifo->ri].id;
    fifo->slot[fifo->ri].id = 99999999;
    fifo->ri = (fifo->ri + 1) % FIFOSZ;
    fifo->cnt--;

    /* unlock access and increment fullness */
    up(ACCESS);
    up(EMPTINESS);
}

/* ************************************************* */

