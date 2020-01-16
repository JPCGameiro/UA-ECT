//João Gameiro  Nº93097
//P3-ECT-UA

package aula4.Ex1;
import java.time.LocalDateTime;

public class Professor extends Pessoa{
	
	private int numfunc=1;
	private Data dataAdmissao;
	
	//Construtor
	public Professor(String nome, int cc, Data dataNasc, Data dataAdmissao)
	{
		super(nome, cc, dataNasc);
		this.numfunc = numfunc++;
		this.dataAdmissao = dataAdmissao;
	}
	public Professor(String nome, int cc, Data dataNasc)
	{
		super(nome, cc, dataNasc);
		this.numfunc = numfunc++;
		this.dataAdmissao = new Data (LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear());
	}
	
	
	public int numfunc() { return numfunc; }
	public Data dataAdmissao() { return dataAdmissao; }
	
	@Override public String toString()
	{
		return super.toString()+" | NºFuncionário: "+numfunc+" | Data de Admissão: "+dataAdmissao.toString();
	}

}
