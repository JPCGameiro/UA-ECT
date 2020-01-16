

public class ArrayGenerator {
	private int[][] array;
	public ArrayGenerator(int k, int len) {
		array = new int[k][len];
		for(int i = 0; i<k; i++) {
			for(int j = 0; j<len; j++) 
				array[i][j] = (int) (Math.random()*227);
		}
	}
	public int[][] getArray(){
		return array;
	}
}
