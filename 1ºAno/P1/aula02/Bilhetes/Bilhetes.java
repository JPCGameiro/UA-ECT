import java.util.Scanner;
public class Bilhetes
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		double idade;
		
		System.out.printf("Digite a idade : ");
		idade=ler.nextDouble();
		
		if (idade<6) 
		{
			System.out.printf("Isento de pagamento");
		}
		else if(idade>=6 && idade<=12)
		{
			System.out.printf("Bilhete de criança");
		}
		else if (idade>=13 && idade<=65)
		{
			System.out.printf("Bilhete normal");
		}
		else if (idade>65)
		{
			System.out.printf("Bilhete 3ª idade");
		}
	}
}
