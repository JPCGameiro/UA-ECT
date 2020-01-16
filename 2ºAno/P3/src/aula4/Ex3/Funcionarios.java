//João Gameiro  Nº93097
//P3-ECT-UA

package aula4.Ex3;
import java.util.LinkedList;

public class Funcionarios extends Utilizador{
	
	private int nfunc;
	private int nfisc;
	
	//Construtor
	public Funcionarios(String nome, int cc, Data dataNasc, int numsoc, LinkedList<Video> videosVistos, int nfunc, int nfisc)
	{
		super(nome, cc, dataNasc, numsoc, videosVistos);
		if(nfunc<=0 || nfisc<=0) { throw new IllegalArgumentException(); }
		this.nfunc = nfunc;
		this.nfisc = nfisc;
	}
	
	public int nfunc() { return nfunc; }
	public int nfisc() { return nfisc; }
	
	@Override public String toString()
	{
		return super.toString()+" | NºFuncionário: "+nfunc+" | NºFiscal: "+nfisc; 
	}

}
