#Mapa de Registos
# p -	$t0
# *p -	$t1
	.data
	.eqv SIZE, 20
str:	.space SIZE
str1:	.asciiz "Instroduza uma string:  "
	.eqv read_string, 8
	.eqv print_string, 4
	.text
	.globl main

	#print_string(str1);
main:	la  $a0, str1
	li  $v0, print_string
	syscall
	
	#$v0 = read_string(str, SIZE)
	la  $a0, str
	li  $a1, SIZE
	li  $v0, read_string
	syscall
	
	#p = str,  *p
	la  $t0, str
while:	lb  $t1, 0($t0)

	#while(*p != '\0')
	beq $t1, '\0', endw
	beq $t1, 0x0a, endw
	
	#if(*p<='0' || *p>='9')
	blt $t1, '0', if
	bgt $t1, '9', if
	j endif
		
	

if:	#if(*p>='a' && *p<='z') ifA
	blt $t1, 'a', ifA
	
	#*p = *p - 'a' + 'A'   //maiúsculas em minúsculas
	sub $t1, $t1, 'a'
	addi $t1, $t1, 'A'
	sb  $t1, 0($t0)
	j endif
	
	#*p = *p - 'A' + 'a'   //minúsculas em maiúsculas
ifA:	sub $t1, $t1, 'A'
	addi $t1, $t1, 'a'
	sb  $t1, 0($t0)
	
	
	#p++
endif:	addi $t0, $t0, 1
	j  while

	#print_string(str)
endw:	la  $a0, str
	li  $v0, print_string
	syscall
	
	jr $ra
