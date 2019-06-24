import static java.lang.System.*;
import java.util.Scanner;

public class p13 
{
  public static final Scanner in = new Scanner(System.in);

  public static void main(String[] args) 
  {
    int n;
    while (true) 
    {
      out.print("N? ");
      n = in.nextInt();
      if (n > 0) break;
      err.println("ERROR: invalid number!");
    }

    String verb = isPrime(n)? "is" : "is not";
    out.printf("Number %d %s prime\n", n, verb);
  }
  //Função que verifica se é primo
  public static boolean isPrime(int n) 
  {
    int i=0;
    boolean r=true;
    
    for(i=2;i<=Math.sqrt(n);i++)
    {
		if(n%i==0)
		{
			r=false;
			break;
		}
	}
	return r;
  }
}
