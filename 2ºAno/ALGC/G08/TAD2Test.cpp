//
// João Manuel Rodrigues, AlgC, May 2020
//
// TESTING SchedulingSequence using array TAD.
//

//Run this program with arguments in the following list.
//ARGS 1
//ARGS 2
//ARGS 3
//ARGS 4
//ARGS 5 
//ARGS 6
//ARGS 7
//ARGS 8
//ARGS 9
//ARGS 10
//ARGS 11


#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "DateTime.h"
#include "TimeInterval.h"

#include "SchedulingSequence.h"

// MACRO to print expression X of type int (int X) and its result
#define SHOWint(X) do{ printf("%s -> %d\n", #X, (X)); }while(0)

// MACRO to print expression X of type string (char* X) and its result
#define SHOWstr(X) do{ printf("%s -> %s\n", #X, (X)); }while(0)

// MACRO to print expression X of type TimeInterval and its result
#define SHOWDT(X) do{ printf("%s -> %s\n", #X, DateTimeFormat(X)); }while(0)

// MACRO to print expression X of type TimeInterval and its result
#define SHOWTI(X) do{ printf("%s -> %s\n", #X, TimeIntervalFormat(X)); }while(0)

// (C preprocessor macros are quite powerful.)

// Returns +1, -1 or 0 if d>0, d<0 or d==0
int signum(int d) {
  return (d>0)-(d<0);
}

// Returns '+', '-' or '0' if d>0, d<0 or d==0
char signchr(int d) {
  return d>0 ? '+' : d<0 ? '-' : '0';
}

int main(int argc, char* argv[]) {
  int lasttest = 9999;
  if (argc > 1) {
    lasttest = atoi(argv[1]);  // last test to perform
  }
  int test = 0;

  if (++test > lasttest) return 0;
  printf("\n%d) Create DateTimes---\n", test);
  const int NDT = 10; // number of datetimes
  DateTime *dt[NDT];
  dt[0] = DateTimeCreate(2019, 12, 30, 14, 0, 0);
  dt[1] = DateTimeCreate(2019, 12, 30, 23, 0, 0);
  dt[2] = DateTimeCreate(2019, 12, 31,  9, 0, 0);
  dt[3] = DateTimeCreate(2019, 12, 31, 12, 0, 0);
  dt[4] = DateTimeCreate(2020,  1,  1, 11, 0, 0);
  dt[5] = DateTimeCreate(2020,  1,  1, 18, 0, 0);
  dt[6] = DateTimeCreate(2020,  2, 28, 20, 0, 0);
  dt[7] = DateTimeCreate(2020,  2, 29,  8, 0, 0);
  dt[8] = DateTimeCreate(2020,  2, 29, 20, 0, 0);
  dt[9] = DateTimeCreate(2020,  3,  1,  8, 0, 0);
  
  if (++test > lasttest) return 0;
  printf("\n%d) DateTimeCompare---\n", test);
  printf("%-19s\t%-19s\t%s\n", "dt[i]", "dt[j]", "cmp");
  for (int i = 1; i < NDT; i+=3) {
    for (int j = 0; j < NDT; j+=4) {
      printf("%s\t", DateTimeFormat(dt[i]));
      printf("%s\t", DateTimeFormat(dt[j]));
      printf("%c\n", signchr(DateTimeCompare(dt[i], dt[j])));
    }
  }

  if (++test > lasttest) return 0;
  printf("\n%d) TimeIntervalCreate---\n", test);
  // We can dynamically create (allocate) a TimeInterval variable
  // and initialize it.
  const int NTI = 7;  // number of timeintervals
  TimeInterval* ti[NTI];
  ti[0] = TimeIntervalCreate(dt[0], dt[1], "0-Passeio Serra");
  ti[1] = TimeIntervalCreate(dt[2], dt[3], "1-Compras fim-de-ano");
  ti[2] = TimeIntervalCreate(dt[3], dt[4], "2-Rave fim-de-ano");
  ti[3] = TimeIntervalCreate(dt[2], dt[5], "3-Férias fim-de-ano");
  ti[4] = TimeIntervalCreate(dt[4], dt[7], "4-Estudar AlgC");
  ti[5] = TimeIntervalCreate(dt[8], dt[8], "5-Telefonar primo");
  ti[6] = TimeIntervalCreate(dt[6], dt[9], "6-Maratona TV");
  
  for (int i = 0; i < NTI; i++) {
    SHOWTI(ti[i]);
  }
  
  if (++test > lasttest) return 0;
  printf("\n%d) TimeIntervalCompare/Overlaps/Contains---\n", test);
  printf("%-14s\t%-14s\t%s\t%s\t%s\n", "ti[i]", "ti[j]", "cmp", "ovr", "cnt");
  for (int i = 0; i < NTI; i+=2) {
    for (int j = 1; j < NTI; j+=2) {
      int cmp = TimeIntervalCompare(ti[i], ti[j]);
      int ovr = TimeIntervalOverlaps(ti[i], ti[j]);
      int cnt = TimeIntervalContains(ti[i], ti[j]);
      printf("%-.14s\t%-.14s\t%c\t%d\t%d\n",
              ti[i]->id, ti[j]->id, signchr(cmp), ovr, cnt);
    }
  }

  if (++test > lasttest) return 0;
  srandom(lasttest);
  printf("\n%d) SchedulingSequenceCreate---\n", test);
  SchedulingSequence *ss1 = SchedulingSequenceCreate(NTI);
  SHOWint( ss1 != NULL );
  SHOWint( ss1->size );
  SHOWint( ss1->capacity );
  
  if (++test > lasttest) return 0;
  printf("\n%d) SchedulingSequenceAdd---\n", test);
  int suc = 0;  // return status of Add
  int bal = 0;  // current balance of successes-failures
  while (!SchedulingSequenceIsFull(ss1) && bal>-5) {
    int i = random()%NTI;
    SHOWint( i );
    SHOWint( suc = SchedulingSequenceAdd(ss1, ti[i]) );
    bal += (2*suc-1); // +1 for success -1 for failure
  }
  
  if (++test > lasttest) return 0;
  printf("\n%d) SchedulingSequenceGet---\n", test);
  for (int idx = 0; idx < ss1->size; idx++) {
    SHOWint( idx );
    SHOWTI( SchedulingSequenceGet(ss1, idx) );
  }
  
  if (++test > lasttest) return 0;
  printf("\n%d) SchedulingSequencePop---\n", test);
  while (!SchedulingSequenceIsEmpty(ss1)) {
    int idx = random()%(ss1->size);
    SHOWint( idx );
    SHOWTI( SchedulingSequencePop(ss1, idx) );
  }
  
  SchedulingSequenceDestroy(&ss1);
  
  // Free everything
  // (If you comment out any of these, valgrind should detect it!)
  for (int i = 0; i < NTI; i++) {
    TimeIntervalDestroy(&(ti[i]));
  }
  for (int i = 0; i < NDT; i++) {
    DateTimeDestroy(&(dt[i]));
  }
  
  return 0;
}

