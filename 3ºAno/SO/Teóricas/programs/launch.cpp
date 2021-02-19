#include    <stdio.h>
#include    <stdlib.h>
#include    <unistd.h>
#include    <sys/types.h>
#include    <sys/wait.h>

int main(int argc, char *argv[])
{
    /* check arguments */
    if (argc != 2)
    {
        fprintf(stderr, "launch <path to file>\n");
        exit(EXIT_FAILURE);
    }
    char *aplic = argv[1];

    printf("===============================\n");

    /* clone phase */
    int pid;
    if ((pid = fork()) < 0)
    {
        perror("Fail cloning process");
        exit(EXIT_FAILURE);
    }

    /* exec and wait phases */
    if (pid != 0) // this only runs in the parent process
    {
        int status;
        while (wait(&status) == -1);
        printf("===============================\n");
        printf("Process %d (child of %d) ends with status %d\n",
                    pid, getpid(), WEXITSTATUS(status));
    }
    else // this only runs in the child process
    {
        execlp(aplic, aplic, NULL);
        perror("Fail launching program");
        exit(EXIT_FAILURE);
    }

    exit(EXIT_SUCCESS); // or return EXIT_SUCCESS
}
