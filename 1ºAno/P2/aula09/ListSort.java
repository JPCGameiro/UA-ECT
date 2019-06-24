import static java.lang.System.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import p2utils.LinkedList;

public class ListSort {

  public static void main(String[] args) {
    if (args.length < 1) {
      err.println("Usage: java -ea ListSort <file> ...");
      exit(1);
    }

    LinkedList<String> lines = new LinkedList<>();
    for(int a = 0; a < args.length; a++) {
      File fin = new File(args[a]);
      readLines(fin, lines);
    }
    quickSort(lines);

    lines.print();
  }

  // Read lines from a file and append them to a list
  static void readLines(File fin, LinkedList<String> lst) {
    try {
      // read the lines
      Scanner scf = new Scanner(fin);
      while (scf.hasNextLine()) {
        lst.addLast(scf.nextLine());
      }
      scf.close();
    } catch (IOException e) {
      err.println(e);
      exit(1);
    }
  }

  /** A quick sort on a generic list.
   * @param lst is a linked list containing comparable elements.
   * The list is sorted 'in-place'.
   */
  public static <T extends Comparable<T>> void quickSort(LinkedList<T> lst) {
    if (lst.size() > 1) {
      // Take first element as a pivot value:
      T pivot = lst.first(); lst.removeFirst();

      // A list for elements smaller than the pivot:
      LinkedList<T> smaller = new LinkedList<T>();
      // A list for elements not smaller than the pivot:
      LinkedList<T> greater = new LinkedList<T>();

      // Now, take each element from lst and append it
      // to the smaller list, if element < pivot, or
      // to the greater list, otherwise.
      //... FILL IN THE CODE ...

      // The lst should be empty now:
      assert lst.isEmpty();

      // Sort each of the lists:
      quickSort(smaller);
      quickSort(greater);
      
      // Transfer smaller elements back to lst:
      //... FILL IN THE CODE ...
      
      // Append the pivot:
      lst.addLast(pivot);
      
      // Transfer greater elements back to lst:
      //... FILL IN THE CODE ...
    }
  }

}
