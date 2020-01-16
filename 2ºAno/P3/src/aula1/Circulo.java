//João Gameiro   Nº93097
//class Circulo

package aula1;

public class Circulo {
	
	private double raio;
	private Ponto centro;
	
	//Construtores
	public Circulo(double x, double y, double raio)
	{
		Ponto centro = new Ponto(x, y);
		this.centro = centro;
		this.raio = raio;
	}
	public Circulo(Ponto centro, double raio)
	{
		this.raio = raio;
		this.centro = centro;
	}
	
	
	public double raio() { return raio; }
	public Ponto centro() { return centro; }
	
	
	//Função da àrea
	public double circleArea()
	{
		return Math.PI * Math.pow(raio, 2);
	}
	//Função do Perímetro
	public double circlePerimeter()
	{
		return 2 * Math.PI * raio;
	}
	//Função que devolve as características numa String
	public String toString()
	{
		return "Centro: " + centro().toString() + " | Raio: " + raio() + " | Área: " + circleArea() + " | Perímetro: " + circlePerimeter();
	}
	//Função que verifica se dois círculos são iguais (são iguais se o seu raio for igual)
	public boolean equalCircle(Circulo c)
	{
		return c.raio == raio;
	}
	//Função para verificar se dois círculos se intersetam (se a distância entre os seus centros for menor que a soma dos seus raios)
	public boolean intersectCircle(Circulo c)
	{
		return c.raio + raio > centro.distPoint(c.centro().x(), c.centro().y());
	}
	
	

}
