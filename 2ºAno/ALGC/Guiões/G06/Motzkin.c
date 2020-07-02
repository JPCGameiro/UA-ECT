#include <stdio.h>

static int numMultRecursive = 0;
static int numMultDynamic = 0;

int RecursiveMotzkin(int n)
{
    if(n==0 || n==1)
        return 1;
    else{
        int result = RecursiveMotzkin(n-1);
        for(int k=0;k<=n-2;k++){
            result += RecursiveMotzkin(k)*RecursiveMotzkin(n-2-k);
            numMultRecursive++;
        }
        return result;
    }
}

int DynamicMotzkin(int n)
{
    int array[n];
    array[0] = 1;
    array[1] = 1;
    for(int i=2;i<=n;i++){
        array[i] = array[i-1];
        for(int k=0;k<=i-2;k++){
            array[i] += (array[k]*array[i-2-k]);
            numMultDynamic++;
        }
    }
    return array[n];
}


int main(void)
{
    printf("----------------------------------------------------------------------------------------\n");
    printf("  n | Motzkin(n)-Recursiva | NºMultiplicações | Motzkin(n)-Dinâmica | NºMultiplicações |\n");
    printf("----|----------------------|------------------|---------------------|------------------|\n");
    for(int n=0;n<=15;n++){
        numMultRecursive = 0;
        numMultDynamic = 0;
        printf(" %2d | %20d |", n, RecursiveMotzkin(n));
        printf(" %16d |", numMultRecursive);
        printf(" %19d |", DynamicMotzkin(n));
        printf(" %16d |\n", numMultDynamic);
    }
    printf("----------------------------------------------------------------------------------------\n");
    return 0;
}