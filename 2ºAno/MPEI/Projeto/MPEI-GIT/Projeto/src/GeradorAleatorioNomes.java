

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class GeradorAleatorioNomes {	
	
	//Gerar N ficheiros com nome aleatório
	public File[] geradorFile(int N) throws FileNotFoundException, UnsupportedEncodingException
	{
		File[] array = new File[N];
		
		for(int i=0;i<N;i++) {
			String name = "./src/Files/RandomFiles/"+geradorStr()+".txt";
			File f = new File(name);
			array[i] = f;
			PrintWriter pw = new PrintWriter(name, "UTF-8");
			pw.write("Random Name: "+geradorStr());
			pw.close();
		}
		
		return array;
	}
	
	//Gerar uma string aleatória
	public String geradorStr() {
		String result ="";
		Random r = new Random();
		int numchar = -1;
		
		do {
			numchar = (int) (r.nextGaussian()*(8));	//número de caracteres da palavra
		}while(numchar <= 0);
		
		result = result+(char) ('A'+ (new Random()).nextInt(26));
		for(int i=0;i<numchar-1;i++) {
			char ch = (char)('a' + (new Random()).nextInt(26));
			result = result+ch;
		}
		return result;
		
	}
}
