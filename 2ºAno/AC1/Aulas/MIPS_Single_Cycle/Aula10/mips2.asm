	.data
	.text
	.globl main
	
main:	addi $3, $0, 0x1F
	addi $4, $0, 0x24
	add  $4, $3, $4
	sw   $4, 4($0)
	lw   $5, -63($4)
	nop