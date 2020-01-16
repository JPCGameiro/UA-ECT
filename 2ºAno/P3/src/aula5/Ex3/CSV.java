//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class CSV implements TipoAgenda{
	
	public LinkedList<Pessoa> readFile(String file) throws IOException
	{
		LinkedList<Pessoa> l = new LinkedList<Pessoa>();
		Scanner readFile = new Scanner(file);
		readFile.nextLine();
		while(readFile.hasNextLine())
		{
			String[] info = readFile.nextLine().split("\t");
			String[] data = info[2].split("/");
			Data d = new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
			l.add(new Pessoa(info[0], Integer.parseInt(info[1]), d));
		}
		readFile.close();
		return l;
	}
	
	public void writeFile(String file, LinkedList<Pessoa> l)throws IOException
	{
		PrintWriter pw = new PrintWriter(file);
		
		pw.print("CSV");
		
		for(Pessoa p : l)
		{
			pw.println("\n"+p.nome()+"\t"+p.num()+"\t"+p.data());
		}
		pw.close();
		
	}

}
