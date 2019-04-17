import java.util.Scanner;
public class pontos
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int i, u ,largura, altura;
		
		System.out.printf("Digite a largura : ");
		largura=ler.nextInt();
		System.out.printf("Digite a altura : ");
		altura=ler.nextInt();
		
		for(i=1;i<=altura;i++)
		{
			for(u=1;u<=(largura-1);u++)
			{
				System.out.printf("*");
			}
			System.out.printf("*\n");
		}
	}
}
		
	
