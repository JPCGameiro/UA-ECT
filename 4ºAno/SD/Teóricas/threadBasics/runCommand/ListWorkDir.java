package runCommand;

import genclass.GenericIO;
import java.io.IOException;

/**
 *   Run the operating system command to list the working directory.
 */

public class ListWorkDir
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

    public static void main (String [] args)
    {  ProcessBuilder ls = new ProcessBuilder ("/usr/bin/ls", "-la");     // command to be executed
       int exitVal = -1;                                                  // command exit status
       Process lsProc = null;                                             // command process

       ls.inheritIO ();                                                   // set standard input, output and error
                                                                          //   streams the same as current process
       GenericIO.writelnString ("Listing current working directory");
       GenericIO.writelnString ();
       GenericIO.writelnString ("----------------------------------------");
       try
       { lsProc = ls.start();                                             // run the command
       }
       catch (IOException e)
       { GenericIO.writelnString (e.toString());
         e.printStackTrace ();
         System.exit (1);
       }
       try
       { exitVal = lsProc.waitFor ();                                     // wait for command to terminate
       }
       catch (InterruptedException e) {}
       GenericIO.writelnString ("----------------------------------------");
       GenericIO.writelnString ("exit status = " + exitVal);
    }
}
