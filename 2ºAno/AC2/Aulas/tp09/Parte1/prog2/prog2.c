#include <detpic32.h>

#define DisableUart1RxInterrupts() IEC0bits.U1RXIE = 0
#define EnableUart1RxInterrupts() IEC0bits.U1RXIE = 1
#define DisableUart1TxInterrupts() IEC0bits.U1TXIE = 0
#define EnableUart1TxInterrupts() IEC0bits.U1TXIE = 1
#define BUF_SIZE 8
#define INDEX_MASK (BUF_SIZE-1)

//circular Buffer struct
typedef struct
{
    unsigned char data[BUF_SIZE];
    unsigned int head;
    unsigned int tail;
    unsigned int count;
} circularBuffer;

volatile circularBuffer txb;    //Transmission Buffer
volatile circularBuffer rxb;    //Reception Buffer

//initialize the variables of the reception buffer
void comDrv_flushRx(void)
{
    rxb.head = 0;
    rxb.tail = 0;
    rxb.count = 0;
}

//initiazlize the variables of the transmission buffer
void comDrv_flushTx(void)
{
    txb.head = 0;
    txb.tail = 0;
    txb.count = 0;
}

//write a char in the transmission buffer
void comDrv_putc(char ch)
{
    while(txb.count == BUF_SIZE);           //wait while buffer is full
    txb.data[txb.tail] = ch;                //copy char ch to "tail" position of the Transmission buffer
    txb.tail = (txb.tail + 1) & INDEX_MASK; //increment "tail" index (mod BUF_SIZE)
    DisableUart1TxInterrupts();             //Begin of the critical section
    txb.count++;                            //increment count variable
    EnableUart1TxInterrupts();              //End of critical section    
}

//write a string in the transmission buffer
void comDrv_puts(char *s)
{
    while(*s != '\0'){
        comDrv_putc(*s);
        s++;
    }
}

//read a char from the reception buffer
char comDrv_getc(char *pchar)
{
    if(rxb.count == 0)                      //the buffer is empty
        return 0;
    DisableUart1RxInterrupts();             //Begining of critical section
    *pchar = rxb.data[rxb.head];            //copy character pointed by "head" to *pchar
    rxb.count--;                            //decrement count variable
    rxb.head = (rxb.head + 1) & INDEX_MASK; //increment "head" index (mod BUF_SIZE)
    EnableUart1RxInterrupts();              //end of critical section
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

//UART1 RSI
void _int_(24) isr_uart1(void)
{
    if(IFS0bits.U1TXIF == 1){                       //Transmission interruption flag
        if(txb.count > 0){                          //means that there is info in the circular buffer
            U1TXREG =  txb.data[txb.head];          //copy char in head position to the U1TXREG register
            txb.head = (txb.head + 1) & INDEX_MASK; //increment "head" index (mod BUF_SIZE)
            txb.count--;
        }
        else if(txb.count == 0){                    //means that there is no info in the circular buffer
            DisableUart1TxInterrupts();
        }
        IFS0bits.U1TXIF = 0;                        //reset UART1 TX interrupt flag   
    }

    if(IFS0bits.U1RXIF == 1){                       //Reception interruption flag        
        rxb.data[rxb.tail] = U1RXREG;               //read a char from UART and write to the "tail" position
        rxb.tail = (rxb.tail + 1) & INDEX_MASK;     //increment "tail" index (mod BUF_SIZE)
        if(rxb.count < BUF_SIZE)                    //If buffer is not full
            rxb.count++;
        else
            rxb.head = (rxb.head + 1) & INDEX_MASK; //Increment "head" variable (discard oldest character)
        IFS0bits.U1RXIF = 0;                        //reset UART1 RX interrupt flag
    }    
}

int main(void)
{
    char s;
    comDrv_config(115200, 'N', 1);
    comDrv_flushRx();
    comDrv_flushTx();
    EnableInterrupts();
    comDrv_puts("PIC32 UART Device-drivers\n");
    while(1){
        comDrv_getc(&s);
        if(s == 'S')
            comDrv_puts("abcdefghijklmpqrstuvxyz1234567890");
        else
            comDrv_putc(s);
    }
    return 0;
}
