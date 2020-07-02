import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
public class ex6
{
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		File f = new File("/home/joao/Documents/Universidade/2ºAno/LFA/Praticas/Bloco1/Ex6/dic1.txt");
		File ff = new File("/home/joao/Documents/Universidade/2ºAno/LFA/Praticas/Bloco1/Ex6/dic2.txt");
		Scanner readFile = new Scanner(f);
		Scanner readFile1 = new Scanner(ff);
		String result = "";
		
		while(readFile.hasNextLine()){
			String[] aux = readFile.nextLine().split(" ");
			map.put(aux[0], aux[1]);
		}
		readFile.close();
		
		while(readFile1.hasNextLine()){
			String[] au = readFile1.nextLine().split("\\s{2,6}");
			map1.put(au[0], au[1]);
		}
		
		System.out.println("-----------------------DIC1.txt-----------------------");
		System.out.println("------------------------------------------------------");
		File f0 = new File("/home/joao/Documents/Universidade/2ºAno/LFA/Praticas/Bloco1/Ex6/texto1.txt");
		File f1 = new File("/home/joao/Documents/Universidade/2ºAno/LFA/Praticas/Bloco1/Ex6/texto2.txt");
		translate(f0, map);
		translate(f1, map);
		
		
		System.out.println("-----------------------DIC2.txt-----------------------");
		System.out.println("------------------------------------------------------");
		translate(f0, map1);
		translate(f1, map1);

	}
	
	//Função para ler conteúdo do ficheiro e traduzir
	public static void translate(File f0, HashMap<String, String> map) throws IOException
	{
		String result = "";
		Scanner readFile0 = new Scanner(f0);
		System.out.println("-----Conteúdo original do ficheiro "+f0.getName()+"-----");
		while(readFile0.hasNextLine()){
			String s = readFile0.nextLine();
			System.out.println(s);
			Scanner readFile00 = new Scanner(s);
			while(readFile00.hasNext()){
				String aux0 = readFile00.next();
				if(map.containsKey(aux0))
					result = result+map.get(aux0)+" ";
				else 
					result = result+aux0+" ";
			}
			result = result+"\n";
			readFile00.close();
		}
		System.out.println("-----Tradução do ficheiro "+f0.getName()+"-----");
		System.out.println(result);
		readFile0.close();
	}
}
