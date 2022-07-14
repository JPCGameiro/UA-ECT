#include "tc74.h"

/**
 * @brief i2c master initialization (esp32 as master)
 */

esp_err_t i2c_master_init(void)
{
    int i2c_master_port = I2C_MASTER_NUM;
    i2c_config_t conf;
    conf.mode             = I2C_MODE_MASTER;
    conf.sda_io_num       = I2C_MASTER_SDA_IO;
    conf.sda_pullup_en    = GPIO_PULLUP_ENABLE;
    conf.scl_io_num       = I2C_MASTER_SCL_IO;
    conf.scl_pullup_en    = GPIO_PULLUP_ENABLE;
    conf.master.clk_speed = I2C_MASTER_FREQ_HZ;
    i2c_param_config(i2c_master_port, &conf);
    return i2c_driver_install(i2c_master_port, conf.mode, I2C_MASTER_RX_BUF_DISABLE, I2C_MASTER_TX_BUF_DISABLE, 0);
}

/**
 * @brief read temperature data from TC74 sensor (I2C)
 *
 * param   i2c_num : Esp32's i2c port number to send data to.
 * param   tmprt : Temperature value returned by the sensor
 * return  err :  error code if anything went wrong
 *
 * Operations executed in the I2C bus
 *      | start | slave_addr + wr_bit + ack | write 1 byte + ack  | start | slave_addr + rd_bit + ack | read 1 byte + nack | stop |
 */
esp_err_t i2c_master_read_temp(i2c_port_t i2c_num, uint8_t *tmprt)
{
    int ret;
    i2c_cmd_handle_t cmd = i2c_cmd_link_create();
    i2c_master_start(cmd);
    i2c_master_write_byte(cmd, TC74_SLAVE_ADDR << 1 | WRITE_BIT, ACK_CHECK_EN);
    i2c_master_write_byte(cmd, READ_TEMP_REGISTER, ACK_CHECK_EN);
    i2c_master_start(cmd);
    i2c_master_write_byte(cmd, TC74_SLAVE_ADDR << 1 | READ_BIT, ACK_CHECK_EN);
    i2c_master_read_byte(cmd, tmprt, NACK_VAL);
    i2c_master_stop(cmd);
    ret = i2c_master_cmd_begin(i2c_num, cmd, 300 / portTICK_RATE_MS);
    i2c_cmd_link_delete(cmd);
    return ret;
}

/**
 * @brief read TC74 operation mode (I2C)
 *
 * param   i2c_num : Esp32's i2c port number to send data to.
 * param   mode : Operation mode returned by the sensor
 * return  err :  error code if anything went wrong
 *
 * Operations executed in the I2C bus
 *      | start | slave_addr + wr_bit + ack | write 1 byte + ack  | start | slave_addr + rd_bit + ack | read 1 byte + nack | stop |
 */
esp_err_t i2c_master_read_tc74_config(i2c_port_t i2c_num, uint8_t *mode)
{
    int ret;
    i2c_cmd_handle_t cmd = i2c_cmd_link_create();
    i2c_master_start(cmd);
    i2c_master_write_byte(cmd, TC74_SLAVE_ADDR << 1 | WRITE_BIT, ACK_CHECK_EN);
    i2c_master_write_byte(cmd, READ_WRITE_CONFIG_REGISTER, ACK_CHECK_EN);
    i2c_master_start(cmd);
    i2c_master_write_byte(cmd, TC74_SLAVE_ADDR << 1 | READ_BIT, ACK_CHECK_EN);
    i2c_master_read_byte(cmd, mode, NACK_VAL);
    i2c_master_stop(cmd);
    ret = i2c_master_cmd_begin(i2c_num, cmd, 300 / portTICK_RATE_MS);
    i2c_cmd_link_delete(cmd);
    return ret;
}

/**
 * @brief write data to TC74 configuration register, used for setting the sensor in normal/standby mode (I2C)
 *
 * param   i2c_num : Esp32's i2c port number to send data to.
 * param   mode : Operation mode you want to set -> NORMAL OR STANDBY
 * return  err :  error code if anything went wrong
 *
 * Operations executed in the I2C bus
 *      | start | slave_addr + wr_bit + ack | write 1 byte + ack | data 1 byte + ack | stop |
 */
esp_err_t i2c_master_set_tc74_mode(i2c_port_t i2c_num,uint8_t mode)
{
    int ret;
    i2c_cmd_handle_t cmd = i2c_cmd_link_create();
    i2c_master_start(cmd);
    i2c_master_write_byte(cmd, TC74_SLAVE_ADDR << 1 | WRITE_BIT, ACK_CHECK_EN);
    i2c_master_write_byte(cmd, READ_WRITE_CONFIG_REGISTER, ACK_CHECK_EN);
    i2c_master_write_byte(cmd, mode, ACK_CHECK_EN);
    i2c_master_stop(cmd);
    ret = i2c_master_cmd_begin(i2c_num, cmd, 300 / portTICK_RATE_MS);
    i2c_cmd_link_delete(cmd);
    return ret;
}