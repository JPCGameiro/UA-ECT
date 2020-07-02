
//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// TESTING the TAD MinHeap implementation
//

// This program accepts multiple arguments.
// If the argument is:
// a number : it is inserted into a min-heap.
//    -     : the first (minimum) item is removed from the head
//    ?     : the heap content is shown, both in tree form and array form.
//
// Try the arguments below.

//ARGS 1 2 3 4 5 6 ?
//ARGS 6 5 4 3 2 1 ?
//ARGS 3 5 2 1 6 4 ?
//ARGS 5 6 3 1 4 2 ?
//ARGS 5 6 3 ? 1 ? 4 ? 2 ?
//ARGS 8 4 9 4 3 1 10 2 5 7 11 6 ?
//ARGS 5 6 3 ? - ? 1 ? 4 ? 2 ? - ? - ? - ? - ?

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "MinHeap.h"

// Storing pointers to integers

// The comparator for integer items

int comparator(const void* p1, const void* p2) {
   int d = *(int*)p1 - *(int*)p2;
   return (d > 0) - (d < 0);
}

// The printer for integer items

void printer(void* p) { printf("%d ", *(int*)p); }

int main(int argc, char* argv[]) {

   printf("CREATE AN EMPTY HEAP\n");
   // with capacity for at most argc items
   MinHeap* h1 = MinHeapCreate(argc, comparator, printer);
   printf("Capacity = %d\n", MinHeapCapacity(h1));
   printf("Size = %d\n", MinHeapSize(h1));

   printf("\nPROCESS ARGS\n");
   for (int i = 1; i < argc; i++) {
      int* aux;
      char* arg = argv[i];
      printf("ARG %s: ", arg);
      switch (arg[0]) {
      case '?':  // View and Check
         printf("View\n");
         MinHeapView(h1);  // for debugging
         printf("Check: %s\n", MinHeapCheck(h1)? "OK": "ERROR");
         break;
      case '-':
         aux = (int*)MinHeapGetMin(h1);
         printf("Removing %d\n", *aux);
         MinHeapRemoveMin(h1);
         free(aux);
         break;
      default:  // assume it's an item to insert
         aux = (int*)malloc(sizeof(*aux));
         *aux = atoi(arg);
         printf("Inserting %d\n", *aux);
         MinHeapInsert(h1, aux);
      }
   }
   printf("\nFINISHED ARGS\n");
   
   printf("\nQUERY FUNCTIONS\n");
   printf("Size = %d\n", MinHeapSize(h1));
   printf("Min = %d\n", *((int*)MinHeapGetMin(h1)));

   printf("\nREMOVING REMAINING ITEMS\n");
   while (!MinHeapIsEmpty(h1)) {
      int* aux = (int*)MinHeapGetMin(h1);
      printf("Removing %d\n", *aux);
      MinHeapRemoveMin(h1);
      free(aux);
   }
   printf("\nFINISHED REMOVING\n");
   MinHeapView(h1);

   MinHeapDestroy(&h1);

   return 0;
}
