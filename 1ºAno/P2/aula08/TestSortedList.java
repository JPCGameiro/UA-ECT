
import static java.lang.System.*;
import p2utils.*;

public class TestSortedList
{
  // Apresenta
  // * os números dados na linha de comando por ordem crescente;
  // * os restantes argumentos por ordem lexicográfica.  (FALTA FAZER!)
  // Utiliza duas listas ordenadas para fazer isto.

  public static void main(String[] args) 
  {
    // Uma lista para os números:
    SortedList<Double> numbers = new SortedList<Double>();
    // Outra lista para as restantes strings:
    SortedList<String> others = new SortedList<String>(); 

    for (int i=0; i<args.length; i++) 
    {
		if(IntVerify(args[i]))
		{
			// Converte args[i] para número e guarda na lista:
			numbers.insert(Double.parseDouble(args[i]) );
		}
		else
		{
			// Caso não seja número, guardar args[i] noutra lista:
			others.insert(args[i]);
		}
    }

    // Escrever a lista dos números (destruindo-a):
    out.printf("Numbers (%b):\n", numbers.isSorted());
    while (!numbers.isEmpty()) 
    {
      out.println(numbers.first());
      numbers.removeFirst();
    }
	out.printf("Resto (%b):\n", others.isSorted());
	while(!others.isEmpty())
	{
		out.println(others.first());
		others.removeFirst();
	}
  }
  
  public static boolean IntVerify(String value)
  {
	  try
	  {
		  Integer.parseInt(value);
		  return true;
	  }
	  catch(NumberFormatException e)
	  {
		  return false;
	  }
  }

}

