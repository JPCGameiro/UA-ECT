#include <detpic32.h>

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

void putc(char byte2send)
{
    while(U1STAbits.UTXBF == 1);                    //wait while UTXBF==1
    U1TXREG = byte2send;

}

int main(void)
{
    //UART configuration
    U1BRG = ((PBCLK + 8*115200)/(16*115200))-1;     //constant U1BRG, baudrate = 115200
    U1MODEbits.BRGH = 0;                            //16x baud clock enabled
    U1MODEbits.PDSEL = 0;                           //8bits, no parity
    U1MODEbits.STSEL = 1;                           //1 stop bit
    U1STAbits.UTXEN = 1;                            //UART transmiter is enabled
    U1STAbits.URXEN = 1;                            //UART receiver is enabled
    U1MODEbits.ON = 1;                              //UART enabled

    while(1)
    {
        putc('+');
        delay(1000);                                //wait 1 s
    }

    return 0;
}
