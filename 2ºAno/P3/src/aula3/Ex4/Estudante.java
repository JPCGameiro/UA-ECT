//João Gameiro  Nº93097
//P3-ECT-UA

package aula3.Ex4;
import java.util.LinkedList;

public class Estudante extends Utilizador{
	
	private int nmec;
	private String curso;
	
	//Construtor
	public Estudante(String nome, int cc, Data dataNasc, int numsoc, LinkedList<Video> videosVistos, int nmec, String curso)
	{
		super(nome, cc, dataNasc, numsoc, videosVistos);
		if(nmec<=0) { throw new IllegalArgumentException(); }
		this.nmec = nmec;
		this.curso = curso;
	}
	
	public int nmec() { return nmec; }
	public String curso() { return curso; }
	
	@Override public String toString()
	{
		return super.toString()+" | NºMecanográfico: "+nmec+" | Curso: "+curso; 
	}
}
