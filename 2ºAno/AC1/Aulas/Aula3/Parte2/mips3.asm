# Mapa de registos:
# $t0 – value
# $t1 – bit
# $t2 - i 
# $t3 - 0 + bit
# $t4 - flag
# $t5 - outras operações
	.data
str1:	.asciiz	"Introduza um número: "
str2:	.asciiz "O valor em binário é: "
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_char, 11
	.text
	.globl main

	#print_string(str1)
main:	la  $a0, str1
	ori $v0, $0, print_string
	syscall
	
	#value($t0) = read_int()
	ori $v0, $0, read_int
	syscall
	or  $t0, $0, $v0
	
	#print_string(str2)
	la  $a0, str2
	ori $v0, $0, print_string
	syscall
	
	#i($t2)=0  && flag($t4)=0
	li  $t2, 0
	li  $t4, 0
	
	#if i($t2) >= 32 j endfor  && i($t2) ++ 
for:	bge $t2, 32, endfor
	
	#bit($t1) = value($t0) >> 31
	srl $t1, $t0, 31 
	
	#if flag($t4)== 1 || bit($t1) !=0 j insideif0
	beq $t4, 1, inif1	
	bne $t1, 0, inif1

	#value($t0) = value($t0) << 1
endif1:	sll $t0, $t0, 1
	addi $t2, $t2, 1
	j  for
	
	#flag($t4) = 1 &&  $t5 = i($t2) rem 4
inif1:	li  $t4, 1
	rem $t5, $t2, 4
	
	#if(i rem 4 != 0) j endif0
	bne $t5, 0, endif0
	
	#print_char(' ')
	ori $a0, $0, 32
	ori $v0, $0, print_char
	syscall
	
	#print_char(0x30 + bit($t1)
endif0: addi $t5, $t1, 0x30
	or  $a0, $0, $t5
	ori $v0, $0, print_char
	syscall	
	j  endif1
	
	#fim do programa
endfor:	jr  $ra
