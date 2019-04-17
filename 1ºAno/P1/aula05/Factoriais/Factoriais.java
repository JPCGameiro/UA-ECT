import java.util.Scanner;
public class Factoriais
{
	public static void main (String[] args)
	{
		Fact(1);		
	}
	public static int Fact(int x)
	{
		Scanner fdp = new Scanner(System.in);
		System.out.printf("Digite um número entre 1 e 10: ");
		x=fdp.nextInt();
		if(x<1 | x>10)
		{
			System.out.printf("Número Inválido");
			System.exit(0);
		}
		int fact=1;
		int i=1, u=1;
		for(i=1;i<=x;i++)
		{
			fact=fact*i;
			System.out.printf("%d! = %d\n",i, fact);
		}
		return fact;
	}
}
		
		
			
