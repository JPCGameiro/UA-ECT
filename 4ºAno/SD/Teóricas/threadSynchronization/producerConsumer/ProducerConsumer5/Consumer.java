package producerConsumer.ProducerConsumer5;

import genclass.GenericIO;

/**
 *    General description:
 *       definition of the consumer thread - monitor-based solution.
 */

public class Consumer extends Thread

{
  /**
   *   Internal data
   */

   private int id,                                   // consumer thread ID
               nIter;                                // number of iterations of the life cycle
   private MemFIFO<Integer> mem;                     // data transfer buffer

  /**
   *   Constructor
   *
   *   @param name thread name
   *   @param prodId consumer ID
   *   @param nIter number of iterations of the life cycle
   *   @param fifo data transfer buffer
   */

   public Consumer (String name, int prodId, int nIter, MemFIFO<Integer> fifo)
   {
     super (name);
     id = prodId;
     this.nIter = nIter;
     mem = fifo;
   }

  /**
   *   Life cycle
   */

   @Override
   public void run ()
   {
     int val;                                        // retrieved value

     for (int i = 0; i < nIter; i++)
     {
       /* retrieve a value */

       val = mem.read ();

       /* print (consume) a value */

       GenericIO.writelnString ("The value that was read by the consumer thread " + id + " is " + val%1000 +
                                " and was written by the producer thread " + (val/1000-1) + ".");

        /* do something else */

       try
       { Thread.sleep ((int) (100 * Math.random ()));
       }
       catch (InterruptedException e) {}
     }
   }
}
