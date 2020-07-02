	.equ	SFR_BASE_HI, 0xBF88	#16 Msbits of SFR area
	.equ	TRISE, 0x6100		#TRISE address is 0xBF886100
	.equ	PORTE, 0x6110		#PORTE address is 0xBF886110
	.equ	LATE,  0x6120		#LATE  address is 0xBF886120
	
	.equ TRISB, 0X6040		#TRISB address is 0xBF886040
  	.equ PORTB, 0x6050		#PORTB address is 0xBF886050
  	.equ LATB,  0x6060		#LATB  address is 0xBF886060
  	
  	.equ printStr, 8
  	
	.data
name:	.asciiz "João Gameiro, Nº93097\n"
msg:	.asciiz "Ex1a Preparação Exame Prático - AC2\n"
	.text
	.globl main
	
main:  	la   $a0, name			#
	li   $v0, printStr		#printStr(name);
	syscall				#
	
	la  $a0, msg			#
	li  $v0, printStr		#printStr(msg);
	syscall				#
	
	lui  $t1, SFR_BASE_HI		#
	lw   $t2, TRISE($t1)		#READ (Mem addr = 0xBF880000 + 0x6100)
	andi $t2, $t2, 0xFFF0		#MODIFY (bit0=bit1=bit2=bit3=0 Output)
	sw   $t2, TRISE($t1)		#WRITE (write in TRISE register)
	
	lw   $t2, TRISB($t1)		#READ (Mem addr = 0xBF880000 + 0x6040)
	ori  $t2, $t2, 0x000F		#MODIFY (bit0=bit1=bit2=bit3=1 Input)
	sw   $t2, TRISB($t1)		#WRITE (write in TRISB register)
	
while:					#while(1){
	lw   $t2, PORTB($t1)		#	Ler valores do porto de entrada (RB)
	andi $t2, $t2, 0x000F		#	Guardar 4 bits menos significativos de RB
	
	lw   $t0, LATE($t1)		#	Ler valores presente no porto de saída
	andi $t0, $t0, 0xFFF0		#	MODIFY
	or   $t0, $t0, $t2		#	MODIFY
	sw   $t0, LATE($t1)		#	Escrever valores lidos no porto de entrada no porto de saída		 
	
	j    while			#}		
	jr   $ra			#terminar
	

