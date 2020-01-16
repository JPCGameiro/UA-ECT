#Mapa de Registos
#argc:		$a0
#argv:		$a1
#cópiaargc:	$t0
#cópiaargv:	$t1
#i:		$t2
#argv+i:	$t3
#argv[i]:	$t4
	.data
str1:	.asciiz "NºParâmetros: "
str2:	.asciiz "\nP"
str3:	.asciiz ": "
	.eqv print_int10, 1
	.eqv print_string, 4
	.text
	.globl main
	
main:	or  $t0, $0, $a0		#$t0 = argc
	or  $t1, $0, $a1		#$t1 = argv
	
	la  $a0, str1			#
	li  $v0, print_string		#print_string("NºParâmetros: ");
	syscall
	
	or  $a0, $0, $t0		#
	li  $v0, print_int10		#print_int(argc)
	syscall				#
	
	li  $t2, 0			#i=0
	
for:	bge $t2, $t0, endf		#for(i=0;i<argc)[
	
	la  $a0, str2			#
	li  $v0, print_string		#	print_string("\nP");
	syscall				#
	
	or  $a0, $0, $t2		#
	li  $v0, print_int10		#	print_int10(i);
	syscall				#
	
	la  $a0, str3			#
	li  $v0, print_string		#	print_string(": ");
	syscall				#
	
	sll $t3, $t2, 2			#	$t3 = i*4;
	add $t3, $t3, $t1		#	$t3 = argv+i;
	lw  $t4, 0($t3)			#	$t4 = argv[i];
	
	or  $a0, $0, $t4		#
	li  $v0, print_string		#	print_string(argv[i]);
	syscall				#
	addi $t2, $t2, 1		#	i++;
	j   for				#}	
	
endf:	jr  $ra				#fim do programa
