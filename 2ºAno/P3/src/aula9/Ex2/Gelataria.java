//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex2;

public class Gelataria {

	public static void main(String[] args) {
		
		Gelado ice;
		ice = new GeladoSimples("Baunilha");
		ice.base(2);
		System.out.println();
		new Copo(ice).base(3);
		System.out.println();
		new Cone(ice).base(1);
		System.out.println();
		new Topping(ice, "Canela").base(2);
		System.out.println();
		ice = new Topping(ice, "Nozes");
		ice.base(1);
		System.out.println();
		ice = new Topping(new Cone(new GeladoSimples("Morango")), "Fruta");
		ice.base(2);
		System.out.println();
		ice = new Topping(
		new Topping(
		new Copo(new GeladoSimples("Manga")), "Chocolate"), "Natas");
		ice.base(4);
		System.out.println();
		ice = new Topping(ice, "Pepitas");
		ice.base(3);

	}

}
