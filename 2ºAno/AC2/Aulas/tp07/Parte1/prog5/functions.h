#include <detpic32.h>

//configure A/D converter with interrupts
void configureAD()
{
    TRISBbits.TRISB4 = 1;       //digital output RB4 disconnected
    AD1PCFGbits.PCFG4 = 0;      //analog input RB4 connected (AN4)
    AD1CON1bits.SSRC = 7;       //internal counter
    AD1CON1bits.CLRASAM = 1;    //stop conversion when first interruption is generated
    AD1CON3bits.SAMC = 16;      //Sample time is 16 TAD
    AD1CON2bits.SMPI = 8-1;     //Interrupt generated after 8 consecutive conversions
    AD1CHSbits.CH0SA = 4;       //AN4 channel
    AD1CON1bits.ON = 1;         //A/D converter enabled

    IPC6bits.AD1IP = 2;         //priority 2
    IEC1bits.AD1IE = 1;         //interrupts enabled
    IFS1bits.AD1IF = 0;         //reset interrupts flag
}

//configure I/O
void configureIO()
{
    TRISB = TRISB & 0x80FF;     //configure RB14-RB8 as outputs
    LATB = LATB & 0x80FF;       //set initial values

    TRISD = TRISD & 0xFF9F;     //configure RD6-RD5 as outputs
    LATD = LATD & 0xFF9F;       //set initial values
}

//configure Timer T1 with interrupts
void configureT1()
{
    T1CONbits.TCKPS = 3;        //Kprescaler = 265  FoutPrescaler = 78125Hz
    PR1 = 19530;                //Fout = 4Hz = (PBCLK/(19530+1)*256)
    TMR1 = 0;                   //reset Timer T1 counter

    IPC1bits.T1IP = 2;          //priority 2
    IEC0bits.T1IE = 1;          //interrupts enabled
    IFS0bits.T1IF = 0;          //reset interrupt flag

    T1CONbits.TON = 1;          //Timer T1 enabled
}

//configure Timer T3 with interrupts 
void configureT3()
{
    T3CONbits.TCKPS = 2;        //Kprescaler = 4  FoutPrescaler = 5000000Hz
    PR3 = 49999;                //Fout = 100Hz = (PBCLK/(49999+1)*4)
    TMR3 = 0;                   //reset Timer T3 counter

    IPC3bits.T3IP = 2;          //priority 2
    IEC0bits.T3IE = 1;          //interrupts enabled
    IFS0bits.T3IF = 0;          //reset interrupt flag

    T3CONbits.TON = 1;          //Timer T3 enabled
}

//convert value in decimal
unsigned char toBcd(unsigned char value)
{
    return ((value/10)<<4) + (value%10);
}

//send value to displays
void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int digit_low = display7Scodes[value & 0x0F];
    int digit_high = display7Scodes[value >> 4];

    if(displayFlag==0){
        LATDbits.LATD5 = 1;                     //set display low
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | digit_low<<8;     //send digit_low to display_low
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;                     //set display high
        LATB = (LATB & 0x80FF) | digit_high<<8;    //send digit_high to display_high
    }
    displayFlag = !displayFlag;                 //toogle displayFlag value
}
