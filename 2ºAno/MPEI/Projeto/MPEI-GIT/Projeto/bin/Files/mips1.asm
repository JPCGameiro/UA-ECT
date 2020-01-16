	.data
	.text
	.globl main
main: 	ori $v0, $0, 5
	syscall			#chamada ao sycall "read_int()"
	or  $t0, $0, $v0	#$t0 = $c0 = valor lido do teclado (valor de x)
	ori $t2, $0, 8 		#$t2 = 8
	add $t1, $t0, $t0	#$t1 = $t0 + $t0 = x + x = 2*x
	sub $t1, $t1, $t2	#$t1 = $t1 + $t2 = 2*x - 8 = y
				#$t1 tem o valor calculado de y
	#or  $a0, $0, $t1	#$a0 = y
	#ori $v0, $0, 1		#
	#syscall		#chamada ao syscall "print_int10()  
				#
	#or  $a0, $0, $t1	#$a0 = y
	#ori $v0, $0, 34	#
	#syscall		#chamada ao syscall "print_int16()
				#
	or  $a0, $0, $t1	#$a0 = y
	ori $v0, $0, 36		#
	syscall			#chamada ao syscall "print_intu10()
				#
	jr $ra			#fim do programa