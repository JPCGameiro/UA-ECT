#include <detpic32.h>

void delay(int ms)
{
    for(; ms > 0; ms--){
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex7a Preparação Exame Prático - AC2\n");

    //Configurar RB3-0 como entrada
    TRISB = (TRISB & 0xFFF0) | 0x000F;

    while(1)
    {
        printStr("\nValor nos interruptores: ");
        printInt(PORTBbits.RB0, 2);
        printInt(PORTBbits.RB1, 2);
        printInt(PORTBbits.RB2, 2);
        printInt(PORTBbits.RB3, 2);
        printStr("\n");

        delay(1000);        
    }
}
