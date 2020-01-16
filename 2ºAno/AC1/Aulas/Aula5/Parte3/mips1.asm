#do:  	li $t4, FALSE
#	li  $t5, 0
#Mapa de registos
#$t0:		p
#$t1:		*p
#$t2:		lista+SIZE
#$t3:		---------
#$t4: 		houveTroca
#$t5:		i
#$t6:		lista
#$t7:		lista+i
	.data
	.eqv SIZE, 10
	.eqv FALSE, 0
	.eqv TRUE, 1
	.eqv print_int10, 1
	.eqv read_int, 5
	.eqv print_string, 4
lista:	.space 40
str1:	.asciiz "Introduza um número: "
str2:	.asciiz "; "
str3:	.asciiz "Conteúdo do Array Ordenado\n"
	.text
	.globl main
	
main:	la  $t0, lista			#$t0(p) = lista
	li  $t2, SIZE			#$t2 = SIZE
	sll $t2, $t2, 2			#$t2 = SIZE*4
	addu $t2, $t2, $t0		#$t2 = lista+SIZE
					#
					#
	#------------------------------	#	
	#Ler e armazenar valores na lista
forr:	bge $t0, $t2, do		#for(p=lista;p<lista+SIZE;
	addiu $t0, $t0, 4		#		p++){
					#	
	lw  $t1, 0($t0)			#	$t1 = *P;
					#
	la  $a0, str1			#
	li  $v0, print_string		#	print_string(str1);
	syscall				#
					#
	li  $v0, read_int		#
	syscall				#	read_int();
	sw  $v0, 0($t0)			#	*p = read_int();
	j forr				#}
					#
					#
	#------------------------------	#	
	#Ordenar lista			#do{
	la  $t6, lista			#
do:	li  $t4, FALSE			#	houve_troca = FALSE	
	li  $t5, 0			# 	i($t5) = 0;
					#
while:	bge $t5, SIZE, endw		#	while(i < SIZE-1){
	la  $t6, lista			#		$t6 = lista
	sll $t7, $t5, 2			#		$t7 = i*4;
	addu $t7, $t7, $t6		#		$t7 = &lista[i];
	lw  $t8, 0($t7)			#		$t8 = lista[i];
	lw  $t9, 4($t7)			#		$t9 = lista[i+1];
					#
if:	ble $t8, $t9, endif		#		if(lista[i] > lista[i+1]{
	sw  $t8, 4($t7)			#			lista[i+1]=$t8;
	sw  $t9, 0($t7)			#			lista[i] = $t9;
	li  $t4, TRUE			#			houve_troca = TRUE;
					#
endif:	addi $t5, $t5, 1		#		}i++;
	j while				#	}
					#
endw: 	beq $t4, TRUE, do		#}while(houve_troca == TRUE)
					#
	#------------------------------	#
	#Imprimir os valores da lista	#
	la  $t0, lista			#$t0(p) = lista
	li  $t2, SIZE			#$t2 = SIZE
	sll $t2, $t2, 2			#$t2 = SIZE*4
	addu $t2, $t2, $t0		#$t2 = lista+SIZE
					#
	la  $a0, str3			#
	li  $v0, print_string		#	print_string(str3);
	syscall				#
					#
fori:	bge $t0, $t2, endfri		#for(p=lista;p<lista+SIZE;
	addiu $t0, $t0, 4		#		p++){
					#
	lw $t1, 0($t0)			#	$t1 = *p;
					#
	or  $a0, $0, $t1		#
	li  $v0, print_int10		#	print_int(*p);
	syscall				#
					#
	la  $a0, str2			#
	li  $v0, print_string		#	print_string(str2);
	syscall				#
	j fori				#}
					#
endfri:	jr $ra				#fim do programa
	
