//NMEC: 93097
//NOME: João Gameiro
//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// A Priority Queue storing pointers to generic items.
// Implemented as a binary min-heap.
//

// PROCURE // vvv SOLUTION vvv
// Para encontrar soluções de problemas de guiões anteriores.

// PROCURE //<<<NEW  para ver o que foi acrescentado.

#include "PriorityQueue.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

//
// A binary or 2-way heap is a complete tree stored in an array.
//
// +-----------------------------------------------+
// |                       0                       |
// +-----------------------+-----------------------+
// |           1           |           2           |
// +-----------+-----------+-----------+-----------+
// |     3     |     4     |     5     |     6     |
// +-----+-----+-----+-----+-----+-----+-----+-----+
// |  7  |  8  |  9  | 10  | 11  | 12  | 13  | 14  |
// +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
// |15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|
// +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//
// The descendents of node k are 2*k+1 and 2*k+2.
//
// The value of each node cannot be larger that those of its descendents.
//

// The heap data structure
struct _Heap {
   void** array;
   int capacity;
   int size;
   compFunc compare;
   printFunc print;
};

// Create a new PriorityQueue with a given capacity.
// compF is a (pointer to) the priority compare function.
// printF is a (pointer to) a function to print each element.
PriorityQueue* PriorityQueueCreate(int capacity, compFunc compF, printFunc printF) {
   PriorityQueue* h = (PriorityQueue*)malloc(sizeof(*h));  // alloc header
   if (h == NULL) abort();
   h->array = (void**)malloc(capacity*sizeof(void*));  // alloc array
   if (h->array == NULL) {
      free(h);
      abort();
   }
   h->capacity = capacity;
   h->size = 0;
   h->compare = compF;
   h->print = printF;
   return h;
}

void PriorityQueueDestroy(PriorityQueue** pph) {
   PriorityQueue* ph = *pph;
   if (ph == NULL) return;
   free(ph->array);
   free(ph);
   *pph = NULL;
}


int PriorityQueueCapacity(PriorityQueue* ph) { return ph->capacity; }

int PriorityQueueSize(PriorityQueue* ph) { return ph->size; }

int PriorityQueueIsEmpty(PriorityQueue* ph) { return ph->size == 0; }

int PriorityQueueIsFull(PriorityQueue* ph) { return ph->size == ph->capacity; }

void* PriorityQueueGetMin(PriorityQueue* ph) {
   assert( !PriorityQueueIsEmpty(ph) );
   return ph->array[0];
}

// Internal functions

// n is the index of a node (n in [0, size[).
// _child(n, 1) is the index of the first _child of node n, if < size.
// _child(n, 2) is the index of the second _child of node n, if < size.
static inline int _child(int n, int c) {
 return 2*n+c;
}

// _parent(n) is the index of the _parent node of node n, if >=0.
static inline int _parent(int n) {
 assert(n>0);
 return (n-1)/2;
}

// Find the node where item is stored.
// Returns the node (index) or -1 if not found.
static int _findItem(PriorityQueue* ph, void* item) {        //<<<NEW
   // This algorithm is a sequential search. Complexity = O(size).
   // IMPROVE!
   int n = ph->size;
   while (--n >= 0) {
      if (ph->array[n] == item) break;
   }
   return n;
}

// Insert an item onto the priority queue ph, starting at node n.
// Node n will be overwritten.
// Return the node where item was stored. (This may not be necessary.)
static int _insertStartingFrom(PriorityQueue* ph, void* item, int n) { //<<<NEW
   while (n > 0) {
      int p = _parent(n);
      
      // if item not less than _parent, then we've found the right spot!
      // vvv SOLUTION vvv
      if (ph->compare(item, ph->array[p]) >= 0) break;
      // ^^^ SOLUTION ^^^
      
      // otherwise, move up the item at node p to open up space for new item
      // vvv SOLUTION vvv
      ph->array[n] = ph->array[p];
      // ^^^ SOLUTION ^^^
      
      n = p;  // p is the new vacant spot
   }
   ph->array[n] = item;  // store item at node n
   return n; // any use?
}


// Insert the item into the priority queue
void PriorityQueueInsert(PriorityQueue* ph, void* item) {
   assert( !PriorityQueueIsFull(ph) );
   // start at the first vacant spot (just after the last occupied node)
   _insertStartingFrom(ph, item, ph->size);     //<<<NEW
   ph->size++;
   //return n; // Could return the node where item was stored.
}

// Remove the Min item
void PriorityQueueRemoveMin(PriorityQueue* ph) {
   assert( !PriorityQueueIsEmpty(ph) );
   
   ph->size--; // NOTE: we're decreasing the size first!
   int n = 0;  // the just emptied spot... must fill it with smallest child
   while (1) {
      int min = _child(n,1);      // first _child (might not exist)
      if (!(min < ph->size)) break;  // if no second _child, stop looking
   
      // if second _child is smaller, choose it
      // vvv SOLUTION vvv
      if (ph->compare(ph->array[min+1], ph->array[min]) < 0) {
         min = min+1;
      }
      // ^^^ SOLUTION ^^^
      
      // if smallest _child is not smaller than last, stop looking
      if (!(ph->compare(ph->array[min], ph->array[ph->size]) < 0)) break;
   
      // move smallest _child down to fill empty _parent spot
      // vvv SOLUTION vvv
      ph->array[n] = ph->array[min];
      // ^^^ SOLUTION ^^^
      
      n = min;     // now, the smallest _child spot was just emptied!
   }
   // move last element to emptied spot
   ph->array[n] = ph->array[ph->size];
   ph->array[ph->size] = NULL;    // mark last element as vacant
}

// Reposition the given item in the queue to reflect its decreased priority.
// Preconditions:
// - The item must be in the priority queue and
// - The item's priority cannot have increased.
void PriorityQueueDecreasePriority(PriorityQueue* ph, void* item) {  //<<<NEW
   // find the node with this item
   int n = _findItem(ph, item);
   assert(n >= 0);   // item should be in the priority queue!
   n = _insertStartingFrom(ph, item, n);
   //return n; // Could return the node where item was stored.
}

// Check the (min-)heap property (the heap invariant):
//    Each node must be <= than each of its children.
// Equivalently (but easier):
//    Each node must be >= its parent.
int PriorityQueueCheck(PriorityQueue* ph) {
   // For each node other than root: compare with its parent
   for (int n = 1; n < ph->size; n++) {
      int p = _parent(n);
      if (ph->compare(ph->array[n], ph->array[p]) < 0) return 0;
   }
   return 1; 
}

// Visualize the items in the priority queue as a heap tree
static
void _HeapView(PriorityQueue* ph, int level, const char* edge, int root) {
   if (root < ph->size) {
      _HeapView(ph, level+1, "/", _child(root,1));
      printf("%*s", 4*level, edge);
      ph->print(ph->array[root]);
      printf("\n");
      _HeapView(ph, level+1, "\\", _child(root,2));
   }
}

// Visualize the priority queue both as a tree and as an array.
void PriorityQueueView(PriorityQueue* ph) {
   printf("tree:\n");
   _HeapView(ph, 0, ":", 0);  // : marks the root
   printf("array:");
   for (int i = 0; i < ph->size; i++) {
      printf(" ");
      ph->print(ph->array[i]);
   }
   printf("\nsize: %d\n", ph->size);
}
