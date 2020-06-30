	.equ SFR_BASE_HI, 0xBF88	#16 MSbits of SFR area
	.equ TRISE, 0x6100		#TRISE address is 0xBF886100
	.equ PORTE, 0x6110		#PORTE address is 0xBF886110
	.equ LATE,  0x6120		#LATE  address is 0xBF886120
	
	.equ TRISB, 0x6040		#TRISB address is 0xBF886040
	.equ PORTB, 0x6050		#PORTB address is 0xBF886050
	.equ LATB,  0x6060		#LATB  address is 0xBF886060
	
	.equ READ_CORE_TIMER, 11
	.equ RESET_CORE_TIMER, 12
	
	.data
	.text
	.globl main

main:	addiu $sp, $sp, -20		#Reservar espaço na stack
	sw  $ra, 0($sp)			#
	sw  $s0, 4($sp)			#
	sw  $s1, 8($sp)			#Salvaguardar valores dos registos
	sw  $s2, 12($sp)		#
	sw  $s3, 16($sp)		#
	
	lui  $s0, SFR_BASE_HI		#
	lw   $s1, TRISE($s0)		#READ(read TRISE address)
	andi $s1, $s1, 0xFFF0		#MODIFY(bit0=bit1=bit2=bit3=0 Output)
	sw   $s1, TRISE($s0)		#WRITE(write TRISE register)
	
	lw   $s1, TRISB($s0)		#READ(read TRISB address)
	andi $s1, $s1, 0x000F		#MODIFY/bit0=bit1=bit2=bit3=1 Input)
	sw   $s1, TRISB($s0)		#WRITE(write TRISB register)
	
	li  $s2, 0x0			#	cnt = 0;
while:					#while(1){
	lw  $s1, PORTB($s0)		#	ler valores do porto de entrada
	andi $s1, $s1, 0x0004		#	extrair bit2 (RB2)
	
	bne  $s1, 0x0004, rigth		#	if(rb2==1)
	
	andi $s3, $s2, 0x8		#		extrair o bit mais significativo de cnt (bit3)
	sll  $s2, $s2, 1		#		cnt = cnt<<1;
	andi $s2, $s2, 0xFFFE		#		manter todos os bits excepto o que se pretende mudar
if1l:	bne $s3, 0x8, if0l		#		if(bit3 = 1)
	ori $s2, $s2, 0x0000		#			cnt[bit0]=0;
	j   endl			#		else
if0l:	ori $s2, $s2, 0x0001		#			cnt[bit0]=1
endl:	j   end				#	
					
rigth:					#	else if(rb2==0)
	andi $s3, $s2, 0x1		#		extrair o bit mais significativo de cnt
	srl  $s2, $s2, 1		#		cnt = cnt>>1;
	andi $s2, $s2, 0xFFF7		#		manter todos os bits excepto o que se pretende mudar
if1:	bne $s3, 0x1, if0		#		if(bit0 = 1)
	ori $s2, $s2, 0x0000		#			cnt[bit3]=0;
	j   end				#		else
if0:	ori $s2, $s2, 0x0008		#			cnt[bit3]=1
end:					#	
	lw  $s1, LATE($s0)		#	ler valores presentes noporto de saída
	andi $s1, $s1, 0xFFF0		#	"extrair" bits0,1,2,3
	or  $s1, $s1, $s2		#	colocar o valor de cnt nos bits0,1,2,3
	sw  $s1, LATE($s0)		#	escrever valores alterados no porto de saída
	li  $a0, 667			#	delay(667); frequencia de 1.5Hz
	jal delay			#
	j   while			#}

endw:
	lw  $ra, 0($sp)			#
	lw  $s0, 4($sp)			#
	lw  $s1, 8($sp)			#
	lw  $s2, 12($sp)		#Devolver valores aos registos
	lw  $s3, 16($sp)		#
	addiu $sp, $sp, 20		#
	jr   $ra
	
	
#Mapa de Registos delay
#$a0:	ms	
delay:						#
for:	ble $a0, 0, endf			#for(ms > 0){
	sub $a0, $a0, 1				#	ms--;
	li  $v0, RESET_CORE_TIMER		#	resetCoreTimer();
	syscall
whiled:	li  $v0, READ_CORE_TIMER		#	while(readCoreTimer()<k)
	syscall
	bge $v0, 20000, endwd			#
	j   whiled				#
endwd:	j   for					#}
endf:	jr  $ra					#fim da função
