import java.util.Scanner;
public class img
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		Complexos num = new Complexos();
		Complexos num1 = new Complexos();
		String opc;
		

			do
			{
				System.out.printf("Operação: ");
				opc=ler.next();				
				switch(opc)
				{
					case "+":
					num=Ler();
					num1=Ler();
					Escrever(num);
					System.out.printf(" + ");
					Escrever(num1);
					System.out.printf(" = ");
					Escrever(Soma(num, num1));
					System.out.printf("\n");
					break;
				
					case "-":
					num=Ler();
					num1=Ler();
					Escrever(num);
					System.out.printf(" - ");
					Escrever(num1);
					System.out.printf(" = ");
					Escrever(Subtracao(num, num1));
					System.out.printf("\n");
					break;
					
					case "*":
					num=Ler();
					num1=Ler();
					Escrever(num);
					System.out.printf(" * ");
					Escrever(num1);
					System.out.printf(" = ");
					Escrever(Multiplicacao(num, num1));
					System.out.printf("\n");
					break;
					
					case "/":
					num=Ler();
					num1=Ler();
					Escrever(num);
					System.out.printf(" / ");
					Escrever(num1);
					System.out.printf(" = ");
					Escrever(Divisao(num, num1));
					System.out.printf("\n");
					break;					
					
					case "=":
					System.out.printf("... o sistema vai terminar");
					System.exit(0);
					break;
					
					default:
					System.out.printf("Operação inválida\n");
					break;
				}
			}
			while(opc!="=");
			
	}
			

	
	public static Complexos Ler()
	{
		Scanner ler = new Scanner (System.in);
		Complexos num = new Complexos();
		
		System.out.printf("Introduza um número complexo: \n");
		System.out.printf("Parte Real: ");
		num.r=ler.nextInt();
		System.out.printf("Parte Imaginária: ");
		num.i=ler.nextInt();
		
		return num;		
	}
	
	public static void Escrever(Complexos num)
	{
		if(num.i<0)
		{
			System.out.printf("%1.1f%1.1fi", num.r, num.i);
		}
		else if(num.i>0)
		{
			System.out.printf("%1.1f+%1.1fi", num.r, num.i);
		}
	}
	
	public static Complexos Soma(Complexos num, Complexos num1)
	{
		Complexos result = new Complexos();
		
		result.r=num.r+num1.r;
		result.i=num.i+num1.i;
		return result;
	}
	
	public static Complexos Subtracao(Complexos num, Complexos num1)
	{
		Complexos result = new Complexos ();
		
		result.r=num.r-num1.r;
		result.i=num.i-num1.i;
		return result;
	}
	
	public static Complexos Multiplicacao(Complexos num, Complexos num1)
	{
		Complexos result = new Complexos();
		
		result.r=(num.r*num1.r)-(num.i*num1.i);
		result.i=(num.r*num1.i)+(num.i*num1.r);
		return result;
	}
	public static Complexos Divisao(Complexos num, Complexos num1)
	{
		Complexos result = new Complexos ();
		
		result.r=(((num.r*num1.r)+(num.i*num1.i))/(Math.pow(num1.r, 2)+Math.pow(num1.i, 2)));
		result.i=(((num.i*num1.r)-(num.r*num1.i))/(Math.pow(num1.r, 2)+Math.pow(num1.i, 2)));
		return result;
	}
		
}

class Complexos
{
	double r,i;
}

