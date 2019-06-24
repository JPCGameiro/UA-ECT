
import static java.lang.System.*;
import p2utils.*;

public class TestList1
{
  public static void main(String[] args) {
    LinkedList<Integer> lst = new LinkedList<Integer>();

    for(int i=0; i<10; i++) 
    {
      lst.addFirst(i);
      lst.addLast(100+i);
    }
    out.println(lst.first());

    lst.removeFirst();

    LinkedList<Integer> cln = lst.clone();

    out.println(cln.first());

    LinkedList<Integer> rev = cln.reverse();

    out.println(rev.first());

    out.println(rev.contains(5));
    rev.remove(5);
    out.println(rev.contains(5));

    LinkedList<Integer> another = new LinkedList<Integer>();
    another.addFirst(123);
    another.addFirst(999);

    LinkedList<Integer> conc = rev.concatenate(another);

    for(int p=0; p<conc.size(); p++) 
      out.println(conc.get(p));
  }
}
