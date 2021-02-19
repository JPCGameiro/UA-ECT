/**
 * @file
 *
 * \brief A producer-consumer application, implemented using threads.
 *
 * \author (2016-2020) Artur Pereira <artur at ua.pt>
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
#include  "fifo.h"
#include  "poolBuffers.h"

#define USAGE "Synopsis: %s [options]\n"\
	"\t----------+--------------------------------------------\n"\
	"\t  Option  |          Description                       \n"\
	"\t----------+--------------------------------------------\n"\
	"\t -c num   | number of clients (dfl: 4; max: 100)       \n"\
    "\t -s num   | number of servers (dfl: 4; max: 100)       \n"\
	"\t -h       | this help                                  \n"\
	"\t----------+--------------------------------------------\n"


/* ******************************************************* */

Fifo* fifofreeBuffers = NULL;
Fifo* fifopendingRequests = NULL;
Pool* poolBuffers = NULL;

pthread_mutex_t* acessCr;
pthread_cond_t* clientVar;


void initSystem(unsigned int nclients)
{
    printf("Initializing System\n");

    fifofreeBuffers = (Fifo*)mem_alloc(sizeof(Fifo));
    fifopendingRequests = (Fifo*)mem_alloc(sizeof(Fifo));
    poolBuffers = (Pool*)mem_alloc(sizeof(Pool));

    fifoInit(fifofreeBuffers);
    fifoInit(fifopendingRequests);
    poolInit(poolBuffers);

    acessCr = new pthread_mutex_t[nclients];
    clientVar = new pthread_cond_t[nclients];

    for(uint32_t i = 0; i < FIFOSZ; i++)
        fifoIn(fifofreeBuffers, 0, i);

    for(uint32_t i = 0; i < nclients; i++){
        clientVar[i] = PTHREAD_COND_INITIALIZER;
        acessCr[i] = PTHREAD_MUTEX_INITIALIZER;
    }

}

/* ******************************************************* */




/* ******************************************************* */
/* FIFOFreeBuffers */

//Take a buffer out of fifo of free buffers
unsigned int getFreeBuffer(unsigned int cid)
{
    unsigned int value, id;
    fifoOut(fifofreeBuffers, &id, &value);
    printf("\033[1;32mFree buffer%d retrieved for Client%d\033[0m\n", value, cid);
    return value;

}
//Add buffer to fifo of free buffers
void releaseBuffer(unsigned int id, unsigned int cid)
{
    fifoIn(fifofreeBuffers, 0, id);
    printf("\033[1;32mClient%d released buffer%d\033[0m\n",cid, id);
}

/* ******************************************************* */




/* ******************************************************* */
/* FifoPendingRequests */

//add a new request to fifo of pending requests
void addNewPendingRequest(unsigned int id, unsigned int cid)
{
    fifoIn(fifopendingRequests, cid, id);
    printf("\033[1;32mClient%d added a new request to buffer%d\033[0m\n", cid, id);
}
//Take a pending request out of fifo of pending requests
unsigned int getPendingRequest(unsigned int *id, unsigned int sid)
{
    unsigned int value;
    fifoOut(fifopendingRequests, id, &value);
    printf("\033[1;34mServer%d took a pending request from buffer%d\033[0m\n", sid, value);
    return value;
}

/* ******************************************************* */



/* ******************************************************* */
/* PoolBuffers */

//Put request data on buffer
void putRequestData(unsigned int id, char* data, unsigned int cid)
{
    poolIn(poolBuffers, id, data);
    printf("\033[1;32mClient%d added request data\033[0m %s \033[1;32mto buffer%d\033[0m\n", cid, data, id);
}
//Get Request data
char * getRequestData(unsigned int id, unsigned int sid)
{
    char* s = (char*)malloc( sizeof(s) * ( 200 + 1 ) );
    poolOut(poolBuffers, id, &s);
    printf("\033[1;34mServer%d retrieved request data\033[0m %s \033[1;34mfrom buffer%d\033[0m\n", sid, s, id);
    return s;
}
//put responde data on buffer
void putResponseData(unsigned int id, char* data, unsigned int sid)
{
    char s[] = "ABCDEF";
    strcat(data, s);

    poolIn(poolBuffers, id, data);
    printf("\033[1;34mServer%d inserted response data\033[0m %s \033[1;34min buffer%d\033[0m\n", sid, data, id);
}

