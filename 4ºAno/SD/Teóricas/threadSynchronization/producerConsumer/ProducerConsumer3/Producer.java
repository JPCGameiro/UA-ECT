package producerConsumer.ProducerConsumer3;

/**
 *    General description:
 *       definition of the producer thread - monitor-based solution.
 */

public class Producer extends Thread

{
  /**
   *   Internal data
   */

   private int id,                                   // producer thread ID
               nIter;                                // number of iterations of the life cycle
   private MemFIFO<Integer> mem;                     // data transfer buffer

  /**
   *   Constructor
   *
   *   @param name thread name
   *   @param prodId producer ID
   *   @param nIter number of iterations of the life cycle
   *   @param fifo data transfer buffer
   */

   public Producer (String name, int prodId, int nIter, MemFIFO<Integer> fifo)
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
     int val;

     for (int i = 0; i < nIter; i++)
     {
       /* produce a value */

       val = (id+1)*1000 + i;

       /* store a value */

       mem.write (val);

       /* do something else */

       try
       { Thread.sleep ((int) (10 * Math.random ()));
       }
       catch (InterruptedException e) {}
     }
   }
}
