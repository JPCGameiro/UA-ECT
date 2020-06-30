#include <detpic32.h>

static int count = 1;

void _int_ (12) isr_t3(void)
{
    if(count==2){                //calling putChar at 1Hz frequency
        putChar('.');
        count=1;
    }
    else 
        count++;
    IFS0bits.T3IF = 0;          //reset T3IF interrupt flag
}

int main(void)
{
    //Configure Timer T3
    T3CONbits.TCKPS = 7;        //Kprescaler = 256, Foutprescaler = 78125Hz
    PR3 = 39062;                //fout = 2Hz = (PBCLK/256*(39062+1))
    TMR3 = 0;                   //reset Timer T3 counter
    T3CONbits.TON = 1;          //enable Timer T3

    //Configute Timer T3 Interruptions
    IPC3bits.T3IP = 2;          //priority 2
    IEC0bits.T3IE = 1;          //enable all interrupts
    IFS0bits.T3IF = 0;          //reset interrupts flag
    EnableInterrupts();

    while(1);
}
