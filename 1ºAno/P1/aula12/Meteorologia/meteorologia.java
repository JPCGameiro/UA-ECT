import java.util.Scanner;
import java.io.*;
public class meteorologia
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner(System.in);
		int opc;
		Temperatura a[] = new Temperatura[31];
		
		System.out.printf("Estação meteorológica: \n");
		System.out.printf("1 - Ler ficheiro de dados\n");
		System.out.printf("2 - Acrescentar medidas\n");
		System.out.printf("3 - Listar valores de temperatura e humidade\n");
		System.out.printf("4 - Listar medidas ordenadas por valor de temperatura\n");
		System.out.printf("5 - Listar medidas ordenadas por valor de humidade\n");
		System.out.printf("6 - Calcular valores médios de temperatura e humidade\n");
		System.out.printf("7 - Calcular valores máximos e mínimos de temperatura e humidade\n");
		System.out.printf("8 - Calcular histograma das temperaturas e humidade\n");
		System.out.printf("9 - Terminar o programa\n");
		
		do
		{
			System.out.printf("Opção -> ");
			opc=ler.nextInt();
			
			switch (opc)
			{
				case 1:
				LerFicheiro(a);
				break;
				
				case 9:
				System.out.printf("\nA encerrar...");
				break;
			}
		}
		while(opc!=9);
	}
	
	public static void LerFicheiro(Temperatura a[]) throws IOException 
	{
		Scanner ler = new Scanner(System.in);
		String fichname;
		int i=0, z=0;
		File fich;
		
		do
		{
			System.out.printf("Insira o nome de um ficheiro válido: ");
			fichname=ler.nextLine();
			fich = new File(fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner lerf = new Scanner(fich);
		
		do
		{

			if(i%2==0)
			{
				f.temperatura=ler.nextInt();
				a[d].temperatura=f.temperatura();
				i++;
			}
			else if(i%2!=0)
			{
				f.humidade=ler.nextInt();
				i++;
			}
				
		}
		while(lerf.hasNextLine());
		lerf.close();
	}
		
}

class Temperatura
{
	int temperatura;
	int humidade;
}
