# Mapa de registos:
# $t0 – value
# $t1 – bit
# $t2 - i 
# $t3 - 0 + bit
	.data
str1:	.asciiz	"Introduza um número: "
str2:	.asciiz "\nO valor em binário é: "
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_char, 11
	.text
	.globl main

	#print_string(str1)
main:	la  $a0, str1
	li  $v0, print_string		# (instrução virtual) 
	syscall				# print_string("Introduza um número: ");
					#
	#$t0 = read_int()		#
	ori $v0, $0, read_int		#
	syscall				#
	or  $t0, $v0, $0		#value($t0) = read_int();
					#
	#print_string(str2)		#
	la  $a0, str2			#(instrução virtual)
	li  $v0, print_string		#print_string("\nO valor binário é: ");
	syscall				#
	li  $t2, 0			#i($t2) = 0
					#
	#if(i>=32) jump endfor		#
for:	bge $t2, 32, endfor		#while(i<32){
					#
	#if(i%4)!=0 jump next		#
	rem $t3, $t2, 4			#	if(i%4 == 0)
	bne $t3, $0, next		#		
	ori $a0, $0, 32			#		print_char(' ');
	ori $v0, $0, print_char		#
	syscall				#
					#
next:	andi $t1, $t0, 0x80000000	#	bit($t1) = value($t0) AND 0x80000000; 
					#
	#shift right logical		#	bit = bit >> 31
	srl $t1, $t1, 31		#
	addi $t3, $t1, 0		#	0 + bit
					#
	or  $a0, $0, $t3		#
	ori  $v0, $0, 1			# 	print(0 + bit)
	syscall				#
					#
endif:	sll $t0, $t0, 1			#	value($t0) = value << 1;
	addi $t2, $t2, 1		#	i($t1) = $t1 + 1;
	j  for				#
					#
endfor: jr $ra				# fim do programa
