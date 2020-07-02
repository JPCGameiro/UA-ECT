#ifndef _DATE_
#define _DATE_

#include <inttypes.h>

struct _date {
  uint16_t year;
  uint8_t month;
  uint8_t day;
};

// The Date type. (To be used in clients.)
typedef struct _date Date;

// Constants
extern const Date DateMIN;
extern const Date DateMAX;

// Macros to select date format
#define YMD 0
#define DMY 1
#define MDY 2

//Date creater (constructor)
Date* DateCreate(int yy, int mm, int dd) ;

//Date destroyer
void DateDestroy(Date* *pd) ;

//Check if a Date if valid
int DateIsValid(int yy, int mm, int dd) ;

//return the number of day of the month mm of the year yy
int DateDaysInMonth(int yy, int mm) ;

//check if a year is a Leap Year
int DateIsLeapYear(int yy) ;

//Compare two Dates
int DateCompare(const Date* a, const Date* b) ;

//Increment a Date
void DateIncr(Date* d) ;

//Decrement a Date
void DateDecr(Date* d) ;

//Return a formated char* representation of Date d
char* DateFormat(const Date* d, int FMT) ;

//Parse a char* str and return a new Date
Date* DateParse(const char* str, int FMT) ;

#endif //_DATE_
