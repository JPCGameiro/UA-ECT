#Mapa de Registos
#$t0:	p
#$t1:	*p
#$t2:	pp
#$t3:	*pp
#$t4:	array+SIZE
	.data
array:	.word str1, str2, str3
str1:	.asciiz "Array"
str2:	.asciiz "de"
str3:	.asciiz "Ponteiros"
str4:	.asciiz "\nString: "
str5:	.asciiz "; "
	.eqv print_string, 4
	.eqv print_char, 11
	.eqv SIZE, 3
	.text
	.globl main

main:	la  $t0, array		#p = &array[0], array;
	li  $t4, SIZE		#SIZE;
	sll $t4, $t4, 2		#SIZE*4;
	addu $t4, $t4, $t0	#array + SIZE;
	
for:	bgeu $t0, $t4, endfor	#for(p=array;p<array+SIZE;p++){

	la  $a0, str4		#
	li  $v0, print_string	#	print_string(str1);
	syscall			# 
	
	lw  $t1, 0($t0)		#	*p;
	or  $t2, $t1, $0	#	pp;

while:	lb  $t3, 0($t2)		#	*pp;
	beq $t3, '\0', endw	#	while(*pp != '\0'){
	
	or  $a0, $t3, $0	#
	li  $v0, print_char	#		print_char(*pp);
	syscall			#
	
	la  $a0, str5		#
	li  $v0, print_string	#		print_string(str5);
	syscall			#
	
	addi $t2, $t2, 1	#		pp++
	j   while		#		

endw:				#	}
	addi $t0, $t0, 4	#	p++;
	j  for			#}

endfor: jr  $ra			#fim do programa
