import static java.lang.System.*;
import java.util.Scanner;

public class TestTarefa2 {
  static final Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    Data christmas = new Data(25, 12, 2017);
    Data halloween = new Data(31, 10, 2017);
    Tarefa t = new Tarefa(halloween, christmas, "Holiday season");
    //assert t.inicio().compareTo(t.fim())==1 : "Inicio tem de ser menor que Fim";
    out.println(t);
    
    // A instrução try...catch permite intercetar exceções!
    // (Falamos disso para a semana...)
    try 
    {
      t = new Tarefa(christmas, halloween, "Holiday season");
      assert t.inicio().compareTo(t.fim())==-1 : "Inicio should be lower than Fim"; // should fail!
      err.printf("OOPS! Esta tarefa não devia ter sido criada: %s\n", t);
      exit(1);
    } 
    catch (AssertionError e) 
    {
      err.printf("As expected: %s\n", e);
    }
    out.println(t);
  
    t = new Tarefa(halloween, christmas, "Holiday Season"); // should fail! when t.texto=""
    out.println(t);
  
    out.println("Class Tarefa passed all tests.");
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
