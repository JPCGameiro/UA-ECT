#include <detpic32.h>
#include "i2c.h"

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

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

int main(void)
{
    int temperature;
    i2c1_init(TC74_CLK_FREQ);
    while(1)
    {
        getTemperature(&temperature);
        printStr("\nTemperatura: ");
        printInt10(temperature);
        delay(250);
    }
    return 0;
}
