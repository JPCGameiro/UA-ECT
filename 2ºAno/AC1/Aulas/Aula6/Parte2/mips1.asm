#Mapa de Registos main
#$a0 -> argumento da função
#$v0 -> retorno da função
	.data
str:	.asciiz "ITED - orievA ed edadisrevinU"
	.eqv print_string, 4
	.text
	.globl main

main:	addiu $sp, $sp, -4	#reservar espaço na Stack
	sw  $ra, 0($sp)		#guardar valor de $ra
	
	la  $a0, str		#$a0 = str
	jal strrev		#strrev(str);
	
	move $a0, $v0		#
	li  $v0, print_string	#print_string(str);
	syscall			#
	
	lw  $ra, 0($sp)		#retornar o valor de $ra
	addiu $sp, $sp, 4	#libertar espaço na Stack
	jr  $ra			#terminar programa

#Mapa de Registos strrev
#str:	$a0 -> $s0(argumento é passado em $a0
#p1:	$s1 
#p2:	$s2
#*p2:	$s3
strrev:	addiu $sp, $sp, -16	#reservar espaço na Stack
	sw  $ra, 0($sp)		#guarda endereço de retorno
	sw  $s0, 4($sp)		#guarda valor do registo $s0
	sw  $s1, 8($sp)		#guarda valor do registo $s1
	sw  $s2, 12($sp)	#guarda valor do registo $s2
	move $s0, $a0		#registo calle saved
	move $s1, $a0		#p1 = str;
	move $s2, $a0		#p2 = str;
	
while1:	lb  $s3, 0($s2)		#*p2
	beq $s3, '\0', endw1	#while(*p2!='\0'){
	addi $s2, $s2, 1	#	p2++;
	j   while1		#}
	
endw1:	sub $s2, $s2, 1		#p2--;

while2:	bge $s1, $s2, endw2	#while(p1 < p2){
	move $a0, $s1		#	$a0 = $s1 - 1ºargumento da função exchange
	move $a1, $s2		#	$a1 = $s2 - 2ºagrumento da função exchange
	jal  exchange		#	exchange($a0, $a1);
	addi $s1, $s1, 1	#	p1++;
	sub  $s2, $s2, 1	#	p2--;
	j    while2		#}

endw2:	move $v0, $s0		#return str
	lw  $ra, 0($sp)		#repõe endereço de retorno
	lw  $s0, 4($sp)		#repões o valor do registo($s0)
	lw  $s1, 8($sp)		#repõe o valor do registo($s1)
	lw  $s2, 12($sp)	#repõe o valor do registo($s2)
	addiu $sp, $sp, 16	#libertar espaço na stack
	jr  $ra			#terminar a sub-rotina

#Mapa de Registos exchange
#c1:	$t0
#c2:	$t1
exchange:
	lb  $t0, 0($a0)		#*c1
	lb  $t1, 0($a1)		#*c2
	sb  $t1, 0($a0)		#*c1 = *c2
	sb  $t0, 0($a1)		#*c2 = valor antigo *c1
	jr  $ra			#terminar sub-rotina
	