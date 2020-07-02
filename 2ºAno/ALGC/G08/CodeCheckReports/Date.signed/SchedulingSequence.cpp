//NMEC: 93097
//NOME: João Gameiro

// Complete the functions (marked by ...)
// so that it passes all tests in DateTimeTest.

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "SchedulingSequence.h"

// You may add auxiliary definitions and declarations here, if you need to.

void exchange(TimeInterval *p0, TimeInterval *p1){
  TimeInterval tmp = *p0;
  *p0 = *p1;
  *p1 = tmp;
}


// Create a SchedulingSequence capable of storing up to (capacity) intervals.
SchedulingSequence* SchedulingSequenceCreate(int capacity) {
  assert(capacity >= 0);
  // You must allocate space for the struct and for the intervals array!
  SchedulingSequence* s = (SchedulingSequence*)malloc(sizeof(SchedulingSequence));
  if(s==NULL) return NULL;
  s->capacity = capacity;
  s->size = 0;
  s->intervals = (TimeInterval**)malloc(capacity * sizeof(TimeInterval*));
  return s;

}

// Destroy SchedulingSequence *pss
void SchedulingSequenceDestroy(SchedulingSequence **pss) {
  assert(*pss != NULL);
  free((*pss)->intervals);
  free(*pss);
  *pss = NULL; 

}

int SchedulingSequenceIsEmpty(SchedulingSequence *ss) {
  return ss->size == 0;
}

int SchedulingSequenceIsFull(SchedulingSequence *ss) {
  return ss->size == ss->capacity;
}

// Add interval *ti to *ss.
// Return true on success,
// return false if *ti overlaps any of the intervals in *ss.
int SchedulingSequenceAdd(SchedulingSequence *ss, TimeInterval *ti) {
  assert(!SchedulingSequenceIsFull(ss));
  if(ss->size == 0){
    ss->intervals[ss->size] = ti;
    ss->size++;
    return 1;
  }
  
  int i;
  for(i=0;i<ss->size;i++){
    if(TimeIntervalOverlaps(ss->intervals[i], ti)==1)
      return 0;
    if(TimeIntervalCompare(ss->intervals[i], ti) >= 0)
      break;  
  }
  ss->intervals[ss->size] = ti;
  ss->size++;

  for(int j=ss->size-1;j>i;j--)
    exchange(ss->intervals[j-1], ss->intervals[j]);
  return 1;

}

// Get the interval at position (idx) of *ss.
// idx=0 is the first position idx=ss->size-1 is the last position.
// Precondition: 0 <= idx < ss->size.
TimeInterval *SchedulingSequenceGet(SchedulingSequence *ss, int idx) {
  assert (0 <= idx && idx < ss->size);
  return ss->intervals[idx];

}

// Remove the interval at position (idx) of *ss, and return it.
// idx=0 is the first position idx=ss->size-1 is the last position.
// Precondition: 0 <= idx < ss->size.
TimeInterval *SchedulingSequencePop(SchedulingSequence *ss, int idx) {
  assert (0 <= idx && idx < ss->size);
  // This implies !SchedulingSequenceIsEmpty(ss).
  TimeInterval *aux = ss->intervals[idx];
  for(int i=idx;i<ss->size-1;i++)
    ss->intervals[i] = ss->intervals[i+1];
  ss->size--;
  return aux;

}

// You may add auxiliary definitions and declarations here, if you need to.



