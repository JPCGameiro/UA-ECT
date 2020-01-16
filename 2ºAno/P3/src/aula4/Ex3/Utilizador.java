//João Gameiro  Nº93097
//P3-ECT-UA

package aula4.Ex3;
import java.util.LinkedList;

public class Utilizador {
	
	protected String nome;
	protected int cc;
	protected Data dataNasc;
	protected int numsoc;
	protected LinkedList<Video> videosVistos;
	protected int currentVideos;
	
	//Construtor
	public Utilizador(String nome, int cc, Data dataNasc, int numsoc, LinkedList<Video> videosVistos)
	{
		this.nome = nome;
		this.cc = cc;
		this.dataNasc = dataNasc;
		this.numsoc = numsoc;
		this.videosVistos = videosVistos;
		this.currentVideos = 0;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public Data dataNasc() { return dataNasc; }
	public int numsoc() { return numsoc; }
	public LinkedList<Video> videosVistos() { return videosVistos; }
	public int currentVideos() { return currentVideos; }
	
	@Override public String toString()
	{
		return "NºSócio: "+numsoc+" | Nome: "+nome+" | DataNascimento: "+dataNasc+" | NºCC: "+cc;
	}
	//Devolve uma string com os vídeos vistos e as suas caracteristicas
	public String vistosString()
	{
		String result = "";
		if(videosVistos.size()==0)
			result = "O utilizador ainda não requistou nenhum filme\n";
		else {
			for(int i=0;i<videosVistos.size();i++) {
				result = result + videosVistos.get(i).toString();
			}				
		}
		return result;
	}
	
}
