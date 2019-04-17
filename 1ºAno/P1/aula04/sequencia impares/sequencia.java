import java.util.Scanner;
public class sequencia
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int n,i=0,l=0,resto;
		
		System.out.printf("Digite uma lista de números inteiros maiores que 0 (termina com 0) : ");
		n=ler.nextInt();
		do
		{
			resto = n%2;
			if(resto==0)
			{
				l++;
			}
			else if (resto!=0)
			{
				i++;
			}
			n=ler.nextInt();
			
		}
		while(n!=0);
		
		if(i<l)
		{
			System.out.printf("A sequência de números não é exclusivamente constituída por números ímpares");
		}
		else if(l<i)
		{
			System.out.printf("A sequência de números é exclusivamente constituída por números ímpares");
		}
	}
}
			


			
			
