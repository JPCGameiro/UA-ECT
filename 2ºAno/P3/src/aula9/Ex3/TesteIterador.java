//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex3;
import java.util.Iterator;

public abstract class TesteIterador {

	public static void main(String[] args) {
		
		VectorPessoas vp = new VectorPessoas();
		for (int i=0; i<10; i++)
			vp.addPessoa(new Pessoa("Bebé no Vector "+i, 1000+i, Data.today()));
		
		ListaPessoas lp = new ListaPessoas();
		for (int i=0; i<10; i++)
			lp.addPessoa(new Pessoa("Bebé na Lista "+i,	2000+i, Data.today()));
		
		//Teste Iterator
		System.out.println("ITERATOR");
		Iterator<Pessoa> vec = vp.iterator();
		while ( vec.hasNext() )
			System.out.println( vec.next() );
		
		Iterator<Pessoa> lista = lp.iterator();
		while ( lista.hasNext() )
			System.out.println( lista.next() );
		
		//Teste BFIterator
		System.out.println("BFITERATOR - VectorPessoas");
		BFIterator bfi = vp.bfiterator();
		while( bfi.hasNext() ) {
			System.out.println("-> Previous: "+bfi.previous());
			System.out.println( bfi.next() );
			System.out.println("BFIterator hasPrevious? "+ bfi.hasPrevious());
			System.out.println();
		}
		
		System.out.println("BFITERATOR - ListaPessoas");
		BFIterator bfil = lp.bfiterator();
		System.out.println("BFIterator hasPrevious? "+ bfil.hasPrevious());
		while( bfil.hasNext() ) {
			System.out.println("-> Previous: "+bfil.previous());
			System.out.println( bfil.next() );
			System.out.println("BFIterator hasPrevious? "+ bfil.hasPrevious());
			System.out.println();
		}
	}

}
