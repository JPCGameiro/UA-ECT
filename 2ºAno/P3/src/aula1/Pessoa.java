//João Gameiro   Nº93097
//class Pessoa

package aula1;

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
	public String toString()
	{
		String result = "Nome: " + nome + " | Cartão de Cidadão: " + cc + " | Data de Nascimento: " + dataNasc.toString();
		return result;
	}

}
