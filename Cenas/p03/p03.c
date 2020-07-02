#include <stdio.h>

int numberOfOcurrences(int n, int array[], int inicialIndex, int finalIndex)
{
    int cnt = 0;
    if(finalIndex - inicialIndex + 1 == 1){
        if(array[finalIndex] == n)
            cnt++;;
    }
    else{
        int middle = (inicialIndex + finalIndex)/2;
        cnt = cnt + numberOfOcurrences(n, array, inicialIndex, middle) + numberOfOcurrences(n, array, middle+1,finalIndex);
    }    
    return cnt;
}

int main(void)
{
    int array[] = {1, 2, 2, 2, 2, 2, 2, 5};
    int num = numberOfOcurrences(2, array, 0, 7);
    printf("2 apprears %d times\n",num);
    return 0;
}