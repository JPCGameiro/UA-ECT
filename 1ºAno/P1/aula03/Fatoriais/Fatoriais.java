import java.util.Scanner;
public class Fatoriais
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int n, fact=1, i;
		
		System.out.printf("Digite um número entre 1 e 10 inclusivé : ");
		n=ler.nextInt();
		
		if(n<1 | n>10)
		{
			System.out.printf("Número Inválido");
		}
		else
		{
			for (i=1;i<=n;i++)
			{
				fact=fact*i;
				System.out.printf("%2d ! = %2d \n", i, fact);
			}
		}
	}
}

				
