/*
 * A simple monitor
 *
 * A very simple module, with an internal data structure
 * and 3 manipulation functions.
 * The data structure is just an integer variable.
 * The 3 functions are to:
 * - set the variable value;
 * - get the variable value;
 * - increment the variable value.
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#include <pthread.h>

#include "bwdelay.h"
#include "thread.h"

/* time delay length */
#define UPDATE_TIME 1000

/* Internal data structure */
static int value = 0;

/* mutual exclusion element */
static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

/* manipulation functions */
void v_set(int new_value)
{
    /* enter critical region */
    mutex_lock(&mutex);

    value = new_value;

    /* leave critical region */
    mutex_unlock(&mutex);
}

int v_get(void)
{
    /* enter critical region */
    mutex_lock(&mutex);

    int val = value;

    /* leave critical region */
    mutex_unlock(&mutex);

    return val;
}

/*
 * The increment represents an operation that have to be done
 * in the shared data structured.
 */
void v_inc(void)
{
    /* enter critical region */
    mutex_lock(&mutex);

    /* make a local copy of the shared data structured */
    int val = value;

    /* update value: an increment, with a delay */
    bwDelay(UPDATE_TIME);
    val = val + 1;

    /* store it back */
    value = val;

    /* leave critical region */
    mutex_unlock(&mutex);
}
