//João Gameiro  Nº93097
//P3-ECT-UA

package aula11.Ex1.Exa;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.ArrayList;

public abstract class TestGeneric {

	public static void main(String[] args) {
		

		List<Pessoa> vp = new LinkedList<Pessoa>();
		System.out.println("\nLinkedList/ArrayList - Pessoa\n");
		for (int i=0; i<10; i++)
			vp.add(new Pessoa("Bebé no Vector "+i,1000+i, Data.today()));
		Iterator<Pessoa> vec = vp.iterator();
	
		printSet(vp.iterator());
		
		
		List<Pessoa> lp = new ArrayList<Pessoa>();
		while ( vec.hasNext() )
			lp.add( vec.next() );
		
		Iterator<Pessoa> lista = lp.iterator();
		while ( lista.hasNext() )
			System.out.println( lista.next() );
		
		
		
		List<Figura> figList = new LinkedList<Figura>();
		figList.add(new Circulo (1,3, 1));
		figList.add(new Quadrado(3,4, 2));
		figList.add(new Rectangulo(1,2, 2,5));
		System.out.println("\nLinkedList - Figura\n");
		printSet(figList.iterator());
		
		System.out.println("Soma da Area de Lista de Figuras: " + sumArea(figList));
		
		System.out.println("\nArrayList - Rectângulos\n");
		// Partindo do principio que Quadrado extends Rectangulo:
		List< Rectangulo > quadList = new ArrayList<Rectangulo>();
		quadList.add(new Quadrado(3,4, 2));
		quadList.add(new Rectangulo(1,2, 2,5));
		printSet(quadList.iterator());
		
		System.out.println("Soma da Area de Lista de Quadrados: " + sumArea(quadList));
	}
	
	public static double sumArea(List<? extends Figura> f)
	{		
		double result=0;
		@SuppressWarnings("rawtypes")
		Iterator iter = f.iterator();
		while(iter.hasNext()) {
			Figura fig = (Figura)iter.next();
			result = result + fig.area();

		}		
		return result;
	}
	
	public static void printSet(@SuppressWarnings("rawtypes") Iterator i)
	{
		while(i.hasNext())
			System.out.println(i.next());
	}
}

