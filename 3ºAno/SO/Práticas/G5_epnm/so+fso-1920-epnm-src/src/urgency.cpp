/**
 * @file
 *
 * \brief A hospital pediatric urgency with a Manchester triage system.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <libgen.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <thread.h>
#include <math.h>
#include <stdint.h>
#include <signal.h>
#include <utils.h>
#include "settings.h"
#include "pfifo.h"

#include "thread.h"
//#include "process.h"

#include <iostream>

#define USAGE "Synopsis: %s [options]\n" \
   "\t----------+-------------------------------------------\n" \
   "\t  Option  |          Description                      \n" \
   "\t----------+-------------------------------------------\n" \
   "\t -p num   | number of patients (dfl: 4)               \n" \
   "\t -n num   | number of nurses (dfl: 1)                 \n" \
   "\t -d num   | number of doctors (dfl: 1)                \n" \
   "\t -h       | this help                                 \n" \
   "\t----------+-------------------------------------------\n"

/**
 * \brief Patient data structure
 */
typedef struct
{
   char name[MAX_NAME+1];
   int done; // 0: waiting for consultation; 1: consultation finished
} Patient;

typedef struct
{
   int num_patients;
   Patient all_patients[MAX_PATIENTS];
   PriorityFIFO triage_queue;
   PriorityFIFO doctor_queue;

   pthread_t* dthr;   //Doctor threads
   pthread_t* nthr;   //Nurse threads
   pthread_t* pthr;   //Patient threads
   uint32_t* pargs;   //Patient's threads args
} HospitalData;

HospitalData * hd = NULL;


/**
 *  \brief patient verification test
 */
#define check_valid_patient(id) do { check_valid_id(id); check_valid_name(hd->all_patients[id].name); } while(0)

int random_manchester_triage_priority();
void new_patient(Patient* patient); // initializes a new patient
void random_wait();

//Thread functions signatures
void* patientThread(void* args);
void* nurseThread(void* args);
void* doctorThread(void * args);

/* changes may be required to this function */
void init_simulation(uint32_t np, uint32_t nn, uint32_t nd)
{
   printf("Initializing simulation\n");
   hd = (HospitalData*)mem_alloc(sizeof(HospitalData)); // mem_alloc is a malloc with NULL pointer verification
   memset((void*)hd, 0, sizeof(HospitalData));
   hd->num_patients = np;
   init_pfifo(&hd->triage_queue);
   init_pfifo(&hd->doctor_queue);

   hd->dthr = new pthread_t[nd];
   hd->nthr = new pthread_t[nn];
   hd->pthr = new pthread_t[np];
   hd->pargs = new uint32_t[np];

   /* Init threads */
   
   /* Lauching doctor threads */
   printf("Launching %d doctor threads\n", nd);
   for(uint32_t i=0; i<nd; i++){
      printf("\033[1;35mDoctor %d launched\n\033[0m", i);
      thread_create(&hd->dthr[i], NULL, doctorThread, NULL);
   }
   
   /* Launching nurse threads */
   printf("Launching %d nurse threads\n", nn);
   for(uint32_t i=0; i<nn; i++){
      printf("\033[1;36mNurse %d launched\n\033[0m", i);
      thread_create(&hd->nthr[i], NULL, nurseThread, NULL);
   }

   /* Launching patient threads */
   printf("Launching %d patient threads\n", np);
   for(uint32_t i=0; i < np; i++){
      printf("\033[1;33mPatient %d launched\n\033[0m", i);
      hd->pargs[i] = i;
      thread_create(&hd->pthr[i], NULL, patientThread, &hd->pargs[i]);
   }

   /* Waiting for all patient threads to finish */
   for(uint32_t i=0; i < np; i++){
      thread_join(hd->pthr[i], NULL);
      printf("Patient thread %d has terminated\n", i);
   }

   //Awake blocked threads to terminate safely
   done_pfifo(&hd->triage_queue);
   done_pfifo(&hd->doctor_queue);


   /* Waiting for nurse thread to finish */
   for(uint32_t i=0; i < nn; i++){
      thread_join(hd->nthr[i], NULL);
      printf("Nurse thread %d has terminated\n", i);
   }

   /* Waiting for doctor thread to finish */
   for(uint32_t i=0; i < nd; i++){
      thread_join(hd->dthr[i], NULL);
      printf("Doctor thread %d has terminated\n", i);
   }
   
}

/* ************************************************* */

int nurse_iteration()
{
   printf("\e[34;01mNurse: get next patient\e[0m\n");
   uint32_t patient = retrieve_pfifo(&hd->triage_queue);
   if(patient == 9999)
   		return 9999;
   check_valid_patient(patient);
   printf("\e[34;01mNurse: evaluate patient %u priority\e[0m\n", patient);
   uint32_t priority = random_manchester_triage_priority();
   printf("\e[34;01mNurse: add patient %u with priority %u to doctor queue\e[0m\n", patient, priority);
   insert_pfifo(&hd->doctor_queue, patient, priority);
   return 0;
}

