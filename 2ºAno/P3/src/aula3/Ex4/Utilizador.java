//João Gameiro  Nº93097
//P3-ECT-UA

package aula3.Ex4;
import java.util.LinkedList;

public class Utilizador {
	
	protected String nome;
	protected int cc;
	protected Data dataNasc;
	protected int numsoc;
	protected LinkedList<Video> videosVistos;
	
	//Construtor
	public Utilizador(String nome, int cc, Data dataNasc, int numsoc, LinkedList<Video> videosVistos)
	{
		this.nome = nome;
		this.cc = cc;
		this.dataNasc = dataNasc;
		this.numsoc = numsoc;
		this.videosVistos = videosVistos;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public Data dataNasc() { return dataNasc; }
	public int numsoc() { return numsoc; }
	public LinkedList<Video> videosVistos() { return videosVistos; }
	
	@Override public String toString()
	{
		return "NºSócio: "+numsoc+" | Nome: "+nome+" | DataNascimento: "+dataNasc+" | NºCC: "+cc;
	}
}
