//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex1;
import java.io.*;

public class Test {

	public static void main(String[] args) throws IOException{
		
		//Testar ao ler valores do teclado
		ScannerAbeirense ab = new ScannerAbeirense();
		
		System.out.println("Teste next");
		System.out.print("Insira uma frase: ");
		String n = ab.next();
		System.out.print(n);
		
		ab.nextLine();
		
		System.out.println("\nTeste nextLine");
		System.out.print("Insira uma frase: ");
		String nl = ab.nextLine();
		System.out.print(nl);
		System.out.println();
		
		ab.close();
		
		//Testar com um ficheiro
		System.out.println("\nTeste de um ficheiro\n");
		File f = new File("./src/aula9/Ex1/test.txt");
		ScannerAbeirense fab = new ScannerAbeirense(f);
		while(fab.hasNext())
			System.out.println(fab.next());
		fab.close();
		
	
		
	}

}
