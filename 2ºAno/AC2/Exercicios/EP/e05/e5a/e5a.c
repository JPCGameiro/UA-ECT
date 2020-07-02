#include <detpic32.h>

void delay(int ms)
{
    for(; ms  > 0; ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000 );
    }
}

int main(void)
{
    int cnt = 0;

    printStr("João Gameiro - Nº93097\n");
    printStr("Ex5a Preparação Exame Prático - AC2\n");

    while(1)
    {
        printStr("\r");
        printInt(cnt, 10 | 2 << 10);

        cnt++;
        if(cnt == 99)
            cnt = 0;

        delay(100);
    }
    return 0;
}

