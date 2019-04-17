import java.util.Scanner;
public class tabuada
{
	public static void main (String[] args)
	{
		Tabuada(1);
	
	}
	public static int Tabuada(int x)
	{
		Scanner ler = new Scanner(System.in);
		int i=1;
		
		System.out.printf("Digite um número entre 1 e 100: ");
		x=ler.nextInt();
		
		if(x<1 | x>100)
		{
			System.out.printf("Invalid Number");
			System.exit(0);
		}
		else
		{
			System.out.printf("--------------------\n");
			System.out.printf("|   Tabuada do %d   |\n", x);
			System.out.printf("--------------------\n");
			for(i=0; i<=10; i++)
			{
				if(i==1 | i==0 | i==2 |i==3)
				{
					System.out.printf("|  %d X %d  |  %d     |\n", i, x, x*i);
				}
									
				System.out.printf("|  %d X %d  |  %d    |\n", i, x, x*i);
			}
						
		}
		return x;		
	}
}

		
			
