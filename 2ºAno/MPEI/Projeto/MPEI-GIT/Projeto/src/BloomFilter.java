

import java.io.File;
import java.util.LinkedList;

public class BloomFilter {
	
	private int[] B;//array Bloom Filter;
	private final double pfn;//probabilidade de falsos negativos;
	private final int n;//numero de posições do bloom filter;
	private final int m;//numero de elem do conjunto;
	private final int k;//numero de hash functions necessarias;
	private static int[][] conjAltr;
	
	public BloomFilter(LinkedList<File> C) {
		m = C.size();
		pfn = 0.01;
		n = (int) (Math.log(pfn)/Math.log(Math.pow(1-Math.exp(-0.693),(0.693/m))));//n otimo
		k = (int) (n*0.693/m);//k otimo
		B = new int[n];
		conjAltr = new ArrayGenerator(k, 50).getArray();
		System.out.printf("Bloom filter com %d posições e %d hash functions\n",n, k );
		for(int i = 0; i < C.size(); i++)//adicionar elementos ao conjunto B
			adicionar(C.get(i));
	}
	
	//string hashFunction
	private int hashFunct(String key, int idx) {
		int h = 0;
		
		for(int i =0; i<key.length(); i++) 
			h = 37 * h + (int)(key.charAt(i));
		
		for(int i =0; i<key.length(); i++)
			h += key.charAt(i)*conjAltr[idx][i%50];
		
		h = h%n;
		if(h < 0)
			h += n;
		return h;
	}
	
	//adicionar novo elemento ao array B
	public void adicionar(File elem) {
		assert !membro(elem);
		
		for(int i=0; i<k; i++) {
			B[hashFunct(elem.getName(), i)] = 1;
		}
	}
	
	//testar se o elemento existe
	public boolean membro(File elem) {
		for(int i=0; i<k; i++) {
			if(B[hashFunct(elem.getName(), i)] == 0)
				return false;
		}
		return true;
	}
}