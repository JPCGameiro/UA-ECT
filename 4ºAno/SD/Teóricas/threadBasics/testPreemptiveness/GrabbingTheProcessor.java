package testPreemptiveness;

import genclass.GenericIO;

/**
 *    Increment the value of the shared access variable.
 *    Computation intensive thread.
 */

public class GrabbingTheProcessor extends Thread
{
  private String varName;                                  // name of the shared access variable
  private Variable val;                                    // reference to the shared access variable
  private int maxVal;                                      // maximum value reached by the shared access variable

  public GrabbingTheProcessor (String varName, Variable val, int maxVal)
  {
    super ();
    this.varName = varName;
    this.val = val;
    this.maxVal = maxVal;
  }

  @Override
  public void run ()
  {
    while (val.a < maxVal)
    { val.a += 1;
      // Thread.yield ();                                  // uncomment to allow thread yielding
    }
    GenericIO.writelnString (varName + " = " + val.a);
  }
}
