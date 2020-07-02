#include <stdio.h>

/* to compile: cc -Wall hello_world.c -o executablename
 * to run: ./executablename
 * 
 * or
 * 
 * to compile: cc -Wall hello_world.c
 * to run: ./a.out
 */

int main(void) {
	
	char fname[20];
	char sname[20];	
	
	printf("Insira o primeiro nome: ");
    scanf("%s", fname);
	printf("Insira o segundo nome: ");
	scanf("%s", sname);
	printf("Olá %s %s!\n", fname, sname);

	
	/*
	 *char name[30];
	 *printf("Enter name: ");
     *fgets(name, sizeof(name), stdin);  // read string
     *printf("Olá %s", name);
     */
     
	return 0;
}
