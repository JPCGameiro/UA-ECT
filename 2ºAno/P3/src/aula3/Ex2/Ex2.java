//João Gameiro Nº93097

package aula3.Ex2;

public class Ex2 {

	public static void main(String[] args) {
		
		Circulo c1 = new Circulo (2);
		//Circulo c2 = new Circulo (1, 3, 2);
		Circulo c3 = new Circulo (c1);
		
		System.out.println(c1 + " tem area: " + c1.area() + " e perimetro: " + c1. perimetro());
		System.out.println(c3 + " tem area: " + c3.area() + " e perimetro: " + c3. perimetro());
		System.out.println("c1 equals to c3?  -> " + c1.equals(c3)); //True
		
		
		Quadrado q1 = new Quadrado(2);
		Quadrado q2 = new Quadrado(3,4,2);
		Quadrado q3 = new Quadrado(q2);
		
		System.out.println(q1 + " tem area: " + q1.area() + " e perimetro: " + q1.perimetro());
		System.out.println(q3 + " tem area: " + q3.area() + " e perimetro: " + q3.perimetro());
		System.out.println("q1 equals to q3? -> " + q1.equals(q3)); // False
		
		
		Retangulo r1 = new Retangulo(2,3);
		Retangulo r2 = new Retangulo(3,4,2,3);
		Retangulo r3 = new Retangulo(r2);
		
		System.out.println(r1 + " tem area: " + r1.area() + " e perimetro: " + r1. perimetro());
		System.out.println(r3 + " tem area: " + r3.area() + " e perimetro: " + r3. perimetro());
		System.out.println("r2 equals to r3? -> " + r2.equals(r3)); // True
		
		
	}

}
