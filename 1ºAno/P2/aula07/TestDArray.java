// Compile and run like:
// man bash | java -ea TestDArray

import static java.lang.System.*;
import java.util.Scanner;
import p2utils.*;

public class TestDArray
{
  private static final Scanner input = new Scanner(System.in);

  public static void main(String[] args) {

    DynamicArray<String> words = new DynamicArray<>();
    int n = 0;
    while (input.hasNext()) {
      String word = input.next();
      words.set(n, word);
      n++;
    }

    out.printf("Final words.length() = %d\n", words.length());
    for (int p = words.length()-1; p > 0; p = p/2) {
      out.printf("words.get(%d) = %s\n", p, words.get(p));
    }
    
    DynamicArray<Boolean> numbers = new DynamicArray<>();
    numbers.set(23, true);
    numbers.set(12, true);
    numbers.set(30, true);
    numbers.set(2, true);
    numbers.set(11, true);
    out.println("Loto numbers:");
    for (int i = 1; i <= 49; i++) 
    {
      out.printf(" %s", numbers.get(i, false) ? "X":"-");
      if (i%7==0)
        out.println();
    }
  }

}
