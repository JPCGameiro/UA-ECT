#include <detpic32.h>

int main(void)
{
    TRISEbits.TRISE0 = 0;           //configure RE0 as digital output

    TRISBbits.TRISB4 = 1;           //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;           //RB4 configured as analog input (AN4)
    AD1CON1bits.SSRC = 7;           
    AD1CON1bits.CLRASAM = 1;        //Stop the conversion when the 1st Interruption is generated
    AD1CON3bits.SAMC = 16;          //Sample time is 16 TAD
    AD1CON2bits.SMPI = 1-1;         //1 sample
    AD1CHSbits.CH0SA = 4;           //Select channel 4
    AD1CON1bits.ON = 1;             //A/D converter is enabled

    TRISEbits.TRISE0 = 0;           //configure RE0 as output

    int tmrVal;
    while(1)
    {
        resetCoreTimer();

        LATEbits.LATE0 = 1;         //set LATE0
        AD1CON1bits.ASAM = 1;       //Start conversion
        while(IFS1bits.AD1IF == 0); //wait while conversion not done

        tmrVal = readCoreTimer();
        printStr("\nTime: ");
        printInt10(tmrVal*50);
        printStr("ns");

        LATEbits.LATE0 = 0;         //reset LATE0
        int aux = ADC1BUF0;         //store conversion result in aux variable
        IFS1bits.AD1IF = 0;         //reset AD1IF

        //Time measure: 3700 ns
    }
    return 0;
}
