import java.util.Scanner;
public class limiteretangulo
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int largura, altura, linha=0, coluna=0;


		System.out.printf("Digite um número inteiro positivo(largura): ");
		largura=ler.nextInt();		
		System.out.printf("Digite outro número inteiro positivo(altura): ");
		altura=ler.nextInt();
		
		for(linha=0;linha<altura;linha++)
		{
			for(coluna=0;coluna<largura;coluna++)
			{
				if(linha==0 || linha==(altura-1))
					System.out.print("*");
				else 
				{
					if(coluna==0 || coluna==(largura-1))
						System.out.print("*");
					else
						System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
			
	}		

}
