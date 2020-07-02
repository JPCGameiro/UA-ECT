//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// Binary Min Heap storing pointers to generic items.
//

#ifndef _PriorityQueue_
#define _PriorityQueue_

// The type for PriorityQueue structures
typedef struct _Heap PriorityQueue;

// The type for item comparator functions
typedef int (*compFunc)(const void* p1, const void* p2);

// The type for item printer functions
typedef void (*printFunc)(void* p);


// CREATE/DESTROY

PriorityQueue* PriorityQueueCreate(int capacity, compFunc compF, printFunc printF) ;

void PriorityQueueDestroy(PriorityQueue** pph) ;

// GETTERS

int PriorityQueueCapacity(PriorityQueue* ph) ;

int PriorityQueueSize(PriorityQueue* ph) ;

int PriorityQueueIsEmpty(PriorityQueue* ph) ;

int PriorityQueueIsFull(PriorityQueue* ph) ;

void* PriorityQueueGetMin(PriorityQueue* ph) ;

// MODIFY

void PriorityQueueInsert(PriorityQueue* ph, void* item) ;

void PriorityQueueRemoveMin(PriorityQueue* ph) ;

void PriorityQueueDecreasePriority(PriorityQueue* ph, void* item) ;  // NEW!

// CHECK/VIEW

int PriorityQueueCheck(PriorityQueue* ph) ;

void PriorityQueueView(PriorityQueue* ph) ;

#endif
