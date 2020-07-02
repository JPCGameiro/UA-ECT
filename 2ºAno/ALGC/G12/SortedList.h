//
// Joaquim Madeira, AlgC, April 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// Adapted from Tomás Oliveira e Silva, AED, September 2015
//
// SORTED LIST implementation based on a linked list
//

#ifndef _SORTED_LIST_
#define _SORTED_LIST_

typedef struct _SortedList List;
typedef int (*compFunc)(const void* p1, const void* p2);

List* ListCreate(compFunc compF);

void ListDestroy(List** p);

void ListClear(List* l) ;

int ListGetSize(const List* l);

int ListIsEmpty(const List* l);

// Current node functions

int ListCurrentIsInside(const List* l) ;

int ListGetCurrentPos(const List* l);

void* ListGetCurrentItem(const List* l);

void ListSetCurrentItem(const List* l, void* p);


// Move current node functions

void ListMove(List* l, int newPos);

void ListMoveToNext(List* l);

void ListMoveToPrevious(List* l);

void ListMoveToHead(List* l);

void ListMoveToTail(List* l);

// Search
// The search function returns 0 on success and -1 on failure.
// On success the current node is changed, on failure it is not changed.
int ListSearch(List* l, const void* p);

// Insert

int ListInsert(List* l, void* p);

// Remove

void* ListRemoveHead(List* l);

void* ListRemoveTail(List* l);

void* ListRemoveCurrent(List* l);

// Tests

void ListTestInvariants(const List* l);

#endif  // _SORTED_LIST_
