/*
 *  @brief A simple FIFO, whose elements are pairs of integers,
 *      one being the id of the producer and the other the value produced
 *
 * @remarks safe, bust waiting version
 *
 *  The following operations are defined:
 *     \li insertion of a value
 *     \li retrieval of a value.
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <errno.h>
#include <pthread.h>

#include "fifo.h"
#include "delays.h"
#include "thread.h"

/* ************************************************* */

/* Initialization of the FIFO */
void fifoInit(Fifo* fifo)
{
    fifo->acessCr = PTHREAD_MUTEX_INITIALIZER;

    //Lock acess to critical zone
    mutex_lock(&fifo->acessCr);

    unsigned int i;
    for (i = 0; i < FIFOSZ; i++)
    {
        fifo->slot[i].id = 99999999;
        fifo->slot[i].value = 99999999;
    }
    fifo->ii = fifo->ri = 0;
    fifo->cnt = 0;

    fifo->fifoNotFull = PTHREAD_COND_INITIALIZER;
    fifo->fifoNotEmpty = PTHREAD_COND_INITIALIZER;

    cond_broadcast(&fifo->fifoNotFull);

    //Unlock acess to critical zone
    mutex_unlock(&fifo->acessCr);
}

/* ************************************************* */

/* Check if FIFO is full */
static bool fifoFull(Fifo* fifo)
{
    return fifo->cnt == FIFOSZ;
}

/* ************************************************* */

/* Check if FIFO is empty */
static bool fifoEmpty(Fifo* fifo)
{
    return fifo->cnt == 0;
}

/* ************************************************* */

/* Insertion of a pait <id, value> into the FIFO  */
void fifoIn(Fifo* fifo, unsigned int id, unsigned int value)
{
    //Lock acess to critical zone
    mutex_lock(&fifo->acessCr);

    /* wait while fifo is full */
    while (fifoFull(fifo))
        cond_wait(&fifo->fifoNotFull, &fifo->acessCr);

    /* Insert pair */
    fifo->slot[fifo->ii].value = value;
    gaussianDelay(1, 0.5);
    fifo->slot[fifo->ii].id = id;
    fifo->ii = (fifo->ii + 1) % FIFOSZ;
    fifo->cnt++;

    //Signall all waiting threads that fifo is not empty
    cond_broadcast(&fifo->fifoNotEmpty);

    //Unlock acess to critical zone
    mutex_unlock(&fifo->acessCr);
}

/* ************************************************* */

/* Retrieval of a pair <id, value> from the FIFO */

void fifoOut (Fifo* fifo, unsigned int * idp, unsigned int * valuep)
{
    //Lock acess to critical zone
    mutex_lock(&fifo->acessCr);

    /* wait while fifo is empty */
    while (fifoEmpty(fifo))
        cond_wait(&fifo->fifoNotEmpty, &fifo->acessCr);

    /* Retrieve pair */
    *valuep = fifo->slot[fifo->ri].value;
    fifo->slot[fifo->ri].value = 99999999;
    *idp = fifo->slot[fifo->ri].id;
    fifo->slot[fifo->ri].id = 99999999;
    fifo->ri = (fifo->ri + 1) % FIFOSZ;
    fifo->cnt--;

    //Signal all waiting threads that fifo is not Full
    cond_broadcast(&fifo->fifoNotFull);

    //Unlock acess to critical zone
    mutex_unlock(&fifo->acessCr);
}

/* ************************************************* */

