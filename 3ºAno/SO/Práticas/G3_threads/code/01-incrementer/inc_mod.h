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
#ifndef __SO_IPC_INCREMENTER_INC_MOD_
#define __SO_IPC_INCREMENTER_INC_MOD_

/* manipulation functions */
void v_set(int new_value);

int v_get(void);

void v_inc(void);

#endif /* __SO_IPC_INCREMENTER_INC_MOD_ */
