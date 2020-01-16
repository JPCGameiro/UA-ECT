//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex3;

import java.util.Iterator;

public class VectorPessoas {
	
	private Pessoa[] listaPessoas;
	private int nPessoas;
	private final int ALLOC = 50;
	private int dimClasse = ALLOC;
	
	//Construtor
	public VectorPessoas()
	{
		listaPessoas = new Pessoa[ALLOC];
		nPessoas = 0;
	}
	
	//Adicionar pessoas
	public boolean addPessoa(Pessoa p)
	{
		if(p==null)
			return false;
		if(nPessoas>=dimClasse) {
			dimClasse = dimClasse + ALLOC;
			Pessoa[] array = new Pessoa[dimClasse];
			System.arraycopy(listaPessoas, 0, array, 0, nPessoas);
			listaPessoas = array;
		}
		listaPessoas[nPessoas++] = p;
		return true;
	}
	
	//Remover Pessoas
	public boolean removePessoa(Pessoa p)
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
	public int totalPessoas()
	{
		return nPessoas;
	}
	
	//Devolver uma pessoa
	public Pessoa getPessoa(int i)
	{
		return listaPessoas[i];
	}
	
	Iterator<Pessoa> iterator() 
	{
		return (this).new VectorIterator();
	}
	BFIterator bfiterator()
	{
		return (this).new VectorBFIterator(); 
	}
	
	
	
	private class VectorIterator implements Iterator<Pessoa>{
		
		private int indice;
		
		VectorIterator() {
			indice = 0;
		}
		@Override
		public boolean hasNext() {
			return (indice < nPessoas);
		}

		@Override
		public Pessoa next() {
			if(hasNext()) {
				Pessoa r = listaPessoas[indice];
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ nPessoas + " elements");
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Operação não suportada!");
		}
		
	}
	
	private class VectorBFIterator implements BFIterator{
		
		private int indice;
		
		VectorBFIterator() {
			indice = 0;
		}
		@Override
		public boolean hasNext() {
			return (indice < nPessoas);
		}

		@Override
		public Pessoa next() {
			if(hasNext()) {
				Pessoa r = listaPessoas[indice];
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ nPessoas + " elements");
		}
		

		@Override
		public boolean hasPrevious() {
			return indice>0;
		}
		@Override
		public Object previous() {
			if(hasPrevious()) {
				int l = indice-1;
				return listaPessoas[l];
			}
			return null;
		}
		
	}

}
