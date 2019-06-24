import static java.lang.System.*;
import java.io.*;
import java.util.Scanner;
public class CopyFile
{
	public static void main (String[] args)throws IOException
	{
		assert args.length == 2 : "Usage Method: java -ea CopyFile.java <file1> <file2>\n";
		
		File file1 = null;
		try
		{
			file1 = new File(args[0]);
			if(!file1.canRead()) throw new IOException();
		}
		catch(ArrayIndexOutOfBoundsException | IOException e)
		{
			out.println("ERROR : Input File does not exist");
			System.exit(0);
		}
		
		Scanner lerfich = new Scanner(file1);
		PrintWriter pwf = new PrintWriter(args[1]);
		
		while(lerfich.hasNextLine())
		{
			String linhas = lerfich.nextLine();
			pwf.println(linhas);
		}
		lerfich.close();
		pwf.close();
		
		out.printf("Copy was Sucessfull\n");
	}
}
