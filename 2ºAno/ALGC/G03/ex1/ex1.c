#include <stdio.h>

static unsigned int numComp=0;

//função pedida no enunciado
int compareElements(int array[], int n)
{
    numComp=0;
    int i, result=0;
    for(i=1;i<n;i++){
        if(array[i]!=array[i-1]){
            result++;
        }
        numComp++;
    }
    return result;
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

    printf("ARRAY0\n");
    int array0[] = {3,3,3,3,3,3,3,3,3,3};
    printArray(array0, 10);
    printf("Resultado: %d",compareElements(array0,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY1\n");
    int array1[] = {4,3,3,3,3,3,3,3,3,3};
    printArray(array1, 10);
    printf("Resultado: %d",compareElements(array1,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY2\n");
    int array2[] = {4,5,3,3,3,3,3,3,3,3};
    printArray(array2, 10);
    printf("Resultado: %d",compareElements(array2,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY3\n");
    int array3[] = {4,5,1,3,3,3,3,3,3,3};
    printArray(array3, 10);
    printf("Resultado: %d",compareElements(array3,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY4\n");
    int array4[] = {4,5,1,2,3,3,3,3,3,3};
    printArray(array4, 10);
    printf("Resultado: %d",compareElements(array4,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY5\n");
    int array5[] = {4,5,1,2,6,3,3,3,3,3};
    printArray(array5, 10);
    printf("Resultado: %d",compareElements(array5,10));
    printf(" NºComparações: %d\n\n", numComp);
    
    printf("ARRAY6\n");
    int array6[] = {4,5,1,2,6,8,3,3,3,3};
    printArray(array6, 10);
    printf("Resultado: %d",compareElements(array6,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY7\n");
    int array7[] = {4,5,1,2,6,8,7,3,3,3};
    printArray(array7, 10);
    printf("Resultado: %d",compareElements(array7,10));
    printf(" NºComparações: %d\n\n", numComp);
    
    printf("ARRAY8\n");
    int array8[] = {4,5,1,2,6,8,7,9,3,3};
    printArray(array8, 10);
    printf("Resultado: %d",compareElements(array8,10));
    printf(" NºComparações: %d\n\n", numComp);

    printf("ARRAY9\n");
    int array9[] = {4,5,1,2,6,8,7,9,3,0};
    printArray(array9, 10);
    printf("Resultado: %d",compareElements(array9,10));
    printf(" NºComparações: %d\n", numComp);
    
    return 0;
}