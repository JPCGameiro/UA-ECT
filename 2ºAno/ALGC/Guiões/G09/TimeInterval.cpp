// SELLECT ALL AND PASTE YOUR SOLUTION HERE!
//NMEC: 93097
//NOME: João Gameiro

// Complete the functions (marked by ...)
// so that it passes all tests in DateTimeTest.

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "TimeInterval.h"

// You may add auxiliary definitions and declarations here, if you need to.



// Every valid instance of a TimeInterval must satisfy this condition.
static int invariant(TimeInterval *ti) {
  return DateTimeCompare(ti->start, ti->end) <= 0;
}

// Allocate and init a TimeInterval, given a start and end instant and an ID.
// The start of the interval must precede or equal the end.
// The interval is considered closed-open, [*t1, *t2[, that is,
// the interval contains the start instant, but not the end instant.
// Empty intervals are acceptable.
// The start and end objects are shared with the client,
// but the ID must be copied.
TimeInterval* TimeIntervalCreate(DateTime *t1, DateTime *t2, const char *id) {
  // Fill in the required precondition.
  assert(DateTimeCompare(t1, t2) <= 0);
  TimeInterval* ti = (TimeInterval*)malloc(sizeof *ti);
  if (ti != NULL) {
    ti->start = t1;
    ti->end = t2;
    // Create a copy of the id string! Use malloc+strcpy or strdup.
    ti->id = strdup(id);  
  }
  assert (invariant(ti));  // the invariant must be ensured here!
  return ti;
}

// Destroy the TimeInterval instance pointed to by *pti.
// This frees the memory used by **pti and by (*pti)->id.
// (*pti)->start and (*pti)->end should be left untouched.
void TimeIntervalDestroy(TimeInterval **pti) {
  assert (*pti != NULL);
  free((*pti)->id);   // Free the id field memory
  free(*pti);          // Free the TimeInterval structure memory
  *pti = NULL;
}

// Compare TimeIntervals.
// Return negative if *ti1 ends before *ti2 starts,
// return positive if *ti1 starts after *ti2 ends, and
// return zero if *ti1 and *ti2 overlap.
// NOTE: this does not establish a total order!
// Result=0 does not imply that *ti1 and *ti2 are equal.
int TimeIntervalCompare(TimeInterval *ti1, TimeInterval *ti2) {
  if (DateTimeCompare(ti1->start, ti2->start) == 0 && DateTimeCompare(ti1->end, ti2->end) == 0) return 0;
  else if (DateTimeCompare(ti1->end, ti2->start) <= 0) return -1;
  else if (DateTimeCompare(ti1->start, ti2->end) >= 0) return 1;
  return 0;  
  
}

// Return true (1) if intervals *ti1 and *ti2 overlap, false otherwise.
int TimeIntervalOverlaps(TimeInterval *ti1, TimeInterval *ti2) {
  return TimeIntervalCompare(ti1, ti2) == 0;
}

// Return true (1) if interval *ti1 contains *ti2, false otherwise.
int TimeIntervalContains(TimeInterval *ti1, TimeInterval *ti2) {
  if((DateTimeCompare(ti1->start, ti2->start) <= 0) && (DateTimeCompare(ti1->end, ti2->end) >= 0))
    return 1;
  return 0;  
}

// Temporary buffer for TimeIntervalFormat output.
static char buf[256];

char* TimeIntervalFormat(TimeInterval *ti) {
  if (ti==NULL)
    snprintf(buf, sizeof(buf), "NULL");
  else {
    strcpy(buf, "[");
    strcat(buf, DateTimeFormat(ti->start));
    strcat(buf, ", ");
    strcat(buf, DateTimeFormat(ti->end));
    strcat(buf, "[(");
    strncat(buf, ti->id, sizeof(buf) - strlen(buf) - 2);
    strcat(buf, ")");
  }
  return buf;
}

// You may add auxiliary definitions and declarations here, if you need to.

