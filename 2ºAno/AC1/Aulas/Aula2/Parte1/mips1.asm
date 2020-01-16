	.data
	.text
	.globl main
main:	ori $t0, $0, 0x0614	#armazenamento de um valor em $t0
	ori $t1, $0, 0xA3E4	#armazenamento de um valor em $t1
	ori $t6, $0, 0xFFFF	#armazenamento de um valor em $t6
	and $t2, $t0, $t1	#$t2 = $t0 &  $t1 (and bit a bit)
	or  $t3, $t0, $t1	#$t3 = $t0 |  $t1 (or  bit a bit)
	nor $t4, $t0, $t1       #$t4 = $t0 ~| $t1 (nor bit a bit)
	xor $t5, $t0, $t1	#$t5 = $t0 ^  $t1 (xor bit a bit)
	xor $t7, $t0, $t6       #$t6 = ~$t0       (not bit a bit)
	jr  $ra			#fim do programa
