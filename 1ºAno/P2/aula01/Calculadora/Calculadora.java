import java.util.Scanner;
public class Calculadora
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner (System.in);
		double num1, num2, result=0;
		String operation;
		
		System.out.printf("Operação? ");
		num1=sc.nextDouble();
		operation=sc.next();
		num2=sc.nextDouble();
		
		if(operation.equals("+"))
		{
			result=num1+num2;
			System.out.printf(num1 + " + " + num2 + " = " + result);
		}
		else if(operation.equals("-"))
		{
			result=num1-num2;
			System.out.printf(num1 + " - " + num2 + " = " + result);
		}
		else if(operation.equals("*"))
		{
			result=num1*num2;
			System.out.printf(num1 + " * " + num2 + " = " + result);
		}
		else if(operation.equals("/"))
		{
			
			result=num1/num2;
			System.out.printf(num1 + " / " + num2 + " = " + result);
		}
		else
		{
			System.err.println("Operação Inválida");
		}
	}
}
		
