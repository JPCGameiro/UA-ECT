//João Gameiro Nº93097

package aula12.Ex1;

public class Circulo extends Figura{

	private double raio;
	
	//Construtores
	public Circulo(Ponto centro, double raio)
	{
		super(centro);
		this.raio = raio;
	}
	public Circulo(double raio)
	{
		super(0,0);
		this.raio = raio;
	}
	public Circulo(double x, double y, double raio)
	{
		super(x, y);
		this.raio = raio;
	}
	public Circulo(Circulo c)
	{
		super(c.centro);
		this.raio = c.raio;
	}
	
	public double raio() { return raio; }
	
	//Função da àrea
	public double area()
	{
		return Math.PI * Math.pow(raio, 2);
	}
	//Função do Perímetro
	public double perimetro()
	{
		return 2 * Math.PI * raio;
	}
	//Função que devolve as características numa String
	@Override public String toString()
	{
		return "Círculo| Centro: " + centro().toString() + " | Raio: " + raio() + " | Área: " + area() + " | Perímetro: " + perimetro();
	}
	//Função que verifica se dois círculos são iguais (são iguais se o seu raio for igual)
	public boolean equals(Circulo c)
	{
		return c.raio == raio;
	}
	//Função para verificar se dois círculos se intersetam (se a distância entre os seus centros for menor que a soma dos seus raios)
	public boolean intersectCircle(Circulo c)
	{
		return c.raio + raio > centro.distPoint(c.centro().x(), c.centro().y());
	}
}
