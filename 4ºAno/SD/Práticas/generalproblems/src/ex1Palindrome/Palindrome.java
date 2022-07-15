package ex1Palindrome;

import java.util.Scanner;
/**
 * @author joao
 */
public class Palindrome {

	public static void main(String[] args) {
		String word;
		//Scanner to read the string
		Scanner read = new Scanner(System.in);
		
		System.out.print("Insert a word:");
		word = read.next();
		
		//Close the Scanner
		read.close();
		
		//Data structures to store string characters
		int size = word.length();
		FifoGeneric fifo = new FifoGeneric(size);
		StackGeneric stack = new StackGeneric(size);
		
		//Insert characters in the fifo and in the stack
		for(int i = 0; i < size; i++) {
			fifo.push(word.charAt(i));
			stack.push(word.charAt(i));
		}
		
		//Check if all characteres are the same
		while(!fifo.isEmpty()) {
			if(fifo.pop() != stack.pop()) {
				System.out.println("Word is not a palindrome!");
				System.exit(0);
			}
		}
		System.out.println("Word is a palindrome!");		
	}

}
