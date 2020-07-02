#include <stdio.h>

//Factorial recursivo
int factorial(int n){
    if(n==0) return 1;
    else return n * factorial(n-1);
}

int main(void)
{
    for(int i=0;i<10;i++){
        int result = factorial(i);
        printf("\n%d ! = %d", i, result);
    }
    printf("\n");
    return 0;
}