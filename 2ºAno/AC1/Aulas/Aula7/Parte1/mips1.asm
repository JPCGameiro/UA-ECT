#Mapa de Registos Main
#$a0:	argumento da função
#$v0:	retorno da funão
	.data
str:	.asciiz "2016 e 2020 sao anos bissextos"
str1:	.asciiz "36954 ABQ"
	.eqv print_int10, 1
	.text
	.globl main

main:	addiu $sp, $sp, -4		#Reservar espaço na Stack	
	sw  $ra, 0($sp)			#Guardar o valor de $ra
	
	la  $a0, str1			#
	jal atoi			#atoi(str)
	
	move $a0, $v0			#
	li  $v0, print_int10		#print_int10(atoi(str));
	syscall
	
	lw  $ra, 0($sp)			#devolver o valor de $ra
	addiu $sp, $sp, 4		#libertar espaço na Stack
	
	jr  $ra				#fim do programa
#Mapa de Registos Atoi
#res:	$v0
#s:	$a0
#*s:	$t0
#digit:	$t1
# Sub-rotina terminal: não devem ser usados registos $sx
atoi:	li  $v0, 0			#res = 0;
while:	lb  $t0, 0($a0)			#$t0 = *s
	blt $t0, '0', endw		#while(*s<0 || *s>'9')
	bgt $t0, '9', endw		#{
	sub $t1, $t0, '0'		#	digit = *s-'0';
	addiu $a0, $a0, 1		#	s++;
	mul $v0, $v0, 10		#	res = 10*res;
	add $v0, $v0, $t1		#	res = 10*res + digit;
	j while				#}
endw:	jr $ra				#fim do programa
 
