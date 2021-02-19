/*
 * @brief A priority FIFO (implemented with a circular array),
 *        whose elements are pairs of integers, one being an
 *        non-negative id and the other a positive priority value.
 * 
 * The following operations are defined:
 *    \li initializer
 *    \li check if is empty
 *    \li check if is full
 *    \li insertion of a value with a given priority
 *    \li retrieval of a value.
 **/


#ifndef PFIFO_H
#define PFIFO_H

#include <stdint.h>
#include  "settings.h"

#include "thread.h"
//#include "process.h"

typedef struct
{
   struct
   {
      uint32_t id;         // element ID (works as an index in array all_patients)
      uint32_t priority;   // patient priority in FIFO
   } array[FIFO_MAXSIZE];
   uint32_t inp;  ///< point of insertion (queue tail)
   uint32_t out;  ///< point of retrieval (queue head)
   uint32_t cnt;  ///< number of items stored

   pthread_mutex_t accessCR;      ///<locking flag wich warrants mutual exclusion inside the monitor
   pthread_cond_t fifoNotFull;   ///<condition variable to check fifo's fullness
   pthread_cond_t fifoNotEmpty;  ///<condition variable to check fifo's emptyness

   uint32_t done = 0;  
} PriorityFIFO;

void init_pfifo(PriorityFIFO* pfifo);
int empty_pfifo(PriorityFIFO* pfifo);
int full_pfifo(PriorityFIFO* pfifo);
void insert_pfifo(PriorityFIFO* pfifo, uint32_t id, uint32_t priority);
uint32_t retrieve_pfifo(PriorityFIFO* pfifo);
void done_pfifo(PriorityFIFO* pfifo);
void print_pfifo(PriorityFIFO* pfifo);

#endif
