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
        if(allow==1){
            int numberOfSamples = eeprom_readData(0x0);
            int index = eeprom_readData(0x01);
            getTemperature(&temp);                              //measure the temperature (every 15s)
            eeprom_writeData(index, temp & 0x00FF);             //write a new temperatures
            if (++numberOfSamples == 65){
             	numberOfSamples = 64;                           //increment number of temperatures
	            printStr("\nEeprom full");
            }
            if (++index == 66) index = 2;                       //if max index return to the initial address
            eeprom_writeData(0x0000, numberOfSamples);          //Update number of temperatures
            eeprom_writeData(0x0001, index);                    //Update number of temperatures
        }
        cnt = 0;
    }
    else 
        cnt++;
    IFS0bits.T3IF = 0;              //reset T3IF flag
}



int main(void)
{
    char command = 0;
    int size, i, temp;
    int index;
    configureT3();
    spi2_init();
    i2c1_init(TC74_CLK_FREQ);
    eeprom_writeStatusCommand(WREN);
    EnableInterrupts();

    printStr("\nAutomatic Reset");
    eeprom_writeData(0x00, 0x00);
    eeprom_writeData(0x01, 0x02);
    printStr("\nEeprom reset sucessfull!");

    printStr("\n\nR->reset, S->Show, L->Log");
    printStr("\nCommand: ");
    while(1){
        if(command != 0){
            printStr("R->reset, S->Show, L->Log");
            printStr("Command: ");
        }
        command = inkey();

        if(command=='R'){
            eeprom_writeData(0x00, 0x00);
		    eeprom_writeData(0x01, 0x02);
            printStr("\nEeprom reset sucessfull!");
            allow = 0;
        }
        else if(command=='L'){
            allow = 1;
        }
        else if(command=='S'){
            size = eeprom_readData(0x00);
            index = eeprom_readData(0x01);
            printStr("\nTemperatures measured so far\n");
            for(i=2;i<(size + 2);i++){
                temp = eeprom_readData(index);
                if ((++index) == 66) index = 2;
                printStr("\nAddress: ");
                printInt10(index);
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
