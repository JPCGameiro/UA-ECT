import static java.lang.System.*;
import java.util.Scanner;
public class TestComplex {
  // Exemplo simples de utilização da class Complex
  public static void main(String[] args) 
  {
	Scanner ler = new Scanner (System.in);
	double r, i;
	int answer;
	
	do
	{
		out.printf("Re: ");
		r=ler.nextDouble();
		out.printf("Im: ");
		i=ler.nextDouble();
	
		Complex a = new Complex( r, i); 
		// Vamos usar métodos do objeto a
		out.println("(" + a.real() + " + " + a.imag() + "i)");
		out.println("  parte real = " + a.real());
		out.println("  parte imaginaria = " + a.imag());
		out.println("  modulo = " + a.abs());
		out.printf("  argumento =  %2.2f\n", a.arg());
		do
		{
			out.printf("Deseja repetir?(1/0) ");
			answer=ler.nextInt();
			if(answer!=1 && answer!=0)
			{
				System.out.printf("Opção Inválida\n");
			}
		}
		while(answer!=0 && answer!=1);
		out.println();
	}
	while(answer==1);
	out.println("Obrigado Boa Continuação");
  }

}
