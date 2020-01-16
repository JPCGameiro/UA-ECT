#Mapa de Registos
#i:		$t0
#array:		$t1
#array+i:	$t2
#array[i]:	$t3
#soma:		$t4
	.data
array:	.word 5, 5, 5, 5
	.eqv  print_int10, 1
	.eqv  SIZE, 4
	.text
	.globl main

main:	li  $t0, 0		#i = 0;
	li  $t4, 0		#soma = 0;
	
while:	la  $t1, array		#$t1 = array ou &array[0];
	addu $t2, $t1, $t0	#$t2 = array+i ou &array[i];
	lb  $t3, 0($t2)		#$t3 = array[i];
	bgt $t0, 16, endw	#while(i <= 16) {                #os endereços estão em múltiplos de 4
	 
	addu $t4, $t4, $t3	#	soma = soma + array[i];
	addi $t0, $t0, 1	#	i++;
	j  while		#}
	
endw:	or  $a0, $0, $t4	#
	li  $v0, print_int10	#print_int10($t4);
	syscall			#
	
	or  $a0, $0, $t0
	li  $v0, print_int10	#print_int10(i);
	syscall
	
	jr  $ra			#fim do programa