#Mapa de Registos
#p:	  $t0
#pultimo: $t1
#*p:	  $t2
#soma:	  $t3
	.data
array:	.word 15, 5, 5, 5
	.eqv  print_int10, 1
	.eqv  SIZE, 4
	.text
	.globl main

main:	li  $t3, 0		#soma($t3) =0;
	li  $t4, SIZE		#
	sub $t4, $t4, 1		#$t4 = SIZE - 1 = 3;
	sll $t4, $t4, 2		#
	la  $t0, array		#p($t0) = array; 
	addu $t1, $t0, $t4	#pultimo = array + SIZE - 1;
	
while:	bgtu $t0, $t1, endw	#while(p<=pultimo) {
	lb  $t2, 0($t0)		#	$t2 = *p;
	add $t3, $t3, $t2	#	soma = soma + (*p);
	addiu $t0, $t0, 1	#	p++;
	j  while		#
	
endw:	or  $a0, $0, $t3	#}
	li  $v0, print_int10	#print_int10(soma)
	syscall			#
	
	jr $ra			#fim do programa
