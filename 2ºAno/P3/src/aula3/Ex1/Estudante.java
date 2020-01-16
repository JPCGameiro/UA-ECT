//João Gameiro Nº93097

package aula3.Ex1;
import java.time.LocalDateTime;

public class Estudante extends Pessoa{
	
	private int nmec;
	private Data dataInsc;
	static int NextNmec = 100;
	
	//Construtores
	public Estudante(String nome, int cc, Data dataNasc, Data dataInsc)
	{
		super(nome, cc, dataNasc);
		this.dataInsc = dataInsc;
		this.nmec = NextNmec;
		NextNmec++;
	}
	public Estudante(String nome, int cc, Data dataNasc)
	{
		super(nome, cc, dataNasc);
		this.dataInsc = new Data (LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear());
		this.nmec = NextNmec;
		NextNmec++;
		
	}
	
	public int nmec() { return nmec; }
	public Data dataInsc() { return dataInsc; }
	
	@Override public String toString()
	{
		return super.toString()+" | Nmec: "+nmec+" | Data de Inscrição: "+dataInsc.toString();
	}

}
