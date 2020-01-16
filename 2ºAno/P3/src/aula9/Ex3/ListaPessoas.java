//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex3;
import java.util.Iterator;
import java.util.LinkedList;


public class ListaPessoas {
	
	protected LinkedList<Pessoa> lista;
	
	//Construtor
	public ListaPessoas(LinkedList<Pessoa> lista)
	{
		this.lista = lista;
	}
	public ListaPessoas()
	{
		lista = new LinkedList<Pessoa>();
	}
	
	//Adicionar uma pessoa
	public void addPessoa(Pessoa p)
	{
		lista.add(p);
	}
	//Remover uma pessoa
	public void remove(Pessoa p)
	{
		lista.remove(p);
	}
	//Devolver o size da lista
	public int size()
	{
		return lista.size();
	}
	//Verificar se a lista está vazia
	public boolean isEmpty()
	{
		return lista.isEmpty();
	}
	
	Iterator<Pessoa> iterator() 
	{
		return (this).new VectorIterator();
	}
	
	BFIterator  bfiterator()
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
			return (indice < lista.size());
		}

		@Override
		public Pessoa next() {
			if(hasNext()) {
				Pessoa r = lista.get(indice);
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ lista.size() + " elements");
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
			return (indice < lista.size());
		}

		@Override
		public Pessoa next() {
			if(hasNext()) {
				Pessoa r = lista.get(indice);
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ lista.size() + " elements");
		}
		
		@Override
		public boolean hasPrevious() {
			return (indice > 0);
		}
		@Override
		public Object previous() {
			if(hasPrevious()) {
				int r = indice-1;
				return lista.get(r);
			}
			return null;
		}
	}

}
