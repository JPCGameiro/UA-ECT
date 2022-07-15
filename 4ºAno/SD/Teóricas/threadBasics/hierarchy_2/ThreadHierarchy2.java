package hierarchy_2;

import genclass.GenericIO;

/**
 *    Thread management.
 */

public class ThreadHierarchy2
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

  public static void main (String [] args)
  {
    ThreadGroup [] thrL1G,                                             // array of thread L1 groups
                   thrL2G;                                             // array of thread L2 groups
    Thread [] l1Thr;                                                   // array of level 1 threads
    int nL1,                                                           // number of level 1 threads
        nL2;                                                           // number of level 2 threads per level 1 threads
    String nTGBase = "Thread_G1.",                                     // base name for thread groups
           nTL1Base = "Thread_L1.";                                    // base name for level 1 threads
    ReportStatus rps = new ReportStatus ();                            // info about threads internal state

    GenericIO.writeString ("Number of level 1 threads? ");
    nL1 = GenericIO.readlnInt ();
    GenericIO.writeString ("Number of level 2 threads per level 1 threads? ");
    nL2 = GenericIO.readlnInt ();

    rps.report ();
    thrL1G = new ThreadGroup[nL1];
    thrL2G = new ThreadGroup[nL1];
    l1Thr = new Thread[nL1];
    for (int n = 0; n < nL1; n++)
    { thrL1G[n] = new ThreadGroup (nTGBase + (n+1));
      thrL2G[n] = new ThreadGroup (thrL1G[n], nTGBase + (n+1) + "_G2");
      thrL1G[n].setMaxPriority (Thread.MAX_PRIORITY - (n+1));
      thrL2G[n].setMaxPriority (Thread.MAX_PRIORITY - (n+2));
      l1Thr[n] = new Thread (thrL1G[n], new Level1ThreadRun (thrL2G[n], nTL1Base + (n+1), nL2, rps), nTL1Base + (n+1));
      l1Thr[n].setPriority (Thread.MAX_PRIORITY);
    }
    for (int n = 0; n < nL1; n++)
      l1Thr[n].start ();
  }
}