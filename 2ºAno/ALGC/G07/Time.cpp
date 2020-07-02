//NMEC: 93097
//NOME: João Pedro Costa Gameiro

// The Time data type represents times in a 24-hour clock.
//
// The times are actually stored as a single integer:
// the number of seconds elapsed since the start of the day.
//
// But the abstract data type includes constructors and getter functions
// that interpret times as (Hour, Minute, Second) tuples.

#include <assert.h>
#include <stdio.h>
#include "Time.h"

// The number of seconds in a day
#define TimeFULLDAY ((Time)24*60*60)

// Constants
const Time TimeMIN = (Time)0;               // Smallest valid time value
const Time TimeMAX = (Time)(TimeFULLDAY-1); // Largest valid time value

const Time TimeERR = (Time)(-1);  // Invalid time value to indicate errors


// Function to test desired internal invariant for valid Time values:
// the time should be within allowed MIN and MAX.
static int invariant(Time t) {
  return TimeMIN <= t && t <= TimeMAX;
}

// Return a Time value corresponding to hh:mm:ss.
// Note that a Time is actually just an integer
// that stores the total number of seconds since midnight.
Time TimeCreate(int hh, int mm, int ss) {
  assert( TimeIsValid(hh, mm, ss) );
  Time t = 3600*hh + 60*mm + ss;  // Fill in the formula for total seconds since midnight
  assert( invariant(t) );         // constructor must ensure the invariant!
  return t;
}

// Check if a (hh, mm, ss) tuple forms a valid time-of-day.
// (May be used to check compliance before calling TimeCreate, for example.)
int TimeIsValid(int hh, int mm, int ss) {
  return 0<=hh && hh<24 &&
         0<=mm && mm<60 &&
         0<=ss && ss<60;
}

// Getters

int TimeGetHH(Time t) {
  return t/3600;
}

int TimeGetMM(Time t) {
  return (t-3600*(t/3600))/60;
}

int TimeGetSS(Time t) {
  return t-3600*(t/3600)-60*((t-3600*(t/3600))/60);
}

// Get the total seconds since start of day
int TimeGetSeconds(Time t) {
  return t;
}

// String buffer for TimeFormat results.
// CAREFUL! this is overwritten whenever TimeFormat is called!
static char str[9];

// Convert t into a string formatted as "hh:mm:ss".
// CAREFUL: the returned string will be overwritten by the next call.
// You should strcpy or strdup the result if you need persistence!
char* TimeFormat(Time t) {
  snprintf(str, sizeof(str), "%02d:%02d:%02d",
              TimeGetHH(t), TimeGetMM(t), TimeGetSS(t));
  return str;
}

// Parse a time string in the format "hh:mm:ss" and return it,
// or return TimeERR if the string is an invalid time.
// MODIFY the function so that it also accepts times in format "hh:mm".
Time TimeParse(const char* str) {
  Time t = TimeERR;
  int hh, mm, ss;
  int n = sscanf(str, "%02d:%02d:%02d", &hh, &mm, &ss);
  if (n == 3 && TimeIsValid(hh, mm, ss)) {
    t = TimeCreate(hh, mm, ss);
  }
  else if(n<3){
    t = TimeCreate(hh, mm, 0);
  }
  assert( t==TimeERR || invariant(t) );  // either TimeERR or ensure the invariant
  return t;
}

// Compare times a and b.
// Return an integer >0 if a>b, 0 if a==b and <0 if a<b.
int TimeCompare(Time a, Time b) {
  if(a>b)
    return 1;
  else if(a==b)
    return 0;
  else  
    return -1;
}

// Add time a and time b and return a valid time (in 24-hour clock).
// For example: adding 2:30:40 to 23:30:00 should return 02:00:40.
Time TimeAdd(Time a, Time b) {
  // KEEP IT SIMPLE, PLEASE!
  int aux = a+b;
  while(aux > TimeMAX){
    aux = aux-(TimeMAX+1);
  }
  Time t = aux;
    
  assert( invariant(t) );  // must ensure the invariant!
  return t;
}

// Subtract time b from time a and return a valid time (in 24-hour clock).
// For example: 2:30:00 minus 4:10:20 should return 22:19:40.
Time TimeSubtract(Time a, Time b) {
  // KEEP IT SIMPLE, PLEASE!
  int aux = a-b;
  while(aux < TimeMIN){
    aux = aux+(TimeMAX+1);
  }
  Time t = aux;
  
  assert( invariant(t) );  // must ensure the invariant!
  return t;
}
