import static java.lang.System.*;
import java.io.*;
import java.util.Scanner;
public class CutColumn
{
	public static void main(String[] args)
	{
		String name, result, word = "";
		File file ;
		int n, z, i=0, t=0;
		String array[];
		String words[] = new String[1110010];
		
		try
		{
			name = args[0];
			file = new File(name);
			n = Integer.parseInt(args[1]);
			assert file.canRead() : "Invalid File";
			assert n>0 : "Invalid Number";
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{
				result = sc.nextLine();
				array = countWords(result);
				if(n<=array.length)
				{
					words[i] = array[n-1];
					i++;
				}
			}
			
		}
		catch(ArrayIndexOutOfBoundsException  | FileNotFoundException e )
		{
			out.printf("Usage Method : java CutColumn <filename> <number>\n");
			
		}
		for(t=0;t<words.length;t++)
		{
			if(words[t]!=null)
			{
				out.println(words[t]);
			}
			else if(words[t]==null)
			{
				words[t]="\n";
				out.print(words[t]);
				break;
			}
		}
				
	}
	
	public static String [] countWords(String sent)
	{
		int i = 0, pos = 0;
		String array[] = sent.split("[ ]+");
		
		return array;
	}
}
