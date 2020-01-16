package aula10.Ex2;

public class BSTTest {

	public static void main(String[] args) {
		
		BinarySearchTree<Figura> tree= new BinarySearchTree<Figura>();
		Circulo c1 = new Circulo(2,2,4);
		Circulo c2 = new Circulo(1,2,4);
		
		tree.insert(c1);
		tree.insert(c2);
		
		
		System.out.println(tree.contains(c1));					//true
		System.out.println(tree.contains(c2));					//true
		System.out.println(tree.contains(new Circulo(2)));		//false
		
		tree.remove(c1);
		
		System.out.println(tree.contains(c1));					//false
		

	}

}
