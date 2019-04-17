import java.util.Scanner;
public class somanumerospares
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner (System.in);
		int numero, n=0; 
		int i=0, resultado=0;
		
		System.out.printf("Digite um número positivo menor ou igual a 100 : ");
		numero=sc.nextInt();
		
		if (numero<0 | numero>100)
		{
			System.out.printf("Número Inválido");
		}
		else
		{
			for(i=1;i<=numero;i++)
			{
				n=n+2;
				resultado +=n;
				System.out.printf("%d\n",n);
			}
			System.out.printf("A soma destes números é %d", resultado);
				
		         
			}
		}
	}

