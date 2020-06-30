	.equ SFR_BASE_HI, 0xBF88	#16 MSBits of SFR area
	.equ TRISE, 0x6100		#TRISE adress is 0xBF886100
	.equ PORTE, 0x6110		#PORTE adress is 0xBF886110
	.equ LATE,  0x6120		#LATE  adress is 0xBF886120
	
	.equ TRISB, 0x6040		#TRISB adress is 0xBF886040
	.equ PORTB, 0x6050		#PORTB adress is 0xBF886050
	.equ LATB,  0x6060		#LATB  adress is 0xBF886060
	
	.data
	.text
	.globl main
					#RE0 is output and RB0 is input
main:	lui  $t1, SFR_BASE_HI
	lw   $t2, TRISE($t1)		#READ(Mem_addr = 0xBF886100)
	andi $t2, $t2, 0xFFFE		#MODIFY(bit0=0 (0 means output)
	sw   $t2, TRISE($t1)		#WRITE(Write TRISE register)
	
	lw   $t2, TRISB($t1)		#READ(Mem_addr = 0xBF886040)
	andi $t2, $t2, 0x001		#MODIFY(bit0=1 (1 means input)
	sw   $t2, TRISB($t1)		#WRITE(Write TRISB register)  

while:					#while(1){
	lw  $t2, PORTB($t1)		#	ler valor do PORTB(porto de entrada)
	andi $t2, $t2, 0x0001		#	extrair bit0
	lw  $t3, LATE($t1)		#	ler valor do LATE(porto de saída)
	andi $t3, $t3, 0xFFFE		#	alterar valor do bit0 e manter os outros
	or  $t2, $t3, $t2		#	guardar o valor do bit0 de RB0 em RE0
	sw  $t2, LATE($t1)		#	escrever valor no porto de saída  
	j   while			#}

endw:	jr  $ra  	
