#Mapa de Registos
#$t0:	p
#$t1:	*p
#$t2:	lista+SIZE
#$t3:	---------------------
#$t4:	houve_troca
#$t5:	*(p+1)
#$t6:	pUltimo
	.data
	.eqv SIZE, 10
	.eqv FALSE, 0
	.eqv TRUE, 1
	.eqv print_int10, 1
	.eqv print_string, 4
	.eqv read_int, 5
str1:	.asciiz "Introduza um número: "
str2:	.asciiz "Conteudo do Array\n"
str3: 	.asciiz "; "
	.align 2
lista:	.space 40
	.text
	.globl main
	
	#-------------Ler valores e armazenar
main:	la  $t0, lista			#$t0(p) = lista
	li  $t2, SIZE			#$t2 = SIZE
	sll $t2, $t2, 2			#$t2 = SIZE*4
	add $t2, $t2, $t0		#$t2 = lista+SIZE
	
forr:	bge $t0, $t2, endrr		#for(p=lista; p<lista+SIZE;
	addi $t0, $t0, 4		#			p++){

	la  $a0, str1			#
	li  $v0, print_string		#print_string(str1);
	syscall				#
	
	li  $v0, read_int		#
	syscall				#	$v0 = read_int();
	sw  $v0, 0($t0)			#	Guardar em $t0 o conteúdo de $v0
	j forr				#}

	#-------------Ordenar o Array
endrr:	la  $t0, lista			#p = &lista;
	li  $t6, SIZE			#$t6 = SIZE
	sll $t6, $t6, 2			#$t6 = (SIZE)*4
	addu $t6, $t0, $t6		#pUltimo = lista + (SIZE);

					#do{
do:	li  $t4, FALSE			#	houve_troca = false
	la  $t0, lista
for:	bge $t0, $t6, endf		#	for(p=lista;p<pUltimo;...){
	
	lw  $t1, 0($t0)			# 		*p = cópia do conteúdo de *p
	lw  $t5, 4($t0)			#		*(p+1) = cópia do conteúdo de *(p+1)
	
if:	ble $t1, $t5, endif		#		if(*p > *(p+1){
	sw  $t1, 4($t0)			#			*p = cópia *(p+1)
	sw  $t5, 0($t0)			#			*(p+1) = cópia *p
	li  $t4, TRUE			#			houve_troca = TRUE
	
endif:	addi $t0, $t0, 4		#		} p++;
	j   for				#	}

endf:	beq $t4, TRUE, do		#} while(houveTROCA == TRUE)


	#-------------Imprimir o conteúdo do array
	la  $t0, lista			#p = lista
	li  $t2, SIZE			#$t2 = SIZE
	sll $t2, $t2, 2			#$t2 = SIZE*4
	add $t2, $t0, $t2		#$t2 = lista+SIZE

	la  $a0, str2			#
	li  $v0, print_string		#print_string(str3);
	syscall				#
	
fori:	bge $t0, $t2, endri		#for(p=lista;p<lista+SIZE;p++){
	addi $t0, $t0, 4		#
	
	lw  $t1, 0($t0)			#	*p = $t1
	or  $a0, $0, $t1		#
	li  $v0, print_int10		#	print_int(*p);
	syscall				#
	
	la  $a0, str3			#
	li  $v0, print_string		#	print_string(str3);
	syscall				#
	j  fori				#}

endri:	jr  $ra				#fim do programa
	
			
	
