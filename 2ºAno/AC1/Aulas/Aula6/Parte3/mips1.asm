#Mapa de Registos Main
#$t0:	exit_value
#$t1:	str2
	.data
	.eqv SIZE, 30
	.eqv MAXSIZE, 31
str1:	.asciiz "I serodatupmoC ed arutetiuqrA"
str2:	.space 31
str3:	.asciiz "\n"
str4:	.asciiz "String too long: "
	.eqv print_string, 4
	.eqv print_int10, 1
	.text
	.globl main

main:	addiu $sp, $sp, -4		#reservar espaço na Stack
	sw  $ra, 0($sp)			#guardar o valor de $ra
	
	la  $a0, str1			#str1
	jal strlen			#strlen(str1);
	
if:	bgt $v0, MAXSIZE, else		#if(strlen(str1) <= MAXSIZE){
	la  $a0, str2			#	
	la  $a1, str1			#
	jal strcopy			#	strcopy(str2, str1);
	
	move $t1, $v0			#
	move $a0, $t1			#
	li  $v0, print_string		#	print_string(strcopy(str2, str1));
	syscall				#
	
	la  $a0, str3			#
	li  $v0, print_string		#	print_string("\n")
	syscall				#
	
	move $a0, $t1			#
	jal strrev			#	strrev(str2);
	
	move $a0, $v0			#
	li  $v0, print_string		#	print_string(strrev(str2));
	syscall				#
	
	li $t0, 0			#	exit_value = 0;
	j end				#}	
else: 					#else{
	la  $a0, str4			#
	li  $v0, print_string		#	print_string(str4);
	syscall				#
	
	la  $a0, str1			#
	jal strlen			#	strlen(str1);
	
	move $a0, $v0			#
	li  $v0, print_int10		#	print_int10(strlen(str1));
	syscall				#
	
	li $t0, -1			#	exit_value = -1;
end:					#}
	lw  $ra, 0($sp)			#Devolver o valor de $ra
	addiu $sp, $sp, 4		#libertar espaço na Stack
	
	move $v0, $t0			# return exxit_value
	jr  $ra				#fim do programa   
	

#Mapa de Registos strlen
#$a0 -> str
#$v0 -> len
strlen:	li  $t1, 0			#len = 0
while:	lb  $t0, 0($a0)			#*str
	addiu $a0, $a0, 1		#$a0++;
	beq $t0, '\0', endw		#while(*str++ !='\0'){
	addi $t1, $t1, 1		#	len++;
	j while				#}
endw:	move $v0, $t1			#$v0 = len
	jr  $ra				#fim da função
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
	addiu $sp, $sp, 8
	jr  $ra

#Mapa de Registos strrev
#str: 	$a0 -> $s0
#p1:	$s1 
#p2:	$s2
#*p1:	$t1
#*p2:	$t2
strrev:	
	addiu $sp, $sp, -16		#reservar espaço na Stack
	sw  $ra, 0($sp)			#guardar valor de $ra
	sw  $s0, 4($sp)			#guardar valor de $s0
	sw  $s1, 8($sp)			#guardar valor de $s1
	sw  $s2, 12($sp)		#guardar valor de $s2
	
	move $s0, $a0			#$s0 = str
	move $s1, $a0			#$s1 = str
	move $s2, $a0 		 	#$s2 = str

while1:	lb  $t2, 0($s2)			#*p2
	beq $t2, '\0', endw1		#while(*p2!='\0'){
	addi $s2, $s2, 1		#	p2++;
	j while1			#}
endw1:	sub $s2, $s2, 1			#p2--;

while2:	bge $s1, $s2, endw2		#while(p1 < p2){
	move  $a0, $s1			#
	move  $a1, $s2			#	
	jal exchange			#	exchange(p1, p2);
	addi $s1, $s1, 1		#	p1++;
	sub  $s2, $s2, 1		#	p2--;
	j  while2			#}
	
endw2:	move $v0, $s0			#return str
	lw  $ra, 0($sp)			#Devolver os valores armazenados anteriormente
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#
	addiu $sp, $sp, 16		#libertar espaço na Stack
	jr  $ra				#terminar o programa
	
#Mapa de Registos exchange
#$t1: c1
#$t2: c2
exchange:
	lb $t1, 0($a0)
	lb $t2, 0($a1)
	sb $t1, 0($a1)
	sb $t2, 0($a0)
	jr $ra  
