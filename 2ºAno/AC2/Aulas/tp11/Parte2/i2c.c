#include <detpic32.h>
#include "i2c.h"

//I2C1 module initilization function
void i2c1_init(unsigned int clkfreq){
    I2C1BRG = (PBCLK + clkfreq)/(2*clkfreq) - 1;    //configure baudrate Generator
    I2C1CONbits.ON = 1;                             //I2C1 module enabled
}

//I2C1 module start event function
void i2c1_start(void){
    while((I2C1CON & 0x001F) != 0);                 //wait until lower 5 bits of I2C1CON are all 0
    I2C1CONbits.SEN = 1;                            //activate start event
    while((I2C1CONbits.SEN == 1));                  //wait for the completion of the Start event
}

//I2C1 module stop event function
void i2c1_stop(void){
    while((I2C1CON & 0x001F) != 0);                 //wait until the lower 5 bits of I2C1CON are all 0
    I2C1CONbits.PEN = 1;                            //activate stop event
    while((I2C1CONbits.PEN == 1));                  //wait for the completion of the Stop event
}

//I2C1 module byte send function
int i2c1_send(unsigned char value){
    I2C1TRN = value;                                //copy value to I2C1TRN register
    while(I2C1STATbits.TRSTAT==1);                  //while while master trasmission is in progress (8 bits + ACK\)
    return I2C1STATbits.ACKSTAT;                    //return acknowledge status bit (1=Ack not received, 0=Ack received)
}

//I2C1 module byte receive function
char i2c1_receive(char ack_bit){
    while((I2C1CON & 0x001F) != 0);                 //wait until the lower 5 bits of I2C1CON are all 0   
    I2C1CONbits.RCEN = 1;                           //activate receive enable bit (RCEN)
    while(I2C1STATbits.RBF == 0);                   //wait while byte not received
    if(ack_bit!=1 && ack_bit!=0)
        ack_bit = 0;                                //ack_bit can only be 1 or 0
    I2C1CONbits.ACKDT = ack_bit;                    //copy ack_bit to ACKDT
    while((I2C1CON & 0x001F) != 0);                 //wait until the lower 5 bits of I2C1CON are all 0 
    I2C1CONbits.ACKEN = 1;                          //Start acknowledge sequence
    while(I2C1CONbits.ACKEN == 1);                  //wait for the completion of the ACK sequence          
    return I2C1RCV;
}
