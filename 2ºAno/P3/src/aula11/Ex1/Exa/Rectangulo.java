//João Gameiro Nº93097

package aula11.Ex1.Exa;

public class Rectangulo extends Figura{
	
	private double largura;
	private double comprimento;
	
	//Construtores
	public Rectangulo(double largura, double comprimento)
	{
		super(0,0);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Rectangulo(double x, double y, double largura, double comprimento)
	{
		super(x,y);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Rectangulo(Ponto p, double largura, double comprimento)
	{
		super(p);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Rectangulo(Rectangulo r)
	{
		super(r.centro);
		this.largura = r.largura;
		this.comprimento = r.comprimento;
	}
	
	public double largura() { return largura; }
	public double comprimento() { return comprimento; }
	
	//Função para a àrea
	public double area()
	{
		return largura*comprimento;
	}
	//Função para o perímetro
	public double perimetro()
	{
		return (largura*2) + (comprimento*2);
	}
	//Função que devolve as características numa String
	@Override public String toString()
	{
		return "Centro: " + centro().toString() + " | Comprimento: " + comprimento() + " | Largura " + largura() + " | Área: " + area() + " | Perímetro: " + perimetro();
	}
	//Função para verificar se dois retangulos são iguais
	public boolean equals(Rectangulo r)
	{
		return (r.largura == largura && r.comprimento == comprimento && r.centro == centro);
	}
	
}
