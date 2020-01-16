//João Gameiro Nº93097
//classe Pessoa

package aula6.Ex2;

public class Pessoa {
	private String nome;
	private int cc;
	private Data dataNasc;
	
	
	//Construtor
	public Pessoa(String nome, int cc, Data dataNasc)
	{
		this.nome = nome;
		this.cc = cc;
		this.dataNasc = dataNasc;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public Data dataNasc() { return dataNasc; }
	
	//Função para devolver uma String com os dados de uma pessoa
	@Override public String toString()
	{
		return "Nome: " + nome + " | Cartão de Cidadão: " + cc + " | Data de Nascimento: " + dataNasc.toString();
	}

}
