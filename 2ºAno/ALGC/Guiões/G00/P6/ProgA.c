#include <stdio.h>

/*
 * Funções adicionais
 */
void printArray(char *string, int array[], int length){
	printf("%s:\n", string);
	for(int i=0;i<length;i++) {
		printf("%d ", array[i]);
	}
	printf("\n");
}

void cumSum(int a[], int b[], int lengtha) {
	int c=0;
	for(int i=0;i<lengtha;i++) {
		c += a[i];
		b[i] = c;
	}
}

/*
 * Compile:	cc -Wall progA.c
 * Run:		./a.out
 */
int main(void){
	
	int a[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	int b[12];
	
	printArray("a", a, sizeof(a)/sizeof(a[0]));
	
	cumSum(a, b, sizeof(a)/sizeof(a[0]));
	
	printArray("b", b, sizeof(b)/sizeof(b[0]));	
	
	return 0;
}
