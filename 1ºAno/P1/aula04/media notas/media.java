import java.util.Scanner;
public class media
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int nota, soma;
		float m,i;
		
		System.out.printf("Nota?(termina com número negativo) ");
		nota=ler.nextInt();
		i=0;
		soma=0;
		do
		{
			soma=soma+nota;
			i++;
			System.out.printf("Notas? ");
			nota=ler.nextInt();
		}
		while(nota>0);
		m=(soma)/(i);
		System.out.printf("Soma = %d\nMédia = %2.2f", soma, m);
	}
}
			
			
			
			
		
