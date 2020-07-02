#include <stdio.h>
/*
 * Compile: cc -Wall sen_cos.c -lm
 * Run:	./a.out
 */
int main(void) {
	
	printf("sizeof(void *): .....%ld\n", sizeof(void *));
	printf("sizeof(void): .......%ld\n", sizeof(void));
	printf("sizeof(char): .......%ld\n", sizeof(char));
	printf("sizeof(short): ......%ld\n", sizeof(short));
	printf("sizeof(int): ........%ld\n", sizeof(int));
	printf("sizeof(long): .......%ld\n", sizeof(long));
	printf("sizeof(long long): ..%ld\n", sizeof(long long));
	printf("sizeof(float): ......%ld\n", sizeof(float));
	printf("sizeof(double): .....%ld\n", sizeof(double));
	
	return 0;
}
