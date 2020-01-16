//João Gameiro   Nº93097
//class Utilizador

package aula2.Ex1;
import java.util.LinkedList;

public class Utilizador {
	
	private LinkedList<Funcionarios> flist;
	private LinkedList<Estudantes> elist;
	
	public Utilizador(LinkedList<Funcionarios> flist, LinkedList<Estudantes> elist)
	{
		this.flist = flist;
		this.elist = elist;
	}
	
	public LinkedList<Funcionarios> flist() { return flist; }
	public LinkedList<Estudantes> elist() { return elist; }

}
