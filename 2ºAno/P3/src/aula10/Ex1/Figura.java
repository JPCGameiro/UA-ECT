//João Gameiro Nº93097

package aula10.Ex1;

public abstract class Figura implements Comparable<Figura>{
	
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
	
	public int compareTo(Figura f)
	{
		if((this.area() - f.area())<0)
			return -1;
		else if((this.area() - f.area())>0)
			return 1;
		else
			return 0;
	}
}
