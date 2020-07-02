#include <detpic32.h>

#define BUF_SIZE 16
#define INDEX_MASK (BUF_SIZE-1)

#define DisableUart1RxInterrupts() IEC0bits.U1RXIE = 0;
#define EnableUart1RxInterrupts() IEC0bits.U1RXIE = 1;
#define DisableUart1TxInterrupts() IEC0bits.U1TXIE = 0;
#define EnableUart1TxInterrupts() IEC0bits.U1TXIE = 1;

typedef struct{
    unsigned char data[BUF_SIZE];
    unsigned int head;
    unsigned int tail;
    unsigned int count;
} circularBuffer;

circularBuffer rxb;
circularBuffer txb;

void comDrv_initTx()
{
    txb.head = 0;
    txb.tail = 0;
    txb.count = 0;
}

void comDrv_initRx()
{
    rxb.head = 0;
    rxb.tail = 0;
    rxb.count = 0;
}

void comDrv_putc(char char2send)
{
    while(txb.count == BUF_SIZE);
    txb.data[txb.tail] = char2send;
    txb.tail = (txb.tail + 1) & INDEX_MASK;
    DisableUart1TxInterrupts();
    txb.count++;
    EnableUart1TxInterrupts();
}

char comDrv_getc(char* s)
{
    if(rxb.count == 0) 
        return 0;
    else{
        DisableUart1RxInterrupts();
        *s = rxb.data[rxb.head];
        txb.count--;
        txb.head = (txb.head+1) & INDEX_MASK;
        EnableUart1RxInterrupts();
        return 1;
    }

}

void configUart(){
    U1BRG = ((PBCLK + 8*1200)/(16*1200)) - 1;
    U1MODEbits.PDSEL = 0;
    U1MODEbits.STSEL = 0;
    U1STAbits.UTXEN = 1;
    U1STAbits.URXEN = 1;

    IPC6bits.U1IP = 2;
    IEC0bits.U1RXIE = 1;
    IEC0bits.U1TXIE = 1;
    IFS0bits.U1RXIF = 0;
    IFS0bits.U1TXIF = 0;                
}

void _int_(24) isr_uart1(void){
    if(IFS0bits.U1TXIF == 1){
        if(txb.count > 0){
            U1TXREG = txb.data[txb.head];
            txb.head = (txb.head + 1) & INDEX_MASK;
            txb.count--; 
        }
        else
            DisableUart1TxInterrupts();
    }
    else if(IFS0bits.U1RXIF == 1){
        U1RXREG = rxb.data[rxb.tail];
        rxb.tail = (rxb.tail + 1) & INDEX_MASK;
        rxb.count++;
        if(rxb.count != BUF_SIZE)
            rxb.count++;    
        else
            rxb.head = (rxb.head + 1) & INDEX_MASK;

    }
    IFS0bits.U1TXIF = 0;
}

void putc(char c){
    while(U1STAbits.UTXBF == 1);
    U1TXREG = c;
}

void puts(char *s){
    int i=0;
    while(s[i]!='\0'){
        putc(s[i]);
        i++;
    }
}

char getc(void){
    if (U1STAbits.OERR == 1) 
        U1STAbits.OERR = 0;
    while(U1STAbits.URXDA == 0);
  
    char aux;
    if(U1STAbits.FERR || U1STAbits.PERR) {
        aux = U1RXREG;
        return 0;
    }

  return U1RXREG;
}


int main(void)
{
    puts("João Gameiro - Nº93097\n");
    puts("Ex10 Preparação Exame Pratico - AC2\n");


    configUart();
    EnableInterrupts();

    while(1)
    {
        putc(getc());
    }
    return 0;
}
