#include <stdio.h>

int movements = 0;

void moveDisk(char origem, char destino){
    printf("%c -> %c\n", origem, destino);
}

void torresHanoi(char origem, char destino, char auxiliar, int n){
    if(n==1){
        movements++;
        moveDisk(origem, destino);
        return;
    }
    torresHanoi(origem, auxiliar, destino, n-1);

    movements++;
    moveDisk(origem, destino);

    torresHanoi(auxiliar, origem, destino, n-1);
}


int main(void)
{
    torresHanoi('A', 'C', 'B', 2);
    return 0;
}