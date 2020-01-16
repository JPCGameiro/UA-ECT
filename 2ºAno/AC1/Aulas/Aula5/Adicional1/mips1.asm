#Mapa de Registos
#i:		$t0
#lista:		$t1
#lista+i:	$t2
#lista[i]:	$t3
#j:		$t4
#lista+j:	$t5
#lista[j]:	$t6
#aux:		$t7
	.data
	.eqv SIZE, 10
	.eqv SIZEM1, 9
str1:	.asciiz "Introduza um número: "
str2:	.asciiz "; "
	.align 2
lista:	.space 40	#SIZE*4
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_int10, 1
	.text
	.globl main

	#-------------------ler valores e armazenar
main:	li  $t0, 0		#i = 0;

while:	bge $t0, SIZE, endw	#while(i<SIZE){
	la  $a0, str1		#
	li  $v0, print_string	#	print_string(str1);
	syscall 		#
	
	li  $v0, read_int	#
	syscall			#	$v0 = read_int();
	
	la  $t1, lista		#	$t1 = lista ou &lista[0];
	sll $t2, $t0, 4		#	$t2 = i*4;
	addu $t2, $t1, $t2	#	$t2 = &lista[i]
	
	sw  $v0, 0($t2)		#	lista[i] = $v0(read_int());
	addi $t0, $t0, 1	#	i++;
	j while			#}
	
endw:	li  $t0, 0		#i=0;
	
	#--------------------Ordenar o array
	la  $t1, lista		#$t1 = lista
	
for1:	bge $t0, SIZEM1, end1	#for(i=0;i<SIZE-1;i++)
	or  $t4, $t0, $0	#j=i+1
	
for2:	bge $t4, SIZE, end2	#for(j=0;j<SIZE;j++)
	
	sll $t2, $t0, 4		#$t2 = i*4
	sll $t5, $t4, 4		#$t5 = j*4
	add $t2, $t1, $t2	#lista+i
	add $t5, $t1, $t5	#lista+j
	lw  $t3, 0($t2)		#$t3 = cópia lista[i]
	lw  $t6, 0($t5)		#$t5 = cópia lista[j]

if:	ble $t3, $t6, endif	#if(lista[i] > lista[j])
	sw  $t3, 0($t5)		#$t3 = cópia lista[j];
	sw  $t6, 0($t2)		#$t6 = cópia lista[i]

endif:	addi $t4, $t4, 1	#j++
	j for2			#
end2:	addi $t0, $t0, 1	#i++
	j for1

end1:

	#--------------------Imprimir o conteúdo do array
	
	li  $t0, 0		#i=0;
	
while0:	bge $t0, SIZE, endw0	#while(i<SIZE){
	la  $t1, lista		#$t1 = lista
	sll $t2, $t0, 4		#$t2 = i*4
	add $t2, $t1, $t2	#$t2 = lista[i];
	
	lw  $t3, 0($t2)		#
	or  $a0, $0, $t3	#
	li  $v0, print_int10	#print_int10();
	syscall			#
	
	la  $a0, str2		#
	li  $v0, print_string	#print_string(str2)
	syscall			#
	
	addi $t0, $t0, 1	#
	j  while0		#

endw0:	jr $ra			
