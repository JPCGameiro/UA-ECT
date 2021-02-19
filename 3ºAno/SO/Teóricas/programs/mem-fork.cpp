#include    <stdio.h>
#include    <stdlib.h>
#include    <unistd.h>
#include    <wait.h>

int n01 = 1;

int main(int argc, char *argv[], char *env[])
{
    int pid = fork();
    if (pid != 0) 
    {
        fprintf(stderr, "%5d: n01 = %-5d (%p)\n", pid, n01, &n01);
        wait(NULL);
        fprintf(stderr, "%5d: n01 = %-5d (%p)\n", pid, n01, &n01);
    }
    else 
    {
        fprintf(stderr, "%5d: n01 = %-5d (%p)\n", pid, n01, &n01);
        n01 = 1111;
        fprintf(stderr, "%5d: n01 = %-5d (%p)\n", pid, n01, &n01);
    }

    return 0;
}

