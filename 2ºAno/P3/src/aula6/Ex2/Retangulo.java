//João Gameiro Nº93097

package aula6.Ex2;

public class Retangulo extends Figura{
	
	private double largura;
	private double comprimento;
	
	//Construtores
	public Retangulo(double largura, double comprimento)
	{
		super(0,0);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Retangulo(double x, double y, double largura, double comprimento)
	{
		super(x,y);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Retangulo(Ponto p, double largura, double comprimento)
	{
		super(p);
		this.largura = largura;
		this.comprimento = comprimento;
	}
	public Retangulo(Retangulo r)
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
		return "Rectângulo| Centro: " + centro().toString() + " | Comprimento: " + comprimento() + " | Largura " + largura() + " | Área: " + area() + " | Perímetro: " + perimetro();
	}
	//Função para verificar se dois retangulos são iguais
	public boolean equals(Retangulo r)
	{
		return (r.largura == largura && r.comprimento == comprimento && r.centro == centro);
	}
	
}
