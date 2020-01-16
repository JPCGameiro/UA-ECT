#Mapa de registos main
#$t0:	i
#$t1:	dup_counter
#$t2:	array
#$t3:	array+i
#$t4:	aux
#$t5:	aux+i
#$t6:	array[i]
#$t7:	aux[i]
	.data
	.eqv SIZE, 10
str1:	.asciiz "array["
str2:	.asciiz "]= "
str3:	.asciiz "*, "
str4:	.asciiz ", "
str5:	.asciiz "\nNº repetidos: "
	.align 2
array:	.word SIZE
aux:	.word SIZE
	.eqv print_string, 4
	.eqv print_int10, 1
	.eqv read_int, 5
	.text
	.globl main

main:	addiu $sp, $sp, -4		#Reservar espaço na STACK
	sw  $ra, 0($sp)			#guardar o valor de $ra
	
	li  $t0, 0			#i=0;
	li  $t1, 0			#dup_counter=0;
	
forr:	bge $t0, SIZE, endforr		#for(i=0;i<SIZE){
	la  $a0, str1			#
	li  $v0, print_string		#	print_string("array[")
	syscall				#
	
	move $a0, $t0			#
	li  $v0, print_int10		#	print_int10(i);
	syscall				#
	
	la  $a0, str2			#
	li  $v0, print_string		#	print_string("]=");
	syscall				#
	
	la  $t2, array			#
	sll $t3, $t0, 2			#	i*4;
	add $t3, $t3, $t2		#	array+i;
	
	li  $v0, read_int		#
	syscall				#	read_int;
	sw  $v0, 0($t3)			#	array[i] = read_int();
	
	addi $t0, $t0, 1		#
	j  forr				#

endforr:				#}
	la  $a0, array			#
	la  $a1, aux			#
	li  $a2, SIZE			#	
	jal find_duplicates		#find_duplicates(array, aux, SIZE);
	
	li  $t0, 0			#i=0;
foro:	bge $t0, SIZE, endforo		#for(i=0;i<SIZE){
	la  $t4, aux			#	aux;
	sll $t5, $t0, 2			#	i*4;
	add $t5, $t4, $t5		#	aux+i;
	lw  $t7, 0($t5)			#	aux[i];
	
	la  $t2, array			#	array;
	sll $t3, $t0, 2			#	i*4;
	add $t3, $t2, $t3		#	array+i;
	lw  $t6, 0($t3)		#	array[i];
	
	blt $t7, 2, else		#	if(aux[i]>=2){
	la  $a0, str3			#		print_string("*, ");
	li  $v0, print_string		#
	syscall				#
	
	addi $t1, $t1, 1		#		dup_counter++;
	j   endif			#	else{
	
else:	move $a0, $t6			#		
	li  $v0, print_int10		#		print_int10(array[i]);
	syscall				#		
	
	la  $a0, str4			#
	li  $v0, print_string		#		print_string(str4);
	syscall				#

endif:	addi $t0, $t0, 1		#	}i++;
	j   foro			#}
	
endforo:
	la  $a0, str5			#
	li  $v0, print_string		#print_string(str5);
	syscall				#
	
	move $a0, $t1			#
	li  $v0, print_int10		#print_int10(dup_counter);
	syscall				#

	lw  $ra, 0($sp)			#Retornar valores
	addiu $sp, $sp, 4		#Liberta espaço na Stack
	li  $v0, 0			#return 0;
	jr  $ra				#fim do programa
	

#Mapa de registos find_duplicates
#$a0:	array
#$a1:	dup_array
#$a2:	size
#$t0: 	i
#$t1:	j
#$t2:	token
#$t3:	dup_array+i
#$t4:	dup_array[i];
#$t5:	array+i
#$t6:	array+j
#$t7:	array[i]
#$t8:	array[j]

find_duplicates:
	addiu $sp, $sp, -40		#Reservar espaço na Stack
	sw  $ra, 0($sp)			#Guardar os valores
	sw  $t0, 4($sp)			#
	sw  $t1, 8($sp)			#
	sw  $t2, 12($sp)		#
	sw  $t3, 16($sp)		#
	sw  $t4, 20($sp)		#
	sw  $t5, 24($sp)		#
	sw  $t6, 28($sp)		#
	sw  $t7, 32($sp)		#
	sw  $t8, 36($sp)		#
	
	li  $t0, 0			#i=0;
	sll $a2, $a2, 2			#size = size*4;
for1:	sll $t0, $t0, 2			#i=i*4;
	bge $t0, $a2, endf1		#for(i=0;i<size){
	
	add $t3, $a1, $t0		#	dup_array+i;
	sw  $0, 0($t3)			#	dup_array[i] = 0;
	
	add $t5, $t0, $a0		#		array+i;
	lw  $t7, 0($t5)			#		array[i];
	li  $t1, 0			#	j=0;
	li  $t2, 1			#	token=1;
for2:	sll $t1, $t1, 2			#	j=j*4;
	bge $t1, $a2, endf2		#	for(j=0,token=1; j<size){
	
	add $t6, $t1, $a0		#		array+j;
	lw  $t8, 0($t6)			#		array[j];
	
	bne $t7, $t8, endiff		#		if(array[i]==array[j]){
	sw  $t2, 0($t3)			#			dup_array[j]=token;
	addi $t2, $t2, 1		#			token++;
					
endiff:	addi $t1, $t1, 1		#		j++;
	j  for2				#	}

endf2:	addi $t0, $t0, 1		#	i++;
	j  for1				#}	

endf1:	
	lw  $ra, 0($sp)			#
	lw  $t0, 4($sp)			#
	lw  $t1, 8($sp)			#
	lw  $t2, 12($sp)		#
	lw  $t3, 16($sp)		#
	lw  $t4, 20($sp)		#
	lw  $t5, 24($sp)		#
	lw  $t6, 28($sp)		#
	lw  $t7, 32($sp)		#
	lw  $t8, 36($sp)		#Retornar os valores
	addiu $sp, $sp, 40		#Libertar espaço na Stack
	jr  $ra				#Fim da função
