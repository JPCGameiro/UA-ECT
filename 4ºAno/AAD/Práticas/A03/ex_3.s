	.data
	.text
arr:	.word 1, 2, 3, 4, 5
	.global main

main:	addi r1, r0, arr	; r1 = &arr[0]
	addi r2, r0, 5		; r2 = 5 (array size)
	addi r3, r0, 0		; r3(sum) = 0 

for:	lw   r5, 0(r1)		; r5 = arr[0]
	add  r3, r3, r5		; sum += r5	
	subi r2, r2, 1		; r2(i)--
	addi r1, r1, 4		; r1 = &arr[i]
	bnez r2, for		;


	trap 0
