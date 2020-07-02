#include <stdio.h>

void swap(int* a1, int* a2)
{
    int tmp = *a1;
    *a1 = *a2;
    *a2 = tmp;
}

int swapAdjacents(int array[], int n)
{
    int trocas = 0;
    for(int i=0;i<n-1;i++){
        if(array[i] > array[i+1]){
            swap(&array[i], &array[i+1]);
            trocas = 1;
        }

    }
    return trocas;
}

void BubbleSort(int array[], int n)
{
    int trocas = 1;
    while(trocas)
        trocas = swapAdjacents(array, n);
}

int main(void)
{
    int array[] = {1, 2, 8, 5, 7, 3};
    printf("ARRAY\n");
    BubbleSort(array, 6);
    for(int i=0;i<=5;i++){
        printf("%d ", array[i]);
    }
    printf("\n");
    return 0;
}