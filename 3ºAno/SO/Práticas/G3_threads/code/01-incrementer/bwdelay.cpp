/*
 * \brief Generation of a busy waiting delay
 *
 * (c) 2004 Artur Pereira <artur@ua.pt>
 */

#include "bwdelay.h"

#include <math.h>

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

