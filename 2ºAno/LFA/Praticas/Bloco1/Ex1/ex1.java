import java.util.Scanner;
import java.util.Stack;
import java.util.*;

/*
 * compile: javac ex1.java
 * run:		java ex1
 */
 
public class ex1
{
	public static void main(String[] args)
	{
		Scanner rd = new Scanner(System.in);
		Stack<String> stack = new Stack<String>();
		String op;
		double op0=0, op1=0, result=0;
		
		System.out.print("<number> <op> <number>: ");
		
		while(rd.hasNextLine() && stack.size() < 3) {
			if(rd.hasNext() && stack.size() < 3) {
				stack.push(rd.next());
			}
		}
		
		//Retirar da Stack o primeiro operando
		try {
			op1 = Double.parseDouble(stack.pop());
		}
		catch(NumberFormatException | InputMismatchException e) {
			System.err.println("ERROR: operands must be numbers");
			System.exit(0);
		}
		
		//Retirar da Stack a operação
		op = stack.pop();
		
		//Retirar da Stack o segundo operando
		try {
			op0 = Double.parseDouble(stack.pop());
		}
		catch(NumberFormatException | InputMismatchException e) {
			System.err.println("ERROR: operands must be numbers");
			System.exit(0);
		}
		
		//Realizar a operação
		switch(op) {
			case "+":
				result = op0+op1;
				break;
			
			case "-":
				result = op0-op1;
				break;
			
			case "/":
				result = op0/op1;
				break;
			
			case "*":
				result = op0*op1;
				break;
			
			default:
				System.err.println("ERROR: Invalid operation (use +,-,*,/)");
				System.exit(0);
				break;
		}

		System.out.println(op0+" "+op+" "+op1+ " = "+result);		
	}
}
