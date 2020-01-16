//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex1;

public class UtilCompare {
		

	@SuppressWarnings("unchecked")
	public static <T> Comparable<T> findMax(Comparable<T> [] array) 
	{
		int index = 0;
		
		for(int i=0;i<array.length;i++) {
			if(array[i]!=null && array[i].compareTo((T)array[index])>0)
				index = i;
		}
		
		return array[index];
	}
	

	@SuppressWarnings("unchecked")
	public static <T> void sortArray(Comparable<T> [] array)
	{
		for(int i=0;i<array.length;i++)
		{
			for(int z=i+1;z<array.length;z++)
			{
				if((array[i].compareTo((T)array[z]))>0)
				{
					Comparable<T> tmp = array[i];
					array[i] = array[z];
					array[z] = tmp;
				}
			}
		}
	}
}
