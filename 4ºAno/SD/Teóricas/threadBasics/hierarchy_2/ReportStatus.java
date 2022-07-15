package hierarchy_2;

import genclass.GenericIO;

/**
 *    Thread status reporting.
 */

public class ReportStatus
{
  public synchronized void report ()
  {
    Thread thr = Thread.currentThread ();                                      // calling thread
    ThreadGroup thrG = thr.getThreadGroup ();                                  // group of calling thread
    int ATCG = thrG.activeCount (),                                            // n. of active threads in the group
        AGCG = thrG.activeGroupCount ();                                       // n. of active subgroups in the group
    Thread [] list = new Thread[ATCG];                                         // info about active threads in the group

    for (int n = 0; n < list.length; n++)
      list[n] = null;

    thrG.enumerate (list, false);
    GenericIO.writelnString ();
    GenericIO.writelnString ("Thread name: " + thr.getName ());
    GenericIO.writelnString ("Thread id: " + thr.getId ());
    GenericIO.writelnString ("Thread priority: " + thr.getPriority ());
    GenericIO.writelnString ("Name of the thread group: " + thrG.getName ());
    GenericIO.writelnString ("Name of the parent group of the thread group: " +
                             thrG.getParent ().getName ());
    GenericIO.writelnString ("N. of active threads in the thread group: " + ATCG);
    GenericIO.writeString ("Name of active threads in the thread group: ");
    for (int n = 0; n < list.length; n++)
      if (list[n] != null)
         { GenericIO.writeString (list[n].getName ());
           if ((n + 1) < list.length) GenericIO.writeString (" - ");
         }
    GenericIO.writelnString ();
    GenericIO.writelnString ("N. of active subgroups in the thread group: " + AGCG);
  }
}