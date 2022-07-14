#include <stdio.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "driver/uart.h"
#include "driver/gpio.h"
#include "sdkconfig.h"
#include "esp_rom_gpio.h"
#include "soc/uart_periph.h"
#include "driver/gpio.h"
#include "driver/adc.h"
#include "esp_adc_cal.h"


#define ECHO_UART_BAUD_RATE     115200
#define BUF_SIZE (1024)

#define UART1_GPIO_RX     (4)
#define UART1_GPIO_TX     (5)

#define TURN_OFF_GPIO     (36)

const char xOn[] = {0x11, '\0'};
const char xOff[] = {0x13, '\0'};

#define DEFAULT_VREF    1100       //Use adc2_vref_to_gpio() to obtain a better estimate
#define NO_OF_SAMPLES   64          //Multisampling

static esp_adc_cal_characteristics_t *adc_chars;
static const adc_channel_t channel = ADC_CHANNEL_6;     // adc1 channel6 é o pino 34
static const adc_bits_width_t width = ADC_WIDTH_BIT_12;
static const adc_atten_t atten = ADC_ATTEN_DB_11;
static const adc_unit_t unit = ADC_UNIT_1;

static void check_efuse(void)
{
    //Check if TP is burned into eFuse
    if (esp_adc_cal_check_efuse(ESP_ADC_CAL_VAL_EFUSE_TP) == ESP_OK) {
        printf("eFuse Two Point: Supported\n");
    } else {
        printf("eFuse Two Point: NOT supported\n");
    }
    //Check Vref is burned into eFuse
    if (esp_adc_cal_check_efuse(ESP_ADC_CAL_VAL_EFUSE_VREF) == ESP_OK) {
        printf("eFuse Vref: Supported\n");
    } else {
        printf("eFuse Vref: NOT supported\n");
    }
}

static void print_char_val_type(esp_adc_cal_value_t val_type)
{
    if (val_type == ESP_ADC_CAL_VAL_EFUSE_TP) {
        printf("Characterized using Two Point Value\n");
    } else if (val_type == ESP_ADC_CAL_VAL_EFUSE_VREF) {
        printf("Characterized using eFuse Vref\n");
    } else {
        printf("Characterized using Default Vref\n");
    }
}

#include <stdio.h>
#include <stdlib.h>

// Function to convert integer to
// character array
char* convertIntegerToChar(u_int32_t N)
{

	// Count digits in number N
	int m = N;
	int digit = 0;
	while (m) {

		// Increment number of digits
		digit++;

		// Truncate the last
		// digit from the number
		m /= 10;
	}

	// Declare char array for result
	char* arr;

	// Declare duplicate char array
	char arr1[digit];

	// Memory allocation of array
	arr = (char*)malloc(digit);

	// Separating integer into digits and
	// accommodate it to character array
	int index = 0;
	while (N) {

		// Separate last digit from
		// the number and add ASCII
		// value of character '0' is 48
		arr1[++index] = N % 10 + '0';

		// Truncate the last
		// digit from the number
		N /= 10;
	}

	// Reverse the array for result
	int i;
	for (i = 0; i < index; i++) {
		arr[i] = arr1[index - i];
	}

	// Char array truncate by null
	arr[i] = '\0';

	// Return char array
	return (char*)arr;
}

static void uart1_task(void *arg)
{
    while (1) {
        printf("->UART1\n");

        uint32_t adc_reading = 0;
        //Multisampling
        for (int i = 0; i < NO_OF_SAMPLES; i++) {
            adc_reading += adc1_get_raw((adc1_channel_t)channel);
        }
        adc_reading /= NO_OF_SAMPLES;
        //Convert adc_reading to voltage in mV
        uint32_t voltage = esp_adc_cal_raw_to_voltage(adc_reading, adc_chars);
        
        // convert u_int32 to char array
        char* characters = convertIntegerToChar(voltage);

        printf("\tvoltage: %d\n",voltage);

        uart_write_bytes(UART_NUM_1, characters, sizeof(characters));
        
        //sleep for 1000 ms
        vTaskDelay(1000 / portTICK_PERIOD_MS);
    }
}

