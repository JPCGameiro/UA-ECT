import java.util.Scanner;
public class maior
{
	public static void main (String[] args)
	{
		Scanner fds = new Scanner(System.in);
		int num1, num2, num3;
		
		System.out.printf("Digite um número : ");
		num1=fds.nextInt();
		System.out.printf("Digite outro número : ");
		num2=fds.nextInt();
		System.out.printf("Digite outro número : ");
		num3=fds.nextInt();
		
		if(num1>num2 && num1>num3)
		{
			System.out.printf("%d é o maior número ", num1);
		}
		else if(num2>num1 && num2>num3)
		{
			System.out.printf("%d é o maior número ", num2);
		}
		else if(num3>num2 && num3>num1)
		{
			System.out.printf("%d é o maior número ", num3);
		}
	}
}

