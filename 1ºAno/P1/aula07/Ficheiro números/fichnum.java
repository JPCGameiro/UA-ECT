import java.util.Scanner;
import java.io.*;
public class fichnum
{
	public static void main (String[] args) throws IOException
	{
		Scanner ler = new Scanner (System.in);
		String fichname;
		File fich;
		int i, u=0;
		
		do
		{
			System.out.printf("Insira o nome do ficheiro  com extensão válida:");
			fichname=ler.nextLine();
			fich=new File(fichname);
		}
		while(!fich.canRead() || !fich.isFile());
		
		Scanner lerfich = new Scanner (fich);
		
		do
		{
			i=lerfich.nextInt();
			if(i>=0)
			{
				System.out.print(i+"\n");
			}
			u++;
			
		}
		while(lerfich.hasNextLine());
		return a;
	}
}
		
