#include <detpic32.h>

void _int_(12) isr_T3(void)
{
    putChar('.');
    IFS0bits.T3IF = 0;
}

int main(void)
{
    //configure Timer T3
    T3CONbits.TCKPS = 7;        //Kprescaler = 256, FoutPrescaler = 78125
    PR3 = 39062;                //Fout = 2Hz (PBLCK / (256*(39062+1))
    TMR3 = 0;                   //reset timer T3 counter
    T3CONbits.TON = 1;          //enable timer T3

    //configure Timer T3 interrupts
    IPC3bits.T3IP = 2;          //set priority
    IEC0bits.T3IE = 1;          //enable interruputs
    IFS0bits.T3IF = 0;          // reset interrupts flag
    EnableInterrupts();

    while(1);
}
