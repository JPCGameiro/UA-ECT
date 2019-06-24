import java.util.Scanner;
public class lista
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int n, i=0;
		double media, soma=0;
		
		System.out.printf("Insira uma lista de números (termina com o 0): ");
		do
		{
			n=ler.nextInt();
			soma=soma+n;
			i++;
			
		}
		while(n!=0);
		media=soma/(i-1);
		System.out.printf("Média = %2.1f\nSoma = %2.1f", media, soma);
	}
}
