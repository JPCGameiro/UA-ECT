import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;
public class ficheiros
{
	public static void main (String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		String fichname1, fichname2;
		File fich1, fich2;
		int i;
		
		do
		{
			out.printf("Insira o nome de dois ficheiros válidos com as suas extensões: "); 
			fichname1=sc.next();
			fichname2=sc.next();
			fich1 = new File(fichname1);
			fich2 = new File(fichname2);
		}
		while((!fich1.canRead() || !fich1.isFile()) && (!fich2.canRead() || !fich2.isFile()));
		out.println("Ficheiros Existentes");
		
		Scanner lerfich = new Scanner(fich1);
		PrintWriter pw = new PrintWriter(fich2);
		
		while(lerfich.hasNextLine())
		{
			i = lerfich.nextInt();
			pw.println(i);
		}
		
		pw.close();
		lerfich.close();
		out.println("Conteúdo do primeiro ficheiro gravado no segundo ficheiro com sucesso");		
	}
}
