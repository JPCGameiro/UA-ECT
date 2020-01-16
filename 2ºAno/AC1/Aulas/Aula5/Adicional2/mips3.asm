#Mapa de Registos
#i:		$t0
#j:		$t1
#array:		$t3
#array+i:	$t4
#array[i]:	$t5
#lista[i]+j:	$t6
#lista[i][j]:	$t7
	.data
array:	.word str0, str1, str2
str0:	.asciiz "Array"
str1:	.asciiz "de"
str2:	.asciiz "Ponteiros"
str3:	.asciiz "\nString "
str4:	.asciiz ": "
str5:	.asciiz "; "
	.eqv print_string, 4
	.eqv print_int10, 1
	.eqv print_char, 11
	.eqv SIZE, 3
	.text
	.globl main

main:	li  $t0, 0			#i=0

for:	bge $t0, SIZE, endfor		#for(i=0;i<SIZE;){
	la  $a0, str3			#
	li  $v0, print_string		#	print_string('\nString');
	syscall
	
	or  $a0, $0, $t0		#
	li  $v0, print_int10		#	print_int10(i);
	syscall				# 
	
	la  $a0, str4			#
	li  $v0, print_string		#	print_string(": ");
	syscall				#
	
	la  $t3, array			#	$t3 = array;
	li  $t1, 0			#	j = 0;

while:	sll $t4, $t0, 2			#	$t4=i*4;
	add $t4, $t4, $t3		#	$t4 = array+i;
	lw  $t5, 0($t4)			#	$t5 = array[i];
	
	add $t6, $t5, $t1		#	$t6 = array[i]+j;
	lb  $t7, 0($t6)			#	$t7 = array[i][j];
	beq $t7, '\0', endw		#	while(array[i][j]!='\0'){
	
	or  $a0, $0, $t7		#
	li  $v0, print_char		#		print_char(array[i][j]);
	syscall				#
	
	la  $a0, str5			#
	li  $v0, print_string		#		print_char('-');
	syscall				#
	
	addi $t1, $t1, 1		#		j++;
	j  while			#	}
	
endw:	addi $t0, $t0, 1		#	i++;
	j  for				#}

endfor: jr  $ra
