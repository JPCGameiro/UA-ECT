/*
 * Joaquim Madeira - February 22, 2020
 */

#include <stdio.h>

#include "elapsed_time.h"

/* definition of the 64-bit unsigned integer type */
typedef unsigned long long u64;

int main(void) {
  /* Large CONSTANT values */

  const u64 SMALLER = 100000000;  // 10^8

  const u64 LARGER = 10000000000;  // 10^10

  /* FIRST TEST */

  (void)elapsed_time();

  for (u64 i = 0; i < SMALLER; i++) {
    /* DO NOTHING !! */
  }

  double smallerRunningTime = elapsed_time();

  printf("\n");

  printf("Time spent for %20llu iterations : %12.9f\n", SMALLER,
         smallerRunningTime);

  printf("\n");

  /* SECOND TEST */

  (void)elapsed_time();

  for (u64 i = 0; i < LARGER; i++) {
    /* DO NOTHING !! */
  }

  double largerRunningTime = elapsed_time();

  printf("\n");

  printf("Time spent for %20llu iterations : %12.9f\n", LARGER,
         largerRunningTime);

  printf("\n");

  return 0;
}
