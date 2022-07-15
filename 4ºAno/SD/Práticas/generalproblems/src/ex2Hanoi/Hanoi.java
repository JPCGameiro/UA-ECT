package ex2Hanoi;

/**
 * @author joao
 */
public class Hanoi {

	public static void main(String[] args) {
		
		TorresHanoi t = new TorresHanoi(9);
		System.out.println(t.toString());
		t.resolv();		
		System.out.println();
		System.out.println(t.toString());
	}

}
