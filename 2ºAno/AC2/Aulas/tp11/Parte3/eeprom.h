#ifndef EEPROM_H
#define EEPROM_H

#define WRSR            0x01
#define WRITE           0x02
#define READ            0x03
#define WRDI            0x04
#define RDSR            0x05
#define WREN            0x06
#define EEPROM_CLOCK    500000

//Configurar o gerador de baudrate
void spi2_init(void);

//Inicializar módulo SPI2
void spi2_init(void);

//Leitura do registo Status
char eeprom_readStatus(void);

//Escrita no registo Status
void eeprom_writeStatusCommand(char command);

//Escrita de um byte numa posição de memória
void eeprom_writeData(int address, char value);

//Leitura de um byte de uma posição de memória
unsigned char eeprom_readData(int address);

#endif
