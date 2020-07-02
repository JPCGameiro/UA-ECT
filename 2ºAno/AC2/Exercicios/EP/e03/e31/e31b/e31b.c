#include <detpic32.h>

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex3-1a Preparação Exame Prático - AC2\n");

    TRISB = (TRISB & 0xFFF0) | 0x000F;    //Configure RB3-RB0 as Inputs
    TRISE = (TRISE & 0xFFF0);             //Configure RB3-RB0 as Outputs

    while(1)
    {
        LATEbits.LATE0 = PORTBbits.RB3;
        LATEbits.LATE1 = PORTBbits.RB2;
        LATEbits.LATE2 = PORTBbits.RB1;
        LATEbits.LATE3 = PORTBbits.RB0;
    }
    return 0;
}
