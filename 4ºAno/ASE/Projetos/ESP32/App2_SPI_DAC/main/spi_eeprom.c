#include <string.h>

#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include <driver/spi_master.h>
#include <driver/gpio.h>
#include "esp_log.h"
#include "spi_eeprom.h"

#define TAG "EEPROM"


/**
 * Initialization of the spi master
 **/

void spi_master_init(EEPROM_t * dev)
{
	esp_err_t ret;

	ESP_LOGI(TAG, "GPIO_MISO=%d",GPIO_MISO);
	ESP_LOGI(TAG, "GPIO_MOSI=%d",GPIO_MOSI);
	ESP_LOGI(TAG, "GPIO_SCLK=%d",GPIO_SCLK);
	ESP_LOGI(TAG, "GPIO_CS=%d",GPIO_CS);

	gpio_reset_pin( GPIO_CS );
	gpio_set_direction( GPIO_CS, GPIO_MODE_OUTPUT );
	gpio_set_level( GPIO_CS, 0 );

	spi_bus_config_t buscfg = {
		.sclk_io_num = GPIO_SCLK,
		.mosi_io_num = GPIO_MOSI,
		.miso_io_num = GPIO_MISO,
		.quadwp_io_num = -1,
		.quadhd_io_num = -1
	};

	ESP_ERROR_CHECK(spi_bus_initialize( EEPROM_HOST, &buscfg, SPI_DMA_CH_AUTO ));
	ESP_LOGD(TAG, "spi_bus_initialize=%d",ret);

	int SPI_Frequency = SPI_MASTER_FREQ_8M;
	dev->_totalBytes = 512;
	dev->_addressBits = 9;

    
	spi_device_interface_config_t devcfg;
	memset( &devcfg, 0, sizeof( spi_device_interface_config_t ) );
	devcfg.clock_speed_hz = SPI_Frequency;
	devcfg.spics_io_num = GPIO_CS;
	devcfg.queue_size = 1;
	devcfg.mode = 0;

	spi_device_handle_t handle;
	ESP_ERROR_CHECK(spi_bus_add_device( EEPROM_HOST, &devcfg, &handle));
	ESP_LOGD(TAG, "spi_bus_add_device=%d",ret);

	dev->_SPIHandle = handle;
}

/**
 * Read Status Register (RDSR)
 * reg(out):Status
**/

esp_err_t eeprom_ReadStatusReg(EEPROM_t * dev, uint8_t * reg)
{
	spi_transaction_t SPITransaction;
	
	uint8_t data[2];
	data[0] = EEPROM_CMD_RDSR;
	
	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = 2 * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;
	
	esp_err_t ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	ESP_LOGD(TAG, "eeprom_ReadStatusReg=%x",data[1]);
	*reg = data[1];
	return ret;
}

/**
 * Busy check
 * true:Busy
 * false:Idle
 **/

bool eeprom_IsBusy(EEPROM_t * dev)
{
	spi_transaction_t SPITransaction;

	uint8_t data[2];
	data[0] = EEPROM_CMD_RDSR;
	
	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = 2 * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;

	esp_err_t ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	if (ret != ESP_OK) return false;
	if( (data[1] & EEPROM_STATUS_WIP) == EEPROM_STATUS_WIP) return true;
	return false;
}


/**
 * Write enable check
 * true:Write enable
 * false:Write disable
 **/

bool eeprom_IsWriteEnable(EEPROM_t * dev)
{
	spi_transaction_t SPITransaction;
	
	uint8_t data[2];
	data[0] = EEPROM_CMD_RDSR;
	
	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = 2 * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;
	
	esp_err_t ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	if (ret != ESP_OK) return false;
	if( (data[1] & EEPROM_STATUS_WEL) == EEPROM_STATUS_WEL) return true;
	return false;
}

/**
 * Set write enable
 **/ 

esp_err_t eeprom_WriteEnable(EEPROM_t * dev)
{
	spi_transaction_t SPITransaction;
	
	uint8_t data[1];
	data[0] = EEPROM_CMD_WREN;
	
	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = 1 * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;
	
	esp_err_t ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	return ret;
}

/**
 * Set write disable
 **/

esp_err_t eeprom_WriteDisable(EEPROM_t * dev)
{
	spi_transaction_t SPITransaction;
	
	uint8_t data[1];
	data[0] = EEPROM_CMD_WRDI;
	
	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = 1 * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;
	
	esp_err_t ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	return ret;
}



/**
 * Read from Memory Array (READ)
 * addr(in):Read start address (16-Bit Address)
 * buf(out):Read data
 * n(in):Number of data bytes to read
 **/

int16_t eeprom_Read(EEPROM_t * dev, uint16_t addr, uint8_t *buf, int16_t n)
{ 
	esp_err_t ret;
	spi_transaction_t SPITransaction;

	if (addr >= dev->_totalBytes) return 0;

	uint8_t data[4];
	int16_t index = 0;
	for (int i=0;i<n;i++) {
		uint16_t _addr = addr + i;
		data[0] = EEPROM_CMD_READ;
		if (dev->_addressBits == 9 && addr > 0xff) data[0] = data[0] | 0x08;
		if (dev->_addressBits <= 9) {
			data[1] = (_addr & 0xFF);
			index = 2;
		} else {
			data[1] = (_addr>>8) & 0xFF;
			data[2] = _addr & 0xFF;
			index = 3;
		}
		memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
		SPITransaction.length = (index+1) * 8;
		SPITransaction.tx_buffer = data;
		SPITransaction.rx_buffer = data;
		ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
		if (ret != ESP_OK) return 0;
		buf[i] = data[index];
	}
	return n;
}

/**
 * Write to Memory Array (WRITE)
 * addr(in):Write start address (16-Bit Address)
 * wdata(in):Write data
 **/
int16_t eeprom_WriteByte(EEPROM_t * dev, uint16_t addr, uint8_t wdata)
{
	esp_err_t ret;
	spi_transaction_t SPITransaction;

	if (addr >= dev->_totalBytes) return 0;

	// Set write enable
	ret = eeprom_WriteEnable(dev);
	if (ret != ESP_OK) return 0;

	// Wait for idle
	while( eeprom_IsBusy(dev) ) {
		vTaskDelay(1);
	}

	uint8_t data[4];
	int16_t index = 0;
	data[0] = EEPROM_CMD_WRITE;
	if (dev->_addressBits == 9 && addr > 0xff) data[0] = data[0] | 0x08;
	if (dev->_addressBits <= 9) {
		data[1] = (addr & 0xFF);
		data[2] = wdata;
		index = 2;
	} else {
		data[1] = (addr>>8) & 0xFF;
		data[2] = addr & 0xFF;
		data[3] = wdata;
		index = 3;
	}

	memset( &SPITransaction, 0, sizeof( spi_transaction_t ) );
	SPITransaction.length = (index+1) * 8;
	SPITransaction.tx_buffer = data;
	SPITransaction.rx_buffer = data;
	ret = spi_device_transmit( dev->_SPIHandle, &SPITransaction );
	assert(ret==ESP_OK);
	if (ret != ESP_OK) return 0;

	// Wait for idle
	while( eeprom_IsBusy(dev) ) {
		vTaskDelay(1);
	}
	return 1;
}


// Get total byte
//
int32_t eeprom_TotalBytes(EEPROM_t * dev)
{
	return dev->_totalBytes;
}


