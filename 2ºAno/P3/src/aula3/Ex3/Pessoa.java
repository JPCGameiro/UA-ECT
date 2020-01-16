//João Gameiro Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Pessoa {
	
	private String nome;
	private int cc;
	private Data dataNasc;
	
	
	public Pessoa(String nome, int cc, Data dataNasc)
	{
		this.nome = nome;
		this.cc = cc;
		this.dataNasc = dataNasc;
	}
	
	public String nome() { return nome; }
	public int cc() { return cc; }
	public Data dataNasc() { return dataNasc; }
	
	//Função para devolver uma String com as caracteristicas da Pessoa
	@Override public String toString()
	{
		return "Nome: "+nome+" | Cartão de Cidadão: "+cc+" | Data de Nascimento: "+dataNasc.toString();
	}

}