//take the response from buffer
char* getResponseData(unsigned int id, unsigned int cid)
{
    char* s = (char*)malloc( sizeof(s) * ( 200 + 1 ) );
    poolOut(poolBuffers, id, &s);
    printf("\033[1;32mClient%d retrieved response data\033[0m %s \033[1;32mfrom buffer%d\033[0m\n", cid, s, id);
    return s;
}

/* ******************************************************* */



/* ******************************************************* */

void* clientThread(void* args)
{
    unsigned int* cid = (unsigned int*)args;
    printf("\033[1;33mClient%d thread launched\033[0m\n", *cid);

    char *data = (char*)malloc( sizeof(data) * ( 200 + 1 ) );
    strcpy(data, "Hello");

    //Take a buffer out of fifo of free buffers
    unsigned int id = getFreeBuffer(*cid);

    //Put request data on buffer
    putRequestData(id, data, *cid);

    //add buffer to fifo of pending requests
    addNewPendingRequest(id, *cid);

    //wait until a response is available
    mutex_lock(&acessCr[*cid]);
    cond_wait(&clientVar[*cid], &acessCr[*cid]);
    mutex_unlock(&acessCr[*cid]);

    //take the response out of the buffer
    getResponseData(id, *cid);

    //Buffer is free so add it to fifo of freeBuffers
    releaseBuffer(id, *cid);

    return NULL;
}

/* ******************************************************* */

void* serverThread(void* args)
{    
    unsigned int * sid = (unsigned int *)args;
    printf("\033[1;31mServer%d thread launched\033[0m\n", *sid);
    while(true)
    {
        //Take a buffer out of fifo of pending requests
        unsigned int cid;
        unsigned int id = getPendingRequest(&cid, *sid);

        //Take the request
        char* req = getRequestData(id, *sid);

        //put response data on buffer
        putResponseData(id, req, *sid);

        //wake client up
        cond_signal(&clientVar[cid]);
    }

    return NULL;
}

/* ******************************************************* */

/*   main thread: it starts the simulation and generates the producer and consumer threads */
int main(int argc, char *argv[])
{
    unsigned int nclients = 4;
    unsigned int nservers = 1;

    /* command line processing */
    int option;
    while ((option = getopt(argc, argv, "c:s:h")) != -1)
    {
        switch (option)
        {
            case 'c':
                nclients = atoi(optarg);
                if (nclients < 1 || nclients > 100) {
                    fprintf(stderr, "Invalid number of clients!\n");
                    return EXIT_FAILURE;
                }
                break;
            case 's':
                nservers = atoi(optarg);
                if (nservers < 1) {
                    fprintf(stderr, "Invalid number of servers!\n");
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


    initSystem(nclients);

    pthread_t cthr[nclients];
    pthread_t sthr[nservers];
    unsigned int cargs[nclients];
    unsigned int sargs[nservers];

    //Launching server threads
    for(uint32_t i=0;i < nservers; i++)
    {
        sargs[i] = i;
        thread_create(&sthr[i], NULL, serverThread, &sargs[i]);
    }
    //Launching client threads
    for(uint32_t i=0;i < nclients; i++)
    {
        cargs[i] = i;
        thread_create(&cthr[i], NULL, clientThread, &cargs[i]);
    } 
    //waiting for client threads to terminate
    for(uint32_t i=0;i < nclients; i++)
    {
        thread_join(cthr[i], NULL);
        printf("\033[1;33mClient%d thread terminating\033[0m\n", i);
    }


    for(uint32_t i=0;i < nservers; i++){
		thread_join(sthr[i], NULL);
        printf("\033[1;31mServer%d thread terminating\033[0m\n", i);
    }
    printf("Terminating Sytem\n");

    return EXIT_SUCCESS;
}

