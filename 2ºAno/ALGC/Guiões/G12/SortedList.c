//NMEC: 93097
//NOME: João Gameiro
//
// Joaquim Madeira, AlgC, April 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// Adapted from Tomás Oliveira e Silva, AED, September 2015
//
// SORTED LIST implementation based on an linked list
//

// PROCURE // vvv SOLUTION vvv
// Para encontrar soluções de problemas de guiões anteriores.

#include "SortedList.h"

#include <assert.h>
#include <stdlib.h>

struct _ListNode {
  void* item;
  struct _ListNode* next;
};

struct _SortedList {
  int size;                   // current List size
  struct _ListNode* head;     // the head of the List
  struct _ListNode* tail;     // the tail of the List
  struct _ListNode* current;  // the current node
  int currentPos;             // the current node position
  compFunc compare;           // the function to compare elements
};

// You may add extra definitions here.

// Create a new SortedList. (abort on memory errors)
List* ListCreate(compFunc compF) {
  List* l = (List*)malloc(sizeof(List));
  if (l == NULL) abort();
  l->size = 0;
  l->head = NULL;
  l->tail = NULL;
  l->current = NULL;
  l->currentPos = -1;  // outside
  l->compare = compF;
  ListTestInvariants(l);  // check invariants
  return l;
}

void ListDestroy(List** p) {
  assert(*p != NULL);
  List* l = *p;
  ListClear(l);
  free(l);
  *p = NULL;
}

// Remove all elements.
// Note: this frees the nodes but not the items!
void ListClear(List* l) {
  struct _ListNode* p = l->head;
  struct _ListNode* aux;

  while (p != NULL) {
    aux = p;
    p = aux->next;
    free(aux);
  }

  l->size = 0;
  l->head = NULL;
  l->tail = NULL;
  l->current = NULL;
  l->currentPos = -1;  // outside
}

int ListGetSize(const List* l) {
  return l->size;
}

int ListIsEmpty(const List* l) {
  return (l->size == 0);
}

// Current node functions

// The current node position may be between -1 and size, exclusive.
// It can point inside (0 <= pos < size) or outside (pos == -1) the list.
// When inside, you can get or set the current item.

// Check if current position is inside List.
// Equivalent to 0 <= ListGetCurrentPos(l) < ListGetSize(l).
int ListCurrentIsInside(const List* l) {
  return (l->current != NULL);
}

int ListGetCurrentPos(const List* l) {
  return l->currentPos;
}

void* ListGetCurrentItem(const List* l) {
  assert(ListCurrentIsInside(l));
  return l->current->item;
}

void ListSetCurrentItem(const List* l, void* p) {
  assert(ListCurrentIsInside(l));
  l->current->item = p;
}

// Move to functions

// Move current node to a new position.
// Require -1 <= newPos <= l->size
// newPos == -1 or newPos == l->size means move outside.
void ListMove(List* l, int newPos) {
  assert (-1 <= newPos && newPos <= l->size) ;
  if (newPos == l->size)
    newPos = -1;  // fix newPos

  if (newPos == -1) {  // move outside
    l->current = NULL;
  } else if (newPos == 0) { // move to head
    l->current = l->head;
  } else if (newPos == l->size - 1) { // move to tail
    l->current = l->tail;
  } else {  // move to an inner node
    // Start at head (or current position) and move forward until newPos.
    // vvv SOLUTION vvv
    if (l->currentPos == -1 || newPos < l->currentPos) {
      l->current = l->head;
      l->currentPos = 0;
    }
    for (int i = l->currentPos; i < newPos; i++) {
      l->current = l->current->next;
    }
    // ^^^ SOLUTION ^^^
    
  }
  l->currentPos = newPos;
}

// Move to next. If on tail, move outside. If outside move to head.
void ListMoveToNext(List* l) {
  ListMove(l, l->currentPos < l->size-1 ? l->currentPos + 1 : -1);
}

// Move to previous. If on head, move outside. If outside move to tail.
void ListMoveToPrevious(List* l) {
  ListMove(l, l->currentPos >= 0 ? l->currentPos - 1 : l->size-1);
}
  
void ListMoveToHead(List* l) { ListMove(l, 0); }

void ListMoveToTail(List* l) { ListMove(l, l->size - 1); }

// SEARCH

// Search for the first node with a value that compares==0 with *p.
// If search succeeds, return 0 and move the current node to the found element.
// If search fails. return -1 and don't change the current node.
// (Try to optimize the search to start at the current node if possible.)
int ListSearch(List* l, const void* p) {
  // vvv SOLUTION vvv
  int end = l->size;
  int pos = 0;
  struct _ListNode* sn = l->head;
  int cmp = 1;
  while (pos < end) {
    cmp = l->compare(p, sn->item);
    if (cmp <= 0) break;
    pos++;
    sn = sn->next;
  }
  if (cmp != 0) return -1;
  l->current = sn;
  l->currentPos = pos;
  // ^^^ SOLUTION ^^^
  
  return 0;
}

