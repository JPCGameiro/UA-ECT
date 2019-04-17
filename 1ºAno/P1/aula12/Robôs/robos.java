import java.util.Scanner;
import java.io.*;
public class robos
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		int opc;
		Rob a[] = new Rob[10];
		
		System.out.printf("Micro-Rato 2013 - Gestão da prova:\n");
		System.out.printf("1 - Adicionar informação relativa a um robô\n");
		System.out.printf("2 - Imprimir informação dos robôs em prova\n");
		System.out.printf("3 - Vencedor da prova e tempos médios de prova\n");
		System.out.printf("4 - Média do número de elementos por equipa\n");
		System.out.printf("5 - Mostrar o nome dos robôs em maiúsculas\n");
		System.out.printf("6 - Alterar informação de um robô\n");
		System.out.printf("7 - Remover robôs da competição\n");
		System.out.printf("8 - Gravar informação da prova num ficheiro\n");
		System.out.printf("9 - Terminar o programa\n");
		
		do
		{	
			System.out.printf("\nOpção -> ");
			opc=ler.nextInt();
			
			switch(opc)
			{
				case 1:
				Adicionar(a);
				break;
				
				case 2:
				Imprimir(a);
				break;
				
				case 3:
				Vencedor(a);
				break;
				
				case 4:
				MediaElementos(a);
				break;
				
				case 5:
				Maisculas(a);
				break;
				
				case 6:
				Mudar(a);
				break;
				
				case 7:
				Remover(a);
				break;
				
				case 8:
				Gravar(a);
				break;
				
				case 9:
				System.out.printf("A encerrar...");
				break;
			}
		}
		while(opc!=9);
	}
	//Adicionar a imformação relativa ao robô
	public static void Adicionar(Rob a[])
	{
		Scanner ler = new Scanner (System.in);
		int i=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		Rob f = new Rob();
		System.out.printf("\nNome do Robô: ");
		f.name=ler.nextLine();
		System.out.printf("Tempo de prova em segundos: ");
		f.tempo=ler.nextInt();
		System.out.printf("Número de Elementos da equipa: ");
		f.elementos=ler.nextInt();
		a[i]=f;
	}
	//Imprimir a imformação dos robôs em prova
	public static void Imprimir(Rob a[])
	{
		int i=0, j=0;
		Horas h = new Horas();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			if(a[j].name!=null)
			{
				System.out.printf("\nNome do Robô: " + a[j].name);
				h.hora=(int)(a[j].tempo/3600);
				h.minuto=(int)((a[j].tempo-(h.hora*3600))/60);
				h.segundo=(a[j].tempo-(h.hora*3600)-(h.minuto*60));
				System.out.printf("\nTempo de Competição: %d:%d:%d", h.hora, h.minuto, h.segundo);
				System.out.printf("\nNúmero de Elementos: " + a[j].elementos + "\n");
			}
			else if(a[j].name==null)
			{
			}			
		}
	}
	//Calcular o tempo médio e o vencedor
	public static void Vencedor(Rob a[])
	{
		int i=0, j=0, min=a[0].tempo, z=0, soma=0 , k=0;
		Horas h = new Horas();
		Horas f = new Horas();
		double media;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(j=0;j<i;j++)
		{
			if(a[j].tempo<min)
			{
				min=a[j].tempo;
				z=j;
			}
			soma=soma+a[j].tempo;
			k++;
		}
		System.out.printf("Vencedor\n");
		System.out.printf("\nNome do Robõ: " + a[z].name);
		h.hora=(int)(a[z].tempo/3600);
		h.minuto=(int)((a[z].tempo-(h.hora*3600))/60);
		h.segundo=(a[z].tempo-(h.hora*3600)-(h.minuto*60));
		System.out.printf("\nTempo de Competição: %d:%d:%d", h.hora, h.minuto, h.segundo);
		System.out.printf("\nNúmero de Elementos: " + a[z].elementos + "\n");
		
		media=soma/k;
		f.hora=(int)(media/3600);
		f.minuto=(int)((media-(f.hora*3600))/60);
		f.segundo=(int)(media-(f.hora*3600)-(f.minuto*60));
		System.out.printf("\nMédia dos tempos de competição: %d:%d:%d\n", f.hora, f.minuto, f.segundo);			
	}
	//Calcular a média de elementos por equipa
	public static void MediaElementos(Rob a[])
	{
		int i=0, z=0, soma=0, j=0;
		double media;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(j=0;j<i;j++)
		{
			soma=soma+a[j].elementos;
			z++;
		}
		media=soma/z;
		System.out.printf("\nMédia do nº de elementos por equipa: " + media + "\n");		
	}
	//Mostrar o nome dos robôs em maiúsculas
	public static void Maisculas(Rob a[])
	{
		int i=0, j=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			System.out.print("\n"+a[j].name.toUpperCase());
		}
		System.out.println();
	}
	//Alterar a imformação de um robô
	public static void Mudar(Rob a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0, z=0;
		String nome;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		System.out.printf("\nInsira o nome do robô: ");
		nome=ler.nextLine();
		for(j=0;j<i;j++)
		{
			if(a[j].name.equals(nome))
			{
				z=j;
			}
		}
		Rob f = new Rob();
		System.out.printf("\nInsira as novas imformações\nNome: ");
		f.name=ler.nextLine();
		System.out.printf("Tempo de prova em segundos: ");
		f.tempo=ler.nextInt();
		System.out.printf("Número de Elementos da equipa: ");
		f.elementos=ler.nextInt();
		a[z]=f;
	}
	//Remover robôs da competição
	public static void Remover(Rob a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0, tmp;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		System.out.printf("Insira o tempo máximo permitido: ");
		tmp=ler.nextInt();
		for(j=0;j<i;j++)
		{
			if(a[j].tempo>tmp)
			{
				a[j] = new Rob();
			}
		}
		
	}
	//Gravar as imformações num ficheiro
	public static void Gravar(Rob a[]) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0;
		String fichname;
		Horas h = new Horas();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		System.out.printf("\nInsira o nome do ficheiro onde pretende gravar: ");
		fichname=ler.nextLine();
		
		File fich = new File(fichname);
		PrintWriter pw = new PrintWriter(fich);
		for(j=0;j<i;j++)
		{
			if(a[j].name!=null)
			{
				pw.println("Nome do Robô: " + a[j].name);
				h.hora=(int)(a[j].tempo/3600);
				h.minuto=(int)((a[j].tempo-(h.hora*3600))/60);
				h.segundo=(a[j].tempo-(h.hora*3600)-(h.minuto*60));
				pw.println("Tempo de Competição: " + h.hora + ":" + h.minuto +":"+ h.segundo);
				pw.println("Número de Elementos: " + a[j].elementos + "\n");
			}
			else if(a[j].name==null)
			{
			}			
		}
		pw.close();
		System.out.printf("\nSequência gravada com sucesso\n");
	}
}

class Rob
{
	String name;
	int tempo;
	int elementos;
}

class Horas
{
	int hora;
	int minuto;
	int segundo;
}
