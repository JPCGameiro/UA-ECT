#include <detpic32.h>
#include "functs.h"

volatile unsigned char voltage = 0;     //global variable

void _int_(27) isr_adc(void)
{
    int sum = 0;
    int *p = (int *) (&ADC1BUF0);
    for(; p<= (int *)(&ADC1BUFF); p+=4) {
        sum += *p;
    }
    
    double average = (double) sum/8.0;
    voltage = (char) ((average*33)/1023); 
    IFS1bits.AD1IF = 0;             //reset flag AD1IF
}

int main(void)
{
    //digital I/O configuration
    TRISB = TRISB & 0x80FF;             //RB15-RB8 as OUTPUTS
    LATB = LATB & 0x80FF;
    
    TRISD = TRISD & 0xFF9F;             //RD6-RD5 as OUTPUTS
    LATD = LATD & 0xF9FF;

    configureAD(8);             //configure A/D converter, 8 samples
    configureADInterrupt(2);    //configure A/D interruptions, priority 2

    int i=0;
    while(1)
    {
        if(i++ == 25)           //250ms (4 samples/second)
        {
            AD1CON1bits.ASAM = 1;       //begin A/D conversion
            i=0;
        }
        send2displays(toBcd(voltage & 0xFF));         //send voltage to displays
        delay(10);              //delay 10 ms
    }
    return 0;
}
