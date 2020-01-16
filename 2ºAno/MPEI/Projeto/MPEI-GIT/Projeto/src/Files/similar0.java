package Files;

import java.util.Scanner;
public class similar0
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int a,b;
		
		System.out.print("Insert a number: ");
		a = ler.nextInt();
		System.out.print("Insert another number: ");
		b = ler.nextInt();
		
		System.out.println(a+" + "+b+" = "+(a+b)); 
	}
}
