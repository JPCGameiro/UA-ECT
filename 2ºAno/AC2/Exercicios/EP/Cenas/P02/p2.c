#include <detpic32.h>

#define DisableUartRxInterrupts() IEC0bits.U1RXIE = 0
#define EnableUartRxInterrupts() IEC0bits.U1RXIE = 1
#define DisableUartTxInterrupts() IEC0bits.U1TXIE = 0
#define EnableUartTxInterrupts() IEC0bits.U1TXIE = 1

int value2send = 0;
volatile unsigned char voltage = 0;

//Configurações

//Configurar Portos
void configIO()
{
    TRISB = (TRISB & 0x80F0) | 0x000F;
    TRISD = TRISD & 0xFF9F;
    TRISE = TRISE & 0xFFF0;
}

//Configurar Timer T2
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

//Configurar ADC
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

//Configurar UART1
void configUART1()
{
    U1BRG = ((PBCLK + 8*115200) / (16*115200)) - 1;
    U1MODEbits.BRGH = 0;

    U1MODEbits.PDSEL = 00;
    U1MODEbits.STSEL = 0;

    U1STAbits.URXEN = 1;
    U1STAbits.UTXEN = 1;

    U1MODEbits.ON = 1;

    U1STAbits.URXISEL = 00;
    U1STAbits.UTXSEL = 00;

    IPC6bits.U1IP = 2;
    IEC0bits.U1TXIE = 1;
    IEC0bits.U1RXIE = 1;
    IFS0bits.U1TXIF = 0; 
    IFS0bits.U1RXIF = 0;
}

//Funções Auxiliares

void delay(int ms)
{
    for(;ms > 0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

unsigned char toBcd(unsigned char value)
{
    return ((value/10)<<4) + (value%10);
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

unsigned char shiftLedsLeft(unsigned char value)
{
    unsigned Msb = value & 0x0008;
    Msb = (Msb ^ 0x0008) >> 3;
    unsigned newValue = (value & 0x000F) << 1;
    newValue = (newValue & 0x000E) | Msb;
    LATE = (LATE & 0xFFF0) | newValue;
    return newValue;    
}

unsigned char shiftLedsRight(unsigned char value)
{
    unsigned Lsb = value & 0x0001;
    Lsb = (Lsb ^ 0x0001) << 3;
    unsigned newValue = (value & 0x000F) >> 1;
    newValue = (newValue & 0x0007) | Lsb;
    LATE = (LATE & 0xFFF0) | newValue;
    return newValue;
}

void putc(char byte2send)
{
    while(U1STAbits.UTXBF == 1);
    U1TXREG = byte2send;
}

void putS(char* s)
{
    while(*s != '\0'){
        putc(*s);
        s++;
    }
}

char getc(void)
{
    if(U1STAbits.OERR == 1)
        U1STAbits.OERR = 0;
    while(U1STAbits.URXDA == 0);
    return U1RXREG;
}

//Rotinas de Serviço à Interruptão (RSIs)

//T2 RSI
void _int_(8) isr_t2(void)
{
    if(value2send != 255) 
        send2displays(toBcd(value2send));
    else    
        send2displays(0x00FF);
    IFS0bits.T2IF = 0;    
}

//ADC RSI
void _int_(27) isr_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(; p <= (int *)(&ADC1BUFF); p += 4)
        sum += *p;
    double average = sum/4.0;
    voltage = (char)((average*33)/1023);
    IFS1bits.AD1IF = 0;
}

//UART1 RSI 
void _int_(24) isr_uart1(void)
{
    if(IFS0bits.U1RXIF == 1){
        putS("\nCarregaste na tecla: ");
        putc(U1RXREG);
        IFS0bits.U1RXIF = 0;
    }
}

int main(void)
{
    configIO();
    configT2();
    configADC();
    configUART1();
    EnableInterrupts();
    DisableUartRxInterrupts();
    DisableUartTxInterrupts();    

    int cnt = 0, portvalue, currentLeds = 0x000F;
    while(1)
    {
        portvalue = PORTB & 0x000F;

        switch(portvalue){
            case 0:
                cnt++;
                value2send = cnt;
                break;
            case 1:
                cnt--;
                value2send = cnt;
                break;
            case 2:
                currentLeds = shiftLedsLeft(currentLeds);
                break;
            case 3:
                currentLeds = shiftLedsRight(currentLeds);
                break;
            case 4:
                AD1CON1bits.ASAM = 1;
                value2send = voltage;
                break;
            case 5:
                EnableUartRxInterrupts();
                break;
            case 6:
                printStr("\nValor no porto RB: ");
                printInt(PORTB & 0xFFFF, 16);
                break;
            default:
                value2send = 255;
                LATE = (LATE & 0xFFF0) | 0x000F;
                break;
        }
        
        if(cnt == 99)
            cnt = 0;
        else if(cnt == -1)
            cnt = 99;
        
        delay(125);
        DisableUartRxInterrupts();
    }
    return 0;
}

