import java.util.Scanner;
public class Tabuada
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner (System.in);
		int num, i;
		
		System.out.printf("Digite um número entre 0 e 100 : ");
		num = sc.nextInt();
		
		if(num<=0 | num>=100)
		{
			System.out.printf("Número Inválido");
		}
		else
		{
			System.out.printf("------------------------\n");
			System.out.printf("|     Tabuada do %d     |\n",num);
			System.out.printf("------------------------\n");
			for(i=0; i<=10; i++)
			{
				
				System.out.printf("    %d X %d= %d   \n ", i, num, num*i);
			}
		
		}
	}
}
		
