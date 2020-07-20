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
    LATE = LATE & 0xFFF0;       //configurar valor inicial antes da configuração
    TRISE = TRISE & 0xFFF0;     //configurar portos RE0,RE1,RE2,RE3 como OUTPUTS

    int cnt = 0;
    while(1){
        delay(250);             //frequência de 4Hz
        LATE = (LATE & 0xFFF0) | cnt;    //escrever valor do contador registo LATE
        cnt++;

    }
    return 0;
}
