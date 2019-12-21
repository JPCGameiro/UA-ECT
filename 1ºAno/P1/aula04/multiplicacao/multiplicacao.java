import java.util.Scanner;
public class multiplicacao
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int x=0, y = 0, res=0;
		String check = "";
		
		System.out.print("Insira um número: ");
		x = ler.nextInt();
		System.out.print("Insira outro número: ");
		y = ler.nextInt();
		
		System.out.println("   X |   Y |  Soma");
		System.out.println("------------------");
		do{
			if(x%2!=0)  {
				check = "sim";
				res = res+y;
			}
			else check = "não";
				
			System.out.printf(" %3d | %3d |  %s\n", x,y, check);
			x = x/2;
			y = y*2;
		}
		while(x>=1);
		
		System.out.println("\nResultado da multiplicação dos dois números é "+res);
		
	}
}
