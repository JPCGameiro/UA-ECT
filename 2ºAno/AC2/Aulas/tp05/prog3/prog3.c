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
    int i;

    TRISBbits.TRISB4 = 1;               //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;               //RB4 configured as analog input (AN4)
    AD1CON1bits.SSRC = 7;

    AD1CON1bits.CLRASAM = 1;            //Stop conversions when the 1st A/D converter interrupt is generates

    AD1CON3bits.SAMC = 16;              //Sample time is 16 TAD
    AD1CON2bits.SMPI = 4-1;             //4 consecutive conversions

    AD1CHSbits.CH0SA = 4;               //Select AN4 as input for the A/D converter
    AD1CON1bits.ON = 1;                 //Enable the A/D converter

    while(1)
    {
        AD1CON1bits.ASAM = 1;           //Start the conversion
        while(IFS1bits.AD1IF == 0);     //wait for conversion ending
        
        int *p = (int *)(&ADC1BUF0);
        for(i=0;i<16;i++) {
            printInt(p[i*4], 10 | 4 << 10);
            printStr(" ");
        }
        printStr("\n");
        delay(800);                     //delay 800 ms


        IFS1bits.AD1IF = 0;             //reset AD1IF
    }
    return 0;
}
