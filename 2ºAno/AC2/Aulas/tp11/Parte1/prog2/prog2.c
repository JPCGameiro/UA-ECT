#include <detpic32.h>

#define WRSR            0x01
#define WRITE           0x02
#define READ            0x03
#define WRDI            0x04
#define RDSR            0x05
#define WREN            0x06
#define EEPROM_CLOCK    500000

void spi2_setClock(unsigned int clock_freq)
{
    SPI2BRG = (PBCLK + clock_freq)/(2*clock_freq) - 1;
}

void spi2_init(void)
{
    volatile char trash;
    SPI2CONbits.ON = 0;             //disable SPI2 module
    SPI2CONbits.CKP = 0;            //clock idle state is logic level '0'
    SPI2CONbits.CKE = 1;            //clock ative transition: from active to idle state
    SPI2CONbits.SMP = 0;            //Input data sample at middle of data output time
    SPI2CONbits.MODE32 = 0;         //Word lenght is 8 bits
    SPI2CONbits.MODE16 = 0;         //Word length is 8 bits
    SPI2CONbits.ENHBUF = 1;         //Enhanced buffer mode enabled
    SPI2CONbits.MSSEN = 1;          //Slave select support enabled
    SPI2CONbits.MSTEN = 1;          //Master mode enabled
    while(SPI2STATbits.SPIRBE == 0) //while RX FIFO not empty read
        trash = SPI2BUF;            //discard FIFO data
    SPI2STATbits.SPIROV = 0;        //overflow error flag
    SPI2CONbits.ON = 1;             //SPI2 module enabled
}

char eeprom_readStatus(void)
{
    volatile char trash;
    while(SPI2STATbits.SPIRBE == 0) //while RX FIFO not empty read
        trash = SPI2BUF;            //discard FIFO data
    SPI2BUF = RDSR;                 //Send RDSR command
    SPI2BUF = 0;                    //Send anything so that EEPROM clocks data into SO
    while(SPI2STATbits.SPIBUSY);    //wait while SPI module is working
    trash = SPI2BUF;                //First char received is garbage
    return SPI2BUF;                 //second char received is the STATUS value
}

void eeprom_writeStatusCommand(char command)
{
    while( eeprom_readStatus() & 0x01 );    //wait while write in progress
    SPI2BUF = command;                      //copy command to TX FIFO
    while(SPI2STATbits.SPIBUSY);            //wait while SPI module is working   
}


int main(void)
{
    char status;
    spi2_init();
    spi2_setClock(EEPROM_CLOCK);
    eeprom_writeStatusCommand(WREN);
    for(;;)
    {
        status = eeprom_readStatus();
        printInt(status, 2 | 4 << 16);
        printStr("\n");
    }
    return 0;
}
