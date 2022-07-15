package testPreemptiveness;

import genclass.GenericIO;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

/**
 *    Report evolution of the shared access variable.
 *    I/O intensive thread.
 */

public class ReportEvolution extends Thread
{
  private Variable val;                                    // reference to the shared access variable
  private String varName;                                  // name of the shared access variable
  private int maxVal;                                      // maximum value reached by the shared access variable

  public ReportEvolution (String varName, Variable val, int maxVal)
  {
    super ();
    this.varName = varName;
    this.val = val;
    this.maxVal = maxVal;
  }

  @Override
  public void run ()
  {
   int n = 0;                                             // iteration number
    FileWriter dump = null;                                // dump stream

    try
    { dump = new FileWriter (new File ("/dev/null"), true);
    }
    catch (IOException e)
    { GenericIO.writelnString (e.toString ());
      e.printStackTrace ();
      System.exit (1);
    }
    while (val.a < maxVal)
    { try
      { dump.write ("Current value of variable " + varName + " is " + val.a);
        dump.flush ();
      }
      catch (IOException e)
      { GenericIO.writelnString (e.toString ());
        e.printStackTrace ();
        System.exit (1);
      }
      n += 1;
    }
    GenericIO.writelnString ("N. of iterations in printing values of " + varName + " = " + n);
  }
}