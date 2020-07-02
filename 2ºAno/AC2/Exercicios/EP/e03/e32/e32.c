#include <detpic32.h>

void delay(int ms)
{
    for(; ms > 0; ms--) {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

int main(void)
{
    char i;
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex3-2 Preparação Exame Prático - AC2\n");

    TRISE = (TRISE & 0xFFF0);       //Configure RE3-RE0 as outputs

    while(1)
    {
        printStr("Carregua numa tecla\n");
        i = getChar();

        if(i=='0')
            LATE = (LATE & 0xFFF1) | 0x0001;   //SET RE0
        else if(i=='1')
            LATE = (LATE & 0xFFF2) | 0x0002;   //SET RE1
        else if(i=='2')
            LATE = (LATE & 0xFFF4) | 0x0004;   //SET RE2
        else if(i=='3')
            LATE = (LATE & 0xFFF8) | 0x0008;   //SET RE3
        else{
            LATE = (LATE & 0xFFF0) | 0x000F;   //SET RE0-RE3
            delay(1000);                       //wait 1s
            LATE = (LATE & 0xFFF0) | 0x0000;   //RESET RE0-RE3
        }
    }
    return 0;
}
