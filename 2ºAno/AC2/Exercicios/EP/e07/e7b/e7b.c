#include <detpic32.h>

//Configuração da ADC com interrupções
void configADC()
{
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 1-1; //verificar
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;
}

//Configuração I/O
void configIO()
{
    TRISB = (TRISB & 0xFFF0) | 0x000F;
}

void delay(int ms)
{
    for(; ms > 0; ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }    
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex7b Preparação Exame Prático - AC2\n");

    configADC();
    configIO();
    EnableInterrupts();

    int freq = 10;
    int t;  //verificar se é com double
    while(1)
    {
        AD1CON1bits.ASAM = 1;
        while(IFS1bits.AD1IF == 0);

        freq = 1 + (ADC1BUF0/255);
        t = 1/freq;
        printStr("\nTensão: ");
        printInt(t, 10 | 2 << 10);
        printStr("\nFrequência: ");
        printInt(freq, 10 | 2 << 10);
        delay(t*1000);

        IFS1bits.AD1IF = 0;

        printStr("\nValor presente nos interruptores: ");
        printInt(PORTBbits.RB0, 2);
        printInt(PORTBbits.RB1, 2);
        printInt(PORTBbits.RB2, 2);
        printInt(PORTBbits.RB3, 2);
        printStr("\n");
    }
    return 0;
}

