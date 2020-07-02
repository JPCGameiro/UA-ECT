#include <stdio.h>
#include <math.h>
/*
 * Compile: cc -Wall roots_squares.c -lm
 * Run:	./a.out
 */
int main(void) {
	
	int lines, i;
	
	do {
		printf("Insira o número de linhas: ");
		scanf("%d", &lines);	
	}while(lines<=0);
	
	printf("----------------------------\n");
	printf("|Num | SquareRoot | Square |\n");
	printf("----------------------------\n");
	for(i=0;i<lines+1;i++) {
		printf("|%3d | %10f | %6d |\n", i, sqrt(i), (i*i));
	}
	printf("----------------------------\n");
}
