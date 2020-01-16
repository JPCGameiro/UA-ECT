//João Gameiro  Nº93097
//Problema 1.1

package aula1;
import java.util.*;

public class Ex1 { 

	public static void main(String[] args) {
		
		Scanner ler = new Scanner (System.in);
		String sent;
		
		//Leitura da frase
		System.out.print("Digite uma frase: ");
		sent = ler.nextLine();
		
		//Indicação do nº de caracteres numéricos
		System.out.println("-> Nº de caracteres numéricos " + VerifyNum(sent));
		
		//Indicação de se é apenas constituida por maiúsculas ou por minúsculas
		if(isLower(sent))
		{
			System.out.println("-> A frase é constituida apenas por minúsculas");
		}
		if(isUpper(sent))
		{
			System.out.println("-> A frase é constituida apenas por maiúsculas");
		}
		
		//Indicação do nº de palavras e das palavras que estão na frase
		System.out.println("-> A frase tem " + countWords(sent).length + " palavras: " + toString(countWords(sent)));
		
		//Impressão da frase com os caracters trocados 2 a 2
		System.out.println("-> " + switchCharacter(sent));
		
		ler.close();
	}
	
	//--------------Funções Adicionais------------------//
	
	
	//Função para verificar o número total de caracteres numéricos
	public static int VerifyNum(String sent)
	{
		int cnt = 0, i=0;
		
		for(i=0;i<sent.length();i++)
		{
			char letter = sent.charAt(i);
			if(Character.isDigit(letter))
			{
				cnt++;
			}
		}
		return cnt;
	}
	
	//Função para verificar se a String é apenas composta por minúsculas
	public static boolean isLower(String sent)
	{
		boolean check = true;
		int i = 0;
		
		for(i=0;i<sent.length();i++)
		{
			char letter = sent.charAt(i);
			if(Character.isUpperCase(letter))
			{
				check= false;
				break;
			}
		}
		return check;
	}
	
	//Função para verificar se a String é apenas composta por maiúsculas
	public static boolean isUpper(String sent)
	{
		boolean check = true;
		int i = 0;
		
		for(i=0;i<sent.length();i++)
		{
			char letter = sent.charAt(i);
			if(Character.isLowerCase(letter))
			{
				check= false;
				break;
			}
		}
		return check;
	}
	
	//Função para contar o total de palavras e devolver um array com as mesmas
	public static String[] countWords(String sent)
	{
		String[] array = sent.split("\\s+");
		
		return array;
	}
	
	//Função para converter o conteúdo de um array de strings numa String
	public static String toString(String array[])
	{
		String result = "[";
		int i=0;
		for(i=0;i<array.length-1;i++)
			result = result + array[i] + ", ";
		
		return (result + array[array.length-1] + "]"); 
	}
	
	//Função para trocar os caracteres 2 a 2
	public static String switchCharacter(String sent)
	{
		String result = "";
		String finalCharacter = "";
		int n = sent.length();
		if(n%2!=0)
			finalCharacter = finalCharacter + sent.charAt(sent.length()-1);
			n=n-1;
		
		for(int i=0;i<n;i=i+2)
		{
			char a = sent.charAt(i);
			char b = sent.charAt(i+1);
			result = result + b + a;
		}
		
		return result + finalCharacter;
	}
}

