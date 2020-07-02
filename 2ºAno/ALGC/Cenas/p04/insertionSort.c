#include <stdio.h>

void swap(int* a1, int* a2)
{
    int tmp = *a1;
    *a1 = *a2;
    *a2 = tmp;
}

void insertOrd(int iindex, int findex, int array[])
{
    for(int i=findex; i > iindex; i--){
        if(array[i] < array[i-1])
            swap(&array[i], &array[i-1]);
    }
}

void insertionSort(int array[], int n)
{
    for(int k=2;k<n;k++)
        insertOrd(0, k, array);
}

int main(void)
{
    int array[] = {1, 2, 8, 5, 7, 3};
    printf("ARRAY\n");
    insertionSort(array, 6);
    for(int i=0;i<=5;i++){
        printf("%d ", array[i]);
    }
    printf("\n");
    return 0;
}
