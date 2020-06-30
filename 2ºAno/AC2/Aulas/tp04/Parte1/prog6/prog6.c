#include <detpic32.h>

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

int main(void)
{
    static const char display7Scodes[] = {0x3F, 0x06, 0x5B, 0x4F, 0x66, 0x6D, 0x7D, 0x07, 0x7F, 0x6F, 0x77, 0x7C, 0x39, 0x5E, 0x79, 0x71};
    
    TRISD = TRISD & 0xFF9F;             //configure RD5, RD6 as outputs
    TRISB = TRISB & 0x80FF;             //configure RB14 to RB8 as outputs
    TRISB = (TRISB & 0xFFF0) | 0x000F;  //configure RB3 t0 RB0 as inputs
    
    //LATD = (LATD & 0xFF9F) | 0x0040;    //Select display high           
    LATD = (LATD & 0xFF9F) | 0x0020;    //Select display low

    LATB = LATB & 0x80FF;

    while(1)
    {   
        int i = PORTB & 0x000F;
        LATB = (LATB & 0x80FF) | (display7Scodes[i]<<8);

    }             
    return 0;
}
