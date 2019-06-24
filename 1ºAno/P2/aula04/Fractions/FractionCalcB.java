import static java.lang.System.*;
import java.util.Scanner;
import number.Fraction; // Usa o tipo Fraction!

/**
 * Calculadora com fracções.
 * Semelhante a FractionCalculator da aula03.
 * @author João Manuel Rodrigues
 * 2007-2018
 */

public class FractionCalcB {
  private static Scanner sc = new Scanner(System.in); 
 
  private static final String GREETING
        = "FractionCalcB: a calculator with fractions.\n"
        + "Enter expressions such as:\n"
        + "  1/2 + 1/3\n"
        + "  3/2 x -1/3\n"
        + "  2/-3 : 4\n"
        + "  5 == 10/2\n"
        + "  25/5 < 4/2\n"
        + "Use Ctrl+D to exit.\n";

  public static void main(String[] args) {
    out.println(GREETING);
    while (true) 
    {
      out.print("? ");  // show prompt
      if (!sc.hasNextLine()) break;
      String line = sc.nextLine();
      
      String[] p = line.split(" +"); // split by 1 or more spaces
      if (p.length != 3) {
        err.println("Invalid expression!");
      }
      else {
        
        String frac1 = p[0];
        String op = p[1];
        String frac2 = p[2];

        // 1) definir fracções (a partir dos argumentos);
        Fraction x = Fraction.parseFraction( frac1 );
        Fraction y = Fraction.parseFraction( frac2 );

        Object result;
        // Algumas operações devolvem Fraction, outras boolean ou int.
        // Por isso, o resultado tem de ser de um tipo mais geral.
        
        // 2) executar a operação;
        switch (op) {
          case "+":
            result = x.add(y);  // .toString() é invocado implicitamente!
            break;
          case "*":
          case "x":
            result = x.multiply(y); break;
          case "-":
            result = x.subtract(y); break;
          case "/":
          case ":":
            result = x.divide(y); break;
          case "==":
            result = x.equals(y); break;
          case "<":
             result = x.compareTo(y) < 0; break; // descomente qdo fizer
          default:
            result = null;
        }

        // 3) imprimir a operação solicitada e o resultado;
        if (result == null)
          err.println("Invalid operator!");
        else
          out.printf("%s %s %s = %s\n", x, op, y, result);
      }
    }
    out.println();
  }

  // Check if program is being run with -ea, exit if not.
  static {  // A static block. Cool!
    boolean ea = false;
    assert ea = true; // assert with a side-effect, on purpose!
    if (!ea) {
      err.println("This program must be run with -ea!");
      exit(1);
    }
  }
}
