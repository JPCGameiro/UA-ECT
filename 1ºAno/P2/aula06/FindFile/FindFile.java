import static java.lang.System.*;
import java.io.*;
public class FindFile
{
	public static void main(String[] args)
	{
		File file = null, fich;
		String fichname, diir;
		int i;
		
		try
		{
			fichname = args[0];
			file = new File(fichname);
			if(!file.canRead()) throw new IOException();
		}
		catch(ArrayIndexOutOfBoundsException | IOException e)
		{
			out.println("ERROR");
			out.println("System will shut down ...");
			System.exit(0);
		}
		
		diir = "..";
		File f = new File(diir);
		File d[] = f.listFiles();
		
		for(i=0;i<d.length;i++)
		{
			if(d[i].isDirectory())
			{
				d[i] = d[i];
			}
			else
			{
				d[i] = null;
			}
		}
		for(i=0;i<d.length;i++)
		{
			if(d[i]!=null)
			{
				out.println(d[i]);
			}
		}
		
	}
}
