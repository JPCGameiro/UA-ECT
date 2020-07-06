#include <detpic32.h>

int cnt = 0;

void configT3()
{
    T3CONbits.TCKPS = 5;
    PR3 = 62499;
    TMR3 = 0;
    T3CONbits.TON = 1;

    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
}

void changeTimerT3Config(int freq){
    if(freq == 10){
        T3CONbits.TCKPS = 5;
        PR3 = 62499;
    }
    else if(freq == 20){
        T3CONbits.TCKPS = 4;
        PR3 = 62499; 
    }
    else if(freq == 30){
        T3CONbits.TCKPS = 4;
        PR3 = 41666;
    }
    else if(freq == 40){
        T3CONbits.TCKPS = 3;
        PR3 = 62499;
    }

}

void _int_(12) isr_T3(void)
{
    printStr("\r");
    if(cnt >= 0 && cnt <= 9)
        printInt(0, 10);
    printInt(cnt, 10 | 2 << 10);

    cnt++;
    if(cnt == 100)
        cnt = 0;
    IFS0bits.T3IF = 0;
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex6a Preparação Exame Prático - AC2\n");
    
    configT3();
    EnableInterrupts();

    char ch = '0', lastChar = '0';
    while(1){
        ch = inkey();

        if(ch=='0'){
            changeTimerT3Config(10);
            lastChar = '0';
        }
        else if(ch=='1'){
            changeTimerT3Config(20);
            lastChar = '1';
        }
        else if(ch=='2'){
            changeTimerT3Config(30);
            lastChar = '2';
        }
        else if(ch=='3'){
            changeTimerT3Config(40);
            lastChar = '3';
        }
        else if(ch=='\n'){
            printStr("   >> ");
            if(cnt>=0 && cnt<=9)
                printInt(0, 10);
            printInt(cnt, 10 | 2 << 10);
            ch = lastChar;
        }
        else if(ch=='\0')
            ch = lastChar;
    }
    return 0;

}
