

import java.io.*;
import java.util.*;

class MinHash{
      	private LinkedList<String> shingle;//lista com todos os shingles;
        private String[] minHash;//array com as assinaturas do conjunto;
        private File f;//ficheiro introduzido;
        private int dimSgl;//length das shingles;
        private final static int k = 10000;//numero de hash functions necessarias;
        private static int[][] conjAltr = new ArrayGenerator(k, 11).getArray();//array de int aleatoitos associados a cada hashfunct
        
        public MinHash(File f) throws IOException {
                this(f, 0);
        }
        
        public MinHash(File f, int d) throws IOException {
        		this.f = f;
                shingle = new LinkedList<>();
                minHash = new String[k];
                dimSgl = d;
                
                if(dimSgl == 0) {
                     if(f.length() < 1000) dimSgl = 5;
                     else dimSgl = 10;
                }
                
                FileReader in = new FileReader(f);//leitura do ficheiro e obten�ao dos shingles
                int c;
                String aux = "";
                while ((c = in.read()) != -1) {
                        if(c >= 32 && c != 127)
                                aux += (char)(c);
                        if(aux.length() == dimSgl && !shingle.contains(aux)) {
                                shingle.add(aux);
                                //System.out.print(aux+",");//linha de testes
                                aux = aux.substring(1, dimSgl);
                        }
                }
                in.close();
                //System.out.println("");//linha de testes
                minHashArray(shingle);
        }
        
        //preencher array com os valores de hash mínimos 
        private void minHashArray(LinkedList<String> tmp) {
                for(int i=0; i<k; i++) {
                	try {
                        String min = tmp.get(0);
                	
                        for(int j = 1; j < tmp.size(); j++ ) {
                                if(hashShingle(tmp.get(j), i) < hashShingle(min, i))
                                        min = tmp.get(j);
                        }
                        minHash[i] = min;
                	}catch(IndexOutOfBoundsException e) {
                		System.out.println("ERRO: Ficheiro introduzido demasiado pequeno.");
                		System.exit(1);
                	}
                }
        }
        
        //similaridade de jaccard
        public double jaccard(MinHash c2) throws IOException {
        	if(c2.getDimSgl() != dimSgl) {
        		if(c2.getDimSgl() < dimSgl) {
        			return new MinHash(f, 5).jaccard(c2);}
        		else {
        			c2 = new MinHash(c2.getFile(), 5);}
        	}
                double inter = 0; 
                for(int i=0; i<k; i++) {
                        if(minHash[i].equals(c2.getElem(i)))
                                inter++;
                }
                return inter/k;
        }
        
        private File getFile() {return f;}//devolve o ficheiro
        
        public int getDimSgl() {return dimSgl;}//devolve o length das shingles

		//devolve o elemento da minHash na posi�ao pretendida
        private String getElem(int idx) {
                assert idx < k;
                return minHash[idx];
        }
        
        //fun�ao de hash com numero primo 227
        private int hashShingle(String sg, int nhash) {
                int hashcode = 0;
                for(int i =0; i<sg.length(); i++)
                        hashcode += ((int)sg.charAt(i)-32)*conjAltr[nhash][i];
                return hashcode%227;
        }
        
        //devolve todos os shingles do file
        public LinkedList<String> getShingles() {
        	return shingle;
        }
        
        public String toString() {
                String str = "";
                for(String s : minHash)
                        str += s+",";
                return str;
        }
}
