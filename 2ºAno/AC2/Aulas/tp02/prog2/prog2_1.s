	.equ READ_CORE_TIMER, 11
	.equ RESET_CORE_TIMER, 12
	.equ PUT_CHAR, 3
	.equ PRINT_INT, 6
	.data
	.text
	.globl main

main:	addiu $sp, $sp, -8			#
	sw  $ra, 0($sp)				#
	sw  $s0, 4($sp)				#
	li  $s0, 0				#counter = 0;
while:						#while(1){
	li  $a0, 100				#	frequência 10Hz
#	li  $a0, 200				#	frequência 5Hz
#	li  $a0, 1000				#	frequência 1Hz 
	jal delay				#
	li  $a0, ' '				#
	li  $v0, PUT_CHAR			#	putChar(' '),
	syscall					#
	addi $s0, $s0, 1			#	counter+;
	move $a0, $s0				#
	li  $a1, 10				#
	li  $v0, PRINT_INT			#	printInt(counter++, 10);
	syscall					#
	j   while				#}
endw:	
	lw  $ra, 0($sp)				#
	lw  $s0, 4($sp)				#
	addiu $sp, $sp, 8			#
	jr  $ra					#


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
