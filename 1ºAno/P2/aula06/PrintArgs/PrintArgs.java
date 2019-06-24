import static java.lang.System.*;

public class PrintArgs {

  public static void main(String[] args) 
  {
    printArray(args, args.length);
  }

  /** Imprime as N primeiras strings do array, uma por linha. */
  public static void printArray(String[] array, int N) 
  {
	  assert N <= array.length;
	  int i=array.length;
	  
	  if(i-N<array.length)
	  {
		  out.printf(array[i-N]+"\n");
		  printArray(array, N-1);
	  }	    
  }

}