/* ************************************************* */

int doctor_iteration()
{
   printf("\e[32;01mDoctor: get next patient\e[0m\n");
   uint32_t patient = retrieve_pfifo(&hd->doctor_queue);
   if(patient == 9999)
   	return 9999;
   check_valid_patient(patient);
   printf("\e[32;01mDoctor: treat patient %u\e[0m\n", patient);
   random_wait();
   printf("\e[32;01mDoctor: patient %u treated\e[0m\n", patient);
   hd->all_patients[patient].done = 1;
   return 0;
}

/* ************************************************* */

void patient_goto_urgency(int id)
{
   new_patient(&hd->all_patients[id]);
   check_valid_name(hd->all_patients[id].name);
   printf("\e[30;01mPatient %s (number %u): get to hospital\e[0m\n", hd->all_patients[id].name, id);
   insert_pfifo(&hd->triage_queue, id, 1); // all elements in triage queue with the same priority!
}

/* changes may be required to this function */
void patient_wait_end_of_consultation(int id)
{
   check_valid_name(hd->all_patients[id].name);

   //if patient consultation not done sleep(thread leaves the cpu for a small amount of time)
   while(hd->all_patients[id].done == 0)
      random_wait();

   printf("\e[30;01mPatient %s (number %u): health problems treated\e[0m\n", hd->all_patients[id].name, id);
}

/* changes are required to this function */
void patient_life(int id)
{
   patient_goto_urgency(id);
   //nurse_iteration();  // to be deleted in concurrent version
   //doctor_iteration(); // to be deleted in concurrent version
   patient_wait_end_of_consultation(id);
   memset(&(hd->all_patients[id]), 0, sizeof(Patient)); // patient finished
}




/* ************************************************* */
void* patientThread(void * args)
{
   uint32_t * np = (uint32_t *) args;
   patient_life(*np);
   return NULL;
}

void* nurseThread(void* args)
{
   while(true){
      if(nurse_iteration() == 9999)
         break;
   }
   return NULL;
}

void* doctorThread(void* args)
{
   while(true){
	   if(doctor_iteration() == 9999)
	   	break;
   }
   return NULL;
}

/* ************************************************* */

int main(int argc, char *argv[])
{
   uint32_t npatients = 4;  ///< number of patients
   uint32_t nnurses = 1;    ///< number of triage nurses
   uint32_t ndoctors = 1;   ///< number of doctors

   /* command line processing */
   int option;
   while ((option = getopt(argc, argv, "p:n:d:h")) != -1)
   {
      switch (option)
      {
         case 'p':
            npatients = atoi(optarg);
            if (npatients < 1 || npatients > MAX_PATIENTS)
            {
               fprintf(stderr, "Invalid number of patients!\n");
               return EXIT_FAILURE;
            }
            break;
         case 'n':
            nnurses = atoi(optarg);
            if (nnurses < 1)
            {
               fprintf(stderr, "Invalid number of nurses!\n");
               return EXIT_FAILURE;
            }
            break;
         case 'd':
            ndoctors = atoi(optarg);
            if (ndoctors < 1)
            {
               fprintf(stderr, "Invalid number of doctors!\n");
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

   /* start random generator */
   srand(getpid());

   /* init simulation */
   init_simulation(npatients, nnurses, ndoctors);

   printf("Simulation has terminated sucessfully!\n");


   return EXIT_SUCCESS;
}


/* YOU MAY IGNORE THE FOLLOWING CODE */

int random_manchester_triage_priority()
{
   int result;
   int perc = (int)(100*(double)rand()/((double)RAND_MAX)); // in [0;100]
   if (perc < 10)
      result = RED;
   else if (perc < 30)
      result = ORANGE;
   else if (perc < 50)
      result = YELLOW;
   else if (perc < 75)
      result = GREEN;
   else
      result = BLUE;
   return result;
}

static char **names = (char *[]) {"Ana", "Miguel", "Luis", "Joao", "Artur", "Maria", "Luisa", "Mario", "Augusto", "Antonio", "Jose", "Alice", "Almerindo", "Gustavo", "Paulo", "Paula", NULL};

char* random_name()
{
   static int names_len = 0;
   if (names_len == 0)
   {
      for(names_len = 0; names[names_len] != NULL; names_len++)
         ;
   }

   return names[(int)(names_len*(double)rand()/((double)RAND_MAX+1))];
}

void new_patient(Patient* patient)
{
   strcpy(patient->name, random_name());
   patient->done = 0;
}

void random_wait()
{
   usleep((useconds_t)(MAX_WAIT*(double)rand()/(double)RAND_MAX));
}

