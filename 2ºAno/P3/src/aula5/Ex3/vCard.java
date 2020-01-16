//João Gameiro  Nº93097
//P3-UA-ECT

package aula5.Ex3;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class vCard implements TipoAgenda{
	
	public LinkedList<Pessoa> readFile(String file) throws IOException
	{
		LinkedList<Pessoa> l = new LinkedList<Pessoa>();
		Scanner readFile = new Scanner(file);
		readFile.nextLine();
		
		while(readFile.hasNextLine())
		{
			String[] info = readFile.nextLine().split("#");
			String[] d = info[2].split("/");
			Data dd = new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
			l.add(new Pessoa(info[0], Integer.parseInt(info[1]), dd));
		}
		readFile.close();
		return l;
 	}
	
	public void writeFile(String file, LinkedList<Pessoa> l) throws IOException
	{
		PrintWriter pw = new PrintWriter(file);
		
		pw.print("vCard");
		for(Pessoa p : l)
		{
			pw.print("\n#");
			pw.print(p.nome());
			pw.print("#");
			pw.print(p.num());
			pw.print("#");
			pw.print(p.data());
		}
		pw.close();
	}

}
