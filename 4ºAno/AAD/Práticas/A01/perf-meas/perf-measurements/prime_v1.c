/*
** simple prime producing program using trial division
**
** ACA 2016-2017
*/

#include <stdio.h>
#include <time.h>

#define p_limit 1e7

int main(void)
{
    unsigned int i,p,n_primes,is_prime;
    clock_t dt;

    dt = clock();
    n_primes = 1; /* 2 is prime */
    for(p = 3;p <= p_limit;p += 2)
    {
       is_prime = 1;
       for(i = 3;i * i <= p && is_prime != 0;i += 2)
           if(p % i == 0)
               is_prime = 0;
       if(is_prime != 0)
           n_primes++;
    }
    printf("Number of primes = %u\n",n_primes);
    dt = clock() - dt;
    printf("CPU time = %f\n",(double)dt / (double)CLOCKS_PER_SEC);
    return 0;
}
