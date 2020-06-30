	.equ SFR_BASE_HI, 0xBF88	#16 MSbits of SFR area
	.equ TRISE, 0x6100		#TRISE address is 0xBF886100
	.equ PORTE, 0x6110		#PORTE address is 0xBF886110
	.equ LATE,  0x6120		#LATE  address is 0xBF886120
	
	.equ TRISB, 0x6040		#TRISB address is 0xBF886040
	.equ PORTB, 0x6050		#PORTB address is 0xBF886050
	.equ LATB,  0x6060		#LATB  address is 0xBF886060
	
	.data
	.text
	.globl main

main:	lui  $t1, SFR_BASE_HI		#
	lw   $t2, TRISE($t1)		#READ(TRISE address)
	andi $t2, $t2, 0xFFF0		#MODIFY(bit0=bit1=bit2=bit3 = 0 means output)
	sw   $t2, TRISE($t1)		#WRITE(write TRISE register)
	
	lw   $t2, TRISB($t1)		#READ(TRISB address)
	andi $t2, $t2, 0x000F		#MODIFY(bit0=bit1=bit2=bit3 = 1 means input)
	sw   $t2, TRISB($t1)		#WRITE(write TRISB register)
	
while:					#while(1){
	lw   $t2, PORTB($t1)		#	ler valor do porto de entrada
	andi $t2, $t2, 0x000F		#	extrair bits 0,1,2 e 3
	xor  $t2, $t2, 0x0009		#	RE0,RE1,RE2,RE3 = /RB0,RB1,RB2,/RB3
	lw   $t3, LATE($t1)		#	ler valor do porto de saída
	andi $t3, $t3, 0xFFF0		#	modificar bits 0,1,2 e 3
	or   $t2, $t2, $t3		#	escrever o valor atualizado de RB em RE
	sw   $t2, LATE($t1)		#	escrever no porto de saída
	j   while			#}
	jr  $ra