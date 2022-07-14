## Status: TESTED SUCCESSFULLY

## Description:
São configuradas a uart1 e a uart2.
É configurado a adc1 channel 6 (pino 34). Alterou-se o valor de atenuação da adc para a macro ADC_ATTEN_DB_11 para alcançar uma maior intervalo de voltagem

Com o FreeRTOS são criadas as tarefas uart_task1 e uart_task2.

Na 1a tarefa é lido da adc o valor da voltagem com multisampling(x64) e é enviado para a uart2 pela uart1.

Na 2a tarefa é lido do buffer de receção da uart2 o valor da voltagem enviado pela 1a tarefa e comparado ao último valor recebido, imprimindo no terminal se a voltagem é maior ou menor que o valor anterior. 

### recursos
código de colegas das semanas anteriores.

exemplo de simple ota [https://github.com/espressif/esp-idf/tree/master/examples/system/ota/simple_ota_example](https://github.com/espressif/esp-idf/tree/master/examples/system/ota/simple_ota_example)

### no projecto1

o 1o projecto que vai para a placa

1. ir ao menu de configuração
```
idf.py menuconfig
```
2. dar enable a CONFIG_PARTITION_TABLE_TWO_OTA

3. configure wi-fi, write ssid and pass of ap

4. configurar o Firmware Upgrade URL para
```
https://host-ip-address:port/projecto2.bin
```

### no projecto2

o que vai substituir o projecto1 via OTA e que tem de estar hosted no servidor

1. dar build e ir à pasta build e fazer
```
openssl req -x509 -newkey rsa:2048 -keyout ca_key.pem -out ca_cert.pem -days 365 -nodes
```

2. copiar os 2 certificados gerados e o projecto2.bin para a pasta OTA_server

3. ir a essa pasta e ligar o servidor
```
openssl s_server -WWW -key ca_key.pem -cert ca_cert.pem -port 8070
```




