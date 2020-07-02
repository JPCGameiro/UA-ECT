//NMEC: 93097
//NOME: João Gameiro

// Complete the functions (marked by ...)
// so that it passes all tests in DateTimeTest.

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "DateTime.h"

// You may add auxiliary definitions and declarations here, if you need to.





// Check if given arguments would form a valid DateTime.
// A DateTime is valid iff both the date part and the time part are valid.
int DateTimeIsValid(int yy, int mm, int dd, int h, int m, int s) {
  return DateIsValid(yy, mm, dd) & TimeIsValid(h, m, s);
}

// Allocate an initialize a DateTime instance.
// Return the pointer to the created instance or NULL if an error occurs.
DateTime* DateTimeCreate(int yy, int mm, int dd, int h, int m, int s) {
  assert(DateTimeIsValid(yy, mm, dd, h, m, s));
  DateTime* d = (DateTime*) malloc(sizeof(*d));
  if(d==NULL) return d;   
  Date* date = DateCreate(yy, mm, dd);
  d->date = *date;
  DateDestroy(&date);
  d->time = TimeCreate(h, m, s);
  return d;

}

// Free a DateTime instance and invalidate its pointer.
void DateTimeDestroy(DateTime **pdt) {
  free(*pdt);
  *pdt = NULL;
}

// Compare datetimes a and b.
// Return an integer >0 if a>b, 0 if a==b and <0 if a<b.
int DateTimeCompare(DateTime *a, DateTime *b) {
  // KEEP IT SIMPLE.  Call the compare functions in Date and Time!
  if(DateCompare(&a->date, &b->date)==0)
    return TimeCompare(a->time, b->time);
  else
    return DateCompare(&(a->date), &(b->date));
}

// Temporary buffer for DateTimeFormat output.
static char buf[64];  // 20 should suffice

// Return *dt as a formatted string.
// The string is returned in a shared buffer, which is overwritten in each call!
char *DateTimeFormat(const DateTime *dt) {
  if (dt==NULL)
    snprintf(buf, sizeof(buf), "NULL");
  else {
    char *sd = DateFormat(&(dt->date), YMD);
    char *st = TimeFormat(dt->time);
    sprintf(buf, "%s %s", sd, st);
  }
  return buf;
}

// Parse a properly formatted string and create a DateTime instance.
DateTime *DateTimeParse(char *str) {
  DateTime * dt = NULL;
  Date *d = DateParse(str, YMD);
  if (d != NULL) {
    dt = DateTimeCreate(d->year, d->month, d->day, 0, 0, 0);
    DateDestroy(&d);  // not needed any more
    char *sep = strchr(str, ' '); // find date and time separator
    if (dt != NULL && sep != NULL) {  // the time part is optional
      Time t = TimeParse(sep+1);
      if (t != TimeERR) {
        dt->time = t;
      }
    }
  }
  return dt;
}
