import java.util.Scanner;
public class palavras
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		String fr;
		System.out.printf("Digite uma frase: ");
		fr=ler.nextLine();
		
		System.out.print("Número de palavras -> " + countWords(fr));		
		
	}
	
	public static int countWords(String fr)
	{
		int  pos=0, words=1;
		for(pos=0;pos<fr.length()-1;pos++)
		{
			char letter = fr.charAt(pos);
			char letter1 = fr.charAt(pos+1);
			if(letter!=' ' && letter1!=' ')
			{
				words=words+0;
			}
			else if(letter!=' ' && letter1==' ')
			{
				words++;
			}
			else if(letter!=' '&& letter1=='\n')
			{
				words++;
			}
			else if(letter==' ' && letter1!=' ')
			{
				words=words+0;
			}
			else if(letter==' '&& letter1==' ')
			{
				words=words+0;
			}
			
		}
		return words;
	}
}
