import java.util.Scanner;
public class Temperatura
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		double temp1;
		double temp2;
		
		System.out.printf("Digite a temperatura em Celsius : ");
		temp1 = ler.nextFloat();
		
		temp2 = (temp1*1.8)+32;
		
		System.out.printf("%5.2fº Celsius equivalem a %5.2fº Farenheit ", temp1, temp2);
	}
} 
