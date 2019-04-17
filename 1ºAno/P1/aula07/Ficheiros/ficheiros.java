import java.util.Scanner;
import java.io.*;
import java.io.PrintWriter;
public class ficheiros
{
	static Scanner ler = new Scanner(System.in);
	public static void main (String[] args) throws IOException
	{
		int opc;
		int a[]=new int[10];
		int b[]=new int[0];
		
		System.out.printf("Análise de uma sequência\n");
		System.out.printf("\n1-Ler a sequência\n");
		System.out.printf("2-Escrever a sequência\n");
		System.out.printf("3-Calcular o máximo da sequência\n");
		System.out.printf("4-Calcular o mínimo da sequência\n");
		System.out.printf("5-Calcular a média da sequência\n");
		System.out.printf("6-Detetar se a sequência é só constituida por números pares\n");
		System.out.printf("7-Ler uma sequência de números de um ficheiro de texto\n");
		System.out.printf("8-Adicionar números à sequência existente\n");
		System.out.printf("9-Gravar a sequência atual de números num ficheiro\n");
		System.out.printf("10-Terminar\n");
		
		do
		{
			System.out.printf("\nOpção-> ");
			opc=ler.nextInt();
			
			switch(opc)
			{
				case 1:
				a=Ler();
				break;
				
				case 2:
				Escrever(a);
				break;
				
				case 3:
				Max(a);
				break;
				
				case 4:
				Min(a);
				break;
				
				case 5:
				Media(a);
				break;
				
				case 6:
				Pares(a);
				break;
				
				case 7:
				a=LerFicheiro();
				break;
				
				case 8:
				int n;
				System.out.printf("Quantos número quer adicionar: ");
				n=ler.nextInt();				
				b= new int[(a.length + n)];
				b=Adicionar(a ,n);
				break;
				
				case 9:
				Gravar(b);
				break;
				
				case 10:
				System.out.printf("...Sistema a encerrar");
				break;
			}
		}
		while(opc!=10);
	}
	
	public static int[] Ler()
	{
		int a[] = new int[10];
		int i=0;
		
		System.out.printf("Insira a sequência: ");
		for(i=0;i<10;i++)
		{
			a[i]=ler.nextInt();
			if(a[i]==0)
			{
				break;
			}
		}
		
		return a;
	}
	
	public static void Escrever(int a[])
	{
		int i=0;
		for(i=0;i<a.length;i++)
		{
			if(a[i]!=0)
			{
				System.out.print(a[i] + " ");
			}
			else
			{
				break;
			}
		}
	}
	
	public static void Max(int a[])
	{
		int max=0, i=0;
		for(i=0;i<10;i++)
		{
			if(a[i]>max)
			{
				max=a[i];
			}
		}
		System.out.print("Máximo: " + max);
	}
	
	public static void Min(int a[])
	{
		int min = a[0], i=0;
		for(i=0;i<=9;i++)
		{
			
			if(a[i]<min && a[i]!=0)
			{
				min=a[i];
			}
		}
		System.out.print("Mínimo: " + min);
	}
	
	public static void Media(int a[])
	{
		int i=0, u=0;
		double soma=0, media;
		for(i=0;i<10;i++)
		{
			if(a[i]!=0)
			{
				soma=soma+a[i];
				u++;
			}
		}
		media=(soma/u);
		System.out.printf("Média: %2.2f",media);
	}
	
	public static void Pares(int a[])
	{
		int i=0, u=0;
		for(i=0;i<10;i++)
		{
			if(a[i]%2!=0)
			{
				u++;
			}			
		}
		if(u!=0)
		{
			System.out.printf("A sequência não é só constituida por números pares");
		}
		else 
		{
			System.out.printf("A sequência é só constituida por números pares");
		}
	}
	
	public static int[] LerFicheiro() throws IOException
	{
		String fichname;
		int i=0;
		int a[] = new int[10];
		File fich;
		
		do
		{			
			System.out.print("Insira o nome do ficheiro: ");
			fichname=ler.next();
			fich = new File (fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner file = new Scanner(fich);
		do
		{	
					
			a[i]=file.nextInt();
			if(a[i]==0)
			{
				break;
			}
			i++;
						
		}
		while(file.hasNextLine() && i<=9);
		return a;
	}
	
	public static int[] Adicionar(int a[], int n)
	{
		int b[] = new int[(n+a.length)];
		for(int i=0;i<a.length;i++)
		{
			b[i]=a[i];
		}
		System.out.printf("Insira os números: ");
		for(int i = a.length;i<(n+a.length);i++)
		{
			b[i]=ler.nextInt();
		}
		return b;
	}
	
	public static void Gravar(int b[]) throws IOException
	{
		String newfile;
		
		System.out.printf("Insira o nome do ficheiro onde vai guardar a imformação: ");
		newfile=ler.next();
		File fich = new File (newfile);
		
		PrintWriter pw = new PrintWriter(fich);
		for(int i=0;i<(b.length);i++)
		{
			pw.println(b[i]);
		}
		pw.close();
	}		
}
		
	
	

		
		
		
