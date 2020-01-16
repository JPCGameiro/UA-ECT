//João Gameiro  Nº93097
//P3-ECT-UA

package aula2.Ex2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ex2 {

	public static void main(String[] args) throws IOException {
		
		File f = new File("./src/aula2/Ex2/puzzle.txt");

		Scanner readFile = new Scanner(f);
		
		while(readFile.hasNextLine()) {
			String line = readFile.next();
			System.out.println(line);
		}
		
		readFile.close();
		

	}

}
