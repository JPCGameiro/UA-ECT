#Mapa de Registos Main (testar a função strcopy)
#$a0 -> argumento da função
#$a1 -> argumento da função
#$v0 -> retorno da função
	.data
stro:	.asciiz "AC1 - Arquitetura de Computadores I"
strc:	.space 50
	.eqv print_string, 4
	.text
	.globl main

main:	addiu $sp, $sp, -4	#reservar espaço na Stack
	sw $ra, 0($sp)		#guardar valor do registo $ra
	
	la  $a0, strc		#
	la  $a1, stro		#
	jal strcopy		#strcopy(strc, stro);
	
	move $a0, $v0		#
	li  $v0, print_string	#print_string(strcopy(strc, stro); 
	syscall			#
	
	lw  $ra, 0($sp)		#retornar valor do registo $ra
	addiu $sp, $sp, 4	#Libertar espaço na Stack
	jr  $ra			#fim do programa


#Mapa de Registos strcopy
#dst:	$t0
#src:	$t1
#*src:	$t2
strcopy:
	move $t0, $a0		#$t0 = dst;
	move $t1, $a1		#$t1 = src;
	
				#do{
do:	lb $t2, 0($t1)		#	$t2 = *src
	sb $t2, 0($t0)		#	guardar o caracter em dst
	
	addi $t0, $t0, 1
	addi $t1, $t1, 1	#	src++;
	bne $t2, '\0', do	#}while(*src!='\0');
	
	move $v0, $a0		#return dst
	jr  $ra			#fim 