import java.util.Scanner;
public class pontos
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int px1, py1, px2, py2, px3, py3, px4, py4;
		boolean verify = false;
		
		System.out.println("Introduza 4 pontos(P1, P2, P3, P4)\n");
		System.out.printf("P1 - x: ");
		px1 = ler.nextInt();
		System.out.printf("P1 - y: ");
		py1 = ler.nextInt();
		
		System.out.printf("P2 - x: ");
		px2 = ler.nextInt();
		System.out.printf("P2 - y: ");
		py2 = ler.nextInt();
		
		System.out.printf("P3 - x: ");
		px3 = ler.nextInt();
		System.out.printf("P3 - y: ");
		py3 = ler.nextInt();
		
		System.out.printf("P4 - x: ");
		px4 = ler.nextInt();
		System.out.printf("P4 - y: ");
		py4 = ler.nextInt();
			
		
		if(py2 == py1 )
		{
			
			if(px3 == px2)
			{
				
				if(px4 != px1 || py4 != py3)
				{
					verify = true;
				}
			}
			else
				verify = true;
		
		}
		else
			verify = true;
		
		if(verify)
			System.out.println("Os pontos não formam um quadrado perfeito");
		else
			System.out.println("Os pontos formam um quadrado perfeito");
	}
}
