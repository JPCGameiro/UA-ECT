//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex1;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class Test {

	public static void main(String[] args) {
		
		Localidade cid1 = new Localidade("Szohod", 31212, TipoLocalidade.Cidade);
		Localidade cid2 = new Localidade("Wadesdah", 23423, TipoLocalidade.Cidade);
		//Localidade cid3 = new Localidade("BedRock", 23423, TipoLocalidade.Vila);
		Localidade cid4 = new Localidade("Lisboa", 225543, TipoLocalidade.Cidade);
		Localidade cid5 = new Localidade("Washington", 444444, TipoLocalidade.Cidade);
		Localidade cid6 = new Localidade("Berlim", 124536, TipoLocalidade.Cidade);
		Estado est1 = new Estado("North Borduria", 223133, cid1);
		Estado est2 = new Estado("South Borduria", 84321, cid2);
		
		Pais p1 = new Pais("Borduria", est1.capital());
		Pais p2 = new Pais("Khemed", cid2);
		//p2 = new Pais("Khemed", cid3);   //descomentar para ver a IllegalArgumentException
		Pais p3 = new Pais("Aurelia");
		Pais p4 = new Pais("Atlantis");
		Pais p5 = new Pais("Portugal", cid4);
		Pais p6 = new Pais("USA", cid5);
		Pais p7 = new Pais("Germany", cid6);
		Pais p8 = new Pais(p7);
		Pais p9 = new Pais(p5);
		p1.addRegiao(est1);
		p1.addRegiao(est2);
		p2.addRegiao(new Provincia("Afrinia", 232475, "Aluko Pono"));
		p2.addRegiao(new Provincia("Eriador", 100000, "Dumpgase Liru"));
		p2.addRegiao(new Provincia("Laurania", 30000, "Mukabamba Dabba"));
		p6.addRegiao(new Provincia("Seatle", 334569, "Daniel Jackson Evans"));
		p5.addRegiao(new Provincia("Pombal", 23456, "Diogo mateus"));
		p5.addRegiao(new Provincia("Aveiro", 5454789, "Ribau Esteves"));
		p9.addRegiao(new Provincia("Pombal", 23456, "Diogo mateus"));
		p9.addRegiao(new Provincia("Aveiro", 5454789, "Ribau Esteves"));
		
		List<Pais> org = new ArrayList<Pais>();
		org.add(p1);
		org.add(p2);
		org.add(p3);
		org.add(p4);
		org.add(p5);
		org.add(p6);
		org.add(p7);
		org.add(p8);
		org.add(p9);
		
		System.out.println("----Iterar sobre o conjunto");
		Iterator<Pais> itr = org.iterator();
		while (itr.hasNext())
			System.out.println(itr.next());
		
		System.out.println("-------Iterar sobre o conjunto - For each (java 8)");
		for (Pais pais: org)
			System.out.println(pais);
		
		// adicionar, remover, ordenar, garantir elementos únicos
		
		System.out.println("\n-----Lista de Países ordenada por ordem alfabética---\n");
		sortArrayName(org);
		
		System.out.println("\n-----Lista de Países ordenada por ordem de População---\n");
		sortArrayPopulation(org);
		
		System.out.println("\n-----Set de Países sem repetições---\n");
		for(Pais p : countrySet(org))
			System.out.println(p);
		
	}
	
	//Imprimir ArrayList ordenado pelo nome do país
	public static void sortArrayName(List<Pais> l)
	{
		 l.stream() 
	        .sorted((p1, p2)->p1.nome().compareTo(p2.nome())) 
	        .forEach(System.out::println); 
	}
	//Imprimir ArrayList ordenado pelo número de habitantes
	public static void sortArrayPopulation(List<Pais> l)
	{
		l.stream() 
	        .sorted((p1, p2)->p1.compareTo(p2)) 
	        .forEach(System.out::println); 		
	}
	//Devolver um set sem países repetidos
	public static Set<Pais> countrySet(List<Pais> l)
	{
		Set<Pais> s = new HashSet<Pais>();
		for(Pais p : l)
			s.add(p);
		return s;
			
	}

}
