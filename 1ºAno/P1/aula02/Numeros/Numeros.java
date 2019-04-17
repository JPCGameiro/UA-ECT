import java.util.Scanner;
public class Numeros
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		double num1, num2;
		
		System.out.printf("Digite um número : ");
		num1=sc.nextDouble();
		System.out.printf("Digite outro número : ");
		num2=sc.nextDouble();
		
		if (num1<num2)
		{
			System.out.printf("O número %3.2f  é menor que %3.2f ", num1, num2);
		}
		else if (num2<num1)
		{
			System.out.printf("O número %3.2f é maior que %3.2f ", num1, num2);
		}
		else if (num2==num1)
		{
			System.out.printf("O número %3.2f é igual a %3.2f ", num1, num2);
		}
	}
}
	
