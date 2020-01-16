	.data
str1:	.asciiz "Introduza 2 números\n"
str2:	.asciiz "A soma dos dois números é: "
	.eqv 	print_string, 4
	.eqv	read_int, 5
	.eqv	print_int10, 36
	
	.text
	.globl main
	
	#Impressão da String 1 no ecrã
main:	la  $a0, str1			
	ori $v0, $0, print_string	#$v0 = 4 (para a syscall)
	syscall				#print_string(str1)
	
	#leitura do valor a 
	ori $v0, $0, read_int		#$v0 = 5
	syscall				#valor lido é retornado em $v0 (a)
	or $t0, $v0, $0			#$t0=read_int()a
	
	#leitura do valor b
	ori $v0, $0, read_int		#$v0 = 5
	syscall				#valor lido é novamento retornado em $v0 (b)
	or  $t1, $v0, $0		#$t1=read_int()b
	
	#soma de a+b
	add $t2, $t1, $t0		#$t2 = $t1 + $t0 = b + a
	
	#Impressão da String 2 no ecrã
	la  $a0, str2			
	ori $v0, $0, print_string	#$v0 = 36 (para a syscall)
	syscall				#print_string(str2)
	
	#Impressão do valor final da soma no ecrã 
	or  $a0, $0, $t2		#armazenamento do resultado em $a0 para ser apresentado pela system call print_int10
	ori $v0, $0, print_int10	#$v0 = 36 (para syscall)
	syscall				#print_int10($t2)
	jr  $ra
	