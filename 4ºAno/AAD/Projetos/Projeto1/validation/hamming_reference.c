/**
 *  Reference implementation of the [15,11] Hamming code.
 *  Bear in mind that this code DOES NOT portray the best implementation in hardware.
 *
 *  Ant�nio Rui Borges, November 2021
 */

#include <stdio.h>
#include <string.h>

int main (int argc, char **argv)
{
  FILE *parCkMat;                                          /* stream handler of the parity-check matrix */
  int h[4][15];                                            /* parity-check matrix */
  int g[11][15];                                           /* code generating matrix */
  int i, j;                                                /* counting variables */

  if ((argc != 2) || (strlen (argv[1]) == 0))
     { fprintf(stderr,"usage: %s parity-check matrix filename\n",argv[0]);
       return 1;
     }

  if ((parCkMat = fopen (argv[1], "r")) == NULL)
     { fprintf(stderr,"usage: %s parity-check matrix filename\n",argv[0]);
       return 1;
     }
  for (i = 0; i < 4; i++)
    for (j = 0; j < 15; j++)
    { if (fscanf (parCkMat, "%d", &h[i][j]) != 1)
         { fclose (parCkMat);
           fprintf(stderr,"error in the parity-check matrix format!\n");
           return 1;
         }
      if ((h[i][j] != 0) && (h[i][j] != 1))
         { fclose (parCkMat);
           fprintf(stderr,"error in some coefficient value of the parity-check matrix!\n");
           return 1;
         }
    }
  fclose (parCkMat);

  for (i = 0; i < 11; i++)
    for (j = 0; j < 15; j++)
      if (j < 11)
         { if (i == j)
              g[i][j] = 1;
              else g[i][j] = 0;
         }
         else g[i][j] = h[j-11][i];
  fprintf (stdout, "\n Code generating matrix G\n\n");
  for (i = 0; i < 11; i++)
  { for (j = 0; j < 15; j++)
      fprintf (stdout, "%d ", g[i][j]);
    fprintf (stdout,"\n");
  }
  fprintf (stdout, "\n Parity check matrix H\n\n");
  for (i = 0; i < 4; i++)
  { for (j = 0; j < 15; j++)
      fprintf (stdout, "%d ", h[i][j]);
    fprintf (stdout,"\n");
  }

  int opt;                                                 /* option */
  int m[11],                                               /* message */
      ml[11];                                              /* received message */
  int x[15],                                               /* encoded message */
      y[15],                                               /* distorted coded message */
      p[4],                                                /* parity check */
      pValue;                                              /* parity value */

  do
  { fprintf (stdout, "\n");
    fprintf (stdout, "1 - message encoding\n");
    fprintf (stdout, "2 - message decoding\n");
    fprintf (stdout, "0 - end of program\n");
    fprintf (stdout, "What is your choice? ");
    fscanf (stdin, "%d", &opt);
    fscanf (stdin, "%*[^\n]");
    fscanf (stdin, "%*c");
    fprintf (stdout, "\n");
    switch (opt)
    { case 1: /* message encoding */
              fprintf (stdout, "message m(m1,m2, ... ,m11) = ");
              for (i = 0; i < 11; i++)
              { fscanf (stdin, "%d", &m[i]);
                if ((m[i] != 0) && (m[i] != 1))
                   { fprintf(stderr,"i = %d : m[i] = %d : error in some bit of the message!\n", i, m[i]);
                     return 1;
                   }
              }
              fscanf (stdin, "%*[^\n]");
              fscanf (stdin, "%*c");
              for (j = 0; j < 15; j++)
              { x[j] = 0;
                for (i = 0; i < 11; i++)
                  x[j] += m[i] * g[i][j];
                x[j] %= 2;
              }
              fprintf (stdout, "encoded message x(x1,x2, ... ,x15) = ");
              for (i = 0; i < 15; i++)
                fprintf (stdout, "%d ",x[i]);
              fprintf (stdout, "\n");
              break;
      case 2: /* message decoding */
              fprintf (stdout, "distorted encoded message y(y1,y2, ... ,y15) = ");
              for (i = 0; i < 15; i++)
              { fscanf (stdin, "%d", &y[i]);
                if ((y[i] != 0) && (y[i] != 1))
                   { fprintf(stderr,"i = %d : y[i] = %d : error in some bit of the distorted encoded message!\n", i, y[i]);
                     return 1;
                   }
              }
              fscanf (stdin, "%*[^\n]");
              fscanf (stdin, "%*c");
              for (i = 0; i < 11; i++)
                ml[i] = y[i];
              pValue = 0;
              for (i = 0; i < 4; i++)
              { 
                p[i] = 0;
                for (j = 0; j < 15; j++)
                  p[i] += h[i][j] * y[j];
                p[i] %= 2;
                pValue += (p[i] << (3 - i));
              }
              if ((pValue != 0) && (pValue != 1) && (pValue != 2) && (pValue != 4)  && (pValue != 8))
                 for (i = 0; i < 11; i++)
                   if (pValue == (8 * h[0][i] + 4 * h[1][i] + 2 * h[2][i] + h[3][i]))
                      { ml[i] = (ml[i] + 1) % 2;
                        break;
                      }
              fprintf (stdout, "retrieved message ml(ml1,ml2, ... ,ml11) = ");
              for (i = 0; i < 11; i++)
                fprintf (stdout, "%d ",ml[i]);
              fprintf (stdout, "\n");
              break;
    }
  } while (opt != 0);

  return 0;
}
