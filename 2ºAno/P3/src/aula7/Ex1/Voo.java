//João Gameiro  Nº93097
//P3-ECT-UA

package aula7.Ex1;

public class Voo {
	
	private String nome;
	private Hora hora;
	private Companhia companhia;
	private String origem;
	private Hora atraso;
	
	//Construtor
	public Voo(Hora hora, Companhia companhia, String origem, String nome)
	{
		this.hora = hora;
		this.origem = origem;
		this.companhia = companhia;
		atraso = null;
		this.nome = nome;
	}
	public Voo(Hora hora, Companhia companhia, String origem, Hora atraso, String nome)
	{
		this.hora = hora;
		this.origem = origem;
		this.companhia = companhia;
		this.atraso = atraso;
		this.nome = nome;
	}
	
	public Hora hora() { return hora; }
	public Companhia companhia() { return companhia; }
	public String origem() { return origem; }
	public Hora atraso() { return atraso; }
	public String nome() { return nome; }
	
	@Override public String toString()
	{
		if(atraso!=null)
			return String.format("%s\t%-10s\t%-18s\t%-20s\t%-5s\t", hora.toString(), nome, companhia.toString(), origem, atraso.toString());
			
		else
			return String.format("%s\t%-10s\t%-18s\t%-20s\t", hora.toString(), nome, companhia.toString(), origem);
	}
	

}
