#include <detpic32.h>

#define BUF_SIZE    16
#define INDEX_MASK  (BUF_SIZE-1)

typedef struct {
    unsigned char data[BUF_SIZE];
    unsigned int tail;
    unsigned int head;
    unsigned int count;
} circularBuffer;

circularBuffer bf;

void add_bf(char value)
{
    if(bf.count == BUF_SIZE)
        printStr("Buffer is Full");
    else{
        printStr("Element added Sucessfully");
        bf.data[bf.tail] = value;
        bf.tail = (bf.tail + 1) & INDEX_MASK;
        bf.count++;
    }
}

unsigned char remove_bf(){
    if(bf.count == 0){
        printStr("Buffer is empty");
        return 0;
    }
    else{
        printStr("Element removed Sucessfully");
        bf.head = (bf.head + 1) & INDEX_MASK;
        bf.count++;
        return 1;
    }
}

void print_bf()
{
    if(bf.count == 0)
        printStr("Buffer is empty");
    else{
        int i = 0, index = bf.head;
        for(; index < bf.tail; index++){
            printStr("\nBUFFER[");
            printInt(i, 10 | 2 << 10);
            printStr("] -> ");
            printInt(bf.data[index], 10 | 2 << 10);
            i++;
        }
        printStr("\n");
    }
}


volatile unsigned char voltage = 0;

//Configurar ADC
void configADC()
{
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 4-1;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;
}

//ADC RSI
void _int_(27) isr_ad1(void)
{
    int sum = 0;
    int *p = (int *)(&ADC1BUF0);
    for(; p <= (int *)(&ADC1BUFF); p += 4)
        sum += *p;
    double average = sum/4.0;
    voltage = (char)((average*33)/1023);
    IFS1bits.AD1IF = 0;
}

int main(void)
{
    configADC();
    EnableInterrupts();

    char command = 1;

    while(1)
    {
        if(command!=0)
            printStr("\ns-Save, r-Remove, p-Print\n");
        command = inkey();

        AD1CON1bits.ASAM = 1;

        if(command == 's')
            add_bf(voltage);
        else if(command == 'r')
            remove_bf();
        else if(command == 'p')
            print_bf();
        else 
            command = 0;

    }
    return 0;
}

