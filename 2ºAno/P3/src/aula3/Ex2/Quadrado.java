//João Gameiro Nº93097

package aula3.Ex2;

public class Quadrado extends Figura{
	
	private double l;
	
	//Construtores
	public Quadrado(double l)
	{
		super(0, 0);
		this.l = l;
	}
	public Quadrado(double x, double y, double l)
	{
		super(x, y);
		this.l = l;
	}
	public Quadrado(Ponto p, double l)
	{
		super(p);
		this.l = l;
	}
	public Quadrado(Quadrado q)
	{
		super(q.centro);
		this.l = q.l;
	}
	
	public double l() { return l; }
	
	//Função para a àrea
	public double area()
	{
		return l*l;
	}
	//Função para o Perímetro
	public double perimetro()
	{
		return l*4;
	}
	//Função que devolve os dados numa String
	@Override public String toString()
	{
		return "Centro: " + centro().toString() + " | Comprimento: " + l() + " | Área: " + area() + " | Perímetro: " + perimetro();
	}
	//Função que verifica se dois quadrados são iguais
	public boolean equals(Quadrado q)
	{
		return q.l == l && (q.centro == centro);
	}
}
