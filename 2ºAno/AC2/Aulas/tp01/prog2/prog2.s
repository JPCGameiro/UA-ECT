#	int main(void) {
#		char c;
#
#		do{
#			c = getChar();
#			if(c != '\n')
#				putChar(c);
#		}while(c!='\n');
#		return 0;
#	}

	.equ	getChar, 2
	.equ	putChar, 3

	
	.data
	.text
	.globl main
	
main:
do:	li  $v0, getChar
	syscall
	or  $t0, $v0, $0
	
	beq $t0, '\n', endif
	or  $a0, $t0, $0
	li  $v0, putChar
	syscall

endif:
	bne $t0, '\n', do
	li  $v0, 0
	jr  $ra
