
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "driver/spi_master.h"
#include "driver/gpio.h"
#include "driver/dac.h"
#include "sdkconfig.h"
#include "esp_log.h"
#include "spi_eeprom.h"
#include "esp_sleep.h"
#include "esp_check.h"


/**
 * Bibliography and examples 
 * 
 * https://github.com/nopnop2002/esp-idf-spi-eeprom
 * https://docs.espressif.com/projects/esp-idf/en/latest/esp32/api-reference/peripherals/spi_master.html
 * https://docs.espressif.com/projects/esp-idf/en/latest/esp32/api-reference/peripherals/dac.html
 * https://github.com/espressif/esp-idf/tree/master/examples/system/light_sleep/main
 * 
 **/ 


#define TIMER_WAKEUP_TIME_US    (2 * 1000 * 1000)
#define	EEPROM_MODEL "25LC040A"

static const char TAG[] = "main";


/**
 * ESP timer wake up (from light sleep mode)
 **/ 

esp_err_t timer_wakeup(void)
{
    ESP_RETURN_ON_ERROR(esp_sleep_enable_timer_wakeup(TIMER_WAKEUP_TIME_US), TAG, "Configure timer as wakeup source failed");
    ESP_LOGI(TAG, "timer wakeup source is ready");
    return ESP_OK;
}





void dump(uint8_t *dt, int n)
{
	uint16_t clm = 0;
	uint8_t data;
	uint32_t saddr =0;
	uint32_t eaddr =n-1;

	printf("--------------------------------------------------------\n");
	uint32_t addr;
	for (addr = saddr; addr <= eaddr; addr++) {
		data = dt[addr];
		if (clm == 0) {
			printf("%05x: ",addr);
		}

		printf("%02x ",data);
		clm++;
		if (clm == 16) {
			printf("| \n");
			clm = 0;
		}
	}
	printf("--------------------------------------------------------\n");
}






void app_main(void)
{
	EEPROM_t dev;
	spi_master_init(&dev);
	int32_t totalBytes = eeprom_TotalBytes(&dev);
	ESP_LOGI(TAG, "totalBytes=%d Bytes",totalBytes);

	// Get Status Register
	uint8_t reg;
	esp_err_t ret;
	ret = eeprom_ReadStatusReg(&dev, &reg);
	if (ret != ESP_OK) {
		ESP_LOGI(TAG, "ReadStatusReg Fail %d",ret);
		while(1) { vTaskDelay(1); }
	} 
	ESP_LOGI(TAG, "readStatusReg : 0x%02x", reg);




	uint8_t wdata[128];
	int len;
	unsigned char str[] = "Hello";	

    for (int i=0; i<sizeof(str); i++) {
        uint8_t data =  str[i];
        wdata[i]= data;
    }

	for (int addr=0; addr<sizeof(str);addr++) {
		len =  eeprom_WriteByte(&dev, addr, wdata[addr]);
		ESP_LOGI(TAG, "WriteByte(addr=%d) len=%d: data=%d", addr, len, wdata[addr]);
		if (len != 1) {
			ESP_LOGI(TAG, "WriteByte Fail addr=%d", addr);
			while(1) { vTaskDelay(1); }
		}
		vTaskDelay(10);
	}

	vTaskDelay(1000);
	// Read 128 byte from Address=0
	uint8_t rbuf[128];
	memset(rbuf, 0, 128);
	len =  eeprom_Read(&dev, 0, rbuf, sizeof(str));
	if (len != sizeof(str)) {
		ESP_LOGI(TAG, "Read Fail");
		while(1) { vTaskDelay(1); }
	}
	ESP_LOGI(TAG, "Read Data: len=%d", len);
	dump(rbuf, 128);

    for(uint8_t i = 0; i < len; i++){
        printf("%c", rbuf[i]);
	}
	printf("\n");


    //Configure timer wake up source
    timer_wakeup();

    //Enable dac channel (GPIO 25)
    dac_output_enable(DAC_CHANNEL_1);    
    while(1){
        for(uint8_t i=0; i < 255; i++){
            //Set output voltage and generate delay
            dac_output_voltage(DAC_CHANNEL_1, i);
            vTaskDelay(10 / portTICK_RATE_MS);
        }
        for(uint8_t i=255; i > 0; i--){
            //Set output voltage and generate delay
            dac_output_voltage(DAC_CHANNEL_1, i);
            vTaskDelay(10 / portTICK_RATE_MS);
        }
        //vTaskDelay(10 / portTICK_RATE_MS);
        
        esp_light_sleep_start();
        
        if (esp_sleep_get_wakeup_cause() == ESP_SLEEP_WAKEUP_TIMER)
          ESP_LOGI(TAG, "Woke up from light sleep mode by timer wource");

    }




}
