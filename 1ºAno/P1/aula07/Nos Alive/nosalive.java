import java.util.Scanner;
import java.io.*;
public class nosalive
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner (System.in);
		String fichname;
		File fich;
		String line;
		
		do
		{
			System.out.printf("Insira o nome do ficheiro com uma extensão válida (.canRead ou .isFile):");
			fichname=ler.nextLine();
			fich = new File (fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner lerfich = new Scanner (fich);
		
		do
		{
			line = lerfich.nextLine();
			System.out.print(line+"\n");
		}
		while(lerfich.hasNextLine());
	}
}
			
