import java.util.Scanner;
import java.io.*;
import java.util.HashMap;

public class ex3{
	public static void main(String[] args) throws IOException
	{
		File f = new File("numbers.txt");										
		Scanner readFile = new Scanner(f);										
		Scanner rd = new Scanner(System.in);									
		HashMap<String, Integer> map = new HashMap<String, Integer>();			
		
		while(readFile.hasNextLine()) {											
			String[] aux = readFile.nextLine().split(" - ");					
			map.put(aux[1], Integer.parseInt(aux[0]));
		}
		readFile.close();
		
		while(rd.hasNextLine()) {
			Scanner sc = new Scanner(rd.nextLine());
			while(sc.hasNext()){
				String[] aux= sc.next().split("-");
				if(map.containsKey(aux[0]))
					System.out.print(map.get(aux[0])+" ");
				else
					System.out.print(aux[0]+" ");
				if(aux.length==2){
					if(map.containsKey(aux[1]))
						System.out.print(map.get(aux[1])+" ");
					else
						System.out.print(aux[1]+" ");
				}
			}
			System.out.println();
		}		
	 }
}
