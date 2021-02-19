/**
 *  @file 
 *
 *  @brief A simple FIFO, whose elements are pairs of integers,
 *      one representing the producer and the other the value produced
 *
 *  The following operations are defined:
 *     \li insertion of a value
 *     \li retrieval of a value.
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#ifndef __SO_IPC_PRODUCER_CONSUMER_FIFO_
#define __SO_IPC_PRODUCER_CONSUMER_FIFO_

/**
 * \brief Init the fifo 
 */
void fifoInit(void);

/**
 *  \brief Insertion of a value into the FIFO.
 *
 * \param id id of the producer
 * \param value value to be stored
 */
void fifoIn (unsigned int id, unsigned int value);

/**
 *  \brief Retrieval of a value from the FIFO.
 *
 * \param idp pointer to recipient where to store the producer id
 * \param valuep pointer to recipient where to store the value 
 */
void fifoOut (unsigned int * idp, unsigned int  *valuep);

#endif /* __SO_IPC_PRODUCER_CONSUMER_FIFO_ */
