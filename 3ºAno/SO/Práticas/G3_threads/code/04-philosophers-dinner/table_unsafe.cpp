/*
 *  @brief A simple implementation of a table for 
 *          the philosopher's dinner problem
 *          
 *      Unsafe version: a mutex is used only to make sure everything is 
 *          printed correctly
 *  
 * \author (2020) João Gameiro <joao.gameiro at ua.pt>
 */

#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <errno.h>
#include <pthread.h>
#include  <stdint.h>
#include <cstdlib>

#include "delays.h"
#include "thread.h"
#include "table.h"

#define N           5
#define EATING      0
#define MEDITATING  1
#define HUNGRY      2
#define WAITING     3
#define LEFT    (i+N-1) % N
#define RIGHT   (i+1) % N

/*
 *  \brief Data structure.
 */

typedef struct Table
{
    uint32_t forks[N];
    pthread_mutex_t acessCR;
} Table;


/** \brief internal storage region of Table type */
static Table table;


/* ************************************************* */
/* Initialization of the Table*/
void initTable(void)
{
    table.acessCR = PTHREAD_MUTEX_INITIALIZER;
    for (uint32_t i = 0; i < N; i++){
        table.forks[i] = MEDITATING;
    }
    printf("Initializing dinner Table!\n");
}

/* ************************************************* */
/* Print the content of the dinner table */

void printTable()
{
    mutex_lock(&table.acessCR);

    for(uint32_t i=0; i < N; i++){
        if(table.forks[i] == MEDITATING)
            printf("\033[0;36mMED\033[0m ");
        else if(table.forks[i] == EATING){
            if(table.forks[RIGHT] == EATING || table.forks[LEFT] == EATING)
                printf("\033[0;31mEAT\033[0m ");
            else
                printf("\033[0;32mEAT\033[0m ");
        }
        else if(table.forks[i] == WAITING)
            printf("\033[0;33mWAI\033[0m ");
        else if(table.forks[i] == HUNGRY)
            printf("\033[0;35mHUN\033[0m ");
    }
    printf("\n");

    mutex_unlock(&table.acessCR);
}

/* ************************************************* */
/* Change philosopher i state to meditating */

void goMeditate(unsigned int i)
{
    table.forks[i] = MEDITATING;
    printTable();
}

/* ************************************************* */
/* Change philosopher state to hungry -> waiting (to be able to eat) */

void getHungry(unsigned int i)
{
    table.forks[i] = HUNGRY;
    printTable();
    table.forks[i] = WAITING;
    printTable();
}

/* ************************************************* */
/* change philosopher i state to eat */

void goEat(unsigned int i)
{
    
    //printTable();

    //If left or right philosopher is eating then wait
    while(table.forks[LEFT] == EATING || table.forks[RIGHT] == EATING);
    
    table.forks[i] = EATING;

    printTable();
}

/* ************************************************* */
/* change philosopher i state to stop eating */

void stopEating(unsigned int i)
{
    table.forks[i] = MEDITATING;

    printTable();
}

/* ************************************************* */
