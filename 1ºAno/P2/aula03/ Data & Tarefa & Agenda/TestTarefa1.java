// Exemplo de invocação:
//   java TestTarefa1 tasks1.txt

import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;

public class TestTarefa1 
{

  public static void main(String[] args) throws IOException 
  {
    Tarefa[] tarefas = new Tarefa[1000];
    
    for (String arg: args) 
    {
      int n = loadFile(new File("tasks1.txt"), tarefas);

      for(int i = 0; i < n; i++) 
      {
        out.println(tarefas[i].toString());
      }
      
      out.println(tarefas[0].fim());
      out.println(tarefas[0].texto());
      out.println(tarefas[n-1].inicio());
    }
  }

  /** Load tasks from a text file.
  * @param f  the name of the file
  * @param tasks an array where tasks are stored
  * @return the number of tasks actually read
  */
  public static int loadFile(File f, Tarefa[] tasks) throws IOException 
  {
    Scanner scf = new Scanner(f);
    int n = 0;  // number of lines read
    while (n < tasks.length && scf.hasNextLine()) 
    {
      String line = scf.nextLine();
      // Cada linha tem 2 datas e um texto, separadas por TAB ("\t")
      // Use o método split para separar os elementos e colocá-los num array p
      String[] p = line.split("\t"); 
      
      Data ini = new Data(p[0]);
      Data fim = new Data(p[1]);
      tasks[n] = new Tarefa(ini, fim, p[2]);
      n++;
    }
    scf.close();
    return n;
  }
}
