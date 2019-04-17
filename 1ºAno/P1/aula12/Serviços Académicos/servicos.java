import java.util.Scanner;
import java.io.*;
public class servicos
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner (System.in);
		Alunos a[] = new Alunos[10];
		int opc;
		
		System.out.printf("Serviços Académicos - Gestão de uma pauta:");
		System.out.printf("\n1 - Ler ficheiro com números mec. e pedir informação de notas");
		System.out.printf("\n2 - Imprimir no terminal a informação da disciplina");
		System.out.printf("\n3 - Calcular estatísticas das notas finais");
		System.out.printf("\n4 - Alterar as notas de um aluno");
		System.out.printf("\n5 - Imprimir um histograma de notas");
		System.out.printf("\n6 - Gravar num ficheiro a informação da disciplina (ordenada)");
		System.out.printf("\n7- Terminar o programa\n");
		
		do
		{
			System.out.printf("\nOpção -> ");
			opc=ler.nextInt();
			
			switch (opc)
			{
				case 1:
				Ler(a);
				break;
				
				case 2:
				Imprimir(a);
				break;
				
				case 3:
				Stats(a);
				break;
				
				case 4:
				Alterar(a);
				break;
				
				case 7:
				System.out.printf("\n Sistema a encerrar ...");
				break;
				
				default:
				System.out.printf("\nOpção Inválida\n");
				break;
			}
		}
		while(opc!=7);
	}
	//Ler números mecanográficos de um ficheiro
	public static void Ler(Alunos a[]) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		int i=0, z=0;
		String fichname;
		File fich;
		
		do
		{
			System.out.printf("\nInsira o nome de um ficheiro válido: ");
			fichname=ler.nextLine();
			fich = new File(fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner lerfich = new Scanner(fich);
		do
		{
			Alunos f = new Alunos ();
			f.mec=lerfich.nextInt();
			System.out.printf("Nº Mecanográfico: " + f.mec + "\n");
			for(i=0;i<3;i++)
			{
				do
				{
					System.out.printf("Nota " + (i+1) + ": ");
					f.notas[i]=ler.nextInt();
				}
				while(f.notas[i]<0 || f.notas[i]>20 && f.notas[i]!=77);
			}
			a[z]=f;
			z++;
		}
		while(lerfich.hasNextLine());
		lerfich.close();
	}
	//Imprimir no terminal a imformação de uma disciplina
	public static void Imprimir(Alunos a[])
	{
		int i=0, j=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		System.out.printf("\nPauta da disciplina");
		System.out.printf("\n---------------------------");
		for(j=0;j<i;j++)
		{
			System.out.printf("\n|  %4d  | %2d | %2d | %2d |", a[j].mec, a[j].notas[0], a[j].notas[1], a[j].notas[2]);
		} 
		System.out.printf("\n---------------------------\n");		
	}
	//Estatísticas dos aluno
	public static void Stats(Alunos a[])
	{
		int i=0, j=0, z=0, soma1=0, aprovados=0, reprovados=0, k=0;
		double notafinal=0, max=0;
		
		System.out.println();
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(j=0;j<i;j++)
		{
			soma1=0;
			for(z=0;z<3;z++)
			{
				if(a[j].notas[z]!=77)
				{
					soma1=soma1+a[j].notas[z];
				}
				else if(a[j].notas[z]==77)
				{
					soma1=soma1;
				}				
			}
			notafinal=(soma1/3);
			if(max<notafinal)
			{
				max=notafinal;
				k=j;
			}
			if(notafinal<9.5)
			{
				reprovados++;
			}
			else if(notafinal>=9.5)
			{
				aprovados++;
			}
			System.out.print(a[j].mec + " -> MédiaFinal : " + notafinal + "\n");
		}
		System.out.printf("\nNº de alunos aprovados: "+ aprovados + "\nNº de alunos reprovados: " + reprovados);
		System.out.printf("\nO melhor aluno tem o número mecanográfico " + a[k].mec + " e a média de " + max + "\n"); 			
	}
	//Alterar as notas de um aluno
	public static void Alterar (Alunos a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0, meca, k=-1, z=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		System.out.printf("\nInsira o número mecanográfico pretendido: ");
		meca=ler.nextInt();
		
		for(j=0;j<i;j++)
		{
			if(a[j].mec==meca)
			{
				k=j;
			}
		}
		if(k!=-1)
		{
			System.out.printf("Nº Mecanográfico existente, altere as notas");
			System.out.printf("\nNº Mecanográfico: " + a[k].mec +"\n");
			for(z=0;z<3;z++)
			{
				do
				{
					System.out.printf("Nota " + (z+1) + ": ");
					a[k].notas[z]=ler.nextInt();
				}
				while(a[k].notas[z]<0 || a[k].notas[z]>20);
			}
			System.out.println();
		}
		else if (k==-1)
		{
			System.out.printf("\nNúmero Mecanográfico inexistente\n");
		}
	}
	//Histograma
	public static void Histograma()
	{
		int i=0, j=0, k=0, z=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
			
			System.out.printf("Histograma de Notas");
			for(j=0;j<i;j++)
			{
				for(z=0;z<20;z++)
				{
					if(a[j].notas[
				}
				
			}
		}
}

class Alunos
{
	int mec;
	int notas[] = new int[3];
}
