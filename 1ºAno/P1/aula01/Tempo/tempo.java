import java.util.Scanner;
public class tempo 
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double tmp;
		double horas, minutos, segundos, resto1, resto2;
		
		System.out.printf("Digite o tempo em segundos : ");
		tmp = ler.nextDouble();
		
		horas = (int)(tmp/3600);
		resto1 = tmp - (horas * 3600);
		minutos = (int)(resto1/60);
		segundos = resto1 - (minutos*60);
		
		
		System.out.printf ("%3.0f segundos correspondem a %3.0f horas, %3.0f minutos e  %3.0f segundos",tmp, horas, minutos, segundos);
	}
}
