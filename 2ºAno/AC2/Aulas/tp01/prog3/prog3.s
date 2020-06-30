#Mapa de registos
#c = $t0
		.equ inkey, 1
		.equ getChar, 2
		.equ putChar, 3

		.data
		.text
		.globl main
	
main:	
do:		
while:	li  $v0, inkey
		syscall
		or  $t0, $v0, $0
		beq $t0, $0, while
		
		beq $t0, '\n', endif
		or  $a0, $t0, $0
		li  $v0, putChar
		syscall
		
endif:	bne $t0, '\n', do
		
		li  $v0, 0
		jr  $ra
