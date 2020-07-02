#include <detpic32.h>

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex3-1a Preparação Exame Prático - AC2\n");

    TRISB = (TRISB & 0xFFF0) | 0x000F;  //Configure RB3-RB0 as Inputs
    TRISE = (TRISE & 0xFFF0);           //Configure RE3-RE0 as Outputs

    while(1)
    {
        int value = PORTB & 0x000F;       
        value =  ((LATE & 0xFFF0) | value);
        LATE = value;               
    }

    return 0;
}

