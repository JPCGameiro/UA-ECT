#include <stdio.h>
#include <math.h>
#include "elapsed_time.h"

//Contar o número de multiplicações
unsigned long numop=0;

unsigned long recursiveP(int n)
{
	if(n==0)
		return 0;
	else if(n==1)
		return 1;
	else{
		numop += 2;
		return 3*recursiveP(n-1) + 2*recursiveP(n-2);
	}
}

unsigned long iterativeP(int n)
{
	unsigned long n2=0, n1=1, current;
	
	if(n==0)
		return 0;
	if(n==1)
		return 1;
	for(int i=2;i<=n;i++){
			current = 3*(n1) + 2*(n2);
			n2 = n1;
			n1 = current;
			numop += 2;
	}
	return current;
}

unsigned long recorrenceP(int n){
	numop += 2;
	double aux = sqrt(17);
	return ((pow(((0.5)*(3+aux)),n) - pow(((0.5)*(3-aux)),n)))/aux;	
}

unsigned long exponencialP(int n){
	double c1=0.24253562503633297352, c2=1.27019663313689157536;
	numop+=2;
	return round(c1*exp(c2*n));
}
int main(void)
{
	int n=0;
	
	printf("-------------------------------RECURSIVE------------------------------------\n");
	printf("  n  |         P(n)         |         numOps         |         time        |\n");
	printf("----------------------------------------------------------------------------\n");
	for(n=0;n<=30;n++){
		(void)elapsed_time();
		unsigned long result = recursiveP(n);
		printf(" %3d | %20ld | %22ld |    %0.11f    |\n", n, result, numop, elapsed_time());
	}
	printf("----------------------------------------------------------------------------\n");
	
	printf("\n\n\n");
	
	numop=0;
	printf("-------------------------------ITERATIVE------------------------------------\n");
	printf("  n  |         P(n)         |         numOps         |         time        |\n");
	printf("----------------------------------------------------------------------------\n");
	for(n=0;n<=30;n++){
		(void)elapsed_time();
		unsigned long result = iterativeP(n);
		printf(" %3d | %20ld | %22ld |    %0.11f    |\n", n, result, numop, elapsed_time());
	}
	printf("----------------------------------------------------------------------------\n");
	
	printf("\n\n\n");
	
	numop=0;
	printf("-------------------------------RECORRENCE-----------------------------------\n");
	printf("  n  |         P(n)         |         numOps         |         time        |\n");
	printf("----------------------------------------------------------------------------\n");
	for(n=0;n<=30;n++){
		(void)elapsed_time();
		unsigned long result = recorrenceP(n);
		printf(" %3d | %20ld | %22ld |    %0.11f    |\n", n, result, numop, elapsed_time());
	}
	printf("----------------------------------------------------------------------------\n");
	
	printf("\n\n\n");
	
	numop=0;
	printf("------------------------------EXPONENCIAL-----------------------------------\n");
	printf("  n  |         P(n)         |         numOps         |         time        |\n");
	printf("----------------------------------------------------------------------------\n");
	for(n=0;n<=30;n++){
		(void)elapsed_time();
		unsigned long result = exponencialP(n);
		printf(" %3d | %20ld | %22ld |    %0.11f    |\n", n, result, numop, elapsed_time());
	}
	printf("----------------------------------------------------------------------------\n");
	
}
