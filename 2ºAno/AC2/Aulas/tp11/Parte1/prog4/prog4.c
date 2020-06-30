#include <detpic32.h>

#define WRSR            0x01
#define WRITE           0x02
#define READ            0x03
#define WRDI            0x04
#define RDSR            0x05
#define WREN            0x06
#define EEPROM_CLOCK    500000

void delay(int ms)
{
    for(;ms>0;ms--) {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

//Configurar o gerador de baudrate
void spi2_setClock(unsigned int clock_freq)
{
    SPI2BRG = (PBCLK + clock_freq)/(2*clock_freq) - 1;
}

//Inicializar o módulo SPI
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

//Leitura do registo de STATUS
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

//Escrita no registo de STATUS
void eeprom_writeStatusCommand(char command)
{
    while( eeprom_readStatus() & 0x01 );    //wait while write in progress
    SPI2BUF = command;                      //copy command to TX FIFO
    while(SPI2STATbits.SPIBUSY);            //wait while SPI module is working   
}

//Escrita de um byte numa posição de memória
void eeprom_writeData(int address, char value)
{
    address = address & 0x01FF;                 //Apply a mask to limit an address to 9 bits
    while(eeprom_readStatus() & 0x01);          //Read STATUS and wait while WIP (Write in Progress)
    eeprom_writeStatusCommand(WREN);            //Enable write operations
    SPI2BUF = WRITE | ((address & 0x100) >> 5); //Copy Write command and A8 address bit to TXFIFO
    SPI2BUF = 0x00FF & address;                 //Copy adress (8 Lsbits) to the TX FIFO
    SPI2BUF = value;                            //Copy value to the TX FIFO
    while(SPI2STATbits.SPIBUSY==1);             //wait while SPI module is working
}

//Leitura de um byte de uma posição de memória
char eeprom_readData(int address)
{
    volatile char trash;
    while(SPI2STATbits.SPIRBE == 0)             //Wait while RX FIFI not empty
        trash = SPI2BUF;                        //discard data
    SPI2STATbits.SPIROV = 0;                    //Clear overflow flag
    address = address & 0x01FF;                 //Apply mask to limit adress to 9 bits
    while((eeprom_readStatus() & 0x01) != 0);   //isolate WIP and Wait while WIP is in progress
    SPI2BUF = READ | ((address & 0x100) >> 5);  //Copy READ command and A8 address bit to TX FIFO
    SPI2BUF = (address & 0xFF);                 //Address 8lsBits
    SPI2BUF = 0x00;                             //Copy any value to the TX FIFO
    while(SPI2STATbits.SPIBUSY==1);             //wait while SPI module is working
    trash = SPI2BUF;                            //Discar two chars
    trash = SPI2BUF;                            //  from the RX FIFO
    return SPI2BUF;                             //return corresponding value
}

int main(void)
{
    int address, value, i;
    spi2_init();
    spi2_setClock(EEPROM_CLOCK);
    eeprom_writeStatusCommand(WREN);
    for(;;)
    {   //Reading operation
        printStr("READ");
        printStr("\nInsert Address: ");
        address = readInt10();
        for(i=0;i<16;i++){
            printStr("\nAddress-> ");
            printInt10(address);
            value = eeprom_readData(address);
            printStr("\nValue-> ");
            printInt10(value);
            putChar('\n');
            address++;
        }    
        //Writing operation
        printStr("WRITE");
        printStr("\nInsert Address: ");
        address = readInt10();
        printStr("\nValue: ");
        value = readInt10() & 0x00FF;
        for(i=0;i<16;i++){
            eeprom_writeData(address, value);
            printStr(" - Write done!\n");
            value += 2;
            address ++;
        } 
    }
    return 0;
}
