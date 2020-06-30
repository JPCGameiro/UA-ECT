#Mapa de Registos:
#$s0: s
#$s1: cnt
#$s2: c
	
	.equ putChar, 3
	.equ printInt, 6
	.equ inkey, 1
	 
	.data
	.text
	.globl main
	
main:	addiu $sp, $sp, -16		#Reservar espaço na stack
	sw  $ra, 0($sp)			#Salvaguardar valores dos registos	
	sw  $s0, 4($sp)
	sw  $s1, 8($sp)
	sw  $s2, 12($sp)
	
	li  $s0, 0			#s=0;
	li  $s1, 0			#cnt=0; 

do:	li  $a0, '\r'			#do{
	li  $v0, putChar		#	putChar('\n');
	syscall				#
	
	move $a0, $s1			#
	li  $a1, 0x0003000A		#
	li  $v0, printInt		#	printInt(cnt, 0x0003000A);
	syscall				#
	
	li  $a0, '\t'			#
	li  $v0, putChar		#	putChar('\t');
	syscall				#
	
	move $a0, $s1			#	
	li  $a1, 0x00080002		#	printInt(cnt, 0x0008002);
	li  $v0, printInt		#
	syscall				#
	
	li  $a0, 5			#
	jal wait			#	wait(5)
	
	li  $v0, inkey			#
	syscall				#
	move $s2, $v0			#	c = inkey();

ifr:	bne $s2, 'r', ifs		#	if(c == 'r')
	li  $s1, 0			#		cnt = 0;

ifs:	bne $s2, 's', ifm		#	if(c == 's')
	j   do		#				cnt = cnt;

ifm:	bne $s2, '-', ifp		#	if(c == '-')
	li  $s0, 1			#		s = 1;
	
ifp:	bne $s2, '+', if0		#	if(c == '+')
	move $s0, $0		        #		s = 0;

if0:	bne $s0, $0, else		#	if(s == 0)
	addi $s1, $s1, 1		#		
	andi $s1, $s1, 0xFF		#		cnt = (cnt+1) & 0xFF;

else:	sub $s1, $s1, 1			#	else
	andi $s1, $s1, 0xFF		#		cnt = (cnt-1) & 0xFF;
	bne $s2, 'q', do		#}

	lw  $ra, 0($sp)
	lw  $s0, 4($sp)
	lw  $s1, 8($sp)
	lw  $s2, 12($sp)		#Devolver os valores aos registos
	addiu $sp, $sp, 16		#Libertar espaço na Stack
	li  $v0, 0			#return 0;
	jr  $ra				#terminar
	
	
#Mapa de Registos - wait(int ts)
#$a0:	ts
#$t0:	i
wait:	li   $t0, 0			#i = 0;
for:	mul  $a0, $a0, 515000		#for(i<515000*ts){
	bge  $t0, $a0, endfor		#
	addi $t0, $t0, 1		#	i++
endfor:	jr   $ra			#}	
