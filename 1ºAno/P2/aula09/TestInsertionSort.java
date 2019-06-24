import static java.lang.System.*;
import java.util.Scanner;
import java.util.Arrays;

import static p2utils.Sorting.*;  // import sorting methods

public class TestInsertionSort {

  static final Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    // Read integers from input into an array:
    int[] array = readInts(input);

    // Make a copy of the array:
    int[] array2 = array.clone();

    // Sort the first n elements in the array with our algorithm:
    insertionSort(array, 0, array.length);
    
    // Print the result:
    out.println(Arrays.toString(array));

    // Sort the second array with Java's sort method and use it to check ours:
    Arrays.sort(array2);
    assert Arrays.equals(array, array2);
  }

  static final int BLOCK = 1024;

  // Read integers from the input scanner.
  // Returns the integers in an array.
  public static int[] readInts(Scanner input) {
    int[] array = new int[0];
    int n = 0;
    while (input.hasNextInt()) {
      int number = input.nextInt();
      if (n == array.length) // if array full, enlarge it
        array = Arrays.copyOf(array, array.length+BLOCK);
      // store each integer in the next available position:
      array[n] = number;
      n++;
    }
    if (input.hasNext()) {
      err.printf("Error: \"%s\" is not an integer!\n", input.next());
      exit(2);
    }
    array = Arrays.copyOf(array, n);  // readjust length to match n
    return array;
  }

}

