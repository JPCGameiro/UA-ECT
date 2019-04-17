import java.util.Scanner;
public class Nota
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		double nota1, nota2, nota3;
		double media;
		
		System.out.printf("Digite a nota do primeiro teste: ");
		nota1=ler.nextDouble();
		System.out.printf("Digite a nota do segundo teste: ");
		nota2=ler.nextDouble();
		System.out.printf("Digite a nota do terceiro teste: ");
		nota3=ler.nextDouble();
		
		media = (nota1*0.2) + (nota2*0.3) + (nota3*0.5);
		
		if (media<9.5)
		{
			System.out.printf("O aluno reprovou com média de %2.2f ", media);
		}
		else if (media>=9.5) 
		{
		
			System.out.printf("O aluno transitou com média de %2.2f ", media);
		}
	}
}
