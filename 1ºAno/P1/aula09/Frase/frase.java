import java.util.Scanner;
public class frase
{
	public static void main (String []args)
	{
		Scanner ler = new Scanner (System.in);
		int pos, mai=0, min=0, dig=0, vog=0, cons=0;
		String fr;
		
		System.out.printf("Análise de uma frase\n");
		System.out.printf("\nFrase -> ");
		fr=ler.nextLine();
		
		for(pos=0;pos<fr.length();pos++)
		{
			char letra = fr.charAt(pos);
			if(Character.isUpperCase(letra))
			{
				mai++;
			}
			else if (Character.isLowerCase(letra))
			{
				min++;
			}
			else if (Character.isDigit(letra))
			{
				dig++;
			}
			if (isVowel(letra)==true)
			{
				vog++;
			}
		}
		
		cons=(min+mai)-vog;
		
		System.out.printf("\nNúmero de maiúsculas -> %d", mai);
		System.out.printf("\nNúmero de mínúsculas -> %d", min);
		System.out.printf("\nNúmero de caracteres numéricos -> %d", dig);
		System.out.printf("\nNúmero de vogais -> %d", vog);
		System.out.printf("\nNúmero de consoantes -> %d", cons);
	}
	
	public static boolean isVowel(char a)
	{
		if(a=='a' | a=='e' | a=='i' | a=='o' | a=='u'|a=='A' | a=='E' | a=='I' | a=='O' | a=='U')
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
}
				
				
			
		
		
