//João Gameiro   Nº93097
//class Quadrado

package aula1;

public class Quadrado {
	
	private double l;
	private Ponto centro;
	
	//Construtor
	public Quadrado(Ponto centro, double l)
	{
		this.centro = centro;
		this.l = l;
	}
	
	public Ponto centro() { return centro; }
	public double l()  { return l; }
	
	
	//Função para a àrea
	public double squareArea()
	{
		return l*l;
	}
	//Função para o Perímetro
	public double squarePerimeter()
	{
		return l*4;
	}
	//Função que devolve os dados numa String
	public String toString()
	{
		return "Centro: " + centro().toString() + " | Comprimento: " + l() + " | Área: " + squareArea() + " | Perímetro: " + squarePerimeter();
	}
	

}
