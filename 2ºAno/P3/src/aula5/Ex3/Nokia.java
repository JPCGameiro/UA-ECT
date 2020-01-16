//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Nokia implements TipoAgenda{
	
	public LinkedList<Pessoa> readFile(String file) throws IOException
	{
		Scanner readFile = new Scanner(file);
		LinkedList<Pessoa> l = new LinkedList<Pessoa>();

		while(readFile.hasNextLine())
		{
			readFile.nextLine();
			String nome = readFile.nextLine();
			int num = readFile.nextInt();
			String Data[] = readFile.nextLine().split("/");
			Data d = new Data(Integer.parseInt(Data[0]), Integer.parseInt(Data[1]), Integer.parseInt(Data[2]));
			l.add(new Pessoa(nome, num, d));
		}
		readFile.close();
		return l;
	}
	
	public void writeFile(String file, LinkedList<Pessoa> l) throws IOException
	{
		PrintWriter pw = new PrintWriter(file);
		pw.println("NOKIA");
		for(Pessoa p : l){
			pw.println(p.nome());
			pw.println(p.num());
			pw.println(p.data());
			pw.println();
		}
		pw.close();
	}

}
