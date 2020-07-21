#include <detpic32.h>

unsigned char segment0;
unsigned char segment1;
volatile unsigned char voltage = 0;

void configADC()
{
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 4-1;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;
}

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

void sendSegment(char segment){
    LATB = LATB & 0x80FF;
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

void currentSegment(unsigned char s0, unsigned char s1){
    static char segarr0[] = {'f', 'a', 'b', 'e', 'd', 'c', 'b', 'a', 'f', 'c', 'd', 'e'};
    static char segarr1[] = {'1', '1', '1', '2', '2', '2', '2', '2', '2', '1', '1', '1'};
    static int currentSeg = 0;

    segment0 = segarr0[currentSeg];
    segment1 = segarr1[currentSeg];

    currentSeg++;
    if(currentSeg > 11)
        currentSeg = 0;
}

void _int_(27) isr_adc(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(; p <= (int *)(&ADC1BUFF); p += 4)
        sum += *p;
    double average = (sum)/4.0;
    voltage = (char)((average*33)/1023);
    IFS1bits.AD1IF = 0;

}

int main(void)
{

    TRISB = TRISB & 0x80FF; //configure RB8-RB14 as outputs
    TRISD = TRISD & 0xFF9F; //configure RD5-RD6 as outputs

    LATDbits.LATD6 = 1;     //display high active
    LATDbits.LATD5 = 0;     //display low inactive

    configADC();
    EnableInterrupts();

    while(1) 
    {
        AD1CON1bits.ASAM = 1;
        currentSegment(segment0, segment1);
        if(segment1 == '1'){
            LATDbits.LATD5 = 0;
            LATDbits.LATD6 = 1;
        }
        else{
            LATDbits.LATD5 = 1;
            LATDbits.LATD6 = 0;
        }
        sendSegment(segment0);
        printStr("\nTensão: ");
        printInt(voltage, 10 | 2 << 10);
        int freq = 1 + 1000/voltage;
        printStr("\nFrequência: ");
        printInt(freq, 10 | 2 << 10);
        delay(freq);
    }
    return 0;
}

