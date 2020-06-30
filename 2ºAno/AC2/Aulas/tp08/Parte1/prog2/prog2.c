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

void puts(char * str)
{
    while(*str !='\0'){
        putc(*str);
        str++;
    }
}

int main(void)
{
    //Configure UART1
    U1BRG = ((PBCLK + 8*115200)/(16*115200))-1;     //Constant U1BRG, baudrate 115200bps
    U1MODEbits.BRGH = 0;                            //16x baud clock enabled
    U1MODEbits.PDSEL = 0;                           //8 data bits, no parity
    U1MODEbits.STSEL = 0;                           //1 stop bit
    U1STAbits.URXEN = 1;                            //Receiver is enabled
    U1STAbits.UTXEN = 1;                            //Transmiter is enabled
    U1MODEbits.ON = 1;                              //UART1 enabled

    while(1)
    {
        puts("String de teste\n");
        delay(1000);
    }

    return 0;
}
