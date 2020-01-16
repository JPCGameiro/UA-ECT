//João Gameiro Nº93097

package aula12.Ex1;

public class Quadrado extends Rectangulo{
	
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
		return "Quadrado| Centro: " + centro().toString() + " | Comprimento: " + l() + " | Área: " + area() + " | Perímetro: " + perimetro();
	}
	//Função que verifica se dois quadrados são iguais
	public boolean equals(Quadrado q)
	{
		return q.l == l && (q.centro == centro);
	}
}
