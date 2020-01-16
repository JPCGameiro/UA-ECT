#Mapa de Registos main
#str1:	$t0
#str2:	$t1
	.data
str1:	.asciiz "Arquitetura de "
str2:	.space 50
str3:	.asciiz "\n"
str4:	.asciiz "Computadores I"
	.eqv print_string, 4
	.text
	.globl main

main:	addiu $sp, $sp, -4		#Reservar espaço
	sw  $ra, 0($sp)			#guardar o valor de $ra
	
	la  $a0, str2			#
	la  $a1, str1			#
	jal strcopy			#strcopy(str2, str1)
	
	la $a0, str2			#
	li  $v0, print_string		#print_string(str2);
	syscall				#
	
	la  $a0, str3			#
	li  $v0, print_string		#print_string("\n");
	syscall				#
	
	la  $a0, str2			#
	la  $a1, str4			#
	jal strcat			#strcat(str2, str4);
	
	move $a0, $v0			#
	li  $v0, print_string		#print_string(strcat(str2, str4));
	syscall				#
	
	lw  $ra, 0($sp)			#devolver $ra
	addiu $sp, $sp, 4		#Libertar espaço
	
	jr  $ra				#terminar o programa

#Mapa de Registos strcat		
#$a0 -> $s0(p)
#$a1 -> $s1
#*p  -> $s2
strcat:	
	addiu $sp, $sp, -16		#reservar espaço na Stack
	sw  $ra, 0($sp)			#guardar o valor dos registos
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#
	sw  $s2, 12($sp)		#
	
	move $s0, $a0			#p = dst
	move $s1, $a1			#$s1 = src
	
while:	lb  $s2, 0($s0)			#*p = dst[0];
	beq $s2, '\0', endw		#while(*p != '\0'){
	addi $s0, $s0, 1		#	p++;
	j  while			#}  
	
endw:	move  $a0, $s0			#
	move  $a1, $s1			#
	jal strcopy			#strcopy(p, src);
	
	lw  $ra, 0($sp)			#Devolver os valores armazenados
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#
	addiu $sp, $sp, 16		#
	jr  $ra				#terminar função
	
	
	

#Mapa de Registos strcopy
#$a0 -> $s0(dst) 
#$a1 -> $s1(src)
#$t0 -> i
#$t1 -> dist+i
#$t2 -> src+i
#$t3 -> dist[i]
#$t4 -> src[i]
strcopy:
	addiu $sp, $sp, -8		#Reservar espaço na Stack
	sw  $s0, 0($sp)			#guardar valor do registo $s0
	sw  $s1, 4($sp)			#guardar valor do registo $s1
	
	li  $t0, 0			#i=0;
	move $s0, $a0			#$s0 = dst;
	move $s1, $a1			#$s1 = src;
					
					#do{
do:	addu $t1, $s0, $t0		#	dist+i;
	addu $t2, $s1, $t0		#	src+i
	lb  $t3, 0($t2)			#	src[i];
	sb  $t3, 0($t1)			#	dist[i];
	
	addi $t0, $t0, 1		#	i++
	bne $t3, '\0', do		#}while(src[i++]!='\0')
	
	move $v0, $s0			#$v0 = dst;
	
	lw  $s0, 0($sp)			#retornar o valor
	lw  $s1, 4($sp)			#retornar o valor
	addiu $sp, $sp, 8		#Liberta epaço na Stack
	jr  $ra				#termina a função
