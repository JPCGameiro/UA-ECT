#include <detpic32.h>

int cnt = 0;

void delay(int ms){
    for(; ms > 0;ms--){
        resetCoreTimer();
        while(readCoreTimer()<20000);
    }
}

void configIO(){
    TRISB = TRISB & 0x80FF;
    TRISD = TRISD & 0xFF9F;
}

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

unsigned char toBcd(unsigned char value){
    return ((value/10)<<4) + (value%10); 
}

void send2displays(unsigned char value)
{
    static const char display7Scodes[] = {0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
    static char displayFlag = 0;

    int display_low = display7Scodes[value & 0x0F];
    int display_high = display7Scodes[value >> 4];

    if(displayFlag == 0){
        LATDbits.LATD5 = 1;
        LATDbits.LATD6 = 0;
        LATB = (LATB & 0x80FF) | (display_low << 8);
    }
    else{
        LATDbits.LATD5 = 0;
        LATDbits.LATD6 = 1;
        LATB = (LATB & 0x80FF) | (display_high << 8);
    }
    displayFlag = !displayFlag;
}


int main(void)
{
    printStr("João Gameiro - Nº93097\n");
    printStr("Ex6b Preparação Exame Prático - AC2\n");
    
    configIO();
    configT3();
    EnableInterrupts();

    char ch = '0', lastChar = '0';
    int value = 0;
    
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
            value = cnt;
            ch = lastChar;
        }
        else if(ch=='\0')
            ch = lastChar;
        
        send2displays(toBcd(value));
        delay(2);
    }
    return 0;

}


