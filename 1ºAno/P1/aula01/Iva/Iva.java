import java.util.Scanner;
public class Iva
{
	public static void main (String[] args)
	{
		Scanner xd = new Scanner (System.in);
		double custo, desconto, iva;
		double valorfinal;
		
		System.out.printf("Digite o custo total : ");
		custo=xd.nextDouble();
		System.out.printf("Digite a percentagem de desconto a aplicar : ");
		desconto=xd.nextDouble();
		System.out.printf("Digite a pergentagem de iva a aplicar : ");
		iva=xd.nextDouble();
		
		desconto = (desconto/100);
		desconto = custo*desconto;
		iva = (iva/100);
		iva=custo*iva;
		valorfinal=  custo - desconto + iva;
		
		System.out.printf("O valor final a pagar é %4.2f ", valorfinal);
	}
}
