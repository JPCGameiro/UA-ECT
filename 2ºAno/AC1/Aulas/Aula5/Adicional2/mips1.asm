#Mapa de Registos
#array:		$t0
#i:		$t1
#array+i:	$t2
#array[i]:	$a0
	.data
array:	.word str1, str2, str3
str1:	.asciiz "Array"
str2:	.asciiz "de"
str3:	.asciiz "Ponteiros"
str4:	.asciiz "\n"
	.eqv print_string, 4
	.eqv SIZE, 3
	.text
	.globl main

main:	la  $t0, array			#$t0 = &(array[0])
	li  $t1, 0			#i = 0;
	
	li  $t3, SIZE			#$t3 = SIZE
	sll $t3, $t3, 2			#$t3 = SIZE*4
	
while:	bge $t1, $t3, endw		#while(i < SIZE){
	
	add $t2, $t0, $t1		#	$t2 = array+i;
	lw  $a0, 0($t2)			#	$a0 = array[i];
	
	li  $v0, print_string		#	print_string(array[i]);
	syscall				#
	
	la  $a0, str4			#
	li  $v0, print_string		#	print_string(; );
	syscall				#
	addi $t1, $t1, 4		#	i++;
	j while				#}
	
endw:	jr  $ra				#fim do programa			
