//João Gameiro   Nº93097
//classe Funcionarios

package aula2.Ex1;
import java.util.LinkedList;

public class Funcionarios {
	
	private String nome;
	private int cc;
	private int numsocio;
	private int idade;
	private Data dataInsc;
	private Data dataNasc;
	private int numfunc;
	private int numfiscal;
	private LinkedList<Video> history;
	public int num;
	
	
	//Construtor
	public Funcionarios(String nome, int cc, int numsocio, int idade, Data dataInsc, Data dataNasc,int numfunc, int numfiscal, LinkedList<Video> history, int num)
	{
		this.nome = nome;
		this.cc = cc;
		this.numsocio = numsocio;
		this.idade = idade;
		this.dataInsc = dataInsc;
		this.dataNasc = dataNasc;
		this.numfunc = numfunc;
		this.numfiscal = numfiscal;
		this.history = history;
		this.numfiscal = num;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public int numsocio() { return numsocio; }
	public int idade() {return idade; }
	public Data dataInsc() { return dataInsc; }
	public Data dataNasc() { return dataNasc; }
	public int numfunc() { return numfunc; }
	public int numfiscal() { return numfiscal; }
	public LinkedList<Video> history() { return history; }
	public int num() { return num; }
	
	public String toString()
	{
		return "NºSócio: "+numsocio+" | Nome: "+nome+" | NºCC: "+cc+" | DataInscrição: "+dataInsc.toString()+" | DataNascimento: "+dataNasc.toString()+" | NºFuncionário:"+numfunc+" | NºFiscal: "+numfiscal;
	}

}
