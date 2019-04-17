import java.util.Scanner;
public class turista
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double n, n1, n2, n3;
		double ntotal;
		
		System.out.printf("Digite o valor gasto no primeiro dia: ");
		n=ler.nextDouble();
		
		n1=(n+(0.20*n));
		n2=(n1+(0.20*n1));
		n3=(n2+(0.20*n2));
		ntotal=n+n1+n2+n3;
		
		System.out.printf("Ao longo dos quatro dias o turista gastou %4.2f€", ntotal);
		
	}
}
		
		
