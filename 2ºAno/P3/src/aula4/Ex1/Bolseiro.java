//João Gameiro Nº93097
//P3-ECT-UA

package aula4.Ex1;

public class Bolseiro extends Estudante{
	private int bolsa;
	
	//Construtores
	public Bolseiro(String nome, int cc, Data dataNasc)
	{
		super(nome, cc, dataNasc);
	}
	public Bolseiro(String nome, int cc, Data dataNasc, Data dataInsc)
	{
		super(nome, cc, dataNasc, dataInsc);
	}
	
	public int bolsa() { return bolsa; }
	public void setBolsa(int bolsa) { this.bolsa = bolsa; }
	
	@Override public String toString()
	{
		return super.toString()+" | Bolsa: "+bolsa+" €";
	}
	

}