.global main
main:
	  addi     r2,r0,2     	; r2 = 2
	  addi     r3,r0,3     	; r3 = 3
	  addi     r5,r0,5     	; r5 = 5
	  addi     r7,r0,7     	; r7 = 7
	  addi     r9,r0,9     	; r9 = 9
	  addi     r11,r0,11   	; r11 = 11

	  add      r1,r2,r3	; r1 = 5
          sub      r4,r1,r5	; r4 = 0
          and      r6,r1,r7	; r6 = 5
          or       r8,r1,r9	; r8 = D
          xor      r10,r1,r11	; r10 = E

	  trap     0           ; end of program
