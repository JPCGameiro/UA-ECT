import java.util.Scanner;
import java.lang.Math;

public class mediadesvio
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		
		System.out.print("Insira quantos números quer inserir: ");
		int N = ler.nextInt();
		
		int[] array = new int[N];
		
		int media = 0; 
		double desvio = 0;
		
		for(int z=0;z<N;z++){
			System.out.print("Insira um número: ");
			array[z] = ler.nextInt();
		}
		
		for(int i=0;i<N;i++)
			media +=array[i];
		media = media/N;
		
		for(int j=0;j<N;j++)
			desvio += Math.pow((array[j] - media), 2);
			
		desvio = Math.sqrt(desvio/N);
		
		System.out.println("Média: "+media);
		System.out.println("Desvio: "+desvio);
	}
}
