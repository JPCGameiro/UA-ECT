#include   <stdio.h>
#include   <stdlib.h>
#include   <unistd.h>
#include   <assert.h>

/* cleaning functions */
static void atexit_1(void)
{
    printf("atexit 1\n");
}

static void atexit_2(void)
{
    printf("atexit 2\n");
}

/* programa principal */
int main(void)
{
    /* registering at exit functions */
    atexit(atexit_1);
    atexit(atexit_2);

    /* normal work */
    printf("hello world 1!\n");

    for (int i = 0; i < 5; i++) sleep(1);

    /* that's all */
    return EXIT_SUCCESS;
}

