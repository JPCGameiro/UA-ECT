//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;

public class Pessoa {
	
	private String nome;
	private int num;
	private Data data;
	
	//Construtor
	public Pessoa(String nome, int num, Data data)
	{
		this.nome = nome;
		this.num = num;
		this.data = data;
	}
	
	public String nome() { return nome; }
	public int num() { return num; }
	public Data data() { return data; }
	
	@Override public String toString()
	{
		return "Nome: "+nome+" | Número: "+num+" | DataNascimento: "+data.toString();
	}
}
