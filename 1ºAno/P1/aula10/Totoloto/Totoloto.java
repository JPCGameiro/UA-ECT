import java.util.Scanner;
public class Totoloto
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int a[] = new int[6];
		int i=0;
		
		System.out.printf("       Aposta Totoloto       \n");
		System.out.printf("Chave: ");
		a=gerarChave();
		for(i=0;i<5;i++)
		{
			System.out.print(a[i] + ", ");
		}
		System.out.print(a[5]);
		
	}
	
	public static int[] gerarChave()
	{
		int i=0, n=0, repetido=0;
		int a[] = new int[6];
		
		for(i=0;i<6;i++)
		{
			a[i]=(int)(49.0*Math.random())+ 2;
		}
		for(i=0;i<5;i++)
		{
			for(n=i+1;n<5;i++)
			{
				if(a[i]==a[n]);
				{
					repetido=a[i];
					do
					{
						a[i]=(int)(49.0*Math.random())+2;
					}
					while(a[i]==repetido);
				}
			}
		}
		return a;
	}		
			
}
