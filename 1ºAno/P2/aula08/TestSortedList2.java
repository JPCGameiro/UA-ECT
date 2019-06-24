
import static java.lang.System.*;
import p2utils.*;

public class TestSortedList2
{
  // Coloca argumentos inteiros numa SortedList e faz testes.
  // Use o argumento "?" para ver o primeiro elemento e "-" para o remover.

  // Experimente, por exemplo:
  // java -ea TestSortedList2 8 31 - 4231 f 12 6 64 - 213 2123 - 10 7 612

  public static void main(String[] args) {
    SortedList<Integer> lst1 = new SortedList<Integer>();

    for(int i=0; i<args.length; i++) {
      String arg = args[i];
      switch (arg) {
      case "f": // show first element
        out.printf("lst1.first(): %d\n", lst1.first()); break;
      case "-": // remove first element
        lst1.removeFirst(); break;
      default:  // insert value
        lst1.insert(Integer.parseInt(arg)); break;
      }
    }
    out.println(lst1.isSorted());

    SortedList<Integer> lst2 = new SortedList<Integer>();
    lst2.insert(999);
    lst2.insert(9);
    lst2.insert(99);

    // Test contains
    assert lst2.contains(999);
    assert lst2.contains(9);
    assert !lst2.contains(10);
    
    // Verifica se os números 1, 10 e 30 pertencem a lst1
    out.println(lst1.contains(1));
    out.println(lst1.contains(10));
    out.println(lst1.contains(30));

    /* Comente/descomente as instruções para testar cada método...
    */

    // Test toString
    out.printf("lst1: %s\n", lst1.toString());
    out.printf("lst2: %s\n", lst2.toString());
    assert lst2.toString().equals("[9, 99, 999,]");

    // Test merge
    SortedList<Integer> lst3 = lst1.merge(lst2);
    out.printf("lst3: %s\n", lst3);

    SortedList<Integer> lst4 = lst2.merge(lst1);
    out.printf("lst4: %s\n", lst4);

    assert lst3.toString().equals(lst4.toString()) : "Should be the same";

    out.printf("lst1: %s\n", lst1); // should be the same as before merge!
    out.printf("lst2: %s\n", lst2);


    // Retira elementos de lst1 e confirma que estão em lst3
    while (!lst1.isEmpty()) {
      assert lst3.contains(lst1.first());
      lst1.removeFirst();
    }
    // Retira elementos de lst2 e confirma que estão em lst3
    while (!lst2.isEmpty()) {
      assert lst3.contains(lst2.first());
      lst2.removeFirst();
    }
  }
}

