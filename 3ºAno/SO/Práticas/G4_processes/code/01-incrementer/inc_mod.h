/*
 * A simple module
 *
 * A very simple module, with an internal data structure
 * and 6 manipulation functions.
 * The data structure is just a pair of integer variables.
 * The 6 functions are to:
 * - set the variables' values;
 * - get the variables' values;
 * - increment the variables' values.
 *
 * \author (2016-2020) Artur Pereira <artur at ua.pt>
 */
#ifndef __SO_IPC_INCREMENTER_INC_MOD_
#define __SO_IPC_INCREMENTER_INC_MOD_

/* manipulation functions */
void v_create();

void v_connect();

void v_destroy();

void v_set(int v1, int v2);

int v_get(int * v1, int * v2);

void v_inc(void);

#endif /* __SO_IPC_INCREMENTER_INC_MOD_ */
