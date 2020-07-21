#include <detpic32.h>

volatile unsigned char voltage = 0;
int freq = 10;

unsigned char toBcd(unsigned char value)
{
    return ((value/10)<<4) + (value%10);
}

void delay(int ms)
{
    for(;ms > 0;ms--){
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int display_low = display7Scodes[value & 0x0F];
    int display_high = display7Scodes[value >> 4];

    if(displayFlag==0){
        LATDbits.LATD5 = 1;
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | (display_low << 8);
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;
        LATB = (LATB & 0x80FF) | (display_high << 8);
    }
    displayFlag = !displayFlag;
}

void putc(char byte2send)
{
    while(U1STAbits.UTXBF == 1);
    U1TXREG = byte2send;
}

void putS(char* s)
{
    int i=0;
    while(s[i]!='\0'){
        putc(s[i]);
        i++;
    }
}

void configUart1()
{
    U1BRG = ((PBCLK + 8 * 1200) / (16 * 1200)) - 1;
    U1MODEbits.BRGH = 0;
    U1MODEbits.PDSEL = 00;
    U1MODEbits.STSEL = 0;
    U1STAbits.URXEN = 1;
    U1STAbits.UTXEN = 1;
    U1MODEbits.ON = 1;
}

void configIO()
{
    TRISB = (TRISB & 0x80F0) | 0x000F;
    TRISD = TRISD & 0xFF9F;
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

void configT2()
{
    T2CONbits.TCKPS = 2;
    PR2 = 49999;
    TMR2 = 0;
    T2CONbits.ON = 1;

    IPC2bits.T2IP = 2;
    IEC0bits.T2IE = 1;
    IFS0bits.T2IF = 0;
}

void _int_(27) rsi_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(;p <= (int *)(&ADC1BUFF); p+=4)
        sum += *p;
    double average = sum/4.0;
    voltage = (char)((average*33)/1023);
    IFS1bits.AD1IF = 0;
}

void _int_(8) rsi_t2(void)
{
    send2displays(toBcd(freq));
    IFS0bits.T2IF = 0;
}


void portValue(unsigned char value)
{
    if((value & 0x0001) == 0x0000)
        putc('0');
    else
        putc('1');
}

int main(void)
{
    putS("João Gameiro - Nº93097\n");
    putS("Ex9 Preparação Exame Prático - AC2\n");

    configIO();
    configT2();
    configADC();
    configUart1();
    EnableInterrupts();

    while(1)
    {
        AD1CON1bits.ASAM = 1;

        putS("\nValor nos Interruptores: ");
        portValue(PORTBbits.RB0);
        portValue(PORTBbits.RB1);
        portValue(PORTBbits.RB2);
        portValue(PORTBbits.RB3);

        putS("\nFrequência: ");
        printInt(freq, 10);

        freq = 10 + (voltage*10/255);
        delay(1000/freq);
    } 
    return 0;
}

