#include <stdio.h>
#include <assert.h>

static int numComp = 0;
static int numShifts = 0;

//Função para imprimir o conteúdo de um array
void printArray(int array[], int n)
{
    printf("[");

    for(int i=0;i<n;i++)
    {
        printf(" %d ",array[i]);
    }
    printf("]\n");
}

void moveElements(int array[], int inicial, int final)
{
    for(int i=inicial;i<final;i++){
        array[i] = array[i+1];
        numShifts++;
    }
}

void eliminateRepeated(int array[], int *n)
{
    assert( *n > 1);
    numComp = 0;
    numShifts = 0;
    int k = *n;
    for(int i=0;i<k-1;i++) {
        for(int j=i+1;j<k;j++){
            numComp++;
            while(array[i]==array[j]){
                moveElements(array, j, k);
                numComp++;
                numShifts--;
                k--;
            }
        }
    }
    *n = k;
}

int main(void)
{   
    int n0=10;
    int array0[] = {1,1,1,1,1,1,1,1,1,2};
    printf("ARRAY0 inicial : ");
    printArray(array0, n0);
    eliminateRepeated(array0, &n0);
    printf("ARRAY1 final :   ");
    printArray(array0, n0);
    printf("NºComparações: %d - NºShifts: %d\n\n", numComp, numShifts);


    int n1 = 10;
    int array1[] = {1,2,2,2,3,3,4,5,8,8};
    printf("ARRAY1 inicial : ");
    printArray(array1, n1);
    eliminateRepeated(array1, &n1);
    printf("ARRAY1 final :   ");
    printArray(array1, n1);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);

    int n2 = 10;
    int array2[] = {1,2,2,2,3,3,3,3,8,8};
    printf("\nARRAY2 inicial : ");
    printArray(array2, n2);
    eliminateRepeated(array2, &n2);
    printf("ARRAY2 final :   ");
    printArray(array2, n2);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);

    int n3 = 7;
    int array3[] = {1,2,3,2,1,3,4};
    printf("\nARRAY3 inicial : ");
    printArray(array3, n3);
    eliminateRepeated(array3,&n3);
    printf("ARRAY3 final :   ");
    printArray(array3, n3);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);

    int n4 = 10;
    int array4[] = {1,2,5,4,7,0,3,9,6,8};
    printf("\nARRAY4 inicial : ");
    printArray(array4, n4);
    eliminateRepeated(array4, &n4);
    printf("ARRAY4 final :   ");
    printArray(array4, n4);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);

    int n5 = 10;
    int array5[] = {1,2,6,4,7,0,5,8,3,3};
    printf("\nARRAY5 inicial : ");
    printArray(array5, n5);
    eliminateRepeated(array5, &n5);
    printf("ARRAY5 final :   ");
    printArray(array5, n5);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);

    int n6 = 10;
    int array6[] = {1,2,4,5,7,7,5,4,2,1};
    printf("\nARRAY6 inicial : ");
    printArray(array6, n6);
    eliminateRepeated(array6, &n6);
    printf("ARRAY6 final :   ");
    printArray(array6, n6);
    printf("NºComparações: %d - NºShifts: %d\n", numComp, numShifts);


    return 0;
}