#Mapa de Registos
#$a0:	argc
#$a1:	argv / max
#$a3:	strM
#$t0:	argc
#$t1:	argc+argv
#$t2:	p
#$t3:	*p
#$t4:	pp
#$t5:	*pp
#$t6:	min
#$t7:	mai
#$t8:	digit
#$t9:	caract

	.data
str0:	.asciiz "\nArg: "
str1:	.asciiz "\nNºMinúsculas: "
str2:	.asciiz "\nNºMaiúsculas: "
str3:	.asciiz	"\nNºDígitos: "
str4:	.asciiz "\nNºCaracteres: "
str5:	.asciiz "\nMaior String: "
	.eqv print_int10, 1
	.eqv print_string, 4
	.eqv print_char, 11
	.text
	.globl main

main:	or  $t0, $0, $a0		#$t0 = argc
	sub $t1, $t0, 1
	sll $t1, $t0, 2			#$t1 = argc*4
	add $t1, $t1, $a1		#$t1 = argv + argc*4
	or  $t2, $a1, $0		#$t2 = p(&argv[0]);
	li  $a1, 0			#$a1(max) = 0;

for:	bge $t2, $t1, endf		#for(p=argv;p<argv+argc;p++){
	lw  $t3, 0($t2)			#	$t3 = *p;
	
	or  $t4, $t3, $0		#	pp = *p;
	li  $t9, 0			#	caract = 0;
	li  $t8, 0			#	digit = 0;
	li  $t7, 0			#	mai = 0;
	li  $t6, 0			#	min = 0;
	
while:	lb  $t5, 0($t4)			#	*pp
	beq $t5, '\0', endw		#	while(*pp != '\0'){
	
	addi $t9, $t9, 1		#		caract++;
	addi $t4, $t4, 1		#		pp++;

ifmin:	blt $t5, 'a', endmin		#		if(*pp>='a' && *pp<='z')
	bgt $t5, 'z', endmin		#		{
	addi $t6, $t6, 1		#			min++;
endmin:					#		}

ifmai:	blt $t5, 'A', endmai		#		if(*pp>='A' && *pp<='Z')
	bgt $t5, 'Z', endmai		#		{
	addi $t7, $t7, 1		#			mai++;
endmai:					#

ifdig:	blt $t5, '0', enddi		#		if(*pp>='0' && *pp<='9')
	bgt $t5, '9', enddi		#		{
	addi $t8, $t8, 1		#			digit++	
enddi:					#		}
	j while				#	}

endw:	bge $a1, $t9, end		# 	if(max<caract){
	or  $a1, $t9, $0		#		max = caract;
	or  $a3, $t3, $0		#		strM = *p
end:					#	}	
	
	la  $a0, str0			#
	li  $v0, print_string		#	print_string("Arg: ");
	syscall				#
	
	or  $a0, $t3, $0		#
	li  $v0, print_string		#	print_string(*p);
	syscall				#
	
	la  $a0, str1			#
	li  $v0, print_string		#	print_string("NºMinúsculas: ");
	syscall				#
	
	or  $a0, $t6, $0		#
	li  $v0, print_int10		#	print_int10(min);
	syscall				#
	
	la  $a0, str2			#
	li  $v0, print_string		#	print_string("NºMaiúsculas: ");
	syscall
	
	or  $a0, $t7, $0		#
	li  $v0, print_int10		#	print_int10(mai);
	syscall				#
	
	la  $a0, str3			#
	li  $v0, print_string		#	print_string("NºDigitos: ");
	syscall				#
	
	or  $a0, $0, $t8		#
	li  $v0, print_int10		#	print_int10(digit):
	syscall				#
		
	la  $a0, str4			#	
	li  $v0, print_string		#	print_string("NºCaracter: ");
	syscall				#
	
	or  $a0, $0, $t9		#
	li  $v0, print_int10		#	print_string(caract);
	syscall				#
	
	addi $t2, $t2, 4		#	p++;			
	j   for				#}	

endf: 	la  $a0, str5			#
	li  $v0, print_string		#print_string("Maior Argumento: ");
	syscall				#
	
	or  $a0, $a3, $0		#
	li  $v0, print_string		#print_string(strM);
	syscall				#

	jr $ra				#Fim do programa
