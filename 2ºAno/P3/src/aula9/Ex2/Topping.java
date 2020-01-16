//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex2;

public class Topping extends GeladoDecorador{
	
	private String topping;
	
	//Construtor
	public Topping(Gelado g , String topping)
	{
		super(g);
		this.topping = topping;
	}
	
	public String topping() { return topping; }
	
	@Override public void base(int i)
	{
		g.base(i);
		System.out.print(" com "+topping);
	}
	
	
		
}
