

import java.io.*;
import java.util.*;

public class BloomFilterTeste {
	public static void main(String[] args) throws IOException {
		System.out.println("\nFALSOS POSITIVOS");
		System.out.println("Gerar 1000 ficheiros adicionar 500 ao Bloom filter \ne verificar se os outros 500 não pertecem (verificar a ocorrência de falsos positivos)\n");
		
		BloomFilter bf;
		GeradorAleatorioNomes gan = new GeradorAleatorioNomes();
		
		double cnt = 0;
		int idx = 0;
		for(int u =0; u<100; u++) {//realiza 100 
			System.out.print(u+"-");
			LinkedList<File> files = new LinkedList<File>();
			File[] listFiles = gan.geradorFile(1000);//total de ficheiros a serem criados;
			
			idx = (int)(listFiles.length/2);//numero de ficheiros a introduzir no bloom, cerca de metade;
			for(int i=0;i<idx;i++)
				files.add(listFiles[i]);
			
			bf = new BloomFilter(files);
			
			for(int z=idx+1;z<listFiles.length;z++) {//verificar comos restantes nao introduzidos
				if(bf.membro(listFiles[z]))
					cnt++;//contagem dos falsos positivos
			}
			for(File f : listFiles)
				f.delete();
		}
		System.out.print("media de falsos positivos :"+cnt/(idx*100));//percentagem de 
	}
}
