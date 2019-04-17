import java.util.Scanner;
public class imparesintervalo
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int a, b, i, resto;
		
		System.out.printf("Digite um número positivo : ");
		a=ler.nextInt();
		if(a<0)
		{
			System.out.printf("Número Inválido");
			System.exit(0);
		}
		
		System.out.printf("Digite outro número maior que o primeiro : ");
		b=ler.nextInt();
		if(b<a)
		{
			System.out.printf("Número inválido");
			System.exit(0);
		}
		i=a;
		do
		{
			resto=i%2;
			if(resto!=0)
			{
				System.out.printf(" %d " ,i);
			}
			i++;
		}
		while(i<=b);
			
	}
}
		
