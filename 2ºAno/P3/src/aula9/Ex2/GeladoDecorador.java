//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex2;

abstract class GeladoDecorador implements Gelado{
	
	protected  Gelado g;
	
	public GeladoDecorador(Gelado g)
	{
		this.g=g;
	}
	
	public void base(int i)
	{
		g.base(i);
	}
}
