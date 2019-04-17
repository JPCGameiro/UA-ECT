import java.util.Scanner;
public class Numerospares
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double numero, resto;
		
		System.out.printf("Digite um número : ");
		numero=ler.nextDouble();
		
		resto = numero%2;
		
		if (resto == 0)
		{
			System.out.printf("O número digitado é par");
		}
		else
		{
			System.out.printf("O número digitado é ímpar");
		}
	}
}
		
		
