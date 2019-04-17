import java.util.Scanner;
public class P1
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int i=0, n;
		
		System.out.printf("Digite um número : ");
		n=ler.nextInt();
		
		if(n<0)
		{
			System.out.printf("Número inválido");
		}
		else		
		{
			for (i=1; i<=n; i++)
		{
			System.out.printf("P1 é fixe\n");
		}
	}
		  
		  
	}
}
