#include <detpic32.h>

void delay(int ms)
{
    for(; ms > 0; ms--)
    {
        resetCoreTimer();
        while(readCoreTimer() < 20000);
    }
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex5b Preparação Exame Prático - AC2\n");
    printStr("\n");
   
    int cnt = 0;
    int freq = 10;
    char number = '0', currentNumber = '0';

    while(1)
    {
        number = inkey();

        printStr("\r");
        if(cnt >=0 && cnt<=9)
            printInt(0, 10);
        printInt(cnt, 10 | 2 << 10);

        switch(number){
            case '0':
                freq = 10;
                currentNumber = '0';
                break;
            case '1':
                freq = 20;
                currentNumber = '1';
                break;         
            case '2':
                freq = 30;
                currentNumber = '2';
                break;
            case '3':
                freq = 40;
                currentNumber = '3';
                break;
            case '4':
                freq = 50;
                currentNumber = '4';
                break;
            case '5':
                freq = 60;
                currentNumber = '5';
                break;
            case '6':
                freq = 70;
                currentNumber = '6';
                break;
            case '7':
                freq = 80;
                currentNumber = '7';
                break;
            case '8':
                freq = 90;
                currentNumber = '8';
                break;
            case '9':
                freq = 100;
                currentNumber = '9';
                break;
            case '\n':
                printStr(" >> ");
                if(cnt >=0 && cnt<=9)
                    printInt(0, 10);
                printInt(cnt, 10 | 2 << 10);
                number = currentNumber;
                break;
            case '\0':
                number = currentNumber;
                break;
        }

        cnt++;
        if(cnt == 99)
            cnt = 0;

        delay(1000/freq);   
    }
    return 0;
}

