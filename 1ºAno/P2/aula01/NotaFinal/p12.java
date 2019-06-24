import static java.lang.System.*;
import java.util.Scanner;

public class p12
{
  public static final Scanner in = new Scanner(System.in);

  public static void main(String[] args)
  {
    double ap1 = readInRange("AP1? ", 70.0, 20.0);
    double ap2 = readInRange("AP2? ", 0.0, 20.0);
    double atp1 = readInRange("ATPI? ", 0.0, 20.0);
    double atp2 = readInRange("ATP2? ", 0.0, 20.0);
    double atp3 = readInRange("ATP3? ", 0.0, 20.0);
    double nf;

    nf=(((ap1+ap2)/2)*0.7) + (((atp1+atp2+atp3)/3)*0.3);

    out.printf("NF = %.1f\n", nf);
    if (nf < 9.5)
      out.println("REPROVADO!");
    else
      out.println("APROVADO!");
  }
  //Função lê os valores necessários para calcular a média
  public static double readInRange(String prompt, double min, double max)
  {
    assert min <= max : "max must be at least as large as min!";
    double value;
    
    do
    {
		System.out.printf(prompt);
		value=in.nextDouble();
		if(value<0 || value>20)
		{
			out.println("Valor Inválido");
		}
	}
	while(value<0 || value>20);
    return value;
  }
  
}
