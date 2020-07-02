#ifndef _SCHEDULINGSEQUENCE_
#define _SCHEDULINGSEQUENCE_

#include "TimeInterval.h"

typedef struct {
  int capacity;  // the maximum number capacity of this SchedulingSequence
  int size;      // the number o TimeIntervals stored currently
  TimeInterval **intervals; // points to an array of TimeInterval pointers
} SchedulingSequence;

// Create a SchedulingSequence capable of storing up to (capacity) intervals.
SchedulingSequence* SchedulingSequenceCreate(int capacity) ;

// Destroy SchedulingSequence *pss
void SchedulingSequenceDestroy(SchedulingSequence **pss) ;

int SchedulingSequenceIsEmpty(SchedulingSequence *ss) ;

int SchedulingSequenceIsFull(SchedulingSequence *ss) ;

// Add interval *ti to *ss.
// Return true on success,
// return false if *ti overlaps any of the intervals in *ss.
int SchedulingSequenceAdd(SchedulingSequence *ss, TimeInterval *ti) ;

// Get the interval at position (idx) of *ss.
// idx=0 is the first position idx=ss->size-1 is the last position.
// Precondition: 0 <= idx < ss->size.
TimeInterval *SchedulingSequenceGet(SchedulingSequence *ss, int idx) ;

// Remove the interval at position (idx) of *ss, and return it.
// idx=0 is the first position idx=ss->size-1 is the last position.
// Precondition: 0 <= idx < ss->size.
TimeInterval *SchedulingSequencePop(SchedulingSequence *ss, int idx) ;

#endif
