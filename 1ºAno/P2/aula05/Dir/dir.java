import static java.lang.System.*;
import java.io.*;
public class dir
{
	public static void main(String[] args)
	{
		File fich;
		int i = 0;
		String diir = null;
		
		try
		{
			diir = args[0];
			fich = new File(diir);
			if(!fich.canRead()) throw new IOException();
					
		}
		catch(ArrayIndexOutOfBoundsException | IOException e)
		{
			diir = ".";     //Diretório atual -> "."     Diretório anterior -> ".."
			fich = new File(diir);
		}
		
		File file[] = fich.listFiles();
		
		for(i=0; i<file.length;i++)
		{
			File arquivos = file[i];
			if(arquivos == null)
			{
				break;
			}			
		}
		for(int j=0;j<i;j++)
		{
			File arquivos = file[j];
			//varifica se é diretório ou ficheiro
			if(arquivos.isDirectory())
			{
					out.printf("D");
			}
			else if(arquivos.isFile())
			{
				out.printf("F");
			}
			//verifica se tem permissões de escrita
			if(arquivos.canRead())
			{
				out.printf("R");
			}
			else if(!arquivos.canRead())
			{
				out.printf("-");
			}
			//verifica se tem permissões de escrita
			if(arquivos.canWrite())
			{
				out.printf("W");
			}
			else if(!arquivos.canWrite())
			{
				out.printf("-");
			}
			out.printf("  ./"+arquivos.getName()+"\n");
			
		}		
		
	}
}
