#Mapa de registos
# num: 	$t0
# i:	$t1
#str:	$t2
#str+1:	$t3
#str[i]:$t4
	.data
	.eqv SIZE,20
	.eqv read_string, 8
	.eqv print_int10, 1
str:	.space SIZE
	.text
	.globl main
	
main:	la  $a0, str		#$a0 = &str[0] ($a0 = endereço da posição inicial do array str[])
	li  $a1, SIZE		#$a1 = SIZE
	li  $v0, read_string	#read_string(str, SIZE)
	syscall
	
	li $t0, 0		#num($t0) = 0
	li $t1, 1		#i($t1) = 0
	
	la  $t2, str		#$t2 = str ou &str[0]
while:	add $t3, $t2, $t1	#$t3 = str+i ou &str[i]
	lb  $t4, 0($t3)		#$t4 = str[i]
	
	beq $t4, '\0', endw	#if($t4(str[i]) == '\0') j endw

if:	blt $t4, '0', endif	#if(str[i] < 0) || str(i) > 9) j endif
	bgt $t4, '9', endif
	addi $t0, $t0, 1	#num = num+1
	
endif:	addi $t1, $t1, 1	#i = i+1
	j  while

endw:	or  $a0, $0, $t0	#print_int10(num)
	ori $v0, $0, print_int10
	syscall
	
	jr  $ra			#fim do programa
