#include <detpic32.h>

int main(void)
{
    //configure Timer T3
    T3CONbits.TCKPS = 2;            //Kprescaler = 4  Foutprescaler = 5*10^6Hz
    PR3 = 49999;                    //Fout = 100Hz = (PBCLK/(49999+1)*4)
    TMR3 = 0;                       //reset Timer T3 counter
    T3CONbits.TON = 1;              //Timer T3 enabled

    OC1CONbits.OCM = 6;             //PWM mode on OCx fault pin disabled
    OC1CONbits.OCTSEL = 1;          //Timer T3 is the clock source for PWM generation
    OC1RS = 12500;                  //OC1RS constant  dutycycle = 25%
    OC1CONbits.ON = 1;              //Enable OC1 module

    while(1);
    
    return 0;
}
