#Mapa de Registos
#p:		$t0
#*p:		$t1
#pUltimo:	$t2
#auxilo:	$t3
	.data
array:	.word str2, str3, str4
str1:	.asciiz "\n"
str2:	.asciiz "Array"
str3:	.asciiz "de"
str4:	.asciiz "Ponteiros"
	.eqv print_string, 4
	.eqv SIZE, 3
	.text
	.globl main

main:	la  $t0, array			#p = array
	li  $t2, SIZE			#$t2 = SIZE
	sll $t2, $t2, 2			#$t2 = SIZE*4
	add $t2, $t0, $t2		#$t2 = array+SIZE = pUltimo
	
for:	bge $t0, $t2, endfor		#for(p<pUltimo){
	lw  $t1, 0($t0)			#	*p = cópia de array[0]
	
	or  $a0, $t1, $0		#
	li  $v0, print_string		#	print_string(*p);
	syscall				#
	
	la  $a0, str1			#
	li  $v0, print_string		#	print_string("\n");
	syscall				#
	
	addi $t0, $t0, 4		#	p++;
	j   for				#}

endfor:	jr $ra				#fim do programa