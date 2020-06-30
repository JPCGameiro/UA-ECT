#include <detpic32.h>
#include "functions.h"

volatile unsigned char voltage = 0x00;
volatile unsigned char voltMin = 0x33;
volatile unsigned char voltMax = 0x00;

//T1 RSI
void _int_(4) isr_t1(void)
{
    AD1CON1bits.ASAM = 1;       //Start AD conversion
    IFS0bits.T1IF = 0;          //reset T1IF flag
}

//T3 RSI
void _int_(12) isr_t3(void)
{
    static int counter = 0;

    send2displays(voltage);      //send voltage to displays
    if(++counter == 100){
        counter = 0;
        char V0 = 0x30 | ((voltage & 0xF0) >> 4);
        char V1 = 0x30 | ((voltage & 0x0F));
        puts("\nVoltagem: ");
        putc(V0);
        putc(V1);
    }
    IFS0bits.T3IF = 0;          //reset T3IF flag
}

//A/C RSI
void _int_(27) isr_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(;p<=(int *)(&ADC1BUFF);p+=4){
        sum += *p;
    }
    double average = (double) sum/8.0;           //calculate buffer average 8 Samples
    voltage = (char) ((average*33)/1023);        //calculate buffer voltage amplitude
    voltage = toBcd(voltage & 0xFF);            //convert voltage amplitude to decimal

    if(voltage > voltMax)
        voltMax = voltage;
    if(voltage < voltMin)
        voltMin = voltage;

    IFS1bits.AD1IF = 0;                         //reset AD1IF flag
}

//UART RSI
void _int_(24) isr_uart1(void)
{
    char dumb;
    if(IFS0bits.U1EIF == 1){
        if(U1STAbits.OERR == 1)
            U1STAbits.OERR = 0;
        else 
            dumb = U1RXREG;
        IFS0bits.U1EIF = 0;
    }

    if(U1RXREG == 'L')
    {
        puts("\nValor mínimo: ");
        char Min1 = 0x30 | ((voltMin & 0xF0) >> 4);
        char Min2 = 0x30 | ((voltMin & 0x0F));
        putc(Min1);
        putc(Min2);
        puts("\nValor máximo: ");
        char Max1 = 0x30 | ((voltMax & 0xF0) >> 4);
        char Max2 = 0x30 | ((voltMax & 0x0F));
        putc(Max1);
        putc(Max2);
    }
    IFS0bits.U1RXIF = 0;    //clear reception interrupts flag 
}

int main(void)
{
    int dutyCycle;
    int portVal;
    configureAD();
    configureIO();
    configureT1();
    configureT3();
    configUart(115200, 'N', 1);
    EnableInterrupts();
    while(1){
        portVal = PORTB & 0x0003;       //read RB1-RB0 to variable portVal

        switch(portVal)
        {
            case 0:  //Measure Input Voltage
                IEC0bits.T1IE = 1;          //enable T1 interrupts
                setPWM(0);                  //led off
                break;
            case 1:  //Freeze
                IEC0bits.T1IE = 0;          //disable T1 interruprs 
                setPWM(100);                //Led on (maximum bright)
                break;
            default:  //Led brightness control
                IEC0bits.T1IE = 1;          //enable T1 interrupts
                dutyCycle = voltage*3;
                setPWM(dutyCycle);
                break;
        }
    }
    return 0;
}
