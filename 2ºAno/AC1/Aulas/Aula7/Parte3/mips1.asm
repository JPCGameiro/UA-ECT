#Mapa de Registos main
#$a0 = dividendo - $t6
#$a1 = divisor	- $t7
#Resultado = $t8
	.data
	.eqv print_string, 4
	.eqv print_int10, 1
	.eqv read_int, 5
str:	.asciiz "Resultado: "
str1:	.asciiz "Dividendo: "
str2:	.asciiz "Divisor: "
	.text
	.globl main
	
main:	addiu $sp, $sp, -4		#Reservar espaço na stack
	sw  $ra, 0($sp)			#guardar o valor $ra
	
	la  $a0, str1			#
	li  $v0, print_string		#print_string("Dividendo: ");
	syscall				#
	
	li  $v0, read_int		#
	syscall				#
	move $t6, $v0			#$t6(dividendo) = read_int()
	
	la  $a0, str2			#
	li  $v0, print_string		#print_string("Divisor: ");
	syscall				#
	
	li  $v0, read_int		#
	syscall				#
	move $t7, $v0			#$t7(divisor) = read_int();
	
	move $a0, $t6			#		
	move $a1, $t7			#
	jal divi			#div(dividendo, divisor);
	move $t8, $v0			#
	
	la  $a0, str			#
	li  $v0, print_string		#print_string("Resultado: ");
	syscall				# 
	
	move $a0, $t8			#
	li  $v0, print_int10		#print_int10($t8);
	syscall				#
	
	lw  $ra, 0($sp)
	addiu $sp, $sp, 4
	jr  $ra

#Mapa de Registos div
#$a0->$t0(dividendo)
#$a1->$t1(divisor)
#$t2(i)
#$t3(bit)
#$t4(quociente)
#$t5(resto)
divi:	sll $t1, $a1, 16		#divisor = divisor<<16;
	andi $t0, $a0, 0xFFFF		#dividendo = dividendo & 0xFFFF;
	sll $t0, $t0, 1			#dividendo = dividendo<<1;
	
	li  $t2, 0			#i=0;	
for:	bge $t2, 16, endfor		#for(i<16){
	addi $t2, $t2, 1		#	i++;
	li  $t3, 0			#	bit=0;
	
	blt $t0, $t1, endif		#	if(dividendo>=divisor){
	sub $t0, $t0, $t1		#		dividendo = dividendo-divisor;
	li  $t3, 1			#		bit=1;
endif:					#	}
	sll $t0, $t0, 1			#	dividendo = dividendo<<1;
	or  $t0, $t0, $t3		#	dividendo = dividendo | bit;
	j   for				#}

endfor:	srl  $t5, $t0, 1		#resto=dividendo>>1:
	andi $t5, $t5, 0xFFFF0000	#resto = resto | 0xFFFF0000;
	andi $t4, $t0, 0xFFFF		#quociente = dividendo & 0xFFFF;
	or   $v0, $t5, $t4		#return resto | quociente;
	jr   $ra			#fim do programa 
		