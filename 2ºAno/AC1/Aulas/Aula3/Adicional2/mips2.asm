#Mapeamento de Variáveis
# $t0 - res
# $t1 - i
# $t2 - mdor
# $t3 - mdo
# $t4 - outras operações
	.data
str1:	.asciiz "Introduza dois números: "
str2:   .asciiz "Resultado: "
	.eqv print_intu10, 36
	.eqv read_int, 5
	.eqv print_string, 4 
	.text
	.globl main

	#print_string(str1);
main:	la  $a0, str1
	ori $v0, $0, print_string
	syscall
	
	#mdor($t2) = read_int() & 0x0F
	ori $v0, $0, read_int
	syscall
	or  $t2, $v0, $0
	andi $t2, $t2, 0x0000FFFF
	
	#mdo($t3) = read_int() & 0x0F
	ori $v0, $0, read_int
	syscall
	or  $t3, $0, $v0
	andi $t3, $t3, 0x0000FFFF
	
	#i($t1) == 1
	li  $t1, 0
	
	#if  mdor($t2) == 0  || i($t1) >= 16  j endw
while:	beqz $t2, endw
	bge $t1, 16, endw
	addi $t1, $t1, 1
	
	#if  ($t4 == mdor($t2) and 0x00000001) == 0  j endif
	andi $t4, $t2, 0x00000001
	beq $t4, $0, endif
	
	#res($t0) = res($t0) + mdo($t3)
	add $t0, $t3, $t0

	#mdo($t3) = mdo($t3) << 1  |  mdor($t2) = mdor($t2) >> 1 | i($t1)++
endif:  sll $t3, $t3, 4
	srl $t2, $t2, 4
	j  while

	#print_string(str2)
endw:	la  $a0, str2
	ori $v0, $0, print_string
	syscall
	
	#print_int10(res($t0))
	or  $a0, $0, $t0
	ori $v0, $0, 36
	syscall
	
	#fim do programa
	jr  $ra
