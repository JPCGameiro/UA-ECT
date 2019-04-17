import java.util.Scanner;
public class sequencia
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int opc;
		int a[]=new int[10];
		System.out.printf("Análise de uma sequência\n");		
		System.out.printf("1-Ler a sequência\n");
		System.out.printf("2-Escrever a sequência\n");
		System.out.printf("3-Calcular o máximo da sequência\n");
		System.out.printf("4-Calcular o mínimo da sequência\n");
		System.out.printf("5-Calcular a média da sequência\n");
		System.out.printf("6-Calcular o número de números pares\n");
		System.out.printf("7-Calcular o número de números ímpares\n");
		System.out.printf("10-Terminar o programa\n");
		
		do
		{
			System.out.printf("\nOpção-> ");
			opc=ler.nextInt();
			
			switch(opc)
			{
				case 1:
				a = Ler();
				break;
				
				case 2:
				Escrever(a);
				break;
				
				case 3:
				Max(a);
				break;
				
				case 4:
				Min(a);
				break;
				
				case 5:
				Media(a);
				break;
				
				case 6:
				Pares(a);
				break;
				
				case 7:
				Impares(a);
				break;
				
				case 10:
				System.out.printf("Até à próxima");
				break;
				
				default:
				System.out.printf("Opção inválida");
				break;
			}
				
		
		}while(opc!=10);
	}
	
	public static int[] Ler()
	{
		Scanner ler = new Scanner (System.in);
		int a[] = new int[10];
		int i=0;
		
		System.out.printf("Digite uma sequência de 10 números: ");
		for(i=0;i<=9;i++)
		{
			a[i]=ler.nextInt();
		}
		return a;
	}
	
	public static void Escrever(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0;
		
		for(i=0;i<=9;i++)
		{
			System.out.print(a[i] + " ");
		}
	}
	
	public static void Max(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, max=a[1];
		
		for(i=0;i<=9;i++)
		{
			if(a[i]>max)
			{
				max=a[i];
			}
		}
		System.out.printf("O máximo da sequência é %d", max);
	}
	
	public static void Min(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, min=a[1];
		
		for(i=0;i<=9;i++)
		{
			if(a[i]<min)
			{
				min=a[i];
			}
		}
		System.out.printf("O minímo da sequência é %d", min);
	}
	
	public static void Media(int a[])
	{
		Scanner ler = new Scanner (System.in);
		int i=0;
		double media, n=0, soma=0;
		
		for(i=0;i<=9;i++)
		{
			soma=a[i]+soma;
			n++;
		}
		media=soma/n;
		System.out.printf("A média da sequência é %2.2f", media);
	}
	
	public static void Pares(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, par=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]%2==0)
			{
				par++;
			}
		}
		System.out.printf("A sequência tem %d números pares", par);
	}
	
	public static void Impares(int a[])
	{
		int i=0, imp=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]%2!=0)
			{
				imp++;
			}
		}
		System.out.printf("A sequência tem %d números ímpares", imp);
	}	
}
	

		
