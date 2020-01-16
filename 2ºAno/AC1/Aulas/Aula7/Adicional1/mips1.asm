#Mapa de Registos main
#$t2:	insert_pos
	.data
str1:	.space 100
str2:	.space 50
s1:	.asciiz "Enter a string: "
s2:	.asciiz "Enter a string to insert: "
s3:	.asciiz "Enter the position: "
s4:	.asciiz "Original string: "
s5:	.asciiz "\nModified string: "
s6:	.asciiz "\n"
	.eqv read_string, 8
	.eqv print_string, 4
	.eqv read_int, 5
	.text
	.globl main

main:	addiu $sp, $sp, -4		#Reservar espaço
	sw  $ra, 0($sp)			#guardar o valor dos registos
	
	la  $a0, s1			#
	li  $v0, print_string		#print_string(s1);
	syscall				#
	
	la  $a0, str1			#
	li  $a1, 50			#
	jal read_str			#read_str(str1, 50);
	
	la  $a0, s2			#
	li  $v0, print_string		#print_string(s2);
	syscall				#
	
	la  $a0, str2			#
	li  $a1, 50			#
	jal read_str			#read_str(str2, 50);
	
	la  $a0, s3			#
	li  $v0, print_string		#print_string(s3);
	syscall				#
	
	li  $v0, read_int		#
	syscall				#read_int();
	move $t2, $v0			#$t0(insert_pos) = read_int
	
	la  $a0, s4			#
	li  $v0, print_string		#print_string(s4);
	syscall				#
	
	la  $a0, str1			#
	li  $v0, print_string		#print_string(str1);
	syscall				#
	
	la  $a0, str1			#
	la  $a1, str2			#
	move $a2, $t2			#
	jal  insert			#insert(str1, str2, insert_pos);
	
	la  $a0, s5			#
	li  $v0, print_string		#print_string(s5);
	syscall				#
	
	la  $a0, str1			#
	li  $v0, print_string		#print_string(str1);
	syscall				#
	
	la  $a0, s6			#
	li  $v0, print_string		#print_string(s6);
	syscall				#
	
	lw  $ra, 0($sp)			#devolver o valor dos registos
	addiu $sp, $sp, 4		#Libertar espaço
	li  $v0, 0			#return 0;
	jr  $ra				#fim do programa

#Mapa de Registos read_str
#$a0 -> $s0 -> s
#len 	-> $s1
#len-1 	-> $s2
#s[len-1] -> $s3
read_str:
	addiu $sp, $sp, -20		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#Guardar valores nos registos
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#
	sw  $s2, 12($sp)		#
	sw  $s3, 16($sp)		#
	
	li  $v0, read_string		#read_string(s, size)
	syscall				#
	move $s0, $a0			#$s0 = s;
	
	jal strlen			#strlen(s);
	move $s1, $v0			#len = strlen(s);
	
if:	sub $s2, $s1, 1			#len-1;
	add $s2, $s2, $s0		#s+len-1;
	lb $s3, 0($s2)			#s[len-1];
	bne $s3, 0x0A, end		#if(s[len-1])
	li  $s3, '\0'			#	
	sb  $s3, 0($s2)			#	sen[len-1] = '\0';

end:	lw  $ra, 0($sp)			#
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#
	lw  $s3, 16($sp)		#Devolver valores aos registos
	addiu $sp, $sp, 20		#Libertar espaço na Stack
	jr  $ra				#fim da função	
	

#Mapa de Registos insert
#$a0 -> dst -> $s0
#$a1 -> src -> $s1
#$a2 -> pos
#$s2 -> i
#$s3 -> len_dst
#$s4 -> len_src
#$s5 -> dst+i / src+i
#$s6 -> dst[i] / src[i]
#$s7 -> dst+i+len_src / dst+i+pos
insert:	addiu $sp, $sp, -36		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#Guardar os valores dos registos
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#
	sw  $s2, 12($sp)		#
	sw  $s3, 16($sp)		#
	sw  $s4, 20($sp)		#
	sw  $s5, 24($sp)		#
	sw  $s6, 28($sp)		#
	sw  $s7, 32($sp)		#
	
	move $s0, $a0			#$s0 = dst;
	jal strlen			#strlen(dst);
	move $s3, $v0			#len_dst = strlen(dst);
	
	move $s1, $a1			#$s1 = src;
	move $a0, $a1			#
	jal strlen			#strlen(src);
	move $s4, $v0			#len_src = strlen(src);
	
	bgt $a2, $s3, endif		#if(pos<=len_dst){
	
	move  $s2, $s3			#	i=len_dst;
for1:	blt $s2, $a2, endf1		#	for(i=len_dst;i>=pos){
	add $s5, $s0, $s2		#		dst+i;
	lb  $s6, 0($s5)			#		dst[i];
	add $s7, $s5, $s4		#		dst+i+len_src;
	sb  $s6, 0($s7)			#		dst[i+len_src] = dst[i];
	sub $s2, $s2, 1			#		i--;
	j  for1				#	}
	
endf1:	li  $s2, 0			#	i=0;
for2:	bge $s2, $s4, endif		#	for(i=0;i<len_src){
	add $s5, $s2, $s1		#		src+i;
	lb  $s6, 0($s5)			#		src[i];
	add $s7, $s2, $a2		#		i+pos;
	add $s7, $s7, $s0		#		dst+i+pos;
	sb  $s6, 0($s7)			#		dst[i+pos] = src[i];
	addi $s2, $s2, 1		#		i++;
	j for2				#	}	
	
endif:					#}	
	move $v0, $s0			#return p;
	
	lw  $ra, 0($sp)			#
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#
	lw  $s3, 16($sp)		#
	lw  $s4, 20($sp)		#
	lw  $s5, 24($sp)		#
	lw  $s6, 28($sp)		#
	lw  $s7, 32($sp)		#Retornar os valores aos registos
	addiu $sp, $sp, 36		#Libertar espaço na Stack
	jr  $ra				#Fim da função
	

#Mapa de registos strlen
#$t1:	len
#$t0:	*s
#$a0:	s
strlen:	li  $t1, 0		#len=0;
while:	lb  $t0, 0($a0)		#*s
	addiu $a0, $a0, 1	#s++;
	beq  $t0, '\0', endw	#while(*s++ != '\0'){
	addi $t1, $t1, 1	#	len++;
	j  while		#}
endw:	move $v0, $t1		#return len;
	jr  $ra			#fim da função
