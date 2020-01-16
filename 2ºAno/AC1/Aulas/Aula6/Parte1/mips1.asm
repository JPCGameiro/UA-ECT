#Mapa de Registos
#$t1:	len
#$t0:	s*
#$t2:	guardar o resultado devolvido($v0) (desnecessário)
#$a0:	argumento(s)
#$v0:	resultado devolvido
	.data
str:	.asciiz "Arquitetura de Computadores I"
	.eqv print_int10, 1
	.text
	.globl main

	#-------------------Função main
main:	addiu $sp, $sp, -4	#reserva 1 posição na stack
	sw  $ra, 0($sp)		#guarda o valor de $a0
	
	la  $a0, str		#inicializa str como argumento da finção
	jal strlen		#chama a função
	#move $t2, $v0		#guarda em $t3 o valor de retorno da função	
	
	move  $a0, $v0		#
	li  $v0, print_int10	#print_int10(valor retornado pela função);
	syscall			#
	
	lw  $ra, 0($sp)		#atualiza o valor de $a0
	addiu $sp, $sp, 4	#liberta espaço na stack("apaga" 1 posição)	
	jr $ra			#termina o programa

	#---------------Função strlen
strlen:	li  $t1, 0		#len=0;
while:	lb  $t0, 0($a0)		#*s
	addiu $a0, $a0, 1	#s++;
	beq  $t0, '\0', endw	#while(*s++ != '\0'){
	addi $t1, $t1, 1	#	len++;
	j  while		#}
endw:	move $v0, $t1		#return len;
	jr  $ra			#
	
