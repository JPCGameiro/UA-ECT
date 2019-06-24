import static java.lang.System.*;
import java.io.*;
import java.util.Scanner;

public class SortInts
{
  public static void main(String[] args) throws IOException 
  {
	  SortedListInt list = new SortedListInt();
	  File f = null;
	  int i=0;
	  
	  assert args.length >= 1 : "Usage Method : java -ea SortInts <file>";
	  
	  for(i=0;i<args.length;i++)
	  {
		  try
		  {
			  f = new File(args[i]);
			  if(!f.canRead()) throw new IOException();
		  }
		  catch(ArrayIndexOutOfBoundsException | IOException e)
		  {
			  out.println("ERROR in entry File");
			  out.println("System will shut down ...");
			  System.exit(0);
		  }
		  
		  Scanner sc = new Scanner(f);
		  
		  while(sc.hasNextLine())
		  {
			  String line = sc.nextLine();
			  if(isNumber(line))
			  {
				  list.insert(Integer.parseInt(line));
			  }		  
		  }		  
		  sc.close();
	 }
	  
	  assert list.isSorted();
	  assert list.size() > 0;
	  
	  list.print();		
  }
  //função que verifica se uma string é um número 
  public static boolean isNumber(String line)
  {
	  try
	  {
		  Integer.parseInt(line);
		  return true;
	  }
	  catch(NumberFormatException e)
	  {
		  return false;
	  }
  }

}


