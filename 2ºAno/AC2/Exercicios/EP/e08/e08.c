#include <detpic32.h>

volatile unsigned char voltage = 0;
int freq = 10;

void delay(int ms){
    for(; ms > 0; ms--){
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

void configIO(){
    TRISB = (TRISB & 0x80F0) | 0x000F;
    TRISD = TRISD & 0xFF9F;
}

void configADC(){
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 1-1;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;
}

void configT2(){
    T2CONbits.TCKPS = 2;
    PR2 = 49999;
    TMR2 = 0;
    T2CONbits.TON = 1;

    IPC2bits.T2IP = 2;
    IEC0bits.T2IE = 1;
    IFS0bits.T2IF = 0;
}

unsigned char toBcd(unsigned char value){
    return ((value/10) << 4) + (value % 10);
}

void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int digit_low = display7Scodes[value & 0x0F];
    int digit_high = display7Scodes[value >> 4];

    if(displayFlag==0){
        LATDbits.LATD5 = 1;
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | digit_low << 8;
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;
        LATB = (LATB & 0x80FF) | digit_high << 8;
    }
    displayFlag = !displayFlag;                 //toogle displayFlag value
}

void _int_(27) isr_adc(void){
    int aux = ADC1BUF0;
    voltage = (aux*33)/1023;
    freq = 1 + voltage/255;
    IFS1bits.AD1IF = 0;
}

void _int_(8) isr_T2(void){
    send2displays(toBcd(freq));
    IFS0bits.T2IF = 0;    
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex8 Preparação Exame Prático - AC2\n");

    configIO();
    configADC();
    configT2();
    EnableInterrupts();

    while(1){

        AD1CON1bits.ASAM = 1;

        printStr("\n\nValor nos interruptores: ");
        printInt(PORTBbits.RB0, 2 | 1 << 2);
        printInt(PORTBbits.RB1, 2 | 1 << 2);
        printInt(PORTBbits.RB2, 2 | 1 << 2);
        printInt(PORTBbits.RB3, 2 | 1 << 2);

        delay(1000/freq);
    }
}
