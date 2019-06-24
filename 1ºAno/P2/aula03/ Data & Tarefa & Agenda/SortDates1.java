import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;
public class SortDates1 
{
  public static void main(String[] args) throws IOException
  {
    int N = 1000;
    Data[] datas = new Data[N];

    int n = loadFile(new File("dates1.txt"), datas);
    
    sort(datas, n);

    printArray(datas, n);
  }

  public static void printArray(Data[] datas, int n) 
  {
    for (int i = 0; i < n; i++) 
    {
      out.printf("%s\n", datas[i]);
    }
  }

  /** Load dates from a text file.
  * @param f  the name of the file
  * @param datas an array where dates are stored
  * @return the number of dates actually read
  */
  public static int loadFile(File f, Data[] datas) throws IOException 
  {
    Scanner scf = new Scanner(f);
    int n = 0;  // number of lines read
    while (n < datas.length && scf.hasNextLine()) 
    {
      String line = scf.nextLine();
      datas[n] = new Data(line);
      n++;
    }
    scf.close();
    return n;
  }

  public static void sort(Data[] datas, int n) 
  {
	int i=0, j=0;
    for (i = 0; i < n-1; i++) 
    {
      for (j = i+1; j < n; j++) 
      {
		if (datas[j].compareTo(datas[i])==-1) 
		{
			Data tmp = datas[j];
			datas[j] = datas[i];
			datas[i] = tmp;
		}
      }
    }
  }
}

