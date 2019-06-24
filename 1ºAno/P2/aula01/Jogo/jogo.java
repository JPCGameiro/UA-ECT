import static java.lang.System.*;
import java.util.Scanner;

public class jogo
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner (System.in);
		int atempt, i=0;
		int secret=(int)(Math.random()*(100+1));
		
		out.printf("Tenta Adivinhar o número entre 0 e 100");
		do
		{
			out.printf("\nNúmero: ");
			atempt=sc.nextInt();
			if(atempt<0 || atempt>100)
			{
				out.println("Número Inválido");
			}
			if(atempt>secret && atempt<=100 && atempt>=0 )
			{
				out.printf("Muito Alto");
			}
			else if(atempt<secret && atempt<=100 && atempt>=0)
			{
				out.printf("Muito Baixo");
			}
			i++;
		}
		while(atempt!=secret);
		out.println("Parabéns conseguiste adivinhar o número!!!");
		out.printf("%d tentativas", i);
	}
}

