	.equ printStr, 8
	.equ readStr, 9
	.equ printInt, 6
	.equ printInt10, 7
	.equ STR_MAX_SIZE, 20
	
	.data
st0:	.asciiz "\nIntroduza 1 string: "
st1:	.asciiz "\nResultados:"
st2:	.asciiz "\nTamanho da String: "
st3:	.asciiz "\nConcatenação: "
st4:	.asciiz "\nComparação: "
str1:	.space 21
str2:	.space 21
str3:	.space 41
	.text
	.globl main
	
main:	addiu $sp, $sp, -4			#Reservar espaço na Stack
	sw  $ra, 0($sp)				#Salvaguardar os valores do registo
	
	la  $a0, st0				#
	li  $v0, printStr			#printStr(st0);
	syscall					#
	
	la  $a0, str1				#
	li  $a1, STR_MAX_SIZE			#
	li  $v0, readStr			#readStr(str1, STR_MAX_SIZE);
	syscall					#

	la  $a0, st0				#
	li  $v0, printStr			#printStr(st0);
	syscall					#
	
	la  $a0, str2				#
	li  $a1, STR_MAX_SIZE			#
	li  $v0, readStr			#readStr(str2, STR_MAX_SIZE);
	syscall					#
	
	la  $a0, st1				#
	li  $v0, printStr			#printStr(st1);
	syscall					#
	
	la  $a0, st2				#
	li  $v0, printStr			#
	syscall					#
	
	la  $a0, str1				#
	jal strlen				#strlen(str1);
	move $a0, $v0				#
	li  $a1, 10				#
	li  $v0, printInt			#printInt(strlen(str1), 10);
	syscall					#
	
	la  $a0, st2				#
	li  $v0, printStr			#printStr(st2);
	syscall					#
	
	la  $a0, str2				#
	jal strlen				#strlen(str2);
	move $a0, $v0				#
	li  $a1, 10				#
	li  $v0, printInt			#printInt(strlen(str2), 10);
	syscall					#
	
	la  $a0, st3				#
	li  $v0, printStr			#printStr(st3);
	syscall					#
	
	la  $a0, str3				#
	la  $a1, str1				#
	jal strcpy				#strcpy(str3, str1);

	la  $a0, str3				#
	la  $a1, str2				#
	jal strcat				#strcat(str3, str2);
	move $a0, $v0				#
	li  $v0, printStr			#printStr(strcat(str3, str2);
	syscall					#

	la  $a0, st4				#
	li  $v0, printStr			#printStr(st4);
	syscall					#
	
	la  $a0, str1				#
	la  $a1, str2				#
	jal strcmp				#strcmp(str1, str2);
	move $a0, $v0				#
	li  $v0, printInt10			#printInt10(strcmp(str1, str2));
	syscall					#
		 			 		
	lw  $ra, 0($sp)				#Devolver os valores aos registos
	addiu $sp, $sp, 4			#Libertar espaço na Stack
	
	li  $v0, 0				#return 0;
	jr  $ra					#

#Mapa de Registos strlen
#s: 	$t0
#len:	$t1
strlen:	li  $t1, 0				#len=0;
while:	lb  $t0, 0($a0)				#*s
	addiu $a0, $a0, 1			#s++;
	beq $t0, 0, endw			#while(*s!='\0'){ 
	addi $t1, $t1, 1			#	len++;
	j   while				#}
endw:	move $v0, $t1				#return len;
	jr  $ra					#

#Mapa de Registos strcpy
#p:	$t0
#dst:	$a0
#src:	$a1
strcpy:						#do{
do:	lb  $t0, 0($a1)				#	$t0 = *dst;
	sb  $t0, 0($a0)				#	*dst = *src;
	
	addi $a1, $a1, 1			#	src++;
	addi $a0, $a0, 1			#	dst++;
	
	bne $t0, 0, do				#}while(*src!='\0');
	move $v0, $a0				#return dst;
	jr  $ra					#

#Mapa de Registos strcat
# $s0 : p
# $s1 : dst
# $s2 : *dst

strcat:	addi $sp, $sp,-16			#Reservar espaço na Stack
	sw  $ra, 0($sp)				#Salvaguardar os valores dos registos
	sw  $s0, 4($sp)				#
	sw  $s1, 8($sp)				#
	sw  $s2, 12($sp)			#
	  
	move $s0,$a0				#p = dst;
	move $s1,$a0				#dst;
   	

whilec:	lb  $s2,0($s1)				#*dst;
	beq $s2, 0, endwc			#while(*dst!='\0'){
	beq $s2, '\n', endwc			#
	addi $s1,$s1,1				#	dst++;
	j   whilec				#}
endwc:
	move $a0, $s1				#strcpy(dst, src);
	jal strcpy				#
	move $v0, $s0				#return p;
	
	lw  $ra, 0($sp)				#
	lw  $s0, 4($sp)				#
	lw  $s1, 8($sp)				#
	lw  $s2, 12($sp)			#Devolver valores aos registos
	addiu $sp, $sp, 16			#Libertar espaço na Stack
	jr $ra					#
	
#Mapa de Registos strcmp			#
#*s1:	$t0
#*s2:	$t1
strcmp:
fors:	lb  $t0, 0($a0)				#*s1;
	lb  $t1, 0($a1)				#*s2
	
	beq $t0, $t1, endfs			#for(;(*s1==*s2)&&(*s1!='\0');
	bne $t0, 0, endfs			#
	
	addi $a0, $a0, 1			#	s1++;
	addi $a1, $a1, 1			#	s2++;
	j   fors				#		
	
endfs:	sub $v0, $t0, $t1			#return(*s1-*s2);
	jr  $ra

	
	
