import java.util.Scanner;
import java.io.*;
public class menu
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		int opc, n=0;
		int a[] = new int[10];
		int b[] = new int[10];
		int i=0;
		
		System.out.printf("Análise de uma sequência\n");
		System.out.printf("\n1-Ler a Sequência");
		System.out.printf("\n2-Escrever a sequência");
		System.out.printf("\n3-Calcular o máximo da sequência");
		System.out.printf("\n4-Calcular o mínimo da sequência");
		System.out.printf("\n5-Calcular a média da sequência");
		System.out.printf("\n6-Calcular o nº de números pares");
		System.out.printf("\n7-Calcular o nº de números impares");
		System.out.printf("\n8-Ler uma sequência de um ficheiro");
		System.out.printf("\n9-Adicionar números à sequência");
		System.out.printf("\n10-Gravar a sequência num ficheiro");
		System.out.printf("\n11-Ordenar a sequência por ordem crescente utilizando ordenação sequencial");
		System.out.printf("\n12-Ordenar a sequência por ordem decrescente utilizando ordenação por flutuação");
		System.out.printf("\n13-Pesquisa de valor na sequência"); 
		System.out.printf("\n14-Terminar\n");
		
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
				System.out.print("Máximo: " + Max(a));
				break;
				
				case 4:
				System.out.print("Mínimo: " +Min(a));
				break;
				
				case 5:
				System.out.print("Média: " +Media(a));
				break;
				
				case 6:
				System.out.print("Nº de pares: " +Pares(a));
				break;
				
				case 7:
				System.out.printf("Nº de impares: " +Impares(a));
				break; 
				
				case 8:
				a=Lerficheiro();
				break;
				
				case 9:
				System.out.printf("Quantos números pretende adicionar: ");
				n=ler.nextInt();
				b=new int[a.length+n];
				b=Adicionar(n, a);
				a=new int[b.length];
				for(i=0;i<b.length;i++)
				{
					a[i]=b[i];
				}			
				break;
				
				case 10:
				Gravar(b);
				break;
				
				case 11:
				a=OrdenarSeq(a);
				break;
				
				case 12:
				a=OrdenarFlu(a);
				break;
				
				case 13:
				Pesquisa(a);
				break;
				
				case 14:
				System.out.printf("...a encerrar");
				break;
				
				default:
				System.out.printf("Opção inválida");
				break;
			} 
		}
		while(opc!=14);
	}
	
	public static int [] Ler()
	{
		Scanner ler = new Scanner(System.in);
		int a[] = new int[10];
		int i=0;
		
		System.out.printf("Insira uma sequência de números (termina com 0): ");
		for(i=0;i<=9;i++)
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
		Scanner ler = new Scanner(System.in);
		int i=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]!=0)
			{
				System.out.print(a[i] + " ");
			}
		}
	}
	
	public static int Max(int a[])
	{
		Scanner ler = new Scanner (System.in);
		int i=0, max=a[1];
		
		for(i=0; i<=9;i++)
		{
			if(a[i]>max && a[i]!=0)
			{
				max=a[i];
			}
		}
		return max;
	}
	
	public static int Min(int a[])
	{
		Scanner ler = new Scanner (System.in);
		int i=0, min=a[1];
		
		for(i=0;i<=9;i++)
		{
			if(a[i]<min && a[i]!=0)
			{
				min=a[i];
			}
		}
		return min;
	}
	
	public static double Media(int a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, soma=0;
		double n=0, media=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]!=0)
			{
				soma=soma+a[i];
				n++;
			}
		}
		media=soma/n;
		
		return media;
	}
	
	public static int Pares(int a[])
	{
		int i=0, n=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]%2==0 && a[i]!=0)
			{
				n++;
			}
		}
		return n;
	}
	
	public static int Impares(int a[])
	{
		int i=0, n=0;
		
		for(i=0;i<=9;i++)
		{
			if(a[i]%2!=0 && a[i]!=0)
			{
				n++;
			}
		}
		return n;
	}
	
	public static int [] Lerficheiro() throws IOException
	{
		Scanner ler = new Scanner (System.in);
		String fichname;
		File fich;
		int a[] = new int[10];
		int i=0, u=0;
		
		do
		{
			System.out.printf("Insira um nome de ficheiro válido: ");
			fichname=ler.nextLine();
			fich = new File(fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner lerfich = new Scanner(fich);
		
		do
		{				
			a[i]=lerfich.nextInt();
			if(a[i]==0)
			{
				break;
			}
			i++;			
		}
		while(lerfich.hasNextLine() && i<=9);
		return a;
	}
	
	public static int [] Adicionar(int n, int a[])
	{
		Scanner ler = new Scanner(System.in);
		int b[] = new int[10+n];
		int i=0;
		
		for(i=0;i<a.length;i++)
		{
			b[i]=a[i];
		}
		System.out.printf("Insira os números: ");
		for(i=a.length;i<10+n;i++)
		{
			b[i]=ler.nextInt();
		}
		return b;
	}
	
	public static void  Gravar(int b[]) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		String name;
		int i=0;
		
		System.out.printf("Insira o nome do ficheiro onde dejesa gravar: ");
		name=ler.nextLine();
		
		File fich = new File(name);
		PrintWriter pw = new PrintWriter(fich);
		for(i=0;i<b.length;i++)
		{
			pw.println(b[i]);
		}
		pw.close();
		System.out.printf("Sequência gravada com sucesso");
	}
	
	public static int[] OrdenarSeq(int a[])
	{
		Scanner ler = new Scanner (System.in);
		int tmp, i, n;
		for(i=0;i<a.length;i++)
		{
			for(n=i+1;n<a.length;n++)
			{
				if(a[i] > a[n]) 
				{
					tmp = a[i];
					a[i] = a[n];
					a[n] = tmp;
				}
			} 
		}
		return a;
	}
	
	public static int[] OrdenarFlu(int a[])
	{
		int tmp, i, n;
		for(i=0;i<a.length;i++)
		{
			for(n=i+1;n<a.length;n++)
			{
				if(a[i]<a[n])
				{
					tmp=a[i];
					a[i]=a[n];
					a[n]=tmp;
				}
			}
		}
		return a;
	}
	
	public static void Pesquisa(int a[])
	{
		Scanner ler = new Scanner (System.in);
		int n, pos=-1, i=0;
		
		System.out.printf("Qual o valor que pretende procurar: ");
		n=ler.nextInt();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==n)
			{
				pos=i+1;
			}
		}
			if(pos==-1)
			{
				System.out.printf("O valor inserido não existe na sequência");
			}
			else
			{
				System.out.printf("O valor está na posição %d", pos);
			}
	}
}
					

		
		
