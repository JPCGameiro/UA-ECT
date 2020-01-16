//João Gameiro  Nº93097
//P3-ECT-UA

package aula10.Ex1;
import java.util.Iterator;
import java.util.LinkedList;


public class ListaGeneric<T> {
	
	protected LinkedList<T> lista;
	
	//Construtor
	public ListaGeneric(LinkedList<T> lista)
	{
		this.lista = lista;
	}
	public ListaGeneric()
	{
		lista = new LinkedList<T>();
	}
	
	//Adicionar uma pessoa
	public void addElem(T p)
	{
		lista.add(p);
	}
	//Remover uma pessoa
	public void removeElem(T p)
	{
		lista.remove(p);
	}
	//Devolver o size da lista
	public int totalElem()
	{
		return lista.size();
	}
	//Verificar se a lista está vazia
	public boolean isEmpty()
	{
		return lista.isEmpty();
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
			return (indice < lista.size());
		}

		@Override
		public T next() {
			if(hasNext()) {
				T r = lista.get(indice);
				indice++;
				return r;
			}
			throw new IndexOutOfBoundsException("only "+ lista.size() + " elements");
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Operação não suportada!");
		}
	}
}
