import java.util.Scanner;
public class pontos
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int i=0;
		double dist=0, max=0;
		Ponto2D pt, ptmax;
		pt = new Ponto2D();
		ptmax = new Ponto2D();

		
		do
		{
			pt = Ler();
			i++;
			dist=dist + Distancia(pt);
			if(Distancia(pt)>max)
			{
				max=Distancia(pt);
				ptmax=pt;
			} 
		}
		while(pt.x!=0 || pt.y!=0);
		
		System.out.printf("A soma das distâncias dos %d pontos à origem é %2.1f", i-1, dist);
		System.out.print("\nO ponto mais afastado da origem foi: ");
		Escrever(ptmax);
		
	}
	
	public static Ponto2D Ler()
	{
		Scanner ler = new Scanner (System.in);
		Ponto2D pt;
		pt = new Ponto2D();
		
		System.out.printf("Introduza um ponto:\n");
		System.out.printf("Coordenada x:");
		pt.x=ler.nextDouble();
		System.out.printf("Coordenada y:");
		pt.y=ler.nextDouble();
		
		return pt;
	}
	
	public static void Escrever(Ponto2D pt)
	{
		System.out.printf("(%1.1f, %1.1f)", pt.x, pt.y);
	}
	
	public static double Distancia(Ponto2D pt)
	{
		double d;		
		d=Math.sqrt((Math.pow(pt.x, 2)) + (Math.pow(pt.y, 2)));
		return d;
	}
		
			
}
class Ponto2D
{
	double x,y;
}
	
