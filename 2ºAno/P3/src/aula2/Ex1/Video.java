//João Gameiro   Nº93097
//classe video

package aula2.Ex1;
import java.util.LinkedList;

public class Video {
	
	private int id;
	private String titulo;
	private String categoria;
	int idade;
	int available;                //vai ser preenchido por um número de sócio se for requisitado
	LinkedList<Integer> ratings;  //Lista para armazenar as classificação dadas aos filmes
	
	
	//Constructor
	public Video(int id, String titulo, String categoria, int idade, int available, LinkedList<Integer> ratings)
	{
		this.id = id;
		this.titulo = titulo;
		this.categoria = categoria;
		this.idade = idade;
		this.available = available;
		this.ratings = ratings;
	}
	
	public int id() { return id; }
	public String titulo() { return titulo; }
	public String categoria() { return categoria; }
	public int idade() { return idade; } 
	public int available() { return available; }
	public LinkedList<Integer> ratings() { return ratings; }
	
	public String toString()
	{
		return "ID: "+id+" | Título: "+titulo+" | Categoria: "+categoria+" | Idade: M"+idade;
	}
	
	public double midRating()
	{
		double result = 0;
		int i;
		if(ratings.size()==0) {
			return 0;
		}
		else {
			for(i=0;i<ratings.size();i++) {
				result = result + ratings.get(i);
			}
			return result/(i);
		}
	}
	
	public String ratingString()
	{
		if(ratings.size()>=0)
			return "ID: "+id+" | Título: "+titulo+" | Rating: "+midRating();
		else
			return "Ainda não foram efectuadas quaisqueres avaliações aos filmes";
	}
	
}
