#Mapa de Registos
# num - $t0
# p -	$t1
# *p -	$t2
	.data
	.eqv SIZE, 20
	.eqv read_string, 8
	.eqv print_int10, 1
str:	.space SIZE
	.text
	.globl main

main:	la  $a0, str		#$a0 = &str[0] ($a0 = endereço da posição inicial do array str[])
	li  $a1, SIZE		#$a1 = SIZE
	li  $v0, read_string	#read_string(str, SIZE)
	syscall
	
	la $t1, str		#p = str

while:	lb  $t2, 0($t1)			
	beq $t2, '\0', endw	#while(*p != '\0') {
	
	blt $t2, '0', endif	#if(*p < 0 && *p > 9) { 	
	bgt $t2, '9', endif
	addi $t0, $t0, 1
	
endif: 	addi $t1, $t1, 1	#p++;
   	j   while		#}
	
endw: 	or  $a0, $0, $t0	#print_int10(num)
	ori $v0, $0, print_int10
	syscall
	
	jr $ra			#fim do programa