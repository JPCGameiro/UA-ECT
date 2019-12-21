import java.util.Scanner;
public class inverso 
{
	public static void main(String args[])
	{
		Scanner ler = new Scanner(System.in);
		int num = -1;
		
		while(num<=0){
			System.out.print("Insira um número positivo: ");
			num = ler.nextInt();
		}
		
		int reversed = 0;
		while(num != 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
		
		System.out.println("Número pela ordem inversa: "+reversed);
	}
}
