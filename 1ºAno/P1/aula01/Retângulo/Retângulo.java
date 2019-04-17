import java.util.Scanner;
public class Retângulo
{
	public static void main (String[] args)
	{
		// Scanner para leitura de dados do teclado
		Scanner sc = new Scanner(System.in);
		double lado1, lado2;                 // Variáveis de Entrada
		double area, perimetro;       //Variáveis de Saída
		
		// 1) Ler os dados do teclado
		System.out.printf("Digite o lado : ");
		lado1 = sc.nextDouble();
		System.out.printf("Digie o outro lado : ");
		lado2 = sc.nextDouble();
		
		// 2)Calcular
		area = lado1*lado2;
		perimetro = (lado1 + lado2)*2;
		
		// 3) Mostrar os resultados
		System.out.printf("A área do quadrado é %5.2f e o perímetro %5.2f ", area, perimetro);
	}
}
