import java.util.Scanner;
public class numerosreais
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double numero=1, multiplicação=1;
		
		System.out.printf("Digite uma lista de números (termina com o 0) : ");
		while(numero!=0)
		{
			multiplicação = multiplicação*numero;
			numero=ler.nextDouble();
		}
		System.out.printf("Produto = %f", multiplicação);
	}
}
			
		
		
		
