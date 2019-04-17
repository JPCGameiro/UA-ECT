import java.util.Scanner;
public class Dolar
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double euro, taxa;
		double dolar;
	
		System.out.printf("Digite o número de euros : ");
		euro = ler.nextDouble();
		System.out.printf("Digite a taxa de conversão pretendida: ");
		taxa = ler.nextDouble();
		
		dolar = euro*taxa;
		
		System.out.printf("%5.2f euros correspondem a %5.2f dólares", euro,dolar);
	}
}
		
