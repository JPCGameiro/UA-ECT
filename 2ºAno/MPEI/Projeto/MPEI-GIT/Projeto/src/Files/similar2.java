package Files;

import java.util.Scanner;
public class similar2
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int d,e;
		
		System.out.print("Insert a number: ");
		e = ler.nextInt();
		System.out.print("Insert a number: ");
		d = ler.nextInt();
		
		System.out.println(e+" + "+d+" = "+(d+e)); 
	}
}