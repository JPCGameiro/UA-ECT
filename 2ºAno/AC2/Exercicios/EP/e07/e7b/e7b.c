#include <detpic32.h>

volatile unsigned char voltage = 0;

void delay(int ms)
{
    for(;ms > 0;ms--){
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

void configADC()
{
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 4-1;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;
}

void _int_(27) rsi_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(; p <= (int *)(&ADC1BUFF);p += 4)
        sum += *p;
    double average = sum/4.0;
    voltage = (char)((average*33)/1023);
    IFS1bits.AD1IF = 0;
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex7b Preparação Exame Prático - AC2\n");

    TRISB = (TRISB & 0xFFF0) | 0x000F;

    configADC();
    EnableInterrupts(); 

    int freq = 10;
    while(1){
        AD1CON1bits.ASAM = 1;

        printStr("\nValor nos Interruptores: ");
        printInt(PORTBbits.RB0, 2);
        printInt(PORTBbits.RB1, 2);
        printInt(PORTBbits.RB2, 2);
        printInt(PORTBbits.RB3, 2);

        freq = 1 + (voltage/255);
        delay(1000/freq);
    }

    return 0;
}
