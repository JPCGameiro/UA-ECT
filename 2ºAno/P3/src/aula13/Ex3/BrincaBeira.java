//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex3;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class BrincaBeira {

	public static void main(String[] args) {
		
		Set<Empregado> Nset = new TreeSet<>();						//Set dos empregados
		LinkedList<Brinquedos> Blist = new LinkedList<>();			//Lista para sorteio dos brinquedos
		
		Scanner ler = new Scanner(System.in);
		int op = -1;
		
		do {
			System.out.println("*** Brinca&Beira");
			System.out.println("1-> Adicionar empregado");
			System.out.println("2-> Remover empregado");
			System.out.println("3-> Listar empregados");
			System.out.println("4-> Sortear brinquedo");
			System.out.println("5-> Ver lista de vencedores");
			System.out.println("6-> Lista de nomes para serem usados em produtos");
			System.out.println("7-> Número de funcionários que têm cada primeiro nome");
			System.out.println("8-> Terminar\n");
			
			System.out.print("\nOpção-> ");
			op = ler.nextInt();
			
			switch(op) {
				
				case 1:
					System.out.print("\nInsira o 1º nome: ");
					String name = ler.next();
					System.out.print("Insira o apelido: ");
					String apelido = ler.next();
					addEmpregado(Nset, name, apelido);
					System.out.println();
					break;
				
				case 2:
					System.out.print("\nInsira o 1º nome: ");
					String n = ler.next();
					System.out.print("Insira o apelido: ");
					String a = ler.next();
					removeEmpregado(Nset, n, a);
					System.out.println();
					break;
				
				case 3:
					listEmpregados(Nset);
					break;
					
				case 4:
					if(Nset.isEmpty()) {
						System.out.println("\nERRO: ainda não foram adicionados funcionários\n");
						break;
					}
					else {
						ler.nextLine();
						System.out.print("\nInsira o nome do brinquedo: ");
						String b = ler.nextLine();
						Blist = sortToy(b, Blist, Nset);
						System.out.println();
						break;
					}
				
				case 5:
					listWinners(Blist);
					break;
				
				case 6:
					if(Nset.isEmpty()) {
						System.out.println("\nERRO: ainda não foram adicionados funcionários\n");
						break;
					}
					else {
						listNamesProduct(Nset);
						break;
					}
				
				case 7:
					if(Nset.isEmpty()) {
						System.out.println("\nERRO: ainda não foram adicionados funcionários\n");
						break;
					}
					else {
						listNumberofNames(Nset);
						break;
					}
				case 8:
					System.out.println("Sistema a encerrar...");
					break;
			}
		}
		while(op!=8);
		
		ler.close();
		System.exit(0);

	}
	
	//Adicionar Funcionários
	public static void addEmpregado(Set<Empregado> Nset, String name, String apelido)
	{
		Empregado e = new Empregado(name, apelido);
		if(Nset.add(e))
			System.out.println("\nEmpregado adicionado com sucesso!");
		else 
			System.out.println("\nERRO: Empregado já foi adicionado!");
	}
	
	//Remover empregados
	public static void removeEmpregado(Set<Empregado> Nset, String name, String apelido)
	{
		Empregado e = new Empregado(name, apelido);
		if(Nset.remove(e))
			System.out.println("\nEmpregado removido com sucesso!");
		else
			System.out.println("\nERRO: Empregado não existe!");
		
	}
	
	//Listar empregados
	public static void listEmpregados(Set<Empregado> Nset)
	{
		if(Nset.isEmpty())
			System.out.println("\nERRO: Ainda não foram adicionados empregados");
		else {
			for(Empregado e : Nset)
				System.out.println(e.toString());
		}
		System.out.println();
	}
	
	//Sortear brinquedos
	public static LinkedList<Brinquedos> sortToy(String toyname, LinkedList<Brinquedos> Blist, Set<Empregado> Nlist)
	{
		Random r = new Random();
		boolean check = false;
		int n = r.nextInt(Nlist.size());
   
        Empregado arr[] = new Empregado[Nlist.size()]; 
        System.arraycopy(Nlist.toArray(), 0, arr, 0, Nlist.size());


        if(!Blist.isEmpty()) {
	        for(Brinquedos b : Blist) {
	        	if(arr[n].toString().equals(b.vencedor().toString())) {	
	           		check = true;
	        		break;
	        	}
	        }
        }
        
        if(!check || Blist.isEmpty()) {
        	Brinquedos br = new Brinquedos(toyname, arr[n]);
        	Blist.add(br);
        	System.out.println(br.toString());
        }
        else
        	System.out.println("ERRO: Empregado já ganhou um brinquedo");
        return Blist;
	}
	
	//Listar Vencedores do sorteio
	public static void listWinners(LinkedList<Brinquedos> Blist)
	{
		if(Blist.isEmpty())
			System.out.println("\nERRO: Ainda não foram realizados sorteios\n");
		else {
			for(Brinquedos b : Blist)
				System.out.println(b.toString());
			System.out.println();
		}
	}
	
	//Devolve uma lista com os nomes dos empregados que podem ser usados em produtos
	public static Set<String> listNamesProduct(Set<Empregado> Nset)
	{
		Set<String> slist = new TreeSet<>();
		for(Empregado N : Nset) {
			if(slist.add(N.nome()))
				System.out.println("-> "+N.nome());
		}
		System.out.println();
		return slist;
	}
	
	public static HashMap<String, Integer> listNumberofNames(Set<Empregado> Nset)
	{
		HashMap<String, Integer> Hmap = new HashMap<>();
		
		for(Empregado e : Nset) {
			if(Hmap.containsKey(e.nome()))
				Hmap.replace(e.nome(), Hmap.get(e.nome())+1);
			else
				Hmap.put(e.nome(), 1);
		}
		
		for(String s : Hmap.keySet())
			System.out.println("Nome: "+s+" - "+Hmap.get(s));
		System.out.println();
		return Hmap;
	}

}
