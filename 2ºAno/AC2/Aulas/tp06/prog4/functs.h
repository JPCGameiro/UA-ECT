 #include <detpic32.h>
 
 //configure A/D converter
 void configureAD(int numcon)
 {
    TRISBbits.TRISB4 = 1;           //RB4 digital output disconnected
    AD1PCFGbits.PCFG4 = 0;          //RB4 analog input connected
    AD1CON1bits.SSRC = 7;           //internal counter
    AD1CON1bits.CLRASAM = 1;        //Stop conversion at the first interruption
    AD1CON3bits.SAMC = 16;          //Sample time is 16 TAD
    AD1CON2bits.SMPI = numcon-1;    //numcon conversions
    AD1CHSbits.CH0SA = 4;           //AN4 channel selected
    AD1CON1bits.ON = 1;             //A/D converter enabled
 }

 //configure Interrupt System
 void configureADInterrupt(int p)
 {
    IPC6bits.AD1IP = p;             //priority p
    IEC1bits.AD1IE = 1;             //enable interruptions
    IFS1bits.AD1IF = 0;             //reset flag AD1IF
    EnableInterrupts(); 
 }

//convert value in decimal
unsigned char toBcd(unsigned char value)
{
    return ((value/10)<<4) + (value%10);
}

//delay function
void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
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
