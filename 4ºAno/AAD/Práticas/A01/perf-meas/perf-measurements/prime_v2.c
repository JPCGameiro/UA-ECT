/*
** simple prime counting program using the Eratosthenes sieve
**
** ACA 2016-2017
*/

#include <time.h>
#include <stdio.h>
#include <stdlib.h>

#define p_limit 1000000000

int main(void)
{
    unsigned int i,p;
    char *sieve;
    clock_t dt;

    sieve = (char *)calloc((size_t)((p_limit + 1) / 2),sizeof(char));
    if(sieve == NULL)
      return 1;
    dt = clock();
    // sieve
    for(p = 3;p * p <= p_limit;p += 2)
      if(sieve[p / 2] == (char)0)
        for(i = p * p;i <= p_limit;i += 2 * p)
          sieve[i / 2] = (char)1;
    // count
    i = 1; // 2 is the only even prime
    for(p = 3;p <= p_limit;p += 2)
      if(sieve[p / 2] == (char)0)
        i++; // one more prime
    // done
    free(sieve);
    printf("The number of primes up to %u is %u\n",p_limit,i);
    dt = clock() - dt;
    printf("CPU time = %f\n",(double)dt / (double)CLOCKS_PER_SEC);
    return 0;
}
