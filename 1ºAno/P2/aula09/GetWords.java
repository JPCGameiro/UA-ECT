import static java.lang.System.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

import static p2utils.Sorting.*;

public class GetWords
{
  public static void main(String[] args) 
  {
    if (args.length < 1) 
    {
      err.println("Usage: java GetWords <file>");
      exit(1);
    }
    for(int z = 0 ; z < args.length; z++)
    {
		File fin = new File(args[z]);
		String[] words = extractWords(fin);
		mergeSort(words, 0, words.length);
		for(int i = 0; i < words.length-1; i++)
			out.printf("%s\n", words[i]);
		out.println("Number of distinct words: " + removeRepeat(words)); 
	}
  }
  
  //Removes the words that are repeated of a String array
  public static int removeRepeat(String[] words)
  {
	  int i=0, j=0, cnt=0, f=0;
	  String[] repeated = new String[words.length];
	  
	  for(i=0;i<words.length;i++)
	  {
			for(j=i+1;j<words.length;j++)
			{
				if(words[j].equals(words[i]))
				{
					cnt++;
					repeated[f] = words[j];
				}
				else
					cnt = cnt;
			}
	  }
	  return cnt;
  }
	
  // Read words from a file, return words in an array
  static String[] extractWords(File fin) 
  {
    try 
    {
      // count the words
      int n = 0;
      Scanner scf = new Scanner(fin).useDelimiter("[\\p{Punct}\\p{Space}]+");
      // (words are delimited by 1 or more punctuation or whitespace char)
      while (scf.hasNext()) 
      {
        scf.next();
        n++;
      }
      scf.close();

      // create the array
      String[] result = new String[n];

      // read the words
      int i = 0;
      scf = new Scanner(fin).useDelimiter("[\\p{Punct}\\p{Space}]+");
      while (scf.hasNext()) 
      {
        result[i] = scf.next();
        i++;
      }
      scf.close();

      return result;
    }
    catch (IOException e) 
    {
      err.printf("Error: %s\n", e);
      exit(1);
      return null;  // never happens...
    }
  }

}
