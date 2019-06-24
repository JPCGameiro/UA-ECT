import static java.lang.System.*;
import java.util.Arrays;
import number.Fraction; // Usa o tipo Fraction

/**
 * Testes do tipo de dados Fraction.
 */
public class TestFractions {
  
  public static void main(String[] args) {
    
    out.println("Creating fractions...");
    Fraction two = new Fraction(2, 1);
    out.printf("two = %s\n", two);
    Fraction x = randomFraction();
    out.printf("x = %s\n", x);
    Fraction x2 = new Fraction(-2*x.num(), 2*x.den());  // == x  //fallha quando denominador negativo
    out.printf("x2 = %s\n", x2);
    Fraction y = randomFraction();
    out.printf("y = %s\n", y);
    Fraction z = randomFraction();
    out.printf("z = %s\n", z);
    
    out.println("Testing equals...");
    assert x.equals(x2) : "Should satisfy x == x2";
    assert (x.add(x2)).equals( two.multiply(x) ) : "Should satisfy x+x2 == 2*x";
    
    // Uncomment code blocks progressively to perform more and more tests...
    
    out.println("Testing compareTo...");
    assert x.compareTo(x2) == 0;
    // Verificar que x < z equivale a z > x:
    assert Integer.signum(x.compareTo(z)) == -Integer.signum(z.compareTo(x));
    assert Integer.signum(x2.compareTo(z)) == -Integer.signum(z.compareTo(x2));
    // O que é signum? Ver https://docs.oracle.com/javase/9/docs/api/java/lang/Integer.html#signum-int-
    
    out.println("Testing sorting of fractions...");
    Fraction[] array = randomArray(10);
    out.println("Random array:");
    showArray(array);
    Arrays.sort(array);  // <- sorting!
    out.println("Sorted array:");
    showArray(array);
  
    out.println("Testing ZERO and ONE...");
    // A instrução abaixo deve dar um erro de compilação!
    Fraction.ZERO = two;  // This should not be possible!
    out.printf("ZERO = %s\n", Fraction.ZERO);
    out.printf("ONE = %s\n", Fraction.ONE);
    assert Fraction.ZERO.compareTo(Fraction.ONE) < 0 : "Should satisfy 0 < 1";
    assert Fraction.ONE.subtract(Fraction.ONE).equals(Fraction.ZERO);
    
    out.println("Testing operations and properties...");
    assert x.add(Fraction.ZERO).equals(x) : "Neutral element of addition";
    assert x.multiply(Fraction.ONE).equals(x) : "Neutral element of multiplication";
    
    assert x.subtract(x2).equals(Fraction.ZERO) : "Should satisfy x-x == 0";
    if (x.num() != 0)
      assert x.divide(x2).equals(Fraction.ONE) : "Should satisfy x/x == 1";
    
    // Comutatividade da adição:
    assert x.add(y).equals( y.add(x) ) : "x+y == y+x";
    
    // Acrescente testes da comutatividade, associatividade e distributividade
    //...
    
    out.println("Testing complex expressions...");
    assert two.equals( Fraction.ONE.add(Fraction.ONE).subtract(Fraction.ZERO) );  // 2 == 1+1-0
    assert x.add(x2).divide(two).subtract(x).equals(Fraction.ZERO): "Should satisfy (x+x2)/2 - x == 0";    
    out.println("All tests OK.");
  }

  // Gera uma fração aleatória com numerador e denominador entre -100 e 100.
  static Fraction randomFraction() 
  {
    int n, d;
    n = (int)(Math.random()*201.0) - 100;
    do 
    {
      d = (int)(Math.random()*201.0) - 100;
    } while (d <= 0);  // avoid zero!
    return new Fraction(n, d);
  }

  // Gera um array com n frações aleatórias
  static Fraction[] randomArray(int n) {
    Fraction[] array = new Fraction[n];
    for (int i = 0; i < n; i++) {
      array[i] = randomFraction();
    }
    return array;
  }

  static void showArray(Fraction[] array) 
  {
    String sep = "";
    for (int i = 0; i < array.length; i++) 
    {
      if (i>0) 
      {
        int d = array[i-1].compareTo(array[i]);
        sep = (d<0)? "<" : (d>0)? ">" : "=";
      }
      out.printf(" %s %s", sep, array[i]);
    }
    out.println();
  }

  // Check if program is being run with -ea, exit if not.
  static 
  {  // A static block. Cool!
    boolean ea = false;
    assert ea = true; // assert with a side-effect, on purpose!
    if (!ea) 
    {
      err.println("This program must be run with -ea!");
      exit(1);
    }
  }
}
