	.equ SFR_BASE_HI, 0xBF88	# 16 MSbits of SFR area
	.equ TRISE, 0x6100		# TRISE address is 0xBF886100
	.equ PORTE, 0x6110		# PORTE address is 0xBF886110
	.equ LATE,  0x6120		# LATE  address is 0xBF886120
	
	.equ READ_CORE_TIMER, 11
	.equ RESET_CORE_TIMER, 12
	
	.data
	.text
	.globl main

main:	addiu $sp, $sp, -16		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#salvaguardar valores nos registos
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#
	sw  $s2, 12($sp)		#  
	
	li   $s0, 0			#v = 0;
	lui  $s1, SFR_BASE_HI		#
	lw   $s2, TRISE($s1)		#READ(read TRISE address)
	andi $s2, $s2, 0xFFFE		#MODIFY(bit0=0 Output)
	sw   $s2, TRISE($s1)		#WRITE(write TRISE address); 

while:					#while(1){
	lw  $s2, LATE($s1)		#	ler o valor presente no registo LATE
	andi $s2, $s2, 0xFFFE		#	extrair o bit 0 mantendo os outros
	or  $s2, $s2, $s0		#	modificar  o bit0
	sw  $s2, LATE($s1)		#	escrever o bit0 em LATE
	li  $a0, 500			#	
#	li  $a0, 20			#
#	li  $a0, 10			#
	jal delay			#	delay(500);
	xor $s0, $s0, 0x0001		#	complementa o bit 0 de v (v = v xor 1)
	j   while			#
	
	lw  $ra, 0($sp)			#
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#Devolver valores aos registos
	addiu $sp, $sp, 16		#Libertar espaço na Stack																	
	jr  $ra				#}
	
#Mapa de Registos delay
#$a0:	ms	
delay:						#
for:	ble $a0, 0, endf			#for(ms > 0){
	sub $a0, $a0, 1				#	ms--;
	li  $v0, RESET_CORE_TIMER		#	resetCoreTimer();
	syscall
whiled:	li  $v0, READ_CORE_TIMER		#	while(readCoreTimer()<k)
	syscall
	bge $v0, 20000, endwd			#
	j   whiled				#
endwd:	j   for					#}
endf:	jr  $ra					#fim da função
