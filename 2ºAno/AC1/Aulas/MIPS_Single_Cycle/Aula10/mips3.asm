	.data
	.text
	.globl main

main:	addi $6, $0, 0
	addi $5, $0, 4
	add  $7, $0, $0
for:	slt  $1, $0, $5
	beq  $1, $0, endf
	add  $8, $5, $5
	sw   $8, 0($6)
	add  $7, $7, $8
	addi $6, $6, 4
	addi $5, $5, -1
	j    for
endf:	sw   $7, 0($6)
w1:	beq  $0, $0, w1
	nop