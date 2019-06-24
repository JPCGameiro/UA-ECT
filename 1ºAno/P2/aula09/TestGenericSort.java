// This program should use the generic mergeSort method in p2utils.Sorting
// to sort its string arguments.
import static java.lang.System.*;
import java.util.Scanner;
import java.util.Arrays;
import static p2utils.Sorting.*;
public class TestGenericSort
{
	public static void main(String[] args)
	{
		if(args.length<1)
		{
			out.printf("Usage method: java TestGenericSort <word> <word> ...\n");
			System.exit(0);
		}
		
		String [] words = args;
		
		mergeSort(words, 0, words.length);
		
		String result = "[";
		for(int z=0; z<words.length; z++)
		{
			if(z==words.length-1)
				result = result + words[words.length-1] + "]";
			else if(z==0)
				result = result + words[0] + ", ";
			else
				result = result + words[z] + ", ";
		}
		out.println(result);
			
			
	}
}

