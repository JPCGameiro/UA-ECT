//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class CountPairs {

	public static void main(String[] args) throws IOException {
		
		LinkedList<String> list = new LinkedList<String>();
		Map<String, LinkedList<String>> map = new TreeMap<>();
		
		Path p =  Paths.get("./src/aula13/Ex2/Policarpo.txt");
		List<String> lines = Files.readAllLines(p);
		
		//Separar a string segundo caracteres especiais e guardar numa LinkedList as palavras		
		for(String l : lines) {
			String[] r = l.split("[\\\\\n\t0@&.?$+-,,:'‘’;/\\(\\)\\-\\['\\]\\s]+");
			for(String s : r) {
				if(s.length()>=3)
					list.add(s);
			}
		}
		
		//Adicionar os pares de palavras a um Map
		for(int i=0;i<list.size()-1;i++) {
			if(map.containsKey(list.get(i)))
				map.get(list.get(i)).add(list.get(i+1));
			else {
				LinkedList<String> aux = new LinkedList<String>();
				aux.add(list.get(i+1));
				map.put(list.get(i), aux);	
			}
		}
		
		if(saveFile(map))
			System.out.println("Saved on output.txt sucessfully");
		else
			System.out.println("ERROR");
	}
	
	//Função que devolve o número de vezes que uma palavra aparece numa LinkedList (conta pares)
	public static int countOccurences(String word, LinkedList<String> l)
	{
		int cnt = 0;
		for(int i=0;i<l.size();i++) {
			if(word.equals(l.get(i)))
				cnt++;
		}
		return cnt;
	}
	
	//Função para guardar o resultado num ficheiro
	public static boolean saveFile(Map<String, LinkedList<String>> map)
	{
		Path p = Paths.get("./src/aula13/Ex2/output.txt");
		
		String s = "";
		
		for(String key : map.keySet()) {
			s = s +(key+"={");
			for(int z=0;z<map.get(key).size();z++) {
				if(z==map.get(key).size()-1)
					s = s +(map.get(key).get(z)+"="+(countOccurences(map.get(key).get(z), map.get(key))));
				else					
					s = s + (map.get(key).get(z)+"="+(countOccurences(map.get(key).get(z), map.get(key)))+", ");
			}
			s = s + ("}\n");
		}
		try (BufferedWriter writer = Files.newBufferedWriter(p, Charset.forName("UTF-8")))
		{
			writer.write(s, 0, s.length());
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}
}
