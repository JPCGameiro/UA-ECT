package Files;

import java.util.Scanner;
public class similar1
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int c,d;
		
		System.out.print("Insert a number: ");
		c = ler.nextInt();
		System.out.print("Insert a number: ");
		d = ler.nextInt();
		
		System.out.println(c+" + "+d+" = "+(d+c)); 
	}
}
