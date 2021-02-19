/*
 * A program
 *
 * Sintaxe: incrementer [options]
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
#include "process.h"

#define OTHER_TIME 1000

#define USAGE "Synopsis %s [options]\n"\
    "\t----------+--------------------------------------------\n"\
"\t  Option  |          Description                       \n"\
"\t----------+--------------------------------------------\n"\
"\t -i num   | number of increments (dfl: 1000)           \n"\
"\t -p num   | number of processes (dfl: 5)               \n"\
"\t -h       | this help                                  \n"\
"\t----------+--------------------------------------------\n"

/* The incrementer routine */
int incrementer(int niter)
{
    int i;
    for (i=0; i<niter; i++)
    {
        v_inc();
        bwDelay(OTHER_TIME);
    }
    exit (EXIT_SUCCESS);
}


/* launcher of a process to run a given routine */
void proc_create(int * pidp, int (*routine)(int), int arg)
{
    int pid = pfork();
    switch (pid)
    {
        case 0:
            break;

        default:
            *pidp = pid;
            return;
    }

    /* child side: run given routine */
    routine(arg);
}

int main (int argc, char *argv[])
{

    /* */
    int niter = 1000;  /* default number of iterations */
    int nproc = 5;      /* default number of threads */

    /* processamento da linha de comando */
    const char *optstr = "i:p:h";

    int option;
    while ((option = getopt(argc, argv, optstr)) != -1)
    {
        switch (option)
        {
            case 'i':
                niter = atoi(optarg);
                break;

            case 'p':
                nproc = atoi(optarg);
                break;

            case 'h':
                printf(USAGE, basename(argv[0]));
                return 0;
                break;

            default:
                fprintf(stderr, "Opção não válida\n");
                fprintf(stderr, USAGE, basename(argv[0]));
                return 1;
        }
    }

    /* create the shared data structure */
    v_create();

    /* connect to the shared data structure */
    v_connect();

    /* setting initial value of module variable */
    v_set(0, 0);

    /* launching the processes */
    printf("Launching %d processes, each one performing %d increments\n", nproc, niter);
    int pid[nproc];
    int i;
    for (i=0; i<nproc; i++)
    {
        proc_create(&pid[i], incrementer, niter);
        printf("Process %d launched\n", pid[i]);
    }

    /* wait for processes to conclude */
    int status[nproc];
    printf("Waiting for processes to return\n");
    for (i=0; i<nproc; i++)
    {
        pwaitpid(pid[i], &status[i], 0);
        printf("Process %d returned\n", pid[i]);
    }

    /* print final value of shared data and quit */
    int v1, v2;
    v_get(&v1, &v2);
    printf("final values = (%d, %d)\n", v1, v2);

    /* destroy shared data structure and quit */
    v_destroy();
    return EXIT_SUCCESS;
}

