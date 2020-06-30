#include <detpic32.h>

unsigned char toBcd(unsigned char value)
{
    return ((value/10)<<4) + (value%10);
}

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int digit_low = display7Scodes[value & 0x0F];
    int digit_high = display7Scodes[value >> 4];

    if(displayFlag==0){
        LATDbits.LATD5 = 1;                     //set display low
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | digit_low<<8;     //send digit_low to display_low
        if(value % 2 == 0) LATBbits.LATB15 = 1;
        else LATBbits.LATB15 = 0;
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;                     //set display high
        LATB = (LATB & 0x80FF) | digit_high<<8;    //send digit_high to display_high
        if(value % 2 != 0) LATBbits.LATB15 = 1;
        else LATBbits.LATB15 = 0;
    }
    displayFlag = !displayFlag;                 //toogle displayFlag value
}
int main(void)
{
    unsigned int i;
    unsigned int counter;

    TRISB = TRISB & 0x00FF;                   //set RB15-RB8 as OUTPUTS
    TRISD = TRISD & 0xFF9F;                   //set RD6-RD5 as OUTPUTS

    counter = 0;
    i = 0;
    while(1)
    {
        i++;
        send2displays(toBcd(counter));
        delay(50);                             //refresh rate 50ms
        //delay(20);                             //refresh rate 20ms
        //delay(10);                             //refresh rate 10 ms

        if(i%4==0)
            counter++;
        if(counter == 61)
            counter = 0;
    }
    return 0;
}
