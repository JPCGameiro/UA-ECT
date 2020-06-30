#include <detpic32.h>
#include "functions.h"

volatile unsigned char voltage = 0;

//isr function - timer T1
void _int_(4) isr_t1(void)
{
    AD1CON1bits.ASAM = 1;   //start A/D conversion
    IFS0bits.T1IF = 0;      //reset T1IF flag
}

//isr function - timer T3
void _int_(12) isr_t3(void)
{
    send2displays(voltage);                     //sending voltage to displays
    IFS0bits.T3IF = 0;                          //reset T3IF flag
}

//isr function - A/D converter
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
    configureIO();          //configureI/O
    configureAD();          //configureA/D converter with interrupts
    configureT1();          //configure timer T1 with interrupts
    configureT3();          //configure timer T3 with interrupts
    EnableInterrupts();     //Global Interrupt Enable    
    while(1);
    return 0;
}
