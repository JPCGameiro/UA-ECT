package testPreemptiveness;

import genclass.GenericIO;

/**
 *    Thread management.
 */

public class TestPreemptiveness
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

  public static void main (String [] args)
  {
    Variable [] a = new Variable [4];                           // array of shared access variables
    for (int i = 0; i < 4; i++)
      a[i] = new Variable (0);

    GrabbingTheProcessor [] gp = new GrabbingTheProcessor[4];   // array of computation intensive threads
    for (int i = 0; i < 4; i++)
      gp[i] = new GrabbingTheProcessor ((i == 0) ? "A" : (i == 1) ? "B" : (i == 2) ? "C" : "D", a[i], 10000000);

    ReportEvolution [] re = new ReportEvolution[4];             // array of I/O intensive threads
    for (int i = 0; i < 4; i++)
      re[i] = new ReportEvolution ((i == 0) ? "A" : (i == 1) ? "B" : (i == 2) ? "C" : "D", a[i], 10000000);

    int pGP,                                                    // priority of computation intensive threads
        pRE;                                                    // priority of I/O intensive threads

    GenericIO.writeString ("Priority level of computation intensive threads? ");
    pGP = GenericIO.readlnInt();
    GenericIO.writeString ("Priority level of I/O intensive threads? ");
    pRE = GenericIO.readlnInt();
    for (int i = 0; i < 4; i++)
    { gp[i].setPriority (pGP);
      re[i].setPriority (pRE);
    }
    GenericIO.writelnString ("Priority of computation intensive threads = " + gp[0].getPriority ());
    GenericIO.writelnString ("Priority of I/O intensive threads = " + re[0].getPriority ());

    for (int i = 0; i < 4; i++)
      re[i].start ();
    GenericIO.writelnString ("I have already started the I/O intensive threads!");

    for (int i = 0; i < 4; i++)
      gp[i].start ();
    GenericIO.writelnString ("I have already started the computation intensive threads!");

    GenericIO.writelnString ("I have finished my job!");
  }
}