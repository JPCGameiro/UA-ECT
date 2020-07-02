#ifndef _TIMEINTERVAL_
#define _TIMEINTERVAL_

#include "DateTime.h"

typedef struct {
  DateTime* start;
  DateTime* end;
  char* id;
} TimeInterval;

TimeInterval* TimeIntervalCreate(DateTime *t1, DateTime *t2, const char *id) ;
void TimeIntervalDestroy(TimeInterval **pti) ;
int TimeIntervalCompare(TimeInterval *ti1, TimeInterval *ti2) ;
int TimeIntervalOverlaps(TimeInterval *ti1, TimeInterval *ti2) ;
int TimeIntervalContains(TimeInterval *ti1, TimeInterval *ti2) ;
char* TimeIntervalFormat(TimeInterval *ti) ;

#endif
