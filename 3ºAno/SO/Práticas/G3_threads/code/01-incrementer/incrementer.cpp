/*
 * A program
 *
 * Sintaxe: incrementer [options]
 * 
 * \remarks 
 * The return status of the system functions called are intentionally ignored, 
 * as to not mess the illustration purpose of the example. 
 * In a real implementation, that should not be done. 
 *
 * \author (2016) Artur Pereira <artur at ua.pt>
 */

#include <unistd.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>
#include <getopt.h>
#include <libgen.h>
#include <pthread.h>

#include "inc_mod.h"
#include "bwdelay.h"
#include "thread.h"

#define OTHER_TIME 10000

#define USAGE "Synopsis %s [options]\n"\
	"\t----------+--------------------------------------------\n"\
	"\t  Option  |          Description                       \n"\
	"\t----------+--------------------------------------------\n"\
	"\t -i num   | number of increments (dfl: 1000)           \n"\
	"\t -t num   | number of 'threads' (dfl: 5)               \n"\
	"\t -h       | this help                                  \n"\
	"\t----------+--------------------------------------------\n"

void *incrementer(void *arg);

int main (int argc, char *argv[])
{

  /* */
  int niter = 1000;  /* default number of iterations */
  int nthr = 5;      /* default number of threads */

  /* processamento da linha de comando */
  const char *optstr = "i:t:h";

  int option;
  do
  {
    switch ((option = getopt(argc, argv, optstr)))
    {
      case 'i':
		    niter = atoi(optarg);
        break;

      case 't':
	  	  nthr = atoi(optarg);
        break;

      case 'h':
        printf(USAGE, basename(argv[0]));
        return 0;
        break;

      case -1:
        break;

      default:
        fprintf(stderr, "Opção não válida\n");
        fprintf(stderr, USAGE, basename(argv[0]));
        return 1;
    }
  } while (option >= 0);

  printf("Launching %d threads, each one performing %d increments\n", nthr, niter);

  /* setting initial value of module variable */
  v_set(0);

  /* launching the 'threads' */
  pthread_t thr[nthr];
  int i;
  for (i=0; i<nthr; i++)
  {
    thread_create(thr+i, NULL, incrementer, (void *)&niter);
  }

  /* wait for threads to conclude */
  for (i=0; i<nthr; i++)
  {
    thread_join(thr[i], NULL);
  }

  /* print variable value and quit */
  printf("final value = %d\n", v_get());
  return 0;
}


/*
 * thread routine
 */
void *incrementer(void *arg)
{
  int i, n;
  n = *((int *)arg);
  for (i=0; i<n; i++)
  {
	  v_inc();
    bwDelay(OTHER_TIME);
  }
  return NULL;
}
