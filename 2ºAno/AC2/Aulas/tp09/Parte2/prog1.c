#include <detpic32.h>

#define DisableUart1RxInterrupts() IEC0bits.U1RXIE = 0;
#define EnableUart1RxInterrupts() IEC0bits.U1RXIE = 1;
#define DisableUart1TxInterrupts() IEC0bits.U1TXIE = 0;
#define EnableUart1TxInterrupts() IEC0bits.U1TXIE = 1;

#define BUF_SIZE 32
#define INDEX_MASK (BUF_SIZE-1)

//define circular buffer struct
typedef struct {
    unsigned char data[BUF_SIZE];
    unsigned int head;
    unsigned int tail;
    unsigned int count;
    unsigned int overrun;
} circularBuffer;

//circularBuffer instancies
volatile circularBuffer txb;
volatile circularBuffer rxb;

//rxb initialization
void comDrv_flushRx(void)
{
    rxb.head = 0;
    rxb.tail = 0;
    rxb.count = 0;
    rxb.overrun = 0;
}

//txb initialization
void comDrv_flushTx(void)
{
    txb.head = 0;
    txb.tail = 0;
    txb.count = 0;
    txb.overrun = 0;
}

//write a char in the Transmission buffer Txb
void comDrv_putc(char ch)
{
    while(txb.count == BUF_SIZE);               //wait while buffer is full
    txb.data[txb.tail] = ch;                    //copy char ch to "tail" position
    txb.tail = (txb.tail + 1) & INDEX_MASK;     //increment "tail" index (mod BUF_SIZE)
    DisableUart1TxInterrupts();                 //Begining of the critical section
    txb.count++;                                //increment "count"  
    EnableUart1TxInterrupts();                  //Ending of the critical section
}

//write a String in the transmission buffer Txb
void comDrv_puts(char *s)
{
    while(*s!='\0'){
        comDrv_putc(*s);
        s++;
    }
}

//read a character from the reception buffer Rxb
char comDrv_getc(char *pchar)
{
    if(rxb.count == 0)                          //reception buffer is empty
        return 0;
    DisableUart1RxInterrupts();                 //Begining of the critical section
    *pchar = rxb.data[rxb.head];                //copy character pointed by "head" to *pchar
    rxb.count--;                                //decrement count variable
    rxb.head = (rxb.head + 1) & INDEX_MASK;     //increment "head" variable (mod BUF_SIZE)
    EnableUart1RxInterrupts();                  //Ending of the critical section
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
    if(IFS0bits.U1TXIF==1){                                 //Transmission interruptions
        while(txb.count > 0 && U1STAbits.UTXBF==0) {        //copy the buffer characters until buffer is empty or FIFO is Full
            U1TXREG = txb.data[txb.head];                   //copy character pointed by "head" to U1TXREG
            txb.head = txb.head & INDEX_MASK;               //increment "head" variable (mod BUF_SIZE)
            txb.count--;                                    //decrement count variable
        }
        if(txb.count == 0)                                  //empty buffer
            DisableUart1TxInterrupts();
        IFS0bits.U1TXIF = 0;                                //reset interrupts flag
    }
    if(IFS0bits.U1RXIF==1){                                 //Reception interruptions
        while(txb.count != BUF_SIZE && U1STAbits.UTXBF!=0){ //while buffer is not full and FIFO empty
            rxb.data[rxb.tail] = U1RXREG;                   //Read character from Uart and write in the "tail" position
            rxb.tail = (rxb.tail+1) & INDEX_MASK;           //increment "tail" variable (mod BUF_SIZE)
            if(rxb.count < BUF_SIZE)                        //if reception buffer is not full
                rxb.count++;                                //increment count variable
            else 
                rxb.head = (rxb.head+1) & INDEX_MASK;       //increment "head" variable (mod BUF_SIZE) discard oldest character
        }
        IFS0bits.U1RXIF = 0;                                //reset interruption flag
    }
    if(IFS0bits.U1EIF == 1){                                //Overrun interruption
        char dumb;
        if(U1STAbits.OERR == 1){
            rxb.overrun = 1;
            U1STAbits.OERR;
        }
        else{
            dumb = U1RXREG;
        }
        IFS0bits.U1EIF = 0;
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
