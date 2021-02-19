/*
 * A simple module
 *
 * A very simple module, with an internal data structure
 * and 3 manipulation functions.
 * The data structure is just an integer variable.
 * The 3 functions are to:
 * - set the variable value;
 * - get the variable value;
 * - increment the variable value.
 *
 * \author (2016-2020) Artur Pereira <artur at ua.pt>
 */

#include "bwdelay.h"

/* time delay length */
#define UPDATE_TIME 1000

/* Internal data structure 
 * It is shared among all the threads
 */
static int value = 0;

/* manipulation functions */
void v_set(int new_value)
{
	value = new_value;
}

int v_get(void)
{
	return value;
}

/*
 * The increment represents an operation that have to be done
 * in the shared data structured.
 */
void v_inc(void)
{
    int val;

    /* make a local copy of the shared variable */
    val = value;

    /* update value: an increment, with a delay */
    bwDelay(UPDATE_TIME);
    val = val + 1;

    /* store the incremented value back to the shared variable */
    value = val;
}
