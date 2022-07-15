package producerConsumer.ProducerConsumer4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 *    General description:
 *       definition of a FIFO memory of generic objects built in explicitly as a monitor using reference types from the
 *         Java concurrency library.
 *       It extends a generic memory data type.
 */

public class MemFIFO<R> extends MemObject<R>
{
  /**
   *  Characterization of the FIFO access discipline
   */

   private int inPnt,                                      // insertion pointer
               outPnt;                                     // retrieval pointer
   private boolean empty;                                  // signaling empty FIFO

  /**
   *  Characterization of the FIFO access synchonization
   */

   private Lock mutex;                                     // mutual exclusion lock
   private Condition fifoFull,                             // FIFO full condition variable
                     fifoEmpty;                            // FIFO empty condition variable

  /**
   *  Constructor
   *
   *    @param storage storage area
   */

   public MemFIFO (R [] storage)
   {
     super (storage);
     inPnt = outPnt = 0;
     empty = true;
     mutex = new ReentrantLock ();
     fifoFull = mutex.newCondition ();
     fifoEmpty = mutex.newCondition ();
   }

  /**
   *  Writing a value in mutual exclusion regime.
   *
   *    @param val value to store
   */

   @Override
   public void write (R val)
   {
     mutex.lock();                                         // enter critical region

     while ((inPnt == outPnt) && !empty)
     { try
       { fifoFull.await ();                                // the calling thread must block if FIFO is fully occupied
       }
       catch (InterruptedException e) {}
     }

     mem[inPnt] = val;
     inPnt = (inPnt + 1) % mem.length;
     empty = false;
     fifoEmpty.signal ();

     mutex.unlock ();                                       // exit critical region
   }

  /**
   *  Reading a value in mutual exclusion regime.
   *
   *    @return the retrieved value
   */

   @Override
   public R read ()
   {
     R val;                                                // retrieved value

     mutex.lock();                                         // enter critical region

     while (empty)
     { try
       { fifoEmpty.await ();                               // the calling thread must block if FIFO is empty
       }
        catch (InterruptedException e) {}
     }

     val = mem[outPnt];
     outPnt = (outPnt + 1) % mem.length;
     empty = (inPnt == outPnt);
     fifoFull.signal ();

     mutex.unlock ();                                       // exit critical region

     return val;
   }
}
