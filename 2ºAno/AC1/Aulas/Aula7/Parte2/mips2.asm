#Mapa de Registos Main
#$t8:	val
#$a1:	base
	.data
buf:	.space 33
str:	.asciiz "\nIntroduza um número: "
str1:	.asciiz "Número em Binário: "
str2:	.asciiz "\nNúmero em Octal: "
str3:	.asciiz "\nNúmero em Hexadecimal: "
	.eqv print_string, 4
	.eqv read_int, 5 
	.text
	.globl main

main:	addiu $sp, $sp, -4		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#Guardar valor $ra
	
	la  $a0, str			#
	li  $v0, print_string		#print_string(str);
	syscall				#
	
	li $v0, read_int		#
	syscall				#read_int();
	move $t8, $v0			#
	
	la  $a0, str1			#
	li  $v0, print_string		#print_string(str1);
	syscall				#
	
	move $a0, $t8			#
	li  $a1, 2			#
	jal print_int_ac1		#print_int_ac1(val, 2);
	
	la  $a0, str2			#
	li  $v0, print_string		#print_string(str2);
	syscall				#
	
	move $a0, $t8			#
	li  $a1, 8			#
	jal print_int_ac1		#print_int_ac1(val, 8);	
	
	la  $a0, str3			#
	li  $v0, print_string		#print_string(str3);
	syscall				#
	
	move $a0, $t8			#
	li  $a1, 16			#
	jal print_int_ac1		#print_int_ac1(val, 16)

	lw  $ra, 0($sp)			#Retornar de $ra
	addiu $sp, $sp, 4		#Libertar espaço na Stack
	jr  $ra				#fim do programa

#Mapa de Registos print_int_ac1
#$a0:		val
#$a1:		base
#$a2:		buf
print_int_ac1:
	addiu $sp, $sp, -4		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#Guardar valor do registo $ra
	
	la  $a2, buf			#$a2 = buf
	jal itoa			#itoa($a0,  $a1, $a2);
	
	move $a0, $v0			#
	li  $v0, print_string		#print_string(itoa($a0,  $a1, $a2)) ;
	syscall				#
	
	lw  $ra, 0($sp)			#retornar valor a $ra
	addiu $sp, $sp, 4		#Libertar espaço na Stack
	jr  $ra				#
	
#Mapa de Registos itoa
#n:	$a0->$s0
#b:	$a1->$s1
#s:	$a2->$s2
#p:	$s3
#digit:	$t0
#outro:	$t1
itoa:	addiu $sp, $sp, -20		#Reservar espaço nas Stack
	sw  $ra, 0($sp)			#guardar registos $sx e $ra
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#
	sw  $s2, 12($sp)		#
	sw  $s3, 16($sp)		#
	
	move $s0, $a0			#$s0 = n;
	move $s1, $a1			#$s1 = b;
	move $s2, $a2			#$s2 = s;
	move $s3, $a2			#p = s;

doit:					#do{
	rem $t0, $s0, $s1		#	digit = n rem(%) b;
	div $s0, $s0, $s1		#	n = n/b;
	move $a0, $t0			#
	jal toascii			#	toascii(digit);
	sb $v0, 0($s3)			#	*p++ = toascii(digit);
	addi $s3, $s3, 1		#	p++;
	bgt $s0, $0, doit		#}while(n > 0);
	
	li  $t1, '\0'
	sb  $t1, 0($s3)			#*p = '\0';	
	move $a0, $s2			#
	jal strrev			#strrev(s);
	move $v0, $s2			#return s;
	
	lw  $ra, 0($sp)			#Devolver valores
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#			 
	lw  $s2, 12($sp)		#
	lw  $s3, 16($sp)		#
	addiu $sp, $sp, 20		#Libertar espaço na Stack
	jr  $ra				#Terminar a função

#Mapa de Registos toascii		
#$a0:	v
toascii:
	addi $a0, $a0, '0'		#v+='0';
	ble  $a0, '9', endasc		#if(v > '9'){
	addi $a0, $a0, 7		#	v+=7;
endasc:	move $v0, $a0			#return v;
	jr   $ra			#fim da função
	
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
