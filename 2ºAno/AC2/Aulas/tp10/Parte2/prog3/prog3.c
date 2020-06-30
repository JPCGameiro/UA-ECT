#include <detpic32.h>
#include "i2c.h"

//configure A/D converter with interrupts
void configureAD()
{
    TRISBbits.TRISB4 = 1;               //digital output RB4 disconnected
    AD1PCFGbits.PCFG4 = 0;              //Analog input in analog mode (RB4)
    AD1CON1bits.SSRC = 7;               //internatl counter ends sampling
    AD1CON1bits.CLRASAM = 1;            //Stop conversions when first interrupt is generated
    AD1CON3bits.SAMC = 16;              //Sample time is 16 TAD
    AD1CON2bits.SMPI = 8-1;             //8 consecutive conversions
    AD1CHSbits.CH0SA = 4;               //Choose AN4 channel
    AD1CON1bits.ON = 1;                 //A/D converter enabled

    IPC6bits.AD1IP = 2;                 //priority 2
    IEC1bits.AD1IE = 1;                 //interrupts enabled
    IFS1bits.AD1IF = 0;                 //reset interrupts flag

}

//configure I/O
void configureIO()
{
    TRISB = TRISB & 0x80FF;             //configure RB14-RB8 as Outputs
    LATB = LATB & 0x80FF;               //set initial value

    TRISD = TRISD & 0xFF9F;             //configure RD6-RD5
    LATD = LATD & 0xFF9F;               //set initial values

    TRISBbits.TRISB0 = 1;               //configure RB0 as input
    TRISBbits.TRISB1 = 1;               //configure RB1 as intput 
}


//configure Timer T1 with interrupts
void configureT1()
{
    T1CONbits.TCKPS = 3;                //Kprescaler = 256  FoutPrescaler = 78125Hz
    PR1 = 19530;                        //Fout = 4Hz
    TMR1 = 0;                           //reset Timer T1 counter
    T1CONbits.TON = 1;                  //enable Timer T1

    IPC1bits.T1IP = 2;                  //priority 2
    IEC0bits.T1IE = 1;                  //interrupts enabled
    IFS0bits.T1IF = 0;                  //reset interrupts flag           
}

//configure Timer T3 with interrupts
void configureT3()
{
    T3CONbits.TCKPS = 2;                //Kprescaler = 4  FoutPrescaler = 5*10^6Hz
    PR3 = 49999;                        //Fout = 100Hz
    TMR3 = 0;                           //reset Timer T3 counter
    T3CONbits.TON = 1;                  //enable Timer T3

    OC1CONbits.OCM = 6;                 //PWM mode
    OC1CONbits.OCTSEL = 1;              //select Timer T3 for clock source
    OC1CONbits.ON = 1;                  //enable Output Compare Module

    IPC3bits.T3IP = 2;                  //priority 2;
    IEC0bits.T3IE = 1;                  //interrupts enabled
    IFS0bits.T3IF = 0;                  //reset interrupts flag
}

//Set PWM of Timer T3 clock with Fout = 100Hz
void setPWM(unsigned int dutyCycle){
    if(dutyCycle> 100)
        return;
    else
        OC1RS = 500*dutyCycle;                //OC1RS = (PR3+1)*(dutycycle/100)    
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

int getTemperature(int *temperature)
{
    int ack;
    i2c1_start();
    ack = i2c1_send(ADDR_WR);
    ack += i2c1_send(RTR);
    i2c1_start();
    ack += i2c1_send(ADDR_RD);
    if(ack != 0)
        i2c1_stop();
    else
        *temperature = i2c1_receive(I2C_NACK);
    i2c1_stop();
    return ack;
}


volatile unsigned char voltage = 0x00;
int temperature;

//T1 RSI
void _int_(4) isr_t1(void)
{
    AD1CON1bits.ASAM = 1;           //Start AD conversion
    getTemperature(&temperature);   //measure the temperature
    IFS0bits.T1IF = 0;              //reset T1IF flag
}

//T3 RSI
void _int_(12) isr_t3(void)
{
    send2displays(voltage);      //send voltage to displays
    IFS0bits.T3IF = 0;                          //reset T3IF flag
}

//A/C RSI
void _int_(27) isr_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(;p<=(int *)(&ADC1BUFF);p+=4){
        sum += *p;
    }
    double average = (double) sum/8.0;           //calculate buffer average 8 Samples
    voltage = (char) ((average*33)/1023);         //calculate buffer voltage amplitude
    voltage = toBcd(voltage & 0xFF);            //convert voltage amplitude to decimal
    IFS1bits.AD1IF = 0;                         //reset AD1IF flag
}

int main(void)
{
    int dutyCycle;
    int portVal;
    configureAD();
    configureIO();
    configureT1();
    configureT3();
    i2c1_init(TC74_CLK_FREQ);
    EnableInterrupts();
    while(1){
        portVal = PORTB & 0x0003;       //read RB1-RB0 to variable portVal

        switch(portVal)
        {
            case 0:  //Measure Input Voltage
                IEC0bits.T1IE = 1;          //enable T1 interrupts
                setPWM(0);                  //led off
                break;
            case 1:  //Freeze
                IEC0bits.T1IE = 0;          //disable T1 interruprs 
                setPWM(100);                //Led on (maximum bright)
                break;
            case 2:  //Led brightness control
                IEC0bits.T1IE = 1;          //enable T1 interrupts
                dutyCycle = voltage*3;
                setPWM(dutyCycle);
                break;
            case 3:  //Print the temperature
                printStr("\nTemperatura: ");
                printInt10(temperature);
                send2displays(toBcd((unsigned char)(temperature)));
                break;
        }
    }
    return 0;
}
