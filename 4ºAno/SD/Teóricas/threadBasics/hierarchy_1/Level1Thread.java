package hierarchy_1;
/**
 *    Level 1 thread.
 */

public class Level1Thread extends Thread
{
  private ThreadGroup thrGL2;                                        // group for level 2 threads
  private String nTL2Base;                                           // base name for level 2 threads
  private Level2Thread [] l2Thr;                                     // array of level 2 threads
  private int nL2;                                                   // number of level 2 threads per level 1 threads
  private ReportStatus rps;                                          // info about threads internal state

  public Level1Thread (ThreadGroup thrGL1, ThreadGroup thrGL2, String name, int nL2, ReportStatus rps)
  {
    super (thrGL1, name);
    this.thrGL2 = thrGL2;
    nTL2Base = name + "_L2.";
    this.nL2 = nL2;
    l2Thr = new Level2Thread[nL2];
    this.rps = rps;
  }

  @Override
  public void run ()
  {
    for (int n = 0; n < nL2; n++)
    { l2Thr[n] = new Level2Thread (thrGL2, nTL2Base + (n+1), rps);
      l2Thr[n].setPriority (Thread.MAX_PRIORITY);
    }

    for (int n = 0; n < nL2; n++)
      l2Thr[n].start ();
    rps.report ();
  }
}