/**
 * @file
 *
 * \brief Generation of a busy waiting delay
 *
 * (c) 2004--2018 Artur Pereira <artur at ua.pt>
 */
#ifndef __SO_IPC_INCREMENTER_DELAY_
#define __SO_IPC_INCREMENTER_DELAY_

/** \brief Generate a busy waiting delay
 * \param n number of delay units
 */
void bwDelay(int n);

/** \brief Generate a random, busy waiting delay
 * \param n maximum number of delay units
 */
void bwRandomDelay(int n);

#endif /* __SO_IPC_INCREMENTER_DELAY_ */
