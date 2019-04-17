import java.util.Scanner;
public class intervalos
{
	public static void main (String[] args)
	{
		Scanner fdp = new Scanner (System.in);
		double num;
		int negativo=0,positivo=0,c=0,d=0;
		
		System.out.printf("Digite um lista de números reais (termina com o 0) : ");
		num=fdp.nextDouble();
		
		do
		{
			if(num<0)
			{
				negativo++;
				if(num>=-1000 && num<=-100)
				{
					d++;
				}
			}
			else if(num>0)
			{
				positivo++;
				if(num>=100 && num<=1000)
				{

					c++;
				}
				
			}
			
			num=fdp.nextDouble();
		}
		while(num!=0);
		System.out.printf("Dos números digitados:\n%d são negativos\n%d são positivos\n%d pertencem a [100,1000]\n%d pertencem a [-1000,-1000]",negativo ,positivo ,c ,d);
	}
}			
			
			
			
		
		
		
