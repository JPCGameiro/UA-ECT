import static java.lang.System.*;
public class Room
{
	private String tipo;
	private Point a, b;
	
	public Room (Point a, Point b, String tipo)
	{
		this.a=a;
		this.b=b;
		this.tipo=tipo;
	}
	public Room()
	{
		this.a=a;
		this.b=b;
		this.tipo=tipo;
	}
	public Point a()
	{
		return a;
	}
	public Point b()
	{
		return b;
	}
	public String tipo()
	{
		return tipo;
	}
	
	//devolve o tipo de divisão
	public String roomType()
	{
		return tipo;
	}
	//devolve o canto inferior esquerdo
	public Point bottomLeft()
	{
		Point c=a;
		
		if((a.x() < b.x()) && (a.y() < b.y()))
		{
			c=a;
		}
		else if((b.x() < a.x()) && (b.y() < a.y()))
		{
			c=b;
		}
		
		return c;
	}
	//devolve o canto superior direito
	public Point topRight()
	{
		Point c=a;
		
		if((a.x() > b.x()) && (a.y() > b.y()))
		{
			c=a;
		}
		else if((b.x() > a.x()) && (b.y() > a.y()))
		{
			c=b;
		}
		return c;
	}
	//devolve o centro geométrico da divisão
	public Point geomCenter()
	{
		double aa = a.x()+b.x()/2;
		double bb = b.y()+a.y()/2;
		Point c = new Point(aa, bb);
		
		return c;
	}
	//devolve a área da sala
	public double area()
	{
		double result;
		
		result = (a.x()+b.x()) * (a.y()+b.y());
		
		return result;
	} 	
}
