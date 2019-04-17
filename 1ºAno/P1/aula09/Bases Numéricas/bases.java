import java.util.Scanner;
public class bases
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int base, num;
		
		System.out.printf("Insira um número: ");
		num=ler.nextInt();
		System.out.printf("Insira a base: ");
		base=ler.nextInt();		
		System.out.print(numToBase(num,base));
	}
	
	public static String numToBase(int num, int base)
	{
		String result="";
		int f;
		
		do
		{
			f=num%base;
			result=result+f;
		}
		while(int(num/base)!=0);
		return result;
	}
}
			
		
		
