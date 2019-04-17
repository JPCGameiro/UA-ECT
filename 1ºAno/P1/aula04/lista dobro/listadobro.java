import java.util.Scanner;
public class listadobro
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int n, dobro, max, min;
		
		System.out.printf("Digite uma lista de números inteiros positivos : ");
		n=ler.nextInt();
		max=n;
		min=n;

		do
		{
			
			dobro=n*2;
			n=ler.nextInt();
			if(n<0)
			{
				System.out.printf("Número Inválido");
				System.exit(0);
			}
			else
			{
				if(n<min)
			{
				min=n;
			}
			if(n>max)
			{
				max=n;
			}
				
			
		}
	}
		while(n!=dobro);
		System.out.printf("O maior número digitado foi %d\no menor foi %d\ne os números que forçaram a paragem foram %d e %d", max, min, (dobro/2), dobro);
	

}
}

