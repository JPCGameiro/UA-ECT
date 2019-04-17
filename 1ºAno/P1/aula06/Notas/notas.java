import java.util.Scanner;
public class notas
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int num, i=0;
		
		do
		{
			System.out.printf("Insira o número de notas a digitar: ");
			num=ler.nextInt();
		}
		while(num<0);
		
		int a[] = new int[num];
		System.out.printf("Insira as notas: ");
		for(i=0;i<num;i++)
		{
			a[i] = ler.nextInt();
		}
		
		PrintNotas(a);
		
	}
	
	public static void PrintNotas(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, f=0;
		
		System.out.printf("Histograma de Notas\n");
		System.out.printf("---------------------------------------------\n");
		for(i=20;i>=0;i--)
		{
			if(i>=10)
			{
				System.out.printf(i + " | " + CalNotas(i, a) + "\n");
			}
			else if(i<10)
			{
				System.out.printf(i + "  | " + CalNotas(i,a) + "\n");
			}
				
		}
		
	}
	
	public static String CalNotas(int  i, int a[])
	{
		String nota="";
		int u=0, z=0;
		for (u=0;u<a.length;u++)
		{
			if(a[u]==i)
			{
				nota=nota+"*";
			}
		}
		return nota;
	}
		
}

			
		
		
		
		

