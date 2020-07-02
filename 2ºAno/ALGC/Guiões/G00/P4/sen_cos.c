#include <stdio.h>
#include <math.h>
#include <stdlib.h>
/*
 * Compile: cc -Wall sen_cos.c -lm
 * Run:	./a.out
 */
int main(void) {
	
	double mina, maxa; 
	int space;
	FILE *pt;
	
	printf("Insira o ângulo mínimo: ");
	scanf("%lf", &mina);
	printf("Insira o ângulo máximo: ");
	scanf("%lf", &maxa);
	printf("Insira o espaçamento entre ângulos: ");
	scanf("%d", &space);
	
	pt = fopen("result.txt", "w");
	fprintf(pt, "ang sin(ang)      cos(ang)     \n--- ------------- -------------\n");
	printf("ang sin(ang)      cos(ang)     \n");
	printf("--- ------------- -------------\n");
	for(double i=mina;i<=maxa;i=i+space){
		printf("%3.0f  %12f  %12f\n", i, sin(i*M_PI/180.0), cos(i*M_PI/180.0));
		fprintf(pt, "%3.0f  %12f  %12f\n", i, sin(i*M_PI/180.0), cos(i*M_PI/180.0));
	}
	
	fclose(pt);
	printf("Informação escrita no ficheiro result.txt com sucesso!\n");
	
	return 0;
}
