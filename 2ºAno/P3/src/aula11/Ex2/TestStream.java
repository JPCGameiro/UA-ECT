//João Gameiro  Nº93097
//P3-ECT-UA

package aula11.Ex2;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TestStream {

	public static void main(String[] args) {
	
		Figura f0 = new Circulo(2);
		Figura f1 = new Circulo(4);
		Figura f2 = new Circulo(3);
		Figura f3 = new Circulo(10);
		Figura f4 = new Quadrado(6);
		Figura f5 = new Quadrado(7);
		Rectangulo r1 = new Quadrado(5);
		Rectangulo r2 = new Rectangulo(10,10);
		Rectangulo r3 = new Rectangulo(1,2);
		
		List<Figura> flist = new LinkedList<Figura>();
		flist.add(f0);
		flist.add(f1);
		flist.add(f2);
		flist.add(f3);
		flist.add(f4);
		flist.add(f5);
		flist.add(r1);
		flist.add(r2);
		flist.add(r3);
		System.out.println("Figuras\n");
		for(Figura f : flist)
			System.out.println(f);
		System.out.println("\nFigura com maior àrea: " + maiorFiguraJ8(flist));
		System.out.println("\nFigura com maior perimetro: " + maiorPerimetroJ8(flist));
		System.out.println("\nSoma das àreas das Figuras: " + AreaTotalJ8(flist));
		System.out.println("\nSoma das àreas dos Circulos: " + areaTotalTipoJ8(flist, f0.getClass().getCanonicalName()));
		System.out.println("\nSoma das àreas dos Quadrados: " + areaTotalTipoJ8(flist, f4.getClass().getCanonicalName()));
		System.out.println("\nSoma das àreas dos Rectangulos: " + areaTotalTipoJ8(flist, r3.getClass().getCanonicalName()));	
		
	}
	
	public static Figura maiorFiguraJ8(List<Figura> figs)
	{
		return figs.stream()							//converter a lista para uma stream
			.max(Comparator.comparing(Figura::area))	//Comparar as áreas das figuras
			.get();										//Obter o resultado da Stream
	}
	
	public static Figura maiorPerimetroJ8(List<Figura> figs)
	{
		return figs.stream()
				.max(Comparator.comparing(Figura::perimetro))
				.get();
	}
	
	public static double AreaTotalJ8(List<Figura> figs)
	{
		return figs.stream() 							
				.mapToDouble(Figura::area)				//figura.area();
			    .sum();			
	}
	
	private static double areaTotalTipoJ8(List<Figura> figs,String subtipoNome)
	{
		return figs.stream()
				.filter(value -> value.getClass().getName()==subtipoNome)
				.mapToDouble(Figura::area)
				.sum();
	}

}
