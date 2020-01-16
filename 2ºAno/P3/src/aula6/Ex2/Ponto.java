//João Gameiro Nº93097

package aula6.Ex2;

public class Ponto {
	
	private double x;
	private double y;
	
	//Construtores
	public Ponto(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double x() { return x; }
	public double y() { return y; }
	
	//Função para calcular a distância entre dois pontos
	public double distPoint(double x1, double y1)
	{
		return Math.sqrt(Math.pow(x-x1, 2) + Math.pow(y-y1, 2));
	}
	//Função que devolve o ponto numa String
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	//Função que verifica se dois pontos são iguais
	public boolean equals(Ponto p)
	{
		return (x==p.x && y==p.y);
	}
	
	
}
