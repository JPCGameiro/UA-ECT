
//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// TESTING the TAD PriorityQueue implementation
//

// This program accepts multiple arguments.
// Each argument produces an action according to the table below.
//
// ARGUMENT : ACTION
// ---------+-----------------------------------------------------------------
//  NUMBER  : insert item with auto ID and PRI=NUMBER into the priority queue.
//    -     : the first (minimum) item is removed from the head
//    ?     : the heap content is shown, both in tree form and array form.
// :ID:PRI  : reduce the priority of item with ID to PRI.

// Try the arguments below.

//ARGS 5 6 3 1 4 2 ?
//ARGS 5 6 3 ? 1 ? 4 ? 2 ?
//ARGS 5 6 3 ? - ? 1 ? 4 ? 2 ? - ? - ? - ? - ?
//ARGS 71 42 83 24 55 96 37 68 19 ? :1:21 ? :4:14 ? :6:76 ?
//ARGS 71 42 83 24 55 96 37 68 19 ? :1:21 ? - ? :4:14 ? - ? :6:76 ?

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "PriorityQueue.h"

// The items in the priority queue will be ID:PRIORITY pairs (type ITEM)

typedef struct {
   int id;     // item id (assigned automatically = index in ITEM array)
   int pri;    // item priority (assigned by the user)
} ITEM;

// The priority comparator for items.
int comparator(const void* p1, const void* p2) {
   return ((ITEM*)p1)->pri - ((ITEM*)p2)->pri;
}

// The printer for items.
void printer(void* p) {
   ITEM* pitem = (ITEM*)p;
   printf("%d:%d", pitem->id, pitem->pri);
}

int main(int argc, char* argv[]) {

   // A LOCAL ARRAY FOR ALL POSSIBLE ITEMS
   // item[1] will contain the first inserted item,
   // item[2] the second inserted item, etc.
   // (item[0] is not used.)
   // The index of an item will be the item ID
   ITEM item[argc];
   int lastID = 0;   // last used ID (index to last inserted item)
   
   printf("CREATE AN EMPTY HEAP\n");
   // with capacity for at most argc items
   PriorityQueue* pq = PriorityQueueCreate(argc, comparator, printer);
   printf("Capacity = %d\n", PriorityQueueCapacity(pq));
   printf("Size = %d\n", PriorityQueueSize(pq));

   printf("\nPROCESS ARGS\n");
   for (int i = 1; i < argc; i++) {
      int id;
      int pri;
      ITEM* pitem;
      char* arg = argv[i];
      printf("ARG %s: ", arg);
      switch (arg[0]) {
      case '?':   // View and Check
         printf("View\n");
         PriorityQueueView(pq);  // for debugging
         printf("Check: %s\n", PriorityQueueCheck(pq)? "OK": "ERROR");
         break;
      case '-':   // RemoveMin
         pitem = (ITEM*)PriorityQueueGetMin(pq);
         printf("Removing item %d:%d\n", pitem->id, pitem->pri);
         item[pitem->id].id = 0;      // invalidate storage!
         PriorityQueueRemoveMin(pq);
         //free(pitem);
         break;
      case ':':   // ":ID:PRI" DecreasePriority of item with ID to PRI
         if (sscanf(arg, ":%d:%d", &id, &pri) != 2) {
            printf("Bad format!\n");
            break;
         }
         if (!(1 <= id && id <= lastID && item[id].id == id)) {
            printf("%d: Invalid ID!\n", id);
            break;
         }
         if (pri > item[id].pri) {
            printf("Increasing item %d:%d to priority %d is not supported!\n",
                     id, item[id].pri, pri);
            break;
         }
         printf("Decreasing item %d:%d to priority %d\n", id, item[id].pri, pri);
         item[id].pri = pri;   // change the priority
         pitem = &(item[id]);
         PriorityQueueDecreasePriority(pq, (void*)pitem);
         break;
      default:  // assume it's the priority of a new item to insert
         id = ++lastID;
         item[id].id = id;          // assign id automatically
         item[id].pri = atoi(arg);  // assign priority
         printf("Inserting item %d:%d\n", id, item[id].pri);
         pitem = &(item[id]);   // No alloc needed! Just point to item storage.
         PriorityQueueInsert(pq, (void*)pitem);
      }
   }
   printf("\nFINISHED ARGS\n");
   
   printf("\nREMOVING REMAINING ITEMS\n");
   while (!PriorityQueueIsEmpty(pq)) {
      ITEM* pitem = (ITEM*)PriorityQueueGetMin(pq);
      printf("Removing item %d:%d\n", pitem->id, pitem->pri);
      PriorityQueueRemoveMin(pq);
      //free(pitem);
   }
   printf("\nFINISHED REMOVING\n");
   PriorityQueueView(pq);

   PriorityQueueDestroy(&pq);

   return 0;
}
