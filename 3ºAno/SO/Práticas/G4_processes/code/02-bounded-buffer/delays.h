/**
 * @file
 *
 * \brief Generation of delays
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */
#ifndef __SO_IPC_PRODCON_DELAYS_
#define __SO_IPC_PRODCON_DELAYS_

/** \brief Generate a fixed, busy waiting delay
 * \param n number of delay units
 */
void bwDelay(int n);

/** \brief Generate a random, busy waiting delay
 * \param n maximum number of delay units
 */
void bwRandomDelay(int n);

/** \brief Generate a delay based on a gaussian distribution
 * \param mean mean time in milliseconds
 * \param std standard deviation in milliseconds
 */
void gaussianDelay(double mean, double std);

#endif /* __SO_IPC_PRODCON_DELAY_ */
