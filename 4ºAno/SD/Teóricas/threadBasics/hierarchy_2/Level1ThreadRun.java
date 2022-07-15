package hierarchy_2;

/**
 *    Level 1 thread.
 */

public class Level1ThreadRun implements Runnable
{
  private ThreadGroup thrGL2;                                        // group for level 2 threads
  private String nTL2Base;                                           // base name for level 2 threads
  private Thread [] l2Thr;                                           // array of level 2 threads
  private int nL2;                                                   // number of level 2 threads per level 1 threads
  private ReportStatus rps;                                          // info about threads internal state

  public Level1ThreadRun (ThreadGroup thrGL2, String name, int nL2, ReportStatus rps)
  {
    this.thrGL2 = thrGL2;
    this.nTL2Base = name + "_L2.";
    this.nL2 = nL2;
    this.l2Thr = new Thread[nL2];
    this.rps = rps;
  }

  @Override
  public void run ()
  {
    for (int n = 0; n < nL2; n++)
    { l2Thr[n] = new Thread (thrGL2, new Level2ThreadRun (rps), nTL2Base + (n+1));
      l2Thr[n].setPriority (Thread.MAX_PRIORITY);
    }

    for (int n = 0; n < nL2; n++)
      l2Thr[n].start ();
    rps.report ();
  }
}