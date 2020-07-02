#include <stdio.h>
#include <math.h>

static int numCalls1 = -1;
static int numCalls2 = -1;
static int numCalls3 = -1;

int T1(int n)
{
    numCalls1++;                
    if(n==0)
        return 0;
    else{
        return (T1(n/3)+n);
    }
}


int T2(int n)
{
    numCalls2++;
    if(n==0 || n==1 || n==2)
        return n;
    else
        return (T2(n/3)+T2((n+2)/3)+n);
}

int T3(int n)
{
    numCalls3++;
    if(n==0 || n==1 || n==2)
        return n;
    if(n%3==0)
        return (2*T3(n/3)+n);
    else
        return (T3(n/3)+T3((n+2)/3)+n);
}

int main(void)
{
    printf("\n--------------T1----------------\n");
    printf("  n | T1(n) | NºRecursiveCalls |\n");
    printf("--------------------------------\n");
    for(int i=0;i<=28;i++){
        int result = T1(i);
        printf(" %2d | %5d | %16d |\n", i, result, numCalls1);
        numCalls1=-1; 
    }
    printf("--------------------------------\n\n");

    printf("\n--------------T2----------------\n");
    printf("  n | T2(n) | NºRecursiveCalls |\n");
    printf("--------------------------------\n");
    for(int i=0;i<=28;i++){
        int result = T2(i);
        printf(" %2d | %5d | %16d |\n", i, result, numCalls2);
        numCalls2=-1; 
    }
    printf("--------------------------------\n\n");

    printf("\n--------------T3----------------\n");
    printf("  n | T3(n) | NºRecursiveCalls |\n");
    printf("--------------------------------\n");
    for(int i=0;i<=28;i++){
        int result = T3(i);
        printf(" %2d | %5d | %16d |\n", i, result, numCalls3);
        numCalls3=-1; 
    }
    printf("--------------------------------\n\n");
    return 0;
}