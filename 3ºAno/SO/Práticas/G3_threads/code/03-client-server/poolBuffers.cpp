

#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <errno.h>
#include <pthread.h>
#include <string.h>

#include "poolBuffers.h"
#include "delays.h"
#include "thread.h"


/* ************************************************* */

void poolInit(Pool* pool)
{
    pool->acessCr = PTHREAD_MUTEX_INITIALIZER;

    //Lock acess to critical zone
    mutex_lock(&pool->acessCr);

    unsigned int i;
    for (i = 0; i < POOLSZ; i++)
    {
        pool->slot[i].id = 99999999;
        strcpy(pool->slot[i].value, "\0");
    }
    pool->cnt = 0;

    //Unlock acess to critical zone
    mutex_unlock(&pool->acessCr);
}

/* ************************************************* */

void poolIn(Pool* pool, unsigned int id, char* value)
{
    //Lock acess to critical zone
    mutex_lock(&pool->acessCr);

    /* Insert pair */
    strcpy(pool->slot[id].value, value);
    gaussianDelay(1, 0.5);
    pool->slot[id].id = id;
    pool->cnt++;

    //Unlock acess to critical zone
    mutex_unlock(&pool->acessCr);

}

/* ************************************************* */

void poolOut (Pool* pool, unsigned int id, char * *valuep)
{
    //lock acess to critical zone
    mutex_lock(&pool->acessCr);

    /* Retrieve pair */
    strcpy(*valuep, pool->slot[id].value);
    strcpy(pool->slot[id].value, "\0");
    pool->slot[id].id = 99999999;
    pool->cnt--;

    //unlock acess to critical zone
    mutex_unlock(&pool->acessCr);
}

/* ************************************************* */

