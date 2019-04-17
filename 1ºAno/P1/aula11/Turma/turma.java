import java.util.Scanner;
public class turma
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int opc=0, n=0;
		Aluno a[] = new Aluno[10];
		
		System.out.println("Gestão de uma turma:");
		System.out.println("1 - Inserir informação da turma");
		System.out.println("2 - Mostrar informação de um aluno");
		System.out.println("3 - Alterar informação de um aluno");
		System.out.println("4 - Listar os alunos ordenados por nº mec");
		System.out.println("5 - Listar os alunos ordenados por nota final");
		System.out.println("6 - Média das notas finais");
		System.out.println("7 - Melhor aluno");
		System.out.println("8 – Inserir pesos dos vários testes");
		System.out.println("0 - Terminar o programa");
		
		do
		{
			System.out.printf("\nOpção? ");
			opc=ler.nextInt();
			
			switch (opc)
			{
				case 1:
				a=Turma();
				break;
				
				case 2:
				Mostrar(a);
				break;
								
				case 3:
				Alterar(a);
				break;
				
				case 4:
				Ordenados(a);
				break;
				
				case 5:
				NotaFinalOrder(a);
				break;
				
				case 6:
				MediaFinal(a);
				break;
				
				case 7:
				Melhor(a);
				break;
				
				case 8:
				Final(a);
				break;
								
				case 0:
				System.out.printf("\nA encerrar o sistema...");
				break;
				
				default:
				System.out.printf("Opção inválida");
				break;
			}
		}
		while(opc!=0);
	}
	//Inserir imformações da turma
	public static Aluno[] Turma()
	{
		Scanner ler = new Scanner(System.in);
		int n, i=0, j=0;
		Aluno a[] = new Aluno[10];
		
		do
		{
			System.out.printf("Quantos alunos pretende inserir(10 no max): ");
			n=ler.nextInt();
		}
		while(n<0 || n>20);
		
		for(i=0;i<n;i++)
		{
			Aluno f = new Aluno();
			System.out.printf("Aluno " + (i+1) + "\n");
			System.out.printf("Número mecanográfico: ");
			f.mec=ler.nextInt();
			System.out.printf("Nome: ");
			String dumb = ler.nextLine();
			f.nome=ler.nextLine();
			for(j=0;j<3;j++)
			{
				do
				{
					System.out.printf("Nota"+(j+1)+": ");
					f.notas[j]=ler.nextInt();
				}
				while(f.notas[j]<0 || f.notas[j]>20);
			}
			a[i]=f;
			
		}
		return a;		
	}
	//Mostrar imformações de um aluno
	public static void Mostrar(Aluno a[])
	{
		Scanner ler = new Scanner(System.in);
		int meca, i=0, j=0, k=0;
		boolean c=false;
		
		System.out.printf("Insira o número mecanográfico do aluno: ");
		meca=ler.nextInt();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(k=0;k<i;k++)
		{
			if(a[k].mec==meca)
			{
				System.out.printf("\nNúmero Mecanográfico: " + a[k].mec);
				System.out.printf("\nNome: " + a[k].nome);
				for(j=0;j<3;j++)
				{
					System.out.printf("\nNota "+(j+1)+": " + a[k].notas[j]);
				}
				if(a[k].notafinal!=0.0)
				{
					System.out.printf("\nNota final: " + a[k].notafinal);
				}
				c=true;
				break;
			}
		}
		if(c==false)
		{
			System.out.printf("Número mecanográfico inválido");
		}
	}
	//Alterar as imformações de um aluno
	public static void Alterar(Aluno a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0, z=0, k=0, n, meca;
		boolean test=false;
		
		System.out.printf("Insira o número mecanográfico do aluno: ");
		meca=ler.nextInt();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		n=i;
		
		for(j=0;j<i;j++)
		{
			if(a[j].mec==meca)
			{
				System.out.printf("Insira os novos valores das notas:\n");
				for(z=0;z<3;z++)
				{
					do
					{
						System.out.print("Nota "+(z+1)+": ");
						a[j].notas[z]=ler.nextInt();
					}
					while(a[j].notas[z]<0 || a[j].notas[z]>20);
				}
				test=true;
			}
		}
		if(test==false)
		{
			System.out.printf("Número não existente");
			if(n==a.length)
			{
				System.out.printf("\nTurma cheia, impossível adicionar aluno");
			}
			else if(n!=a.length)
			{
				Aluno f= new Aluno();
				System.out.printf("\nTurma não cheia, possível adicionar aluno\n");
				System.out.printf("Número mecanográfico: ");
				f.mec=ler.nextInt();
				System.out.printf("Nome: ");
				String dumb=ler.nextLine();
				f.nome=ler.nextLine();
				for(k=0;k<3;k++)
				{
					do
					{
						System.out.printf("Nota "+(k+1)+": ");
						f.notas[k]=ler.nextInt();
					}
					while(f.notas[k]<0 || f.notas[k]>20);
				}
				a[n]=f;
			}
		}			
	}
	//Mostrar alunos ordenados por nº mec
	public static void Ordenados(Aluno a[])
	{
		int i=0,j=0,k=0;
		Aluno tmp;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(j=0;j<i;j++)
		{
			for(k=j+1;k<i;k++)
			{
				if(a[j].mec>a[k].mec)
				{
					tmp=a[j];
					a[j]=a[k];
					a[k]=tmp;
				}
			}
		}
		for(int z=0;z<i;z++)
		{
			System.out.printf("\nNúmero Mecanográfico: " + a[z].mec);
			System.out.printf("\nNome: " + a[z].nome);
			for(j=0;j<3;j++)
			{
				System.out.printf("\nNota "+(j+1)+": " + a[z].notas[j]);
			}
		}
	}
	//Nota final
	public static void Final(Aluno a[])
	{
		Scanner ler= new Scanner(System.in);
		double p1, p2, p3;
		int i=0, j=0, k=0;
		
		do
		{
			System.out.printf("Insira o peso do primeiro teste em número decimal: ");
			p1=ler.nextDouble();
			System.out.printf("Insira o peso do segundo teste em número decimal: ");
			p2=ler.nextDouble();
			System.out.printf("Insira o peso do terceiro teste em número decimal: ");
			p3=ler.nextDouble();
		}
		while(p1+p2+p3!=1.00);
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(j=0;j<i;j++)
		{
			a[j].notafinal=a[j].notas[0]*p1 + a[j].notas[1]*p2 + a[j].notas[2]*p3;
		}
		for(k=0;k<i;k++)
		{
			System.out.printf("\nNúmero Mecanográfico: " + a[k].mec);
			System.out.printf("\nNome: " + a[k].nome);
			for(j=0;j<3;j++)
			{
				System.out.printf("\nNota "+(j+1)+": " + a[k].notas[j]);
			}
			System.out.printf("\nNota final: " + a[k].notafinal);
		}		
	}
	//Ordenar por Nota Final
	public static void NotaFinalOrder(Aluno a[])
	{
		int i=0, z=0, k=0, j=0, s=0;
		Aluno tmp;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		for(z=0;z<i;z++)
		{
			for(k=z+1;k<i;k++)
			{
				if(a[z].notafinal<a[k].notafinal)
				{
					tmp=a[z];
					a[z]=a[k];
					a[k]=tmp;
				}
			}
		}
		for(j=0;j<i;j++)
		{
			System.out.printf("\nNúmero Mecanográfico: " + a[j].mec);
			System.out.printf("\nNome: " + a[j].nome);
			for(s=0;s<3;s++)
			{
				System.out.printf("\nNota "+(s+1)+": " + a[j].notas[s]);
			}
			System.out.printf("\nNota Final: " + a[j].notafinal);
		}
		
	}
	//Média das Notas finais
	public static void MediaFinal(Aluno a[])
	{
		int i=0, j=0, k=0;
		double soma=0, media;
		
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			soma=soma+a[j].notafinal;
			k++;
		}
		media=soma/k;
		System.out.printf("Média das notas finais: " + media);
	}
	//Melhor Aluno
	public static void Melhor(Aluno a[])
	{
		int i=0, k=0, j=0;
		double max;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		
		max=a[0].notafinal;
		for(j=0;j<i;j++)
		{
			if(a[j].notafinal>max)
			{
				k=j;
			}
		}
		System.out.printf("O melhor aluno foi " + a[k].nome + " de nº mecanográfico " + a[k].mec + " de nota final " + a[k].notafinal);
	}
		 	
}

class Aluno
{
	int mec;
	String nome;
	int notas[] = new int[3];
	double notafinal;
}
