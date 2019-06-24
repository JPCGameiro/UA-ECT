import static java.lang.System.*;
import java.util.Scanner;

public class NumberArray
{
  private int[] array = null;

  static final Scanner in = new Scanner(System.in);
  static final int NUMBER_OF_COLUMNS = 16;

  public NumberArray() {
    read();
  }

  public int[] array() {
    return array;
  }

  public int length() {
    return array.length;
  }

  public void read() {
    array = new int[0];

    while (in.hasNext()) {
      String word = in.next();
      int[] newArray = new int[array.length+1];
      arraycopy(array, 0, newArray, 0, array.length);
      try {
        newArray[array.length] = Integer.parseInt(word);
      }
      catch (NumberFormatException e) {
        err.println("Error: input text \""+word+"\"cannot be converted to integer!");
        exit(2);
      }
      array = newArray;
    }
  }

  public void print() {
    for (int i = 0; i < array.length; i++) {
      if (i > 0 && i % NUMBER_OF_COLUMNS == 0)
        out.println();
      out.printf("%5d", array[i]);
    }
    out.println();
  }

}

