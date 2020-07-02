#include <stdio.h>

static unsigned int numComp=0;

//Função pedida no enunciado
int getNumber(int array[], int n)
{
    int i, j, maxi=-1, cntmax=0, cnt=0;
    numComp=0;

    for(i=1;i<n;i++) 
    {
        for(j=0;j<i;j++)
        {
            numComp++;
            if(array[j]<array[i])
                cnt++;
        }
        numComp++;
        if(cnt>cntmax){
            cntmax = cnt;
            maxi = i;
        }
        cnt=0;
    }
    return maxi;
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
    int array0[] = {1,9,2,8,3,4,5,3,7,2};
    int array1[] = {1,7,4,6,5,2,3,2,1,0};
    int array2[] = {2,2,2,2,2,2,2,2,2,2};

    int i0 = getNumber(array0, 10);
    printf("ARRAY0\n");
    printArray(array0, 10);
    printf("Result: %2d NºComparações: %d\n\n",i0, numComp);
   
    int i1 = getNumber(array1, 10);
    printf("ARRAY1\n");
    printArray(array1, 10);
    printf("Result: %2d NºComparações: %d\n\n", i1, numComp);

    int i2 = getNumber(array2, 10);
    printf("ARRAY2\n");
    printArray(array2, 10);
    printf("Result: %2d NºComparações: %d\n", i2, numComp);
    return 0;
}