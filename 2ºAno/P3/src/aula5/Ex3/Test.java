//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;
import java.io.*;
import java.util.*;

public class Test {

	public static void main(String[] args) throws IOException {
		
		
		//Formato NOKIA
		Agenda a = new Agenda(new vCard().readFile("vCard.txt"));
		System.out.println("Formato NOKIA");
		System.out.println(a.toString());
		

	}

}
