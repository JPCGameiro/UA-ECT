/**
 * @file
 *
 * \brief A simple implementation of the Philosopher's Dinner problem.
 *
 * \author (2020) João Gameiro <joao.gameiro at ua.pt>
 */

#include  <stdio.h>
#include  <stdlib.h>
#include  <libgen.h>
#include  <unistd.h>
#include  <sys/wait.h>
#include  <sys/types.h>
#include  <pthread.h>
#include  <stdint.h>
#include  <math.h>
#include  <string.h>

#include  "utils.h"
#include  "delays.h"
#include  "thread.h"
#include  "table.h"

#define MAX_WAIT    10
#define N           5
#define EATING      0
#define MEDITATING  1
#define HUNGRY      2
#define WAITING     3


#define USAGE "Synopsis: %s [options]\n"\
	"\t----------+--------------------------------------------\n"\
	"\t  Option  |          Description                       \n"\
	"\t----------+--------------------------------------------\n"\
    "\t -i num   | number of iterations (dfl: 1)              \n"\
	"\t -h       | this help                                  \n"\
	"\t----------+--------------------------------------------\n"

/* Argument value for philosopher threads */
typedef struct
{
    uint32_t id;      ///< philosopher id
    uint32_t niter;   ///< number of iterations
} ARGV;

void random_wait();


//Philosopher thread
void* philosopherThread(void* args)
{
    ARGV* argv = (ARGV*)args;
    uint32_t i = argv->id;
    uint32_t n = argv->niter;

    for(uint32_t j=0; j < n; j++){
        goMeditate(i);
        getHungry(i);
        goEat(i);
        random_wait();
        stopEating(i);
        random_wait();
        goMeditate(i);
    }

    return NULL;
}

//Main thread (launches all philosopher threads)
int main(int argc, char *argv[])
{
    int niterations = 1;
    int option;
    while ((option = getopt(argc, argv, "i:h")) != -1)
    {
        switch (option)
        {
            case 'i':
                niterations = atoi(optarg);
                if (niterations < 1)
                {
                    fprintf(stderr, "Invalid number of iterations!\n");
                    return EXIT_FAILURE;
                }
                break;
            case 'h':
                printf(USAGE, basename(argv[0]));
                return EXIT_SUCCESS;
            default:
                fprintf(stderr, "Non valid option!\n");
                fprintf(stderr, USAGE, basename(argv[0]));
                return EXIT_FAILURE;
        }
    }

    //Philosophers threads
    pthread_t philosophers[N];
    //Threads arguments
    ARGV pargs[N];
    //Initialize system
    initTable();

    //Launching philosophers threads
    printf("Launching %d philosopher's to perform %d iteration(s)!\n", N, niterations);
    for(uint32_t i=0; i < N; i++){
        pargs[i].id = i;
        pargs[i].niter = niterations;
        thread_create(&philosophers[i], NULL, philosopherThread, &pargs[i]);
    }

    //Wait for philosophers threads to finish
    for(uint32_t i=0; i < N; i++)
        thread_join(philosophers[i], NULL);

    printf("Philosopher threads finished sucessfully!\n");

    return EXIT_SUCCESS;
}


//Generate a random delay
void random_wait()
{
   usleep((useconds_t)(MAX_WAIT*(double)rand()/(double)RAND_MAX));
}