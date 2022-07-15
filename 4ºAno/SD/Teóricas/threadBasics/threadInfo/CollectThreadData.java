package threadInfo;

import genclass.GenericIO;

/**
 *   Basic features of threads.
 */

public class CollectThreadData
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

    public static void main (String [] args)
    {
       Thread thr = Thread.currentThread ();               // current thread

       GenericIO.writelnString ("Thread characterization");
       GenericIO.writelnString ();
       GenericIO.writelnString ("Name = " + thr.getName ());
       GenericIO.writelnString ("Internal identifier = " + thr.getId ());
       GenericIO.writelnString ("Group = " + thr.getThreadGroup ().getName ());
       GenericIO.writelnString ("Priority = " + thr.getPriority ());
       GenericIO.writelnString ("Current state = " + thr.getState ().toString ());
       GenericIO.writelnString ();
       GenericIO.writelnString ("Possible states:");
       for (Thread.State c : Thread.State.values())
         GenericIO.writelnString ("   " + c.toString ());
    }
}
