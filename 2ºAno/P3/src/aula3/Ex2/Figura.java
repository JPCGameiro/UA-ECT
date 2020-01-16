//João Gameiro Nº93097

package aula3.Ex2;

public class Figura {
	
	protected Ponto centro;
	
	//Construtores
	public Figura(Ponto centro)
	{
		this.centro = centro;
	}
	public Figura(double x, double y)
	{
		this.centro = new Ponto(x, y);
	}
	
	public Ponto centro() { return centro; }
	
}
