import java.util.Scanner;
public class calendario
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int z=1, maxZ;
		String m;
		
		System.out.printf("Insira o Nº mês: ");
		int mes = ler.nextInt();
		System.out.printf("Insira o ano: ");
		int ano = ler.nextInt(); 
		System.out.println("Domingo(1) - Sábado(7)");
		System.out.printf("Insira o dia da semana em que começa o mês: ");
		int dia = ler.nextInt();
		
		if(mes==1){m="Janeiro"; maxZ=31;}
		else if(mes==2) {m="Fevereiro"; maxZ=29;}
		else if(mes==3) {m="Março"; maxZ=31;}
		else if(mes==4) {m="Abril"; maxZ=30;}
		else if(mes==5) {m="Maio"; maxZ=31;}
		else if(mes==6)	{m="Junho"; maxZ=30;}
		else if(mes==7)	{m="Julho"; maxZ=31;}
		else if(mes==8)	{m="Agosto"; maxZ=31;}
		else if(mes==9)	{m="Setembro"; maxZ=30;}
		else if(mes==10){m="Outubro"; maxZ=31;}
		else if(mes==11){m="Novembro"; maxZ=30;}
		else{m="Dezembro"; maxZ=31;}	
		 
		
		System.out.print("-------------------------------------\n");
		System.out.printf("          %6s        %4d       \n", m, ano);
		System.out.print("-------------------------------------\n");
		System.out.print("|  D    S    T    Q    Q    S    S  |\n"); 
		System.out.print("-------------------------------------\n");
		
		int Ncolunas, Ninicial;
		if(dia==7){
			Ncolunas=5;
			Ninicial=1;
		}
		else{
			Ncolunas=4;
			Ninicial=0;
		}
		
		for(int i=Ninicial;i<=Ncolunas;i++)
		{

			System.out.print("|");
			for(int j=0;j<7;j++)
			{
				if(j<(dia-1) && i==0)
				{
					System.out.printf("     ");
				}
				else if(z>maxZ)
				{
					System.out.printf("     ");
				}
				else
				{
					System.out.printf(" %2d  ",z);
					z++;
				}
			}
			System.out.print("|\n");
		}
		System.out.print("-------------------------------------\n");
		
	}
}
