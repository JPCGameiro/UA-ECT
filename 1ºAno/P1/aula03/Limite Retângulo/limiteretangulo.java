import java.util.Scanner;
public class limiteretangulo
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int largura, altura, i,u ;


		System.out.printf("Digite um número inteiro positivo(largura): ");
		largura=ler.nextInt();		
		System.out.printf("Digite outro número inteiro positivo(altura): ");
		altura=ler.nextInt();
		
		if(largura>1 && altura>1) 
		{
			for(i=1;i<=altura;i++)
			{
				for(u=1;u<=(largura);u++)
				{
						if(u==1)
						{
							System.out.printf("*");
						}
						else if(u>1 && u<(largura))
						{
							System.out.printf(" ");
						}
						else if(u==(largura))
						{
							System.out.printf("*");
						}
					}
				if(i==1)
				{
					System.out.printf("*\n");
				}
				else if(i>1 && i<altura)
				{
					System.out.printf(" \n");
				}
				else if(i==altura)
				{
					System.out.printf("*\n");
				}
			}
			
		}
			
	}		

}



