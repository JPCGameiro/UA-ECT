# Mapa de registos:
# $t0 - soma
# $t1 - value
# $t2 - i
	.data
str1:	.asciiz "Introduza um número: "
str2:	.asciiz "Valor ignorado\n"
str3:	.asciiz "A soma dos positivos é: "
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_int10, 1
	.text
	.globl main

main:	li  $t0, 0			#soma($t0) = 0
	li  $t2, 0			#i($t2) = 0
for:	bge $t2, 5, endfor		#while(i($t2) < 5) {       --if($t2>=5) jump to endfor
					#
	#print_string(str1)		#
	la  $a0, str1			#	print_string("Introduza um número: ");
	ori $v0, $0, print_string	#
	syscall				# 
					#
	#$t1 = read_int()		#
	ori $v0, $0, read_int		#
	syscall				#
	or  $t1, $v0, $0		#	value = read_int();
					#
	ble $t1, $0, else		#	if(value($t1) > 0)	--if($t1 <= 0 jump to else
	add $t0, $t1, $t0		#		soma($t0) = soma($t0) + value($t1);
	j endif				#
					#
	#print_string(str2)		#
else:	la  $a0, str2			#	else
	ori $v0, $0, print_string	#		print_string_("Valor ignorado\n");
	syscall				#
					#
endif:	addi, $t2, $t2, 1		#	i++;        --$t2++ 
	j for				#
					#}
	#print_string(str3)		#
endfor: la  $a0, str3			#
	ori $v0, $0, print_string	#print_string("A soma dos positivos é: ");
	syscall				#
					#
	#print_int10($t0)		#
	or  $a0, $t0, $0		#
	ori $v0, $0, print_int10	#print_int(soma($t0));
	syscall				#
	jr  $ra				#fim do programa