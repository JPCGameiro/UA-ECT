#include <detpic32.h>

void delay(int ms)
{
    for(; ms > 0; ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

void printSignalModule(char value) {
    char aux = value & 0x0008;
    char aux1 = value & 0x0007;

    if(aux==0x0008)
        printStr("-");
    printInt(aux1, 10 | 2 << 10);
}

void printSignal1Complement(char value) {
    char aux = value & 0x0008;
    char aux1 = value & 0x0007;

    if(aux==0x0008){
        printStr("-");
        aux1 = (aux1 ^ 0x0007) & 0x0007;
    }
    printInt(aux1, 10 | 2 << 10);
}

void printSignal2Complement(char value){
    char aux = value & 0x0008;
    char aux1 = value & 0x000F;

    if(aux==0x0008){
        printStr("-");
        aux1 = ((aux1 ^ 0x000F) + 1) & 0x000F;
    }
    printInt(aux1, 10 | 2 << 10);
}

int main(void)
{
    TRISB = (TRISB & 0xFFF0) | 0x000F;
    char value = 0;
    while(1)
    {
        value = PORTB & 0x000F;
        printStr("\nValor nos Interruptores: ");
        printInt(value, 2 | 4 << 2);
        printStr("\nValor nos interruptores (Sinal e Módulo): ");
        printSignalModule(value);
        printStr("\nValor nos interruptores (Complemento para 1): ");
        printSignal1Complement(value);
        printStr("\nValor nos interruptores (Complemento para 2): ");
        printSignal2Complement(value);
        printStr("\n");
        delay(250);
    }
    return 0;
}

