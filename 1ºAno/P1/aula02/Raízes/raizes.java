import java.util.Scanner;
public class raizes
{
	public static void main (String[] args)
	{
		Scanner ler =  new Scanner(System.in);
		int a, b, c;
		double raiz1=1, raiz2=1, delta;
		
		System.out.printf("Digite as constantes do polinómio ax² + bx +c = 0\n");
		System.out.printf("a:");
		a=ler.nextInt();
		System.out.printf("b:");
		b=ler.nextInt();
		System.out.printf("c:");
		c=ler.nextInt();
		
		delta = (Math.pow(b,2.0) - (4*a*c));
		if (delta>0)
		{
			raiz1=(-b + Math.sqrt(delta))/(2*a);
			raiz2=(-b - Math.sqrt(delta))/(2*a);
		}
		System.out.printf("As raizes são %4.2f e %4.2f ", raiz1, raiz2);
	}
}
			
		
		
		
