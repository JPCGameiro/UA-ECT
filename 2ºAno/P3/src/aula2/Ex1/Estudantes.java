//João Gameiro   Nº93097
//classe Estudantes

package aula2.Ex1;

import java.util.LinkedList;

public class Estudantes {
	
	private String nome;
	private int cc;
	private int numsocio;
	private int idade;
	private Data dataInsc;
	private Data dataNasc;
	private int nmec;
	private String curso;
	private LinkedList<Video> history;
	public int num;
	
	//Construtor	
	public Estudantes(String nome, int cc, int numsocio, int idade, Data dataInsc, Data dataNasc, int nmec, String curso, LinkedList<Video> history, int num)
	{
		this.nome = nome;
		this.cc = cc;
		this.numsocio = numsocio;
		this.idade = idade;
		this.dataInsc = dataInsc;
		this.dataNasc = dataNasc;
		this.nmec = nmec;
		this.curso = curso;
		this.history = history;
		this.num = num;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public int numsocio() { return numsocio; }
	public int idade() {return idade; }
	public Data dataInsc() { return dataInsc; }
	public Data dataNasc() { return dataNasc; }
	public int nmec() { return nmec; }
	public String curso() { return curso; }
	public LinkedList<Video> history() { return history; }
	public int num() { return num; }
	
	
	public String toString()
	{
		return "NºSócio: "+numsocio+" | Nome: "+nome+" | NºCC: "+cc+" | DataInscrição: "+dataInsc.toString()+" | DataNascimento: "+dataNasc.toString()+" | NºMec.:"+nmec+" | Curso: "+curso;
	}
}
