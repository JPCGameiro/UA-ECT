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
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;                     //set display high
        LATB = (LATB & 0x80FF) | digit_high<<8;    //send digit_high to display_high
    }
    displayFlag = !displayFlag;                 //toogle displayFlag value
}

int main(void)
{
    //analog input/AD converter configuration
    TRISBbits.TRISB4 = 1;               //RB4 digital output disabled
    AD1PCFGbits.PCFG4 = 0;              //RB4 configured as analog input (AN4)
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;            //Stop conversion when first interruption is generated
    AD1CON3bits.SAMC = 16;              //Sample time is 16 TAD
    AD1CON2bits.SMPI = 4-1;             //4 samples
    AD1CHSbits.CH0SA = 4;               //channel 4 (AN4)
    AD1CON1bits.ON = 1;                 //A/D converter enabled

    //digital I/O configuration
    TRISB = TRISB & 0x80FF;             //RB15-RB8 as OUTPUTS
    LATB = LATB & 0x80FF;
    
    TRISD = TRISD & 0xFF9F;             //RD6-RD5 as OUTPUTS
    LATD = LATD & 0xF9FF;

    int i=0, v=0;

    while(1)
    {
        int soma = 0;
        double media;

        if(i++ % 25 == 0)                    //0ms-250ms-500ms-750ms...
        {
            AD1CON1bits.ASAM = 1;            //Convertion order
            while(IFS1bits.AD1IF == 0);      //wait for the interruption
            int *p = (int *) (&ADC1BUF0);
            for(; p<=(int *)(&ADC1BUFF); p+=4)
                soma += *p;
            media = (double)soma/4.0;
            v = (media*33)/1023;
            IFS1bits.AD1IF = 0;              //reset AD1IF;
        }

        send2displays(toBcd(v & 0xFF));
        delay(10);                  
    }
    return 0;
}
