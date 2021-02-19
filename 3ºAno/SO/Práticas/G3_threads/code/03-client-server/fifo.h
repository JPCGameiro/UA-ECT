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

/** \brief internal storage size of <em>FIFO memory</em> */
#define  FIFOSZ         20

/*
 *  \brief Data structure.
 */
typedef struct ITEM
{
    unsigned int id;     ///< id of the producer
    unsigned int value;  ///< value stored
} ITEM;

typedef struct Fifo
{ 
    unsigned int ii;   ///< point of insertion
    unsigned int ri;   ///< point of retrieval
    unsigned int cnt;  ///< number of items stored
    ITEM slot[FIFOSZ];  ///< storage memory

    pthread_mutex_t acessCr;        //Mutex to control acess to criticalzone
    pthread_cond_t fifoNotFull;     //cond variable to control the fullness
    pthread_cond_t fifoNotEmpty;    //cond variable to control the emptyness
} Fifo;


/**
 * \brief Init the fifo 
 */
void fifoInit(Fifo* fifo);

/**
 *  \brief Insertion of a value into the FIFO.
 *
 * \param id id of the producer
 * \param value value to be stored
 */
void fifoIn (Fifo* fifo, unsigned int id, unsigned int value);

/**
 *  \brief Retrieval of a value from the FIFO.
 *
 * \param idp pointer to recipient where to store the producer id
 * \param valuep pointer to recipient where to store the value 
 */
void fifoOut (Fifo* fifo, unsigned int * idp, unsigned int  *valuep);

#endif /* __SO_IPC_PRODUCER_CONSUMER_FIFO_ */
