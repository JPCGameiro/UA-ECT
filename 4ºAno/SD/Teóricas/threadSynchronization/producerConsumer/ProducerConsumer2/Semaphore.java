package producerConsumer.ProducerConsumer2;

/**
 *    General description:
 *       definition of a Dijstra type semaphore.
 */

public class Semaphore
{
  /**
   *   Internal data
   */

   private int val = 0,                              // green / red indicator
               numbBlockThreads = 0;                 // number of the blocked threads in the monitor

  /*
   *  The down /up operations are made atomic by access locking to the associated object monitor.
   */

  /**
   *  Down operation.
   */

   public synchronized void down ()
   {
     if (val == 0)
        { numbBlockThreads += 1;
          try
          { wait ();
          }
          catch (InterruptedException e) {}
        }
        else val -= 1;
   }

  /**
   *  Up operation.
   */

   public synchronized void up ()
   {
     if (numbBlockThreads != 0)
        { numbBlockThreads -= 1;
          notify ();
        }
        else val += 1;
   }
}
