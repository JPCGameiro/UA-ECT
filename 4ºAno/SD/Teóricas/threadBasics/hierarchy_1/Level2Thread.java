package hierarchy_1;
/**
 *    Level 2 thread.
 */

public class Level2Thread extends Thread
{
  private ReportStatus rps;                                          // info about threads internal state

  public Level2Thread (ThreadGroup thrG, String tName, ReportStatus rps)
  {
    super (thrG, tName);
    this.rps = rps;
  }

  @Override
  public void run ()
  {
    rps.report ();
  }
}