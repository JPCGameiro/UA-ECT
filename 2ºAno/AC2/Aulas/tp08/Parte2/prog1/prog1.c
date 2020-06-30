#include <detpic32.h>

void configUart(unsigned int baud, char parity, unsigned int stopbits)
{
    if(baud < 600 || baud > 115200)
        baud = 115200;
    if(parity!='N' || parity!='E' || parity!='O')
        parity = 'N';
    if(stopbits!=1 || stopbits!=2)
        stopbits = 1;
    
    U1BRG = ((PBCLK+8*baud)/(16*baud))-1;       //configure baudrate generator
    U1MODEbits.BRGH = 0;                        //16x baud clk enabled

    //config parity,  nºbits = 8
    switch(parity){
        case 'N':
            U1MODEbits.PDSEL = 0;               //8 bits, no parity
            break;
        case 'E':
            U1MODEbits.PDSEL = 1;               //8 bits, even parity
            break;
        case 'O':
            U1MODEbits.PDSEL = 2;               //8 bits, odd parity
            break;
    }

    //config stopbits
    switch(stopbits){
        case 2:
            U1MODEbits.STSEL = 1;               //2 stop bits
            break;
        case 1:
            U1MODEbits.STSEL = 0;               //1 stop bit
            break;
    }

    U1STAbits.URXEN = 1;                        //UART1 receiver is enabled
    U1STAbits.UTXEN = 1;                        //UART1 transmitter is enabled
    
    IPC6bits.U1IP = 2;                          //priority 2
    IEC0bits.U1RXIE = 1;                        //interrupts enabled
    IFS0bits.U1RXIF = 0;                        //clear reception interrupts flag
    
    U1MODEbits.ON = 1;                          //UART1 enabled


}

void putc(char byte2send)
{
    while(U1STAbits.UTXBF == 1);                    //wait while UTXBF==1
    U1TXREG = byte2send;

}

void _int_(24) isr_uart1(void)
{
    putc(U1RXREG);
    IFS0bits.U1RXIF = 0;
}

int main(void)
{
    configUart(115200, 'N', 1);

    EnableInterrupts();
    while(1);
}
