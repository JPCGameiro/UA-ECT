//João Gameiro   Nº93097
//Problema 1.3

package aula1;
import java.util.*;

public class Ex3 {
	
	static Scanner ler = new Scanner(System.in);

	public static void main(String[] args) {
		
		int option=-1;
		
		//Menu
		do
		{
			System.out.println("--------------------------");
			System.out.println("1-> Criar um círculo      ");
			System.out.println("2-> Criar um quadrado     ");
			System.out.println("3-> Criar um retângulo    ");
			System.out.println("4-> Igualdade de círculos ");
			System.out.println("5-> Interseção de círculos");
			System.out.println("0-> Terminar              ");
			System.out.println("--------------------------");
			System.out.print("\nOpção: ");
			option = ler.nextInt();
			
			switch(option)
			{
			    case 1:
			    	Circle();
			    	break;
			    
			    case 2:
			    	Square();
			    	break;
			    
			    case 3:
			    	Rectangle();
			    	break;
			    
			    case 4:
			    	circleCompare();
			    	break;
			    
			    case 5:
			    	circleIntersect();
			    	break;
			    	
				case 0:
					System.out.println("Sistema a encerrar...");
					ler.close();
					System.exit(0);
					break;
				
				default:
					System.out.println("ERRO: Opção Inválida\n");
					break;
			}
		}
		while(option!=0);

	}
	
	
	//------------------FUNÇÕES ADICIONAIS-----------------------//
	
	//Círculo
	public static void Circle()
	{
		ler.nextLine();
		double x, y, ra;
		
		System.out.print("Insira a Abcissa do centro (x): ");
		x = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y = ler.nextDouble();
		System.out.print("Insira o raio do círculo: ");
		ra = ler.nextDouble();
		
		Circulo circle = new Circulo(x, y, ra);
		System.out.println(circle.toString());
	}
	
	//Quadrado
	public static void Square()
	{
		ler.nextLine();
		double l, x, y;
		
		System.out.print("Insira a Abcissa do centro (x): ");
		x = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y = ler.nextDouble();
		System.out.print("Insira o lado do quadrado: ");
		l = ler.nextDouble();
		
		Ponto p = new Ponto(x, y);
		Quadrado square = new Quadrado(p, l);
		System.out.println(square.toString());
	}
	
	//Retângulo
	public static void Rectangle()
	{
		ler.nextLine();
		double c, l, x, y;
		
		System.out.print("Insira a Abcissa do centro (x): ");
		x = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y = ler.nextDouble();
		System.out.print("Insira o comprimento do retângulo: ");
		c = ler.nextDouble();
		System.out.print("Insira a largura do retângulo: ");
		l = ler.nextDouble();
		
		Ponto p = new Ponto(x, y);
		Retangulo rectangle = new Retangulo(l, c, p);
		System.out.println(rectangle.toString());		
	}
	
	//Testar a se dois círculos são iguais
	public static void circleCompare()
	{
		ler.nextLine();
		double x, y, ra, x1, y1, ra1;
		
		System.out.println("Círculo1");
		System.out.print("Insira a Abcissa do centro (x): ");
		x = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y = ler.nextDouble();
		System.out.print("Insira o raio do círculo: ");
		ra = ler.nextDouble();
		Circulo c1 = new Circulo (x, y, ra);
		
		System.out.println("Círculo2");
		System.out.print("Insira a Abcissa do centro (x): ");
		x1 = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y1 = ler.nextDouble();
		System.out.print("Insira o raio do círculo: ");
		ra1 = ler.nextDouble();
		Circulo c2 = new Circulo (x1, y1, ra1);
		
		System.out.println("Os dois círculos são iguais? " + c1.equalCircle(c2));
	}
	
	//Testar se dois círculos se intersetam
	public static void circleIntersect()
	{
		ler.nextLine();
		double x, y, ra, x1, y1, ra1;
		
		System.out.println("Círculo1");
		System.out.print("Insira a Abcissa do centro (x): ");
		x = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y = ler.nextDouble();
		System.out.print("Insira o raio do círculo: ");
		ra = ler.nextDouble();
		Circulo c1 = new Circulo (x, y, ra);
		
		System.out.println("Círculo2");
		System.out.print("Insira a Abcissa do centro (x): ");
		x1 = ler.nextDouble();
		System.out.print("Insira a Ordenada do centro(y): ");
		y1 = ler.nextDouble();
		System.out.print("Insira o raio do círculo: ");
		ra1 = ler.nextDouble();
		Circulo c2 = new Circulo (x1, y1, ra1);
		
		System.out.println("Os círculos interseptam-se? " + c1.intersectCircle(c2));
	}
}
