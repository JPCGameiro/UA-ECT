#include <detpic32.h>

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

void sendSegment(char segment){
    LATB = LATB & 0x00FF;
    switch(segment){
        case 'a':
            LATBbits.LATB8 = 1; 
            break;
        case 'b':   
            LATBbits.LATB9 = 1;
            break;
        case 'c':
            LATBbits.LATB10 = 1;
            break;
        case 'd':
            LATBbits.LATB11 = 1;
            break;
        case 'e':
            LATBbits.LATB12 = 1;
            break;
        case 'f':
            LATBbits.LATB13 = 1;
            break;
        case 'g':
            LATBbits.LATB14 =  1;
            break;
        default:
            break; 
    }
}
int main(void)
{
    unsigned char segment;

    LATB = LATB & 0x00FF;   //set values before the configuration  
    TRISB = TRISB & 0x80FF; //configure RB8-RB14 as outputs
    TRISD = TRISD & 0xFF9F; //configure RD5-RD6 as outputs

    LATD = (LATD & 0xFF9F) | 0x0040;

    while(1) 
    {
        LATD = (LATD^0x0060);
        segment = 'a';
        while(segment!='h')
        {
            sendSegment(segment);                   //send segment value to display
            delay(500);                           //wait 0.5 s
            //delay(100);                           //10Hz
            //delay(20);                            //50Hz
            //delay(10);                              //100Hz
            segment++; 
        }
    }
    return 0;
}
