import java.util.Scanner;
public class inversa
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int n, i;
		System.out.printf("Digite quantos números quer digitar: ");
		n=ler.nextInt();
		int a[] = new int[n];
		System.out.printf("Digite a lista de números inteiros: ");
		
		for(i=0;i<=n-1;i++)
		{
			a[i] = ler.nextInt();
			
		}
		for(i=n-1;i>=0;i--)
		{
			System.out.printf("%d ", a[i]);
		}
	}
}

