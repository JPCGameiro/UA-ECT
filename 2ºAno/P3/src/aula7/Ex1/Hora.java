//João Gameiro  Nº93097
//P3-ECT-UA

package aula7.Ex1;

public class Hora {
	
	private int h;
	private int m;
	
	//Construtor
	public Hora(int h, int m)
	{
		if(!validHora(h,m))
			throw new IllegalArgumentException();
		this.h=h;
		this.m = m;
	}
	
	public int h() { return h; }
	public int m() { return m; }
	
	public boolean validHora(int h, int m)
	{
		if(h>=0 && h<=23) {
			if(m>=0 && m<=59)
				return true;
			else
				return false;
		}
		else 
			return false;
	}
	
	@Override public String toString()
	{
		return h+":"+m;
	}

}
