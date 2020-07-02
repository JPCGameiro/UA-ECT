#include <detpic32.h>

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}


void send2displays(int value0, int value1)
{
    static char displayFlag = 0;
    
    if(displayFlag==0){
        LATDbits.LATD5 = 1;
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | value0;
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;
        LATB = (LATB & 0x80FF) | value1;
    }
    displayFlag = !displayFlag;
}

int main(void)
{

    printStr("João Gameiro - Nº93097\n");
    printStr("Ex4 Preparação Exame Prático - AC2\n");

    char ch = 'f', lastChar = 'f';
    int valueToSend0 = 0x7100;
    int valueToSend1 = 0x7100;
    
    //Configure PORTS
    TRISB = TRISB & 0x80FF;     //Configure RB14-RB8 as Outputs
    TRISD = TRISD & 0xFF9F;     //Configure RD6-RD5 as Outputs

    printStr("\nCarrega numa tecla");
    while(1)
    {        
        ch = inkey();

        if(ch == '0'){
            valueToSend0 = 0x3F00;                      //0 from Display value 00
            valueToSend1 = 0x3F00;                      //0 from Display value 00
            lastChar = '0';

        }
        else if(ch == '1'){
            valueToSend0 = 0x0600;                      //1 from Display value 01
            valueToSend1 = 0x3F00;                      //0 from Display value 01
            lastChar = '1';
        }
        else if(ch=='2'){
            valueToSend0 = 0x5B00;                      //2 from Display value 02
            valueToSend1 = 0x3F00;                      //0 from Display value 02
            lastChar = '2';
        }
        else if(ch=='3'){
            valueToSend0 = 0x4F00;                      //3 from Display value 03
            valueToSend1 = 0x3F00;                      //0 from Display value 03
            lastChar = '3';
        }
        else if(ch=='4'){
            valueToSend0 = 0x6600;                      //4 from Display value 04
            valueToSend1 = 0x3F00;                      //0 from Display value 04
            lastChar = '4';
        }
        else if(ch!='\0'){
            valueToSend0 = 0x7100;                      //F from Display value FF
            valueToSend1 = 0x7100;                      //F from Display value FF
            lastChar = ch; 
        }
        else
            ch = lastChar;    
        
        send2displays(valueToSend0, valueToSend1);      //Send value to displays
        delay(10);                                      //T = 10ms -> f = 100Hz

    }
    return 0;
}
