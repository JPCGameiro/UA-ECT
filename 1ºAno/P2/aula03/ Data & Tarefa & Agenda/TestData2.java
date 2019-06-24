import static java.lang.System.*;
import java.util.Scanner;
public class TestData2
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);		
		String sentence, sentence1;
		
		out.printf("Insira uma data válida: ");
		sentence = ler.next();
		out.printf("Insira outra data válida: ");
		sentence1 = ler.next();
		Data d0 = new Data(sentence);
		Data d1 = new Data(sentence1);
		
		if(d0.compareTo(d1)==0)
		{
			out.println(d0.toString() + " = " + d1.toString());
		}
		else if(d0.compareTo(d1)==1)
		{
			out.println(d0.toString() + " > " + d1.toString());
		}
		else if(d0.compareTo(d1)==-1)
		{
			out.println(d0.toString() + " < " + d1.toString());
		}
				
	}
}
