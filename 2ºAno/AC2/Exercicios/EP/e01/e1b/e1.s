	.equ SFR_BASE_HI, 0xBF88	#16 MSbits of SFR area
	
	.equ TRISE, 0x6100		#TRISE address is 0xBF886100
	.equ PORTE, 0x6110		#PORTE address is 0xBF886110
	.equ LATE,  0x6120		#LATE  address is 0xBF886120
	
	.equ TRISB, 0x6040		#TRISB address is 0xBF886040
	.equ PORTB, 0x6050		#PORTB address is 0xBF886050
	.equ LATB,  0x6060		#LATB  address is 0xBF886060
	
	.equ printStr, 8
	
	.data
name:	.asciiz "João Gameiro - Nº93097\n"
msg:	.asciiz "Ex1b Preparação Exame Prático - AC2\n"
	.text
	.globl main
	
main:	la  $a0, name			#
	li  $v0, printStr		#printStr(name);
	syscall				#
	
	la  $a0, msg			#
	li  $v0, printStr		#printStr(msg);
	syscall				#
	
	lui  $t0, SFR_BASE_HI		#
	lw   $t1, TRISE($t0)		#READ( Mem_addrs = 0xBF886100 )
	andi $t1, $t1, 0xFFF0		#MODIFY bit0=bit1=bit2=bit3=0 Ouput
	sw   $t1, TRISE($t0)		#WRITE (Write TRISE Register)
	
	lw   $t1, TRISB($t0)		#READ( Mem addrs = 0xBF886040 )
	andi $t1, $t1, 0xFFF0		#MODIFY extract bits 0123 )
	ori  $t1, $t1, 0x000F		#MODIFY bit0=bit1=bit2=bit3= 1 INPUT
	sw   $t1, TRISB($t0)		#WRITE (Write TRISB Register)
	
while:					#while(1){
	lw   $t1, PORTB($t0)		#	Ler valor de RB
	
	andi $t2, $t1, 0x0001		#	Extract bit RB0
	sll  $t2, $t2, 3		# 	bit3 = RB0
	
	andi $t3, $t1, 0x0002		#	Extract bit RB1
	sll  $t3, $t3, 1		#	
	or   $t2, $t2, $t3		#	bit2=RB1
	
	andi $t3, $t1, 0x0004		#	Extract bit RB2
	srl  $t3, $t3, 1		#	
	or   $t2, $t2, $t3		#	bit1 = RB2
	
	andi $t3, $t1, 0x0008		#	Extract bit RB3
	srl  $t3, $t3, 3		#
	or   $t2, $t2, $t3		#	bit0 = RB3
	
	ori  $t2, $t2, 0xFFF0		
	
	lw   $t3, LATE($t0)		#	ler valor presente em RE
	and  $t3, $t3, $t2		#	MODIFY
	sw   $t2, LATE($t0)		#	escrever valor em RE
	
	j   while			#}	
	jr  $ra				#terminar
	
