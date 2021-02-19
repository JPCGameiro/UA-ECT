#include  <stdio.h>
#include  <stdlib.h>
#include  <pthread.h>
#include  <unistd.h>
#include  <sys/syscall.h>
#include  <sys/types.h>

pid_t gettid()
{
    return syscall(SYS_gettid);
}

/* child thread */
int status;
void *threadChild (void *par)
{
  printf ("Child thread: PPID: %d, PID: %d, TID: %d\n",
            getppid(), getpid(), gettid());

  status = EXIT_SUCCESS;
  pthread_exit (&status);
}

/* main thread */

int main (int argc, char *argv[])
{
  printf ("Main thread: PPID: %d, PID: %d, TID: %d\n",
            getppid(), getpid(), gettid());

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
  
  return EXIT_SUCCESS;
}

