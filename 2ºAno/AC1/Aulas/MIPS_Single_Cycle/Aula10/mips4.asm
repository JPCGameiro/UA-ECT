	.data
	.text
	.globl main
	
main:	addi $5, $0, 0
	addi $4, $0, 5

do:	addi $2, $0, 1
	addi $3, $0, 0
for:	slt  $1, $3, $4
	beq  $1, $0, endfor
	add  $6, $3, $3
	add  $6, $6, $6
	add  $6, $6, $5
	lw   $7, 0($6)
	lw   $8, 4($6)
	slt  $1, $8, $7
	beq  $1, $0, endif
	sw   $7, 4($6)
	sw   $8, 0($6)
	addi $2, $0, 0

endif:	addi $3, $3, 1
	j    for

endfor:	addi $4, $4, -1
	beq  $2, $0, do
	nop
	
w1:	beq  $0, $0, w1