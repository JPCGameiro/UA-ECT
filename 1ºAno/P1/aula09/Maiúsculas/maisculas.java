import java.util.Scanner;
public class maisculas
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		String fr;
		
		System.out.printf("Digite uma frase: ");
		fr = ler.nextLine();
		
		capitalize(fr);
	}
	
	public static void capitalize(String fr)
	{
		int pos=0;
		char c = fr.charAt(0);
	
		if(Character.isLetter(c))
		{
			c=Character.toUpperCase(c);
		}
		for(int i=1;i<fr.lenth();i++)
		{
			char a = fr.charAt(i);
			
		}
	}
}
