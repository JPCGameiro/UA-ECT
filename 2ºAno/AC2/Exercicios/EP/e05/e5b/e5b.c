#include <detpic32.h>

void delay(int ms)
{
    for(; ms > 0; ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

int freqToMs(int freq){
    int ts = 1 / freq;
    int tms = ts * 1000;
    return tms;
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex5b Preparação Exame Prático - AC2\n");
   
    int cnt = 0;
    int freq = 10;
    char number = '0', currentNumber = '0';

    while(1)
    {
        printStr("\r");
        printInt(cnt, 10 | 2 << 10);

        number = inkey();

        switch(number){
            case '0':
                freq = 10;
                currentNumber = freq;
            case '1':
                freq = 20;
                currentNumber = freq;
            case '2':
                freq = 30;
                currentNumber = freq;
            case '3':
                freq = 40;
                currentNumber = freq;
            case '4':
                freq = 50;
                currentNumber = freq;
            case '\n':
                printInt(cnt, 10 | 2 << 10);
                printStr(" >> ");
                printInt(cnt, 10 | 2 << 10);
                freq = 10;
                currentNumber = 10;
            case '\0':
                freq = currentNumber;
            default:
                freq = 10;
                currentNumber = 10;
        }

        cnt++;
        if(cnt == 99)
            cnt = 0;

        delay(freqToMs(freq));   
    }
    return 0;
}

