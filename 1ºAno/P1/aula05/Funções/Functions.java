import java.util.Scanner;
public class Functions {

	public static void main (String args[])
	{		
		// Testar função sqr:
		System.out.printf("sqr(%f) = %f\n", 10.1, sqr(10.1));
		//Testar função f:
		System.out.printf("f(%d) = %f\n", 5 , f(5));
		//Testar função max:
		System.out.printf("max(%f, %f) = %f\n", 8.0, 6.0, max(8.0, 6.0)); 
		//Testar função max:
		System.out.printf("max(%d, %d) = %d\n", 75, 8, max(75, 8));
		//Testar função poly:
		System.out.printf("Se X = %f então %fx%f³ + %fx%f² + %fx%f + %f = %f\n", 2.0, 3.0, 2.0, 4.0, 2.0, 5.0, 2.0, 6.0, poly(3.0, 4.0, 5.0, 6.0, 2.0));
		//Testar função fact:
		System.out.printf("%d! = %d\n", 5, fact(5)); 
		//Testar função getIntPos:
		int ano = getIntPos("Ano?");
		System.out.printf("ano = %d\n", ano);
		//Testar função getIntRange:
		int numero=getIntRange("Número?", 1, 10);
		System.out.printf("%d pertence a [%d, %d]\n", numero, 1, 10);
		//Testar função printfNtimes:
		printNtimes(3, "Foo");
		
	}
	public static double sqr(double x) {
		double y;	
		y = x*x;	
		return y;
	}
	
	public static double f(int x)
	{
		double y;
		y=1/(1+(sqr(x)));
		return y;
		
	}
	
	public static double max(double x, double y)
	{
		double z=0;
		if(x<y)
		{
			z=y;
		}
		else if(x>y)
		{
			z=x;
		}
		return z;
	}
	
	public static int max(int x,int y)
	{
		int z=0;
		if(x<y)
		{
			z=y;
		}
		else if(x>y)
		{
			z=x;
		}
		return z;
	}
	
	public static int fact(int n)
	{
		int i=1,fat=1;
		for (i=1;i<=n;i++)
			{
				fat=fat*i;
			}
		return fat;
	}
	
	public static double poly(double a, double b, double c, double d ,double x)
	{
		double z;
		z=(a*(Math.pow(x,3))) + (b*sqr(x)) + (c*x)+d;
		return z;
	}
	
	public static int getIntPos(String x)
	{
		Scanner ler = new Scanner(System.in);
		int num;
		do
		{
			System.out.printf(x);
			num=ler.nextInt();
			
		}
		while(num<0);
		return num;
	}
	
	public static int getIntRange(String x, int y, int z)
	{
		Scanner ler = new Scanner(System.in);
		int n;
	do
	{
		System.out.printf(x);
		n=ler.nextInt();
	}
	while(n<y | n>z);
	return n;
	}
	
	public static void printNtimes(int x, String f)
	{
		int i;
		for(i=1;i<=x;i++)
		{
			System.out.printf(f);
		}
	}
}
