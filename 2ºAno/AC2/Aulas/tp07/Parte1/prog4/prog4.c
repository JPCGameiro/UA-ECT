#include <detpic32.h>

void _int_(4) isr_t1(void)
{
    printInt10(1);
    IFS0bits.T1IF = 0;          //reset T1IF flag
}

void _int_(12) isr_t3(void)
{
    printInt10(3);
    IFS0bits.T3IF = 0;          //reset T3IF flag
}

int main(void)
{
    //Configure Timer T1
    T1CONbits.TCKPS = 3;        //Kprescaler = 256  FoutPrescaler = 78125Hz;
    PR1 = 39062;                //Fout = 2Hz = (PBLCK/(39062+1)*256)
    TMR1 = 0;                   //reset Timer T1 counter
    T1CONbits.TON = 1;          //enable Timer T1

    //Configure Timer T3
    T3CONbits.TCKPS = 5;        //Kprescaler = 32  FoutPrescaler = 62500Hz;
    PR3 = 62499;                //Fout = 10Hz = (PBLCK/(62499+1)*32)
    TMR3 = 0;                   //reset Timer T3 counter
    T3CONbits.TON = 1;          //enable Timer T3

    //Configure Timer T1 Interrupts
    IPC1bits.T1IP = 2;          //priority 2
    IEC0bits.T1IE = 1;          //interrupts enabled
    IFS0bits.T1IF = 0;          //reset interrupt flag

    //Configure Timer T3 Interrupts
    IPC3bits.T3IP = 2;          //priority 2
    IEC0bits.T3IE = 1;          //interrupts enabled
    IFS0bits.T3IF = 0;          //reset interrupt flag

    EnableInterrupts();
    while(1);

    return 0;
}
