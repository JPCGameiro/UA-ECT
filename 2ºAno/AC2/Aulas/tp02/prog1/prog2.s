	.equ READ_CORE_TIMER, 11
	.equ RESET_CORE_TIMER, 12
	.equ PUT_CHAR, 3
	.equ PRINT_INT, 6
	.data
	.text
	.globl main

main:	li  $t0, 0				
while:	li  $v0, READ_CORE_TIMER		#while(1){
	syscall					#
#	blt $v0, 200000, while			#	while(readCoreTimer() < 200000);   --100Hz
	blt $v0, 2000000, while			#	while(readCoreTimer() < 2000000);  --10Hz
#	blt $v0, 4000000, while			#	while(readCoreTimer() < 4000000);  --5Hz
#	blt $v0, 20000000, while		#	while(readCoreTimer() < 20000000); --1Hz
	li  $v0, RESET_CORE_TIMER		#
	syscall					#	resetCoreTimer();
	li  $a0, ' '				#
	li  $v0, PUT_CHAR			#	putChar(' '),
	syscall					#
	addi $t0, $t0, 1			#	counter+;
	move $a0, $t0				#
	li  $a1, 10				#
	li  $v0, PRINT_INT			#	printInt(counter++, 10);
	syscall					#
	j   while				#}
	jr  $ra					#


#Mapa de Registos delay
#$a0:	ms	
delay:						#
for:	ble $a0, 0, endf			#for(ms > 0){
	sub $a0, $a0, 1				#	ms--;
	li  $v0, RESET_CORE_TIMER		#	resetCoreTimer();
whiled:	li  $v0, READ_CORE_TIMER		#	while(readCoreTimer()<k)
	bge $v0, 20000, endwd			#
	j   whiled				#
endwd:	j   for					#}
endf:	jr  $ra					#fim da função 