static void uart2_task(void *arg)
{
    unsigned int count = 0;
    int lastGPIOValue = 0;
    int lastVoltage = 0;          // variable to store last voltage receive
    while (1) {
        printf("->UART2\n ");
        // Read data from the UART
        uint8_t voltage[12];
        int length = 0;
        int volt = 0;
        ESP_ERROR_CHECK(uart_get_buffered_data_len(UART_NUM_2, (size_t*)&length));
        if(length != 0 ) {
            uart_read_bytes(UART_NUM_2, voltage, length, 20 / portTICK_PERIOD_MS);
            volt = atoi((char* )voltage);
            printf("\t%s\n", volt > lastVoltage ? "higher" : "lower");
            count++;
            lastVoltage = volt;
        }
        
        // Check gpio level
        int value = gpio_get_level(TURN_OFF_GPIO);
        if(value == 1 && lastGPIOValue == 0)
        {
            uart_write_bytes(UART_NUM_2, &xOff, sizeof(xOff));
            printf("UART2 : turn off transmission\n");
        }
        if(value == 0 && lastGPIOValue == 1)
        {
            uart_write_bytes(UART_NUM_2, &xOn, sizeof(xOn));
            printf("UART2 : turn on transmission\n");
        }
        lastGPIOValue = value;

        //sleep for 1000 ms
        vTaskDelay(1000 / portTICK_PERIOD_MS);
    }
}
void app_main(void)
{
    /* Configure parameters of an UART driver,
     * communication pins and install the driver */
    uart_config_t uart_config = {
        .baud_rate = ECHO_UART_BAUD_RATE,
        .data_bits = UART_DATA_8_BITS,
        .parity    = UART_PARITY_DISABLE,
        .stop_bits = UART_STOP_BITS_1,
        .flow_ctrl = UART_HW_FLOWCTRL_DISABLE,
        .source_clk = UART_SCLK_APB,
    };
    int intr_alloc_flags = 0;

#if CONFIG_UART_ISR_IN_IRAM
    intr_alloc_flags = ESP_INTR_FLAG_IRAM;
#endif

    ESP_ERROR_CHECK(uart_driver_install(UART_NUM_1, BUF_SIZE * 2, 0, 0, NULL, intr_alloc_flags));
    ESP_ERROR_CHECK(uart_param_config(UART_NUM_1, &uart_config));
    ESP_ERROR_CHECK(uart_set_pin(UART_NUM_1, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE));


    ESP_ERROR_CHECK(uart_driver_install(UART_NUM_2, BUF_SIZE * 2, 0, 0, NULL, intr_alloc_flags));
    ESP_ERROR_CHECK(uart_param_config(UART_NUM_2, &uart_config));
    ESP_ERROR_CHECK(uart_set_pin(UART_NUM_2, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE, UART_PIN_NO_CHANGE));
    
    //enabling software control flow for UART1
    uart_set_sw_flow_ctrl(UART_NUM_1, true, 0, 0);

    /* Connect uart1 and uart 2 */
    //UART2 TX -> UART1_GPIO_RX
    esp_rom_gpio_connect_out_signal(UART1_GPIO_RX, UART_PERIPH_SIGNAL(2, SOC_UART_TX_PIN_IDX), false, false);
    //UART1 RX -> UART1_PIN_RX
    esp_rom_gpio_connect_in_signal(UART1_GPIO_RX, UART_PERIPH_SIGNAL(1, SOC_UART_RX_PIN_IDX), false);

    //UART1 TX -> UART1_GPIO_TX
    esp_rom_gpio_connect_out_signal(UART1_GPIO_TX, UART_PERIPH_SIGNAL(1, SOC_UART_TX_PIN_IDX), false, false);
    //UART2 RX -> UART1_GPIO_TX
    esp_rom_gpio_connect_in_signal(UART1_GPIO_TX, UART_PERIPH_SIGNAL(2, SOC_UART_RX_PIN_IDX), false);


    //initialize the GPIO config structure.
    gpio_config_t io_conf = {};
    
    //disable interrupt
    io_conf.intr_type = GPIO_INTR_DISABLE;
    
    //set as output mode
    io_conf.mode = GPIO_MODE_INPUT;

    //bit mask of the pins that you want to set,e.g.GPIO36
    io_conf.pin_bit_mask = 1ULL << TURN_OFF_GPIO;
    
    //enable pull-down mode
    io_conf.pull_down_en = 1;
    
    //disable pull-up mode
    io_conf.pull_up_en = 0;
    
    //configure GPIO with the given settings
    gpio_config(&io_conf);

    /**
     * Configure parameters of adc
     */ 

    //Check if Two Point or Vref are burned into eFuse
    check_efuse();

    //Configure ADC
    if (unit == ADC_UNIT_1) {
        adc1_config_width(width);
        adc1_config_channel_atten(channel, atten);
    } else {
        adc2_config_channel_atten((adc2_channel_t)channel, atten);
    }

    //Characterize ADC
    adc_chars = calloc(1, sizeof(esp_adc_cal_characteristics_t));
    esp_adc_cal_value_t val_type = esp_adc_cal_characterize(unit, atten, width, DEFAULT_VREF, adc_chars);
    print_char_val_type(val_type);

    // task 1 retrieve adc value and send to uart2
    xTaskCreate(uart1_task, "uart1_task", 2048, NULL, 5, NULL);

    //sleep for 1000 ms
    vTaskDelay(50 / portTICK_PERIOD_MS);

    // task 2 receive adc value and prints if higher or lower than last value
    xTaskCreate(uart2_task, "uart2_task", 2048, NULL, 10, NULL);
}