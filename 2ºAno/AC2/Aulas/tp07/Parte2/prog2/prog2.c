#include <detpic32.h>

void setPWM(unsigned int dutyCycle){
    if(dutyCycle>100)
        return;
    else
        OC1RS = 500*dutyCycle;                //OC1RS = (PR3+1)*(dutycycle/100) = 500*dutycyle
}

int main(void)
{
    TRISEbits.TRISE0 = 0;

    //Configure Timer T3
    T3CONbits.TCKPS = 2;            //Kprescaler = 4  FoutPrescaler = 5*10^6 Hz
    PR3 = 49999;                    //Fout = 100 Hz = (PBCLK/(49999+1)*4)
    TMR3 = 0;                       //reset Timer T3 counter
    T3CONbits.TON = 1;              //Timer T3 enabled

    OC1CONbits.OCM = 6;             //PWM mode on OCx fault pin disabled
    OC1CONbits.OCTSEL = 1;          //Timer T3 selected as clock source
    OC1RS = 12500;                  //OC1RS constant  default value dutycycle = 25%
    OC1CONbits.ON = 1;              //Enable OC1 module

    while(1){
        //setPWM(0);
        //setPWM(10);                     //set OC1RS constant value
        //setPWM(40);
        //setPWM(65);
        //setPWM(80);
        setPWM(90);
    }
    return 0;
}
