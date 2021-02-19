/*
 * \brief Generation of a busy waiting delay
 *
 * (c) 2004--2018 Artur Pereira <artur at ua.pt>
 */

#include "bwdelay.h"

#include <math.h>
#include <iostream>

/*
 * A busy waiting delay
 */
void bwDelay(int n)
{
    double b = 0.0;
    double c = 0.0;
    /* generate time delay */
    int i;
    for (i = 0; i < n; i++)
    {
        b = cos(c + M_PI / 4);
        c = sqrt(fabs(b));
    }
}

/*
 * A busy random, busy waiting delay
 */
void bwRandomDelay(int n)
{
    int k = (int)(((double)rand() / (RAND_MAX + 1.0)) * (n+1));
    bwDelay(k);
}

