#include <stdio.h>

int main(int argc, char* argv[]) {
	
	if(argc == 3) {
		printf("Olá %s %s!\n", argv[1], argv[2]);
    }
    else {
		printf("ERRO insira: ./a.out FirstName SecondName\n");
	}
	return 0;
}
