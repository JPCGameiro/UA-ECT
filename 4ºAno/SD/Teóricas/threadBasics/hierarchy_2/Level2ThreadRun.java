package hierarchy_2;

/**
 *    Level 2 thread.
 */

public class Level2ThreadRun implements Runnable
{
  private ReportStatus rps;                                          // info about threads internal state

  public Level2ThreadRun (ReportStatus rps)
  {
    this.rps = rps;
  }

  @Override
  public void run ()
  {
    rps.report ();
  }
}