//João Gameiro  Nº93097
//P3-ECT-UA

package aula10.Ex1;

import java.util.Iterator;

public class VectorGeneric<T> {
	
	private T[] listaPessoas;
	private int nPessoas;
	private final int ALLOC = 50;
	private int dimClasse = ALLOC;
	
	//Construtor
	@SuppressWarnings("unchecked")
	public VectorGeneric()
	{
		listaPessoas = (T[]) new Object[ALLOC];
		nPessoas = 0;
	}
	
	//Adicionar pessoas
	public boolean addElem(T p)
	{
		if(p==null)
			return false;
		if(nPessoas>=dimClasse) {
			dimClasse = dimClasse + ALLOC;
			@SuppressWarnings("unchecked")
			T[] array = (T[]) new Object[dimClasse];
			System.arraycopy(listaPessoas, 0, array, 0, nPessoas);
			listaPessoas = array;
		}
		listaPessoas[nPessoas++] = p;
		return true;
	}
	
	//Remover Pessoas
	public boolean removeElem(T p)
	{
		for(int i=0;i<nPessoas;i++) {
			if(listaPessoas[i]==p) {
				nPessoas--;
				for(int j=i;j<nPessoas;j++)
					listaPessoas[j] = listaPessoas[j+1];
				return true;
			}
		}
		return false;
	}
	
	//Devolver o total de pessoas
	public int totalElem()
	{
		return nPessoas;
	}
	
	//Devolver uma pessoa
	public T getPessoa(int i)
	{
		return listaPessoas[i];
	}
	
	Iterator<T> iterator() 
	{
		return (this).new VectorIterator();
	}
	
	
	
	private class VectorIterator implements Iterator<T>{
		
		private int indice;
		
		VectorIterator() {
			indice = 0;
		}
		@Override
		public boolean hasNext() {
			return (indice < nPessoas);
		}

		@Override
		public T next() {
			if(hasNext()) {
				T r = listaPessoas[indice];
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ nPessoas + " elements");
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Operação não suportada!");
		}
		
	}

}
