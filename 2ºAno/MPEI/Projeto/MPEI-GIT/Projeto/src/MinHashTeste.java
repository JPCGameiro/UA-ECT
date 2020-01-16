

import java.io.*;
import java.util.*;

public class MinHashTeste {
	public static void main(String[] args) throws IOException {
		System.out.println("\nMINHASH\n");
		
		double dif =0 , real =0, jac =0, cnt =0;
		MinHash conj3;
		
		for(int i=0; i<100; i++) {
			MinHash conj1 = new MinHash(new File("./src/Files/similar0.java"));
			MinHash conj2 = new MinHash(new File("./src/Files/similar1.java"));

			
			LinkedList<String> shingles = conj1.getShingles();//lista com todos os shingles do conj1
			Iterator<String> it = conj2.getShingles().iterator();
			
			if(conj1.getDimSgl() != conj2.getDimSgl()) {//teste quando os ficheiros t�m dimensoes diferentes
				if(conj1.getDimSgl() > conj2.getDimSgl()) conj3 = new MinHash(new File("./src/Projeto/Files/similar0.java"), 5);
				else {
					conj3 = new MinHash(new File("./src/Files/similar1.java"), 5);	
					it = conj3.getShingles().iterator();
				}
				shingles = conj3.getShingles();											
			}
			
			cnt = 0;
			while(it.hasNext()) {
				String s = it.next();						//verifica se shingle do conj2 pertece;
				if(!shingles.contains(s)) shingles.add(s);	//se nao, introduz-se na lista de shingles;
				else cnt++;									//se sim, conta como intersec�ao;
			}
			real += cnt/shingles.size();  	//similaridade real dos ficheiros;
			jac += conj1.jaccard(conj2);	//similaridae obtida no minHash;
			
			if(jac > real) { dif += (conj1.jaccard(conj2)) - (cnt/shingles.size());}
			else {dif += (cnt/shingles.size()) - (conj1.jaccard(conj2));}
		}		
		System.out.println("Similaridade real: "+ real/100 + ", minHash similaridade: "+jac/100);//media dos valores reais e de jaccard
		System.out.println("Diferença entre os valores : "+dif/100);	//desvio entre o valor real e o de jaccard
	}
}
