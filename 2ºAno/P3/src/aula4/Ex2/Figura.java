//João Gameiro Nº93097

package aula4.Ex2;

public abstract class Figura {
	
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
	
	public abstract double area();
	public abstract double perimetro();
	
}
