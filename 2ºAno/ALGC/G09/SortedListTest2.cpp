//
// Joaquim Madeira, AlgC, April 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// TESTING the TAD LIST implementation
//

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "Date.h"
#include "SortedList.h"

// Storing pointers to Dates

// The comparator

int comparatorForDates(const void* p1, const void* p2) {
  return DateCompare((Date*)p1, (Date*)p2);
}

// Print a list of Dates, one per line, and test invariants.
void printList(List* list) {
  int cpos = ListGetCurrentPos(list); // save current position
  int size = ListGetSize(list);
  for (int i = 0; i < size; i++) {
    ListMove(list, i);
    Date* p = (Date*)ListGetCurrentItem(list);
    printf("%s ", DateFormat(p, DMY));
    ListTestInvariants(list);
  }
  printf("\n");
  ListMove(list, cpos); // restore current position
}

int main(void) {
  List* list = ListCreate(comparatorForDates);
  
  printf("INSERTING\n");
  int r;
  Date* aux;
  for (int i = 2; i <= 6; i += 2) {
    aux = DateCreate(2020, 01, i);
    ListInsert(list, aux);
    ListTestInvariants(list);
  }

  r = ListInsert(list, aux);  // Inserting equal element should fail
  assert (r == -1);  // failure
  
  for (int i = 1; i <= 5; i += 2) {
    aux = DateCreate(2020, 01, i);
    ListInsert(list, aux);
    ListTestInvariants(list);
  }

  printf("%d elements\n", ListGetSize(list));
  printList(list);

  printf("REMOVING\n");

  ListMoveToHead(list);
  while (ListCurrentIsInside(list)) {
    Date* aux = (Date*)ListRemoveCurrent(list);
    printf("RemoveCurrent -> %s\n", DateFormat(aux, DMY));
    DateDestroy(&aux);
    printList(list);
    ListMoveToNext(list); // skip one
  }

  // Cleanup (Try commenting out and run with valgrind)
  while (!ListIsEmpty(list)) {
    Date* aux = (Date*)ListRemoveHead(list);
    printf("RemoveHead -> %s\n", DateFormat(aux, DMY));
    DateDestroy(&aux);
    printList(list);
  }
  ListDestroy(&list);
  
  return 0;
}

