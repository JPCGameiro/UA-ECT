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
    TRISBbits.TRISB4 = 1;           //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;          //RB4 configured as analog input AN4
    AD1CON1bits.SSRC = 7;

    AD1CON1bits.CLRASAM = 1;        //Stop the conversion when the 1st A/D interrupt is generated

    AD1CON3bits.SAMC = 16;          //Sample time is 16 TAD    nota mudar este valor quando tiver a placa
    AD1CON2bits.SMPI = 4-1;         //Interrupt generated after 4 samples

    AD1CHSbits.CH0SA = 4;           //Select analog chanel 4

    AD1CON1bits.ON = 1;             //Enable the A/D

    while(1)
    {
        AD1CON1bits.ASAM = 1;
        while(IFS1bits.AD1IF == 0);        
        int soma = 0, v;
        double media;
        int *p = (int *) (&ADC1BUF0);
        for(;p <= (int *)(&ADC1BUFF);p+=4){
            printInt(*p, 10 | 4 << 10);
            printStr(" ");
            soma += *p;
        }
        media = (double) soma/4.0;
        v = (media*33)/1023;
        printStr("\nMédia: ");
        printInt((int) media, 10 | 4 << 10);
        printStr("\nTensão: ");
        printInt(v , 10 | 4 << 10);
        printStr("\n");

        delay(900);                     //delay 900 ms
        IFS1bits.AD1IF = 0;             //Reset AD1IF
    }
    return 0;
}
