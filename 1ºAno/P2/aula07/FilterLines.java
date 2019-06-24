import static java.lang.System.*;
import java.io.*;
import java.util.Scanner;
// import java.util.*;   // => "error: reference to LinkedList is ambiguous"
// java.util.LinkedList colide com p2utils.LinkedList!
import p2utils.*;

public class FilterLines
{
  public static void main(String[] args) throws IOException
  {
    if (args.length != 1) 
    {
      err.printf("Usage: java -ea FilterLines text-file\n");
      exit(1);
    }
    File fil = null;
    
    try
    {
		fil = new File(args[0]);
		if(!fil.canRead()) throw new IOException();
	}
	catch(ArrayIndexOutOfBoundsException | IOException e)
	{
		System.out.printf("ERROR: File does not exist\nSystem will shut down\n");
		System.exit(0);
	}

    // Criar listas para as linhas curtas, médias e longas.
    LinkedList <String> curtas = new LinkedList<>();
    LinkedList <String> medias = new LinkedList<>();
    LinkedList <String> longas = new LinkedList<>();

    
    Scanner sf = new Scanner(fil);
    // exceções poderiam ser intercetadas e mostrar mensagem de erro.
    while (sf.hasNextLine()) 
    {
      String line = sf.nextLine();
      // Guardar linha na lista apropriada, consoante o tamanho.
      if(line.length() < 20)
      {
		  curtas.addFirst(line);
	  }
	  else if(line.length() >= 20 && line.length() < 40)
	  {
		  medias.addFirst(line);
	  }
	  else if(line.length() > 40)
	  {
		  longas.addFirst(line);
	  }

    }
    sf.close();

    // Escrever conteúdo das listas...
    out.println("Curtas---|---------|---------|---------|---------");
    curtas.print();

    out.println("Médias---|---------|---------|---------|---------");
    medias.print();

    out.println("Longas---|---------|---------|---------|---------");
    longas.print();
  }

}