// You may add extra definitions here.
// vvv SOLUTION vvv

// Search Optimized version.
int ListSearch2(List* l, const void* p) {
  if (l->size == 0) return -1;  // if empty => fail
  int end = l->size;
  int pos = 0;
  struct _ListNode* sn = l->head;
  int cmp;
  if (l->currentPos > 0) {
    pos = l->currentPos;
    sn = l->current;
    cmp = l->compare(p, sn->item);
    if (cmp < 0) {
      end = pos;
      pos = 0;
      sn = l->head;
      cmp = l->compare(p, sn->item);
    }
  }
  // search until end
  while (cmp > 0 && pos+1 < end) {
    pos++;
    sn = sn->next;
    cmp = l->compare(p, sn->item);
  }
  if (cmp != 0) return -1;
  l->current = sn;
  l->currentPos = pos;
  return 0;
}
// ^^^ SOLUTION ^^^

// INSERT

// Insert a node.
// The current node is not changed
// return 0 on success
// return -1 on failure
int ListInsert(List* l, void* p) {
  struct _ListNode* sn = (struct _ListNode*)malloc(sizeof(struct _ListNode));
  if (sn == NULL) abort();
  sn->item = p;
  sn->next = NULL;

  // Empty list
  if (l->size == 0) {
    l->head = l->tail = sn;
    l->size = 1;
    return 0;
  }

  // Search
  struct _ListNode* prev = NULL;
  struct _ListNode* aux = l->head;
  int i = 0;
  while (i < l->size && l->compare(p, aux->item) > 0) {
    prev = aux;
    aux = aux->next;
    i++;
  }

  if (i == l->size) {  // Append after the tail
    l->tail->next = sn;
    l->tail = sn;
    l->size++;
    return 0;
  }

  if (l->compare(p, aux->item) == 0) {  // Already exists !!
    free(sn);
    return -1;  // failure
  }

  // Append before aux
  sn->next = aux;
  if (i == 0)
    l->head = sn;     // Append at the head
  else
    prev->next = sn;  // Append after prev
  if (l->currentPos >= i)
    l->currentPos++;  // Fix currentPos
  l->size++;
  return 0;
}

// Remove functions

// Remove the head of the list and make its next node the new head.
// If the current node is the head, it is replaced by the new head.
void* ListRemoveHead(List* l) {
  assert(l->size > 0);
  void* item = l->head->item;  // item to be removed and returned
  struct _ListNode* sn = l->head->next; // new head (even if NULL)
  if (l->current == l->head) {
    l->current = sn;
    if (l->current == NULL) l->currentPos = -1;
  }
  free(l->head);
  l->head = sn;
  if (l->size == 1) {
    l->tail = NULL;
  }
  l->size--;
  return item;
}

// Remove the tail of the list and make its previous node the new tail.
// If the current node is the tail, it is moved outside.
void* ListRemoveTail(List* l) {
  assert(l->size > 0);
  if (l->current == l->tail) {
    l->current = NULL;
    l->currentPos = -1;
  }
  void* item = l->tail->item;
  struct _ListNode* sn = NULL;
  if (l->size == 1) {
    l->head = NULL;
  } else {
    // find sn = node before tail
    sn = l->head;
    while (sn->next != l->tail) sn = sn->next;
    sn->next = NULL;
  }
  free(l->tail);
  l->tail = sn;
  l->size--;
  return item;
}

// REMOVE current

// Remove the current node and make its next node the current node
void* ListRemoveCurrent(List* l) {
  assert (ListCurrentIsInside(l));
  void* item = l->current->item;
  if (l->currentPos == 0)
    item = ListRemoveHead(l);
  else if (l->currentPos == l->size - 1)
    item = ListRemoveTail(l);
  else {
    // find node before current, change its next field,
    // free current, change current, change size
    // vvv SOLUTION vvv
    struct _ListNode* sn = l->head;
    while (sn->next != l->current) sn = sn->next;
    sn->next = l->current->next;
    free(l->current);
    l->current = sn->next;
    l->size--;
    // ^^^ SOLUTION ^^^
    
  }
  return item;
}

// Tests

void ListTestInvariants(const List* l) {
  assert(l->size >= 0);
  // check equivalence: size==0 <=> head==NULL <=> tail==NULL
  assert ( (l->size == 0) == (l->head == NULL) );
  assert ( (l->size == 0) == (l->tail == NULL) );
  // check equivalence size<=1 <=> head==tail
  assert ( (l->size <= 1) == (l->head == l->tail) );
  // check currentPos is valid
  assert( -1 <= l->currentPos && l->currentPos < l->size );
  // check that position outside <=> current==NULL
  assert( (l->currentPos == -1) == (l->current == NULL) );
  struct _ListNode* sn = l->head;
  for (int i = 0; i < l->size; i++) {
    if (i < l->size-1)
      assert(sn->next != NULL);
    else
      assert(sn == l->tail && sn->next == NULL);
    if (i == l->currentPos)
      assert(sn == l->current);
    sn = sn->next;
  }
}

// You may add extra definitions here.

