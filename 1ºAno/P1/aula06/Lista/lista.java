import java.util.Scanner;
public class lista
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner (System.in);
		int i=0, n, x=1, f=0;
		int a[] = new int[11];
		System.out.printf("Digite 10 números inteiros: ");
		do
		{
			i++;
			a[i]=sc.nextInt();

		}
		while(a[i]>=0 && i<=9);
		
		System.out.printf("Dos digitados escolha um : ");
		n=sc.nextInt();
		for(x=0;x<=10;x++)
		{
			if(a[x]==n)
			{
				f++;
			}
		}
		System.out.printf("%d foi introduzido %d vezes", n, f);
	}
}
		
