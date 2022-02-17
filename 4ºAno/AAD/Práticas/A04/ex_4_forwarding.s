; Arquitetura do programa
; r1 = address arr[i] 
; r2 = value arr[i]
; r3 = address arr[j]
; r4 = value arr[j]
; r5 = value size
; r6 = value size-1
; r7 = value i
; r8 = value j
; r9 = value tmp
; r10 = condição


	.data
arr:	.word 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
size:	.word 10
	.text
	.global main

main:	addi r5, r0, size		;
	lw r5, 0(r5)			;
	nop
	subi r6, r5, 1			; r6 = size - 1

	add  r7, r0, r0			; i = 0
	addi r8, r7, 1			; j = i+1

	addi r1, r0, arr		; r1 = &arr[i]
	addi r3, r3, arr		; r3 = &arr[j]
	addi r3, r3, 4			; r3 = &arr[i+1] = & arr[j]

for1:	slt r10, r7, r6			; for(; i < size - 1;)
	beqz r10, endf1			; {
	nop
	nop	
	
for2: 	slt r10, r8, r5			;	for(; j < size;)
	beqz r10, endf2			;	{
	nop
	nop
		
	lw r2, 0(r1)			;		r2 = arr[i]
	lw r4, 0(r3)			;		r4 = arr[j]
	nop
	nop

	slt r10, r2, r4			;		if(arr[i] < arr[j])
	beqz r10, endif			;
	nop
	nop

	add r9, r2, r0			;			tmp = arr[i]
	add r2, r4, r0			;			arr[i] = arr[j]
	add r4, r9, r0			;			arr[j] = tmp
	sw 0(r1), r2			;
	sw 0(r3), r4			;

endif:  addi r8, r8, 1			;		j++
	addi r3, r3, 4			;
	j  for2				;	}
	nop
	nop

endf2:	addi r7, r7, 1			; 	i++
	addi r1, r1, 4			;
	add r8, r7, r0			;
	add r3, r1, r0			;
	addi r3, r3, 4			;	 
	addi r8, r8, 1			;	j = i+1
	j  for1				; }
	nop
	nop

endf1:	trap 0
