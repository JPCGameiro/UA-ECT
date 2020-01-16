//João Gameiro Nº93097
//P3-ECT-UA

package aula4.Ex3;
import java.util.LinkedList;

public class Video {
	
	private int id;
	private String title;
	private String categoria;
	private int idade;
	protected int available;
	private LinkedList<Integer> ratings;
	
	//Construtor
	public Video(int id, String title, String categoria, int idade, int available, LinkedList<Integer> ratings)
	{
		this.id = id;
		this.categoria = categoria;
		this.idade = idade;
		this.available = available;
		this.title = title;
		this.ratings = ratings;
	}
	
	public int id() { return id; }
	public String categoria() { return categoria; }
	public int idade() { return idade; }
	public int available() { return available; }
	public String title() { return title; }
	public LinkedList<Integer> ratings() { return ratings; }
	
	
	@Override public String toString()
	{
		return "ID: "+id+" | Título: "+title+" | Categoria: "+categoria+" | Idade: M"+idade;
	}
	//Devolve uma string com a indicação se um vídeo se encontra disponível ou não  
	public String availableString()
	{
		if(available==-1)
			return "ID: "+id+" | Título: "+title+" | Disponibilidade: Disponível";
		else
			return "ID: "+id+" | Título: "+title+" | Disponibilidade: Indisponível";
	}
	
	//Calcular a média dos ratings
	public double averageRating()
	{
		double sum=0;
		int i=0;
		if(ratings.size()==0)
			return 0;
		else {
			for(i=0;i<ratings.size();i++) {
				sum = sum + ratings.get(i);
			}
			return sum/(i);
		}
	}
	//Imprimir caracteristicas do filme incluindo a rating média
	public String ratingString()
	{
		return "ID: "+id+" | Título: "+title+" | Classificação: "+averageRating();
	}
	
}
