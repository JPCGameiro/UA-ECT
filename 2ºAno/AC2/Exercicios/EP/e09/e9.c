#include <detpic32.h>

void configUART1()
{
    U1BRG = ((PBCLK + 8*1200)/(16*1200)) - 1;
    U1MODEbits.PDSEL = 0;
    U1MODEbits.STSEL = 0;
    
    U1STAbits.UTXEN = 1; 
    U1STAbits.URXEN = 1;  
    
    U1MODEbits.ON = 1;
}

void configIO()
{
    TRISB = (TRISB & 0xFFF0) | 0x000F;
}

void putc(char byte2send)
{
    while(U1STAbits.UTXBF == 1);
    U1TXREG = byte2send;
}

void puts(char *str)
{
    int i=0;
    while(str[i]!='\0'){
        putc(str[i]);
        i++;
    }
}

void portValue(unsigned char value)
{
    if((value & 0x0001) == 0x0000)
        putc('0');
    else
        putc('1');
}

int main(void)
{
    puts("João Gameiro - Nº93097\n");
    puts("Ex9 Preparação Exame Pratico - AC2\n");

    configUART1();
    configIO();
    while(1)
    {
        puts("\nValor nos interruptores: ");
        portValue(PORTBbits.RB0);
        portValue(PORTBbits.RB1);
        portValue(PORTBbits.RB2);
        portValue(PORTBbits.RB3);
    }

    return 0;
}

