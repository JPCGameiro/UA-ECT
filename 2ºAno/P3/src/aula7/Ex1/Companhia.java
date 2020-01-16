//João Gameiro  Nº93097
//P3-ECT-UA

package aula7.Ex1;

public class Companhia {
	
	public String sigla;
	public String nome;
	
	//Construtor
	public Companhia(String sigla, String nome)
	{
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public String sigla() { return sigla; }
	public String nome() { return nome; }
	
	@Override public String toString()
	{
		return sigla+" "+nome;
	}

}
