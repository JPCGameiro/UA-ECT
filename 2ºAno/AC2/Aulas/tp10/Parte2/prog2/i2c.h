#ifndef _I2C_H 
#define _I2C_H

#define I2C_READ    1
#define I2C_WRITE   0
#define I2C_ACK     0
#define I2C_NACK    1

#define SENS_ADDRESS    0x4D
#define ADDR_WR         ((SENS_ADDRESS << 1) | I2C_WRITE)
#define ADDR_RD         ((SENS_ADDRESS << 1) | I2C_READ)
#define TC74_CLK_FREQ   100000
#define RTR             0

//I2C1 module initilization function
void i2c1_init(unsigned int clk_freq);

//I2C1 module start event function
void i2c1_start(void);

//I2C1 module stop function
void i2c1_stop(void);

//I2C1 module send function
int i2c1_send(unsigned char value);

//I2C1 module receive function
char i2c1_receive(char ack_bit);

#endif
