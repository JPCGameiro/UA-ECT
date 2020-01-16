//João Gameiro  Nº93097
//P3-ECT-UA

package aula10.Ex1;
import java.util.Iterator;

public abstract class TestGeneric {

	public static void main(String[] args) {
		

		VectorGeneric<Pessoa> vp = new VectorGeneric<Pessoa>();
		System.out.println("\nVECTORGENERIC - Pessoa\n");
		for (int i=0; i<10; i++)
			vp.addElem(new Pessoa("Bebé no Vector "+i,1000+i, Data.today()));
		Iterator<Pessoa> vec = vp.iterator();
	
		printSet(vp.iterator());
		

		ListaGeneric<Pessoa> lp = new ListaGeneric<Pessoa>();
		while ( vec.hasNext() )
			lp.addElem( vec.next() );
		
		System.out.println("\nLISTAGENERIC - Pessoa\n");
		Iterator<Pessoa> lista = lp.iterator();
		while ( lista.hasNext() )
			System.out.println( lista.next() );
		
		
		ListaGeneric<Figura> figList = new ListaGeneric<Figura>();
		figList.addElem(new Circulo (1,3, 1));
		figList.addElem(new Quadrado(3,4, 2));
		figList.addElem(new Rectangulo(1,2, 2,5));
		System.out.println("\nLISTAGENERIC - Figura\n");
		printSet(figList.iterator());
		
		System.out.println("Soma da Area de Lista de Figuras: " + sumArea(figList));
		
		System.out.println("\nLISTAGENERIC - Rectângulos\n");
		// Partindo do principio que Quadrado extends Rectangulo:
		ListaGeneric< Rectangulo > quadList = new ListaGeneric<Rectangulo>();
		quadList.addElem(new Quadrado(3,4, 2));
		quadList.addElem(new Rectangulo(1,2, 2,5));
		printSet(quadList.iterator());
		
		System.out.println("Soma da Area de Lista de Quadrados: " + sumArea(quadList));
	}
	
	public static double sumArea(ListaGeneric<? extends Figura> f)
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

