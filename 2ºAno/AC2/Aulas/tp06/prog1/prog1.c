#include <detpic32.h>

//delay function
void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

//interruption handler
void _int_(27) isr_adc(void)
{
    printInt(ADC1BUF0, 16 | 3 << 16);
    printStr(" ");
    delay(500);                     //delay 500 ms 
    AD1CON1bits.ASAM = 1;           //Start the conversion
    IFS1bits.AD1IF = 0;             //reset interruption flag
}

int main(void)
{
    //A/D Configuration
    TRISBbits.TRISB4 = 1;           //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;          //RB4 configured as analog input (AN4)
    AD1CON1bits.SSRC = 7;           //internal counter ends sampling and starts conversion
    AD1CON1bits.CLRASAM = 1;        //Stop conversion when first interruption is generated
    AD1CON3bits.SAMC = 16;          //Sample time is 16 TAD
    AD1CON2bits.SMPI = 1-1;         //1 conversion
    AD1CHSbits.CH0SA = 4;           //channel AN4
    AD1CON1bits.ON = 1;             //A/D converter activated
    
    //Interrupt System configuration
    IPC6bits.AD1IP = 2;             //Interruption priority is 2
    IEC1bits.AD1IE = 1;             //A/D interruptions are enabled
    IFS1bits.AD1IF = 0;             //reset AD1IF flag
    EnableInterrupts();             //Global interrupt enable

    AD1CON1bits.ASAM = 1;           //A/D conversion begins

    while(1) {};
    return 0;
}
