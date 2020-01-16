//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex3;

public class Empregado implements Comparable<Empregado> {

	private String nome;
	private String apelido;
	
	//Constutor
	public Empregado(String nome, String apelido)
	{
		this.nome = nome;
		this.apelido = apelido;
	}
	
	public String nome() { return nome; }
	public String apelido() { return apelido; }
	
	@Override
	public String toString() {
		return "Nome: "+nome+" "+apelido;
	}

	@Override
	public int compareTo(Empregado e) {
		String t0 = e.nome()+e.apelido;
		String t1 = nome()+apelido();
		
		return t1.compareTo(t0);
	}
}
