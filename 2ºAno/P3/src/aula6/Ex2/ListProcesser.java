//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex2;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Predicate;

public class ListProcesser {
	
	public static <E> List<E> filter(List<E> list, Predicate<E> tester)
	{
		List<E> l = new LinkedList<E>();
		for(E e : list)
		{
			if(tester.test(e))
			{
				l.add(e);
			}
		}
		return l;
	}
	
}
