#include <stdio.h>
#include <assert.h>

static unsigned int numSubs;

int isArithmeticProgression1(int array[], int n)
{
    assert(n>1);
    int i=0;
    numSubs = 0;
    for(i=0;i<n-1;i++){
        numSubs++;                          //contagem do número de subtrações
        if((array[i+1]-array[i])!=1)
            return 0;
    }
    return 1;
}

//Função para imprimir o conteúdo de um array
void printArray(int array[], int n)
{
    printf("[");
    int i=0;

    for(i=0;i<n;i++)
    {
        printf(" %d ",array[i]);
    }
    printf("]\n");
}

int main(void)
{
    int array1[] = {1,3,4,5,5,6,7,7,8,9};
    int array2[] = {1,2,4,5,5,6,7,8,8,9};
    int array3[] = {1,2,3,6,8,8,8,9,9,9};
    int array4[] = {1,2,3,4,6,7,7,8,8,9};
    int array5[] = {1,2,3,4,5,7,7,8,8,9};
    int array6[] = {1,2,3,4,5,6,8,8,9,9};
    int array7[] = {1,2,3,4,5,6,7,9,9,9};
    int array8[] = {1,2,3,4,5,6,7,8,8,9};
    int array9[] = {1,2,3,4,5,6,7,8,9,9};
    int array10[] = {1,2,3,4,5,6,7,8,9,10};
    
    printf("\nARRAY1\n");
    printArray(array1, 10);
    printf("Rehsult: %d", isArithmeticProgression1(array1, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY2\n");
    printArray(array2, 10);
    printf("Result: %d", isArithmeticProgression1(array2, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY3\n");
    printArray(array3, 10);
    printf("Result: %d", isArithmeticProgression1(array3, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY4\n");
    printArray(array4, 10);
    printf("Result: %d", isArithmeticProgression1(array4, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY5\n");
    printArray(array5, 10);
    printf("Result: %d", isArithmeticProgression1(array5, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY6\n");
    printArray(array6, 10);
    printf("Result: %d", isArithmeticProgression1(array6, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY7\n");
    printArray(array7, 10);
    printf("Result: %d", isArithmeticProgression1(array7, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY8\n");
    printArray(array8, 10);
    printf("Result: %d", isArithmeticProgression1(array8, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY9\n");
    printArray(array9, 10);
    printf("Result: %d", isArithmeticProgression1(array9, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    printf("\nARRAY10\n");
    printArray(array10, 10);
    printf("Result: %d", isArithmeticProgression1(array10, 10));
    printf(" | NºSubtracções: %d\n",numSubs);

    return 0;
}