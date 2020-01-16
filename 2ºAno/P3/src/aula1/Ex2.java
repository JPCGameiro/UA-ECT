//João Gameiro  Nº93097
//Problema 1.2

package aula1;
import java.util.*;

public class Ex2 {
	
	static Scanner ler = new Scanner(System.in);
		
	public static void main(String[] args) {
		
		LinkedList<Pessoa> Person = new LinkedList<Pessoa>();
		int option=0;
		
		//-----------------MENU---------------------//
		do
		{
			option=-1;
			
			System.out.println("-----------------------------");
			System.out.println("      Gestão de Pessoas      ");
			System.out.println("-----------------------------");
			System.out.println("1 -> Adicionar uma pessoa    ");
			System.out.println("2 -> Remover uma pessoa      ");
			System.out.println("3 -> Ver a lista completa    ");
			System.out.println("4 -> Ordenar a lista por nome");
			System.out.println("5 -> Ordenar a lista por cc  ");
			System.out.println("6 -> Terminar                ");
			System.out.print("\nOpção: ");
			option = ler.nextInt();
			
			switch(option)
			{
				case 1:
					Person.add(addPessoa());
					break;
					
				case 2:
					removePessoa(Person);
					break;
					
				case 3:
					printList(Person);
					break;
				
				case 4:
					orderName(Person);
					break;
				
				case 5:
					orderCC(Person);
					break;
					
				case 6:
					System.out.println("Sistema a encerrar...");
					ler.close();
					System.exit(0);
				
				default:
					System.out.println("Opção Inválida");
					break;
				
			}
		}
		while(option!=6);		

	}
	
	
	//-----------FUNÕES ADICIONAIS----------------//
	
	//Função para adicionar uma pessoa nova à lista
	public static Pessoa addPessoa()
	{
		ler.nextLine();
		int d, m, a, cid;
		String nome;
		
		System.out.print("Nome: ");
		nome = ler.nextLine();
		System.out.print("Cartão de Cidadão: ");
		cid = ler.nextInt();
		System.out.print("Data de Nascimento\nDia: ");
		d = ler.nextInt();
		System.out.print("Mês: ");
		m = ler.nextInt();
		System.out.print("Ano: ");
		a = ler.nextInt();
		
		Data date = new Data(d, m, a);
		Pessoa p = new Pessoa(nome, cid, date);
		System.out.println("Pessoa adicionada com sucesso!");
		
	
		return p;
		
	}
	
	
	
	//Função para remover pessoas da lista
	public static void removePessoa(LinkedList<Pessoa> Person)
	{
		ler.nextLine();
		int size = Person.size(), i=0, j=-1;
		String name;
		
		System.out.print("Insira o nome da pessoa que pretende remover: ");
		name = ler.nextLine();
		
		for(i=0;i<size;i++)
		{
			if(Person.get(i).nome().contentEquals(name))
			{
				j=i;
			}
		}	
		
		if(j!=-1)
		{
			Person.remove(j);
			System.out.println("Pessoa removida com sucesso");
		}
		else
			System.out.println("ERRO: Pessoa Inválida");
	}
	
	
	
	//Função para imprimir o conteúdo da lista
	public static void printList(LinkedList<Pessoa> Person)
	{
		int size = Person.size(), i=0;
		
		if(size!=0)
		{
			for(i=0;i<size;i++)
			{
				System.out.println(Person.get(i).toString());
			}
		}
		else
			System.out.println("Lista Vazia");
	}
	
	
	
	//Função para ordenar a lista por nome
	public static void orderName(LinkedList<Pessoa> Person)
	{
		int size = Person.size(), i=0, n=0;
		for(i=0;i<size;i++)
		{
			for(n=i+1;n<size;n++)
			{
				Pessoa pes1 = Person.get(i);
				Pessoa pes2 = Person.get(n);
				if(pes2.nome().compareTo(pes1.nome()) < 0)
				{
					Person.set(n, pes1);
					Person.set(i, pes2);
				}
			}
		}
		System.out.println("Lista Ordenada por nome com sucesso!");
	}
	
	
	
	//Função para ordenar a lista por cartão de cidadão
	public static void orderCC(LinkedList<Pessoa> Person)
	{
		int size = Person.size(), i=0, n=0;
		for (i=0;i<size;i++)
		{
			for(n=i+1;n<size;n++)
			{
				Pessoa pes1 = Person.get(i);
				Pessoa pes2 = Person.get(n);
				if(pes2.cc()<pes1.cc())
				{
					Person.set(n, pes1);
					Person.set(i, pes2);
				}
			}
		}
		System.out.println("Lista Ordenada por Cartão Cidadão com sucesso!");
	}
}
