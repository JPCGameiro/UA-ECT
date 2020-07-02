#include <stdio.h>

int getMax(int n, int array[], int inicialIndex, int finalIndex)
{
    int max = array[0];
    if(finalIndex - inicialIndex + 1 == 1){
        max = array[finalIndex];
    }
    else{
        int middle = (inicialIndex + finalIndex)/2;
        int maxLeft = getMax(n, array, inicialIndex, middle);
        int maxRight = getMax(n, array, middle+1, finalIndex);
        if(maxLeft > max)
            max = maxLeft;
        if(maxRight > max)
            max = maxRight;
    }    
    return max;
}

int main(void)
{
    int array[] = {1, 4, 5, 101, 2, 1, 4, 211};
    int num = getMax(2, array, 0, 7);
    printf("Max is %d\n",num);
    return 0;
}