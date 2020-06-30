#value: $t0

		.equ printStr, 8
		.equ readInt10, 5
		.equ printInt10, 7
		.equ printInt, 6
		
		.data
str0:	.asciiz "\n\nIntroduza um numero (sinal e módulo): "
str1:	.asciiz "\nValor lido em base 2: "
str2:	.asciiz "\nValor lido em base 16: "
str3:	.asciiz "\nValor lido em base 10(unsigned): "
str4:	.asciiz "\nValor lido em base 10(signed): "
		.text
		.globl main
		
main:	la  $a0, str0												
		li  $v0, printStr					#printStr(str0);
		syscall
		
		la  $v0, readInt10					#$t0 = readInt10();
		syscall
		or 	$t0, $v0, $0
		
		la  $a0, str1
		li  $v0, printStr					#printStr(str1);
		syscall 
		
		move $a0, $t0
		li  $a1, 2
		li  $v0, printInt					#printInt($t0, 2);
		syscall
		
		la  $a0, str2					
		li  $v0, printStr					#printStr(str2);
		syscall
		
		move $a0, $t0
		li  $a1, 16							#printInt($t0, 16);
		li  $v0, printInt
		syscall
		
		la  $a0, str3					
		li  $v0, printStr					#printStr(str3);
		syscall		
		
		move $a0, $t0			
		li  $a1, 10							#printInt($t0, 10);
		li  $v0, printInt				
		syscall
		
		la  $a0, str4
		li  $v0, printStr					#printStr(str4)
		syscall
		
		move $a0, $t0
		li  $v0, printInt10					#printInt10($t0);
		syscall
		
		j   main
		
		li  $v0, 0
		jr  $ra
		
		
	

	
