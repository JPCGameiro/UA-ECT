import java.util.Scanner;
public class NotaProgramacao
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double tp1, tp2, ap1, ep;
		double notafinal;
		
		System.out.printf("Digite a nota do primeiro teste teórico prático : ");
		tp1=ler.nextDouble();
		tp1=tp1*0.15;
		System.out.printf("Digite a nota do segundo teste teórico prático : ");
		tp2=ler.nextDouble();
		tp2=tp2*0.15;
		System.out.printf("Digite a nota do projeto final : ");
		ap1=ler.nextDouble();
		ap1=ap1*0.30;
		System.out.printf("Digite a nota do exame final : ");
		ep=ler.nextDouble();
		ep=ep*0.4;
		
		notafinal=tp1+tp2+ap1+ep;
		
		System.out.printf("A média do aluno foi de %2.2f ",notafinal);
	}
}
