//João Gameiro   Nº93097
//class Retangulo

package aula1;

public class Retangulo {
	
	private double largura;
	private double comprimento;
	private Ponto centro;
	
	//Constutor
	public Retangulo(double largura, double comprimento, Ponto centro)
	{
		this.largura = largura;
		this.comprimento = comprimento;
		this.centro = centro;
	}
	
	public double largura() { return largura; }
	public double comprimento() { return comprimento; }
	public Ponto centro() { return centro; }
	
	
	//Função para a àrea
	public double rectangleArea()
	{
		return largura*comprimento;
	}
	//Função para o perímetro
	public double rectanglePerimeter()
	{
		return (largura*2) + (comprimento*2);
	}
	//Função que devolve as características numa String
	public String toString()
	{
		return "Centro: " + centro().toString() + " | Comprimento: " + comprimento() + " | Largura " + largura() + " | Área: " + rectangleArea() + " | Perímetro: " + rectanglePerimeter();
	}

}
