import static java.lang.System.*;
import util.Input;

public class TestInput
{
  
  static String MESSAGE ="This program requests several real values.\n" + "Try inserting numbers with invalid format or out of range.\n";
  
  public static void main(String[] args) 
  {
    out.println(MESSAGE);
    
    double x = Input.getDouble("Real value X? ");
    out.println(x);
    
    double nota = Input.getDouble("Nota? ", 0.0, 20.0);
    out.println(nota);
    
    double temp = Input.getDouble("Temperature (Celsius)? ",-273.15, Double.POSITIVE_INFINITY);
    out.println(temp);

    //double z = Input.getDouble("Nota? ", 0.0, -10.0);  // should fail!
    //double z = Input.getDouble("Nota?", 0.0, 10.0);      // should work! 
    
  }
}
