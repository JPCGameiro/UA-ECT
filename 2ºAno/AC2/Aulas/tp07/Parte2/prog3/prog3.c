#include <detpic32.h>
#include "functions.h"

volatile unsigned char voltage = 0x00;

//T1 RSI
void _int_(4) isr_t1(void)
{
    AD1CON1bits.ASAM = 1;       //Start AD conversion
    IFS0bits.T1IF = 0;          //reset T1IF flag
}

//T3 RSI
void _int_(12) isr_t3(void)
{
    send2displays(voltage);      //send voltage to displays
    IFS0bits.T3IF = 0;                          //reset T3IF flag
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
    voltage = (char) ((average*33)/1023);         //calculate buffer voltage amplitude
    voltage = toBcd(voltage & 0xFF);            //convert voltage amplitude to decimal
    IFS1bits.AD1IF = 0;                         //reset AD1IF flag
}

int main(void)
{
    int dutyCycle;
    int portVal;
    configureAD();
    configureIO();
    configureT1();
    configureT3();
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
