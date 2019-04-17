import java.util.Scanner;
public class numerosinteiros
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int i=0, num;
		
		System.out.printf("Digite um número inteiro positivo : ");
		num=ler.nextInt();
		while(num>0)
		{
			System.out.printf("Digite um número inteiro positivo : ");
			num=ler.nextInt();
			i++;
		}
		System.out.printf("Digitou %d números positivos", i);
	}
}
