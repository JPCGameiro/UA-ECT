#include <detpic32.h>

int cnt = 0, cnt2send = 0;

unsigned char toBcd(unsigned char value){
    return ((value/10) << 4) + (value % 10);
}

void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int digit_low = display7Scodes[value & 0x0F];
    int digit_high = display7Scodes[value >> 4];

    if(displayFlag==0){
        LATDbits.LATD5 = 1;
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | (digit_low << 8);
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;
        LATB = (LATB & 0x80FF) | (digit_high << 8);
    }
    displayFlag = !displayFlag;                 
}

void configIO(){
    TRISB = TRISB & 0x80FF;
    TRISD = TRISD & 0xFF9F;
}

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

void configT3()
{
    T3CONbits.TCKPS = 3;
    PR3 = 49999;
    TMR3 = 0;
    T3CONbits.TON = 1;

    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
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

void _int_(12) isr_T3(void){
    
    send2displays(toBcd(cnt2send));
    IFS0bits.T3IF = 0;
}



int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex6b Preparação Exame Prático - AC2\n");

    configIO();
    configT2();
    configT3();
    EnableInterrupts();

    char ch;

    while(1){
        ch = inkey();

        if(ch=='1')
            cnt2send = cnt;
    }
    return 0;

}

