#include <detpic32.h>

void _int_(27) isr_adc(void)
{
    resetCoreTimer();
    LATBbits.LATB6 = 0;
    printInt(ADC1BUF0, 16 | 3 << 16);
    printStr(" ");
    int timeValue = readCoreTimer();
    printStr("Time: ");
    printInt10(timeValue*50);
    printStr(" ns\n");
    LATBbits.LATB6 = 1;
    AD1CON1bits.ASAM = 1;
    IFS1bits.AD1IF = 0;
}

int main(void)
{
    //A/D converter
    TRISBbits.TRISB4 = 1;       //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;      //RB4 configured as analog input
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;    //Stop conversion at the first interruption
    AD1CON3bits.SAMC = 16;      //Sample time is 16 TAD
    AD1CON2bits.SMPI = 1-1;     //1 Conversion
    AD1CHSbits.CH0SA = 4;       //AN4 channel selected
    AD1CON1bits.ON = 1;         //A/D converter enabled

    //digital I/O
    TRISBbits.TRISB6 = 0;       //RB6 is Output
    LATBbits.LATB6 = 0;         //set default value    

    //Interrupt System
    IPC6bits.AD1IP = 2;         //set priority to 2
    IEC1bits.AD1IE = 1;         //Enable interrupt
    IFS1bits.AD1IF = 0;         //reset AD1IF flag
    EnableInterrupts();

    AD1CON1bits.ASAM = 1;        //Start conversion
    while(1) {}

    return 0;
}
