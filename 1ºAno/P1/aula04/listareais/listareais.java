import java.util.Scanner;
public class listareais
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double num, num1,  max, min, media, i;
		
		System.out.printf("Digite uma lista de números reais (termina com o primmeiro introduzido : ");
		num1=ler.nextDouble();
		max=num1;
		min=num1;
		i=1;
		media=num1;
		num=num1;
		
		do
		{
			i++;
			media=media+num;
			if(num>max)
			{
				max=num;
			}
			if(num<min)
			{
				min=num;
			}
			num=ler.nextDouble();	
		}
		while(num!=num1);
		media=((media-num)/(i-1));
		System.out.printf("Máximo = %4.2f\nMinímo = %4.2f\nMédia = %4.2f\nNúmero de Elementos = %3.0f", max, min, media, (i-1));
	}
}
		
