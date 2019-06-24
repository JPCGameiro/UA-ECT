import static java.lang.System.*;
import java.io.*;
import java.util.Scanner;
import p2utils.*;
public class FilterLines2
{
	public static void main(String[] args)throws IOException
	{
		
		if(args.length != 1)
		{
			out.println("Usage: java -ea FilterLines text-file");
			System.exit(0);
		}
		File file = null;
		try
		{
			file = new File(args[0]);
			if(!file.canRead()) throw new IOException();
		}
		catch(ArrayIndexOutOfBoundsException | IOException e)
		{
			out.println("ERROR file doesn't exist, System will shut down");
			System.exit(0);
		}
		
		LinkedList <String> curtas = new LinkedList<>();
		LinkedList <String> medias = new LinkedList<>();
		LinkedList <String> longas = new LinkedList<>(); 
		

		Scanner ler = new Scanner(file);
		
		while(ler.hasNextLine())
		{
			String line = ler.nextLine();
			if(line.length() < 20)
			{
				curtas.addFirst(line);
			}
			else if(line.length()>= 20 && line.length()<40)
			{
				medias.addFirst(line);
			}
			else
			{
				longas.addFirst(line);
			}
		}
		
		LinkedList <String> longasrev = longas.reverse();
		
		curtas.print();
		longasrev.print();
	}
}
