	.data
	.text
	.globl main

main:	addi $3, $0, 0x8F
	addi $4, $0, 0x36
	slt  $1, $3, $4
	beq  $1, $0, else
	sw   $3, 0x80($0)
	j    endif
else:	sw   $4, 0x80($0)
endif:	j    endif