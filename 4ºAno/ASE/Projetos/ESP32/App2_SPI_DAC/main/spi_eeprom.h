#define MAIN_EEPROM_H_

#include "driver/spi_master.h"

/* EEPROM SPI commands */
#define EEPROM_CMD_WRSR    0x01    // Write Status Register
#define EEPROM_CMD_READ    0x03    // Read from Memory Array
#define EEPROM_CMD_WRITE   0x02    // Write to Memory Array   
#define EEPROM_CMD_WRDI    0x04    // Write Disable
#define EEPROM_CMD_RDSR    0x05    // Read Status Register
#define EEPROM_CMD_WREN    0x06    // Write Enable

/* EEPROM SPI status */
#define EEPROM_STATUS_SRWD    0x80       // Status Register Write Disable
#define EEPROM_STATUS_BP      0x0C       // Block Protect
#define EEPROM_STATUS_WEL     0x02       // Write Enable   
#define EEPROM_STATUS_WIP     0x01       // Write in Progress

#define EEPROM_HOST VSPI_HOST

#define GPIO_CS		5
#define GPIO_MISO	19
#define GPIO_MOSI	23
#define GPIO_SCLK	18

typedef struct {
	int32_t	_totalBytes;
	int16_t	_addressBits;
	spi_device_handle_t _SPIHandle;
} EEPROM_t;

void spi_master_init(EEPROM_t * dev);
esp_err_t eeprom_ReadStatusReg(EEPROM_t * dev, uint8_t * reg);  
bool eeprom_IsBusy(EEPROM_t * dev);
bool eeprom_IsWriteEnable(EEPROM_t * dev);
esp_err_t eeprom_WriteEnable(EEPROM_t * dev);
esp_err_t eeprom_WriteDisable(EEPROM_t * dev);
int16_t eeprom_Read(EEPROM_t * dev, uint16_t addr, uint8_t *buf, int16_t n);
int16_t eeprom_WriteByte(EEPROM_t * dev, uint16_t addr, uint8_t wdata);
int32_t eeprom_TotalBytes(EEPROM_t * dev);