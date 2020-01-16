//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex2;

public class Copo extends GeladoDecorador{
	
	//Construtor
	public Copo(Gelado g)
	{
		super(g);
	}
	
	@Override public void base(int i)
	{
		g.base(i);
		System.out.print(" em copo");
	}
}
