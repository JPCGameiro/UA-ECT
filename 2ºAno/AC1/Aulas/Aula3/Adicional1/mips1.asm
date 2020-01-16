#Mapa de registos
# $t0 = gray
# $t1 = bin
# $t2 = mask
	.data
str1: 	.asciiz "Introduza um número: "
str2:	.asciiz "\nValor em código Gray: "
str3:	.asciiz "\nValor em binário: "
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_int16, 34
	.text
	.globl main

	#print_string(str1)
main:	la  $a0, str1
	ori $v0, $0, print_string
	syscall
	
	#gray($t0) = read_int
	ori $v0, $0, read_int
	syscall
	or  $t0, $0, $v0
	
	#mask($t2) = gray($t0) >> 1    && bin($t1) == gray($t0)
	srl $t2, $t0, 1
	or  $t1, $0, $t0
	
	#if mask($t2) == 0 j endw
while: 	beq $t2, 0, endw
	
	#bin = bin($t1) xor mask($t2),   mask >> 1
	xor $t1, $t1, $t2
	srl $t2, $t2, 1
	j   while	

	#print_string(str2);
endw:	la  $a0, str2
	ori $v0, $0, print_string
	syscall
	
	#print_int16(gray($t0))
	or  $a0, $0, $t0
	ori $v0, $0, print_int16
	syscall
	
	#print_string(str3)
	la  $a0, str3
	ori $v0, $0, print_string
	syscall
	
	#print_int16(bin($t1))
	or  $a0, $0, $t1
	ori $v0, $0, print_int16
	syscall
	
	jr  $ra
