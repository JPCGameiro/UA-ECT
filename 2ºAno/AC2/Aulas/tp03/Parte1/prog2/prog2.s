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
	lw   $t2, TRISE($t1)		#READ (Mem_addr = 0xBF880000 + 0x6100)
	andi $t2, $t2, 0xFFFE		#MODIFY(bit0 = 0 means output)
	sw   $t2, TRISE($t1)		#WRITE (Write TRISE register)
	
	lw   $t2, TRISB($t1)		#READ (Mem_addr = 0xBF880000 + 0x6040(
	andi $t2, $t2, 0x0001		#MODIFY(bit0 = 1 means input)
	sw   $t2, TRISB($t1)		#WRITE (Write TRISB register)
	
while:					#while(1){
	lw  $t2, PORTB($t1)		#	ler valor do porto de entrada
	not $t2, $t2			#	inverter todos os bits
	andi $t2, $t2, 0x0001		#	extrair bit 0 e colocar os restantes a zero
	lw  $t3, LATE($t1)		#	ler valor do porto de saída
	andi $t3, $t3, 0xFFFE		#	manter todos excepto o bit0
	or  $t2, $t3, $t2		#	guardar valor do bit0
	sw  $t2, LATE($t1)		#	escrever no porto de entrada
	j   while			#
	jr  $ra			
		  