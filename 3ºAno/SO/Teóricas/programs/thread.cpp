#include  <stdio.h>
#include  <stdlib.h>
#include  <pthread.h>
#include  <unistd.h>

/* child thread */
int status;
void *threadChild (void *par)
{
  printf ("I'm the child thread! (PID: %d)\n", getpid());

  sleep(1);
  status = EXIT_SUCCESS;
  pthread_exit (&status);
}

/* main thread */

int main (int argc, char *argv[])
{
  printf ("I'm the main thread! (PID: %d)\n", getpid());

  /* launching child thread */
  pthread_t thr;
  if (pthread_create (&thr, NULL, threadChild, NULL) != 0)
  {
    perror ("Fail launching thread");
    return EXIT_FAILURE;
  }

  /* waits for child termination */
  if (pthread_join (thr, NULL) != 0)
  {
    perror ("Fail joining child thread");
    return EXIT_FAILURE;
  }
  
  printf ("Child threads ends with status %d.\n", status);

  return EXIT_SUCCESS;
}

