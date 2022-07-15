package ex1Palindrome;

import java.util.Arrays;

/**
 * Stack data structure
 * @author joao
 *
 */
public class StackGeneric {
	
	/**
	 * storage array
	 */
	private Object[] stack;
	/**
	 * stack pointer
	 */
	private int ptr;
	/**
	 * size of the stack
	 */
	private int size;

	/**
	 * Stack class constructor
	 * @param size of the stack
	 */
	public StackGeneric(int size) {
		this.size = size;
		this.stack = new Object[size];
		this.ptr = 0;
	}
	
	/**
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return ptr == 0;
	}
	
	/**
	 * @return true is stack is full, false otherwise
	 */
	public boolean isFull() {
		return ptr == size;
	}
	
	/**
	 * @param c character to be inserted at the top of the stack
	 */
	public void push(Object c) {
		if(isFull()) {
			System.out.println("ERROR: Stack is full");
			return;
		}
		stack[ptr] = c;
		ptr++;
	}
	
	/**
	 * @return character at the top of the stack
	 */
	public Object pop() {
		if(isEmpty()) {
			System.out.println("ERROR: Stack is empty");
			return 0;
		}
		ptr--;
		return stack[ptr];
	}

	@Override
	public String toString() {
		return "Stack [stack=" + Arrays.toString(stack) + "]";
	}
	
	
}
