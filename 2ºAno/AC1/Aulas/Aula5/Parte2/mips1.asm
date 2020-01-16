#Mapa de Registos
#p:		$t0
#*p:		$t1
#lista+SIZE:	$t2
	.data
str1:	.asciiz ";\n"
str2:	.asciiz "\nConteúdo do array\n"
lista:	.word 8, -4, 3, 5, 124, -15, 87, 9, 27, 15
	.eqv print_int10, 1
	.eqv print_string, 4
	.eqv SIZE, 10
	.text
	.globl main

	#print_string(str2);
main:	la  $a0, str2
	li  $v0, print_string
	syscall
	
	#p = lista, $t2 = SIZE
	la  $t0, lista
	li  $t2, SIZE
	
	#$t2 = lista+size
	sll $t2, $t2, 2
	addu $t2, $t2, $t0

	#while(p<lista+SIZE), $t1 = *p
while:	bgeu $t0, $t2, endw
	lw  $t1, 0($t0)
	
	#print_int10(*p)
	or $a0, $0, $t1
	li  $v0, print_int10
	syscall
	
	#print__string(str1)
	la  $a0, str1
	li  $v0, print_string
	syscall
	
	#p++
	addiu $t0, $t0, 4
	j while
	
	#fim do programa
endw:	jr  $ra
	
