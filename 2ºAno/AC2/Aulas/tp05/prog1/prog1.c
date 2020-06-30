#include <detpic32.h>

void delay(int ms)
{
    for(;ms>0;ms--)
    {
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

int main(void)
{
    TRISBbits.TRISB4 = 1;               //Desligar a componente digital de saída do porto
    AD1PCFGbits.PCFG4 = 1;              //Configurar o porto como entrada analógica (AN4)

    AD1CON1bits.SSRC = 7;               
    AD1CON1bits.CLRASAM = 1;            //Parar conversões quando uma interrupção for gerada

    AD1CON3bits.SAMC = 16;              
    AD1CON2bits.SMPI = 1-1;             //Número de conversões consecutivas no canal é 1

    AD1CHSbits.CH0SA = 4;               //Selecionar AN4 como entrada para o Conversor A/D

    AD1CON1bits.ON = 1;                 //ativar o conversor

    while(1)
    {
        AD1CON1bits.ASAM = 1;           //Ordem de conversão
        while(IFS1bits.AD1IF == 0);     //esperar pelo fim da conversão
        printInt(ADC1BUF0, 16|3<<16);   //imprimir o resultado da conversão
        printStr("\n");    
        delay(1000);                    //atrasar 1 segundo
        IFS1bits.AD1IF = 0;             //reset a AD1IF

    }
    return 0;
}
