#include <detpic32.h>
#include "i2c.h"
#include "eeprom.h"

int temp;
int allow;
int cnt = 0;
//configure Timer T3 with interrupts
void configureT3()
{
    T3CONbits.TCKPS = 7;                //Kprescaler = 256  FoutPrescaler = 78125Hz
    PR3 = 39062;                        //Fout = 2Hz, Tout = 0.5s
    TMR3 = 0;                           //reset Timer T3 counter
    T3CONbits.TON = 1;                  //enable Timer T3

    IPC3bits.T3IP = 2;                  //priority 2;
    IEC0bits.T3IE = 1;                  //interrupts enabled
    IFS0bits.T3IF = 0;                  //reset interrupts flag
}

//Get Temperature 
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

//T3 RSI
void _int_(12) isr_t3(void)
{
    if(cnt==1){         //a cada 1s
        if(allow==1 && eeprom_readData(0x0) <= 65){
            int auxAddress = eeprom_readData(0x0);
            getTemperature(&temp);                              //measure the temperature (every 15s)
            eeprom_writeData(auxAddress+2, temp & 0x00FF);      //write a new temperatures
            auxAddress++;                                       //increment number of temperatures
            eeprom_writeData(0x0000, auxAddress);               //Update number of temperatures
        }
        else if(allow==1 && eeprom_readData(0x0000) > 64){
            printStr("\nEeprom full");
        }
        cnt = 0;
    }
    else 
        cnt++;
    IFS0bits.T3IF = 0;              //reset T1IF flag
}



int main(void)
{
    char command = 0;
    int size, i, temp;
    configureT3();
    spi2_init();
    i2c1_init(TC74_CLK_FREQ);
    eeprom_writeStatusCommand(WREN);
    EnableInterrupts();

    printStr("\nAutomatic Reset");
    eeprom_writeData(0x00, 0x00);
    eeprom_writeData(0x01, 0x00);
    printStr("\nEeprom reset sucessfull!");

    printStr("\n\nR->reset, S->Show, L->Log");
    printStr("\nCommand: ");
    while(1){
        if(command != 0){
            printStr("\nR->reset, S->Show, L->Log");
            printStr("\nCommand: ");
        }
        command = inkey();

        if(command=='R'){
            eeprom_writeData(0x00, 0x00);
            eeprom_writeData(0x01, 0x00);
            printStr("\nEeprom reset sucessfull!");
            allow = 0;
        }
        else if(command=='L'){
            allow = 1;
        }
        else if(command=='S'){
            size = eeprom_readData(0x00);
            printStr("\nTemperatures measured so far\n");
            for(i=2;i<size;i++){
                temp = eeprom_readData(i);
                printStr("\nAddress: ");
                printInt10(i);
                printStr("\nTemperature: ");
                printInt10(temp);
                printStr("\n");
            }
        }
        else 
            command = 0;

    }
    return 0;
}
