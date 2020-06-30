#include <detpic32.h>

void _int_(27) isr_adc(void)
{
    int value = readCoreTimer();
    printStr(" Time: ");
    printInt10(value*50);
    printStr(" ns\n");
    
    printInt(ADC1BUF0, 16 | 3 << 16);
    AD1CON1bits.ASAM = 1;
    IFS1bits.AD1IF = 0;
    LATBbits.LATB6 = 1;
}

int main(void)
{
    //A/D converter
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 1-1;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    //Digital I/O
    TRISBbits.TRISB6 = 0;

    //Interrupt System
    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;
    EnableInterrupts();

    resetCoreTimer();
    //Start conversion
    AD1CON1bits.ASAM = 1;
    while(1) 
    {
        LATBbits.LATB6 = 0;
    };
}
