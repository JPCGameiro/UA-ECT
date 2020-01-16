	.data
	.text
	.globl main

main:	addi $2, $0, -7
	addi $3, $0, 0x1A
	add  $4, $2, $3
	sub  $5, $2, $3
	and  $6, $2, $3
	or   $7, $2, $3
	nor  $8, $2, $3
	xor  $9, $2, $3
	slt  $10, $2, $3
	slt  $11, $3, $2
	slti $12, $2, 26
	slti $13, $3, -4
	nop