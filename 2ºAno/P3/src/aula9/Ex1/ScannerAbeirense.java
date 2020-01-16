//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex1;
import java.util.*;
import java.io.*;

public class ScannerAbeirense implements Iterator<String>, Closeable{
	
	Scanner ler;
	
	//Construtor 
	public ScannerAbeirense()
	{
		ler = new Scanner(System.in);
	}
	public ScannerAbeirense(File f) throws IOException
	{
		ler = new Scanner(f);
	}
	
	@Override
	public void close() throws IOException {
		ler.close();
	}

	@Override
	public boolean hasNext() {
		if(ler.hasNext())
			return true;
		else
			return false;
	}

	@Override
	public String next() {
		String original =  ler.next();
		String result = original.replaceAll("v", "b");
		result = result.replaceAll("V", "B");
		return result;
	}
	

	public String nextLine() {
		String original =  ler.nextLine();
		String result = original.replaceAll("v", "b");
		result = result.replaceAll("V", "B");
		return result;
	}
	

}
