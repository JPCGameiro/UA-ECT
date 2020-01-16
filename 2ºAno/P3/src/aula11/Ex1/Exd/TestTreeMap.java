//João Gameiro   Nº93097
//P3-ECT-UA

package aula11.Ex1.Exd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

public class TestTreeMap {
	
	static Map<String, Integer> hm = new TreeMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		Path p = Paths.get("./src/aula11/Ex1/Exd/test.txt");
		List<String> content = Files.readAllLines(p);
		
		for(String s : content)
			splitWords(s);
		
		System.out.println("Palavras: \n");
		for(String s : hm.keySet())
			System.out.println(s + " : " + hm.get(s));
		
	}
	
	public static void splitWords(String sent) {
		String[] array = sent.split("\\s+");
		for(int i=0;i<array.length;i++) {
			if(!hm.containsKey(array[i]))
				hm.put(array[i], 1);
			else
				hm.put(array[i], hm.get(array[i]) + 1);
		}
	}

}
