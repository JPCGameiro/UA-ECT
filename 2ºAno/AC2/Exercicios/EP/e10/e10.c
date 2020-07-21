#include <detpic32.h>

#define DisableUart1RxInterrupt() IEC0bits.U1RXIE = 0
#define EnableUart1RxInterrupt() IEC0bits.U1RXIE = 1
#define DisableUart1TxInterrupt() IEC0bits.U1TXIE = 0
#define EnableUart1TxInterrupt() IEC0bits.U1TXIE = 1

#define BUF_SIZE 32
#define INDEX_MASK (BUF_SIZE-1)

typedef struct {
    unsigned char data[BUF_SIZE];
    unsigned int tail;
    unsigned int head;
    unsigned int count;
} circularBuffer;

volatile circularBuffer txb;
volatile circularBuffer rxb;

//Inicializar buffer Receção
void comDrv_flushRx()
{
    rxb.count = 0;
    rxb.tail = 0;
    rxb.head = 0;
}

//Inicializar buffer Transmissão
void comDrv_flushTx()
{
    txb.count = 0;
    txb.tail = 0;
    txb.head = 0;
}

//Enviar um caracter para o buffer Transmissão
void comDrv_putc(char ch)
{
    while(txb.count == BUF_SIZE);
    txb.data[txb.tail] = ch;
    txb.tail = (txb.tail + 1) & INDEX_MASK;
    DisableUart1TxInterrupt();
    txb.count++;
    EnableUart1TxInterrupt();
}

//Enviar uma string para o buffer de Transmissão
void comDrv_putS(char *s)
{
    int i=0;
    while(s[i]!='\0'){
        comDrv_putc(s[i]);
        i++;
    }
}

char comDrv_getc(char *s)
{
    if(rxb.count == 0)
        return 0;
    DisableUart1RxInterrupt();
    *s = rxb.data[rxb.head];
    rxb.count--;
    rxb.head = (rxb.head + 1) & INDEX_MASK;
    EnableUart1RxInterrupt();
    return 1;
}

//configure Driver
void comDrv_config(unsigned int baud, char parity, unsigned int stopbits)
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

    IPC6bits.U1IP = 2;                          //interrupts priority is 2
    IEC0bits.U1RXIE = 1;                        //Rx interrupts enabled
    IEC0bits.U1TXIE = 1;                        //Tx interrupts enabled
    IFS0bits.U1RXIF = 0;                        //reset Rx interrupt flag
    IFS0bits.U1TXIF = 0;                        //reset Tx interrupt flag

    U1MODEbits.ON = 1;                          //UART1 enabled
}


void _int_(24) isr_uart1(void)
{
    if(IFS0bits.U1TXIF == 1){
        if(txb.count > 0){
            U1TXREG = txb.data[txb.head];
            txb.head = (txb.head + 1) & INDEX_MASK;
            txb.count--;
        }
        else if(txb.count==0)
            DisableUart1TxInterrupt();
        IFS0bits.U1TXIF = 0;
    }
    if(IFS0bits.U1RXIF == 1){
        rxb.data[rxb.tail] = U1RXREG;
        rxb.tail = (rxb.tail + 1) & INDEX_MASK;
        if(rxb.count < BUF_SIZE)
            rxb.count++;
        else
            rxb.head = (rxb.head + 1) & INDEX_MASK;
        IFS0bits.U1RXIF = 0;
    }

}

void delay(int ms)
{
    for(; ms > 0; ms--){
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}


int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex10 Preparação Exame Prático - AC2\n");

    comDrv_config(115200, 'N', 1);
    comDrv_flushRx();
    comDrv_flushTx();
    EnableInterrupts();

    char s;   
    comDrv_putS("PIC32 UART Device-drivers\n");


    while(1){
        if(comDrv_getc(&s)){
            if(s == 'e')
                comDrv_putS("Uma string com 30 caracteress.");
            else    
                comDrv_putS("\n1234");
        }
        delay(1000);
    }

    return 0;
}

