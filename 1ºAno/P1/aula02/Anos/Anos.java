import java.util.Scanner;
public class Anos
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		int mes, ano;
		
		System.out.printf("Digite o número do mês : ");
		mes=ler.nextInt();
		System.out.printf("Digite o ano : ");
		ano=ler.nextInt();
		
		if (mes<=0 | mes>12)
		{
			System.out.printf("Mês inválido\n");
		}
		if (ano<0)
		{
			System.out.printf("Ano inválido");
		}
		
		else
		{
			if((ano%4==0) | (ano%400==0))
			{
				if((mes==1) | (mes==3) | (mes==5) | (mes==7) | (mes==8) | (mes==10) | (mes==12))
				{
					System.out.printf("O mês %d do ano %d tem 31 dias", mes,ano);
				}
				else 
				if((mes==4) | (mes==6) | (mes==9) | (mes==11))
				{
					System.out.printf("O mês %d do ano %d tem 30 dias",mes, ano);
				}
				else
				if(mes==2)
				{
					System.out.printf("O mês %d do ano %d tem 29 dias", mes, ano);
				}
			}
			else
			{
				if((mes==1) | (mes==3) | (mes==5) | (mes==7) | (mes==8) | (mes==10) | (mes==12))
				{
					System.out.printf("O mês %d do ano %d tem 31 dias", mes, ano);
				}
				else 
				if((mes==4) | (mes==6) | (mes==9) | (mes==11))
				{
					System.out.printf("O mês %d do ano %d tem 30 dias",mes, ano);
				}
				else
				if(mes==2)
				{
					System.out.printf("O mês %d do ano %d tem 28 dias", mes, ano);
				}
			}
		}
	}
}

