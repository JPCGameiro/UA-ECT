package monitorSynchronization;

import genclass.GenericIO;

/**
 *    Analysis of thread synchronization mechanisms in Java.
 */

public class TestSynchronization
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

    public static void main (String [] args)
    {
       int NT = 4;                                         // number of communicating threads

       TestThread [] thr = new TestThread[NT];             // communicating threads array
       Class<?> cl = null;                                 // representation of data type GenericIO in Java virtual
                                                           //   machine

       try
       { cl = Class.forName ("genclass.GenericIO");
       }
       catch (ClassNotFoundException e)
       { GenericIO.writelnString ("Data type genclass.GenericIO was not found!");
         e.printStackTrace ();
         System.exit (1);
       }

       for (int i = 0; i < NT; i++)
         thr[i] = new TestThread ("Thread_base_" + i, i, cl);

       for (int i = 0; i < NT; i++)
         thr[i].start ();

       synchronized (cl)
       { GenericIO.writelnString ("I have already created the threads!");
       }

       for (int i = 0; i < NT; i++)
       { try
         { thr[i].join ();
         }
         catch (InterruptedException e) {}
         synchronized (cl)
         { GenericIO.writelnString ("The thread " + "Thread_base_" + i + " has terminated.");
         }
       }
    }
}