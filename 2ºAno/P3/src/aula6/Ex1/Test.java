//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public class Test {

	public static void main(String[] args) {
		
		Ementa ementa = new Ementa("Especial Caloiro", "Snack da UA");
		Prato[] pratos = new Prato[10];
	
		for (int i=0; i < pratos.length; i++){
			pratos[i] = randPrato(i);
			int cnt = 0;
		
			while (cnt <2){ // Adicionar 2 Ingredientes a cada Prato
				Alimento aux = randAlimento();
				if (pratos[i].addIngrediente(aux)) {
					cnt++;
				}
				else
					System.out.println("ERRO: Não é possivel adicionar '" +
												aux + "' ao -> " + pratos[i]);
			}
			ementa.addPrato(pratos[i], DiaSemana.rand()); // Dia Aleatório
		}
		System.out.println("\n"+ementa);
	}
	
	
	// Retorna um Alimento Aleatoriamente
	public static Alimento randAlimento() {
		switch ((int) (Math.random() * 4)) {
			default:
				case 0:
					return new Carne(VariedadeCarne.frango, 22.3, 345.3, 300);
				case 1:
					return new Peixe(TipoPeixe.congelado, 31.3, 25.3, 200);
				case 2:
					return new Legume("Couve Flor", 21.3, 22.4, 150);
				case 3:
					return new Cereal("Milho", 19.3, 32.4, 110);
		}
	}
	
	// Retorna um Tipo de Prato Aleatoriamente
	public static Prato randPrato(int i) {
		switch ((int) (Math.random() * 3)) {
			default:
				case 0:
					return new Prato("Prato N." + i);
				case 1:
					return new PratoVegetariano("Prato N." + i + " (Vegetariano)");
				case 2:
					return new PratoDieta("Prato N." + i + " (Dieta)", 90.8);
		}
	}

}
