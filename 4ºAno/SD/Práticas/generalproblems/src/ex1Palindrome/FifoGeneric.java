package ex1Palindrome;

import java.util.Arrays;

/**
 * Fifo data structure
 * @author joao
 */

public class FifoGeneric {

	/**
	 * Storage array
	 */
	private Object fifo[]; 
	/**
	 * Pointers to the first empty location and to the first occupied position
	 */
	private int ptIn, ptOut;
	/**
	 * Size of the fifo
	 */
	private int size;
	
	
	
	
	/**
	 * Fifo Class Constructor
	 * @param size of the fifo
	 */
	public FifoGeneric(int size) {
		this.size = size;
		this.fifo = new Object[size];
		this.ptIn = 0;
		this.ptOut = 0;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @return true if fifo is empty, false otherwise
	 */
	public boolean isEmpty() {
		return ptIn == ptOut;
	}
	
	/**
	 * 
	 * @return true if fifo is full, false otherwise
	 */
	public boolean isFull() {
		return (ptIn % size) == size;
	}
	
	/**
	 * 
	 * @param c character to be inserted in the fifo
	 */
	public void push(Object c) {
		if(isFull()) {
			System.out.println("ERROR: Fifo is full");
			return;
		}
		fifo[ptIn % size] = c;
		ptIn++;
	}
	
	/**
	 * 
	 * @return character at the head of the fifo
	 */
	public Object pop() {
		if(isEmpty()) {
			System.out.println("ERROR: Fifo is empty");
			return 0;
		}
		int i = ptOut;
		ptOut++;
		return fifo[i % size];
	}



	@Override
	public String toString() {
		return "Fifo [fifo=" + Arrays.toString(fifo) + "]";
	}

}