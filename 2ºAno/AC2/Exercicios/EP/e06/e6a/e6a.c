#include <detpic32.h>

int cnt = 0;

void configT2()
{
    T2CONbits.TCKPS = 5;
    PR2 = 62499;
    TMR2 = 0;
    T2CONbits.TON = 1;

    IPC2bits.T2IP = 2;
    IEC0bits.T2IE = 1;
    IFS0bits.T2IF = 0;
}

void _int_(8) isr_T2(void)
{
    printStr("\r");
    printInt(cnt, 10 | 2 << 10);

    cnt++;
    if(cnt == 100)
        cnt = 0;
    IFS0bits.T2IF = 0;
}

int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex6a Preparação Exame Prático - AC2\n");
    
    configT2();
    EnableInterrupts();
    while(1);
    return 0;

}
