	.equ SFR_BASE_HI, 0xBF88	
	.equ TRISE, 0x6100	#TRISE address is 0xBF886100
	.equ PORTE, 0x6110	#PORTE address is 0xBF886110
	.equ LATE,  0x6120	#LATE  address is 0xBF886120
	
	.equ getChar, 2
	.equ printStr, 8
	.equ readCoreTimer, 11
	.equ resetCoreTimer, 12
	
	.data
name:	.asciiz "João Gameiro - Nº93097\n"
msg:	.asciiz "Ex2 Preparação Exame Prático - AC2\n"
str:	.asciiz "\nCarregue numa tecla"
	.text
	.globl main
	
main:	la   $a0, name			#
	li   $v0, printStr		#printStr(name);
	syscall				#
	
	la   $a0, msg			#
	li   $v0, printStr		#printStr(name);
	syscall				#

	lui  $t1, SFR_BASE_HI		#
	lw   $t2, TRISE($t1)		#READ (TRISE register)
	andi $t2, $t2, 0xFFF0		#MODIFY  RE0=RE1=RE2=RE3=0 output
	sw   $t2, TRISE($t1)		#WRTIE (TRISE register)
	
while:					#while(1){

	la   $a0, str			#
	li   $v0, printStr		#printStr(name);
	syscall				#
	la   $v0, getChar		#	s = getChar();
	syscall				# 	
	
if0:	bne  $v0, '0', if1		#	if(s=0){
	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFFE		#		LATE0
	ori  $t2, $t1, 0x0001		#		SET LATE0
	sw   $t2, LATE($t1)		#		WRITE
	j    end			#	}
if1:	bne  $v0, '1', if2		#	if(s=1){
	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFFD		#		LATE1
	ori  $t2, $t1, 0x0002		#		SET LATE1
	sw   $t2, LATE($t1)		#		WRITE
	j    end			#	}
if2:	bne  $v0, '2', if3		#	if(s=2){
	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFFB		#		LATE2
	ori  $t2, $t1, 0x0004		#		SET LATE2
	sw   $t2, LATE($t1)		#		WRITE
	j    end			#	}
if3:	bne  $v0, '3', else		#	if(s=3){
	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFF7		#		LATE3
	ori  $t2, $t1, 0x0008		#		SET LATE3
	sw   $t2, LATE($t1)		#		WRITE
	j    end			#	}
					#	else{

else:	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFF0		#		LATE
	ori  $t2, $t1, 0x000F		#		SET LATE
	sw   $t2, LATE($t1)		#		WRITE	
	
	li   $t3, 1000			#		ms = 1000 ms = 1 s
for:	ble  $t3, 0, edelay		#		for(ms > 0){
	sub  $t3, $t3, 1		#			ms--;
	li   $v0, resetCoreTimer 	#			resetCoreTimer();
	syscall				#
whiled:	li   $v0, readCoreTimer  	#			while(readCoreTimer()<k)
	syscall				#
	bge  $v0, 20000, endwd		#
	j    whiled			#
endwd:	j    for			#		}
	
edelay:	lw   $t2, LATE($t1)		#		READ LATE
	andi $t2, $t2, 0xFFF0		#		RESET LATE
	sw   $t2, LATE($t1)		#		WRITE
					#	}
					#
end:	j    while			#}	
	jr   $ra			#Terminar
	
	
