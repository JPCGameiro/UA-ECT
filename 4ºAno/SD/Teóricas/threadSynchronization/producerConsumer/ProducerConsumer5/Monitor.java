package producerConsumer.ProducerConsumer5;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

/**
 *    General description:
 *       explicit definition of a Lampson-Redell monitor using basic building blocks from the Java concurrency library.
 *
 */

public class Monitor
{
  /**
   *   Mutual exclusion synchronization
   */

   private final AtomicBoolean locked = new AtomicBoolean (false);
   private final Queue<Thread> waitTurn = new ConcurrentLinkedQueue<> ();
   private Thread threadInside = null;

  /**
   *  Lock operation.
   *
   */

   public void lock ()
   {
      boolean wasInterrupted;                              // interrupt signaling flag
      Thread current;                                      // thread reference

      wasInterrupted = false;
      current = Thread.currentThread ();
      waitTurn.add (current);
      while ((waitTurn.peek () != current) || !locked.compareAndSet (false, true))
      { LockSupport.park (this);
        if (Thread.interrupted ()) wasInterrupted = true;
      }
      waitTurn.remove ();
      if (wasInterrupted) current.interrupt ();
      threadInside = current;
   }

  /**
   *  Unlock operation.
   *
   */

   public void unlock ()
   {
      locked.set(false);
      threadInside = null;
      LockSupport.unpark (waitTurn.peek());
   }

  /**
   *  Instantiation of a condition variable.
   *
   *    @return a condition variable
   */

   public ConditionVar newCondition ()
   {
      return new ConditionImpl ();
   }

  /**
   *    General description:
   *       definition of a condition variable.
   *
   *
   */

   private class ConditionImpl implements ConditionVar
   {

     /**
      *   Blocking information
      */

      private final Queue<Thread> sleep = new ConcurrentLinkedQueue<> ();

     /**
      *  Wait operation.
      *
      */

      @Override
      public void await () throws IllegalThreadStateException, InterruptedException
      {
         Thread current;                                   // thread reference

         current = Thread.currentThread();
         if (current != threadInside)
            throw new IllegalThreadStateException ("Invoking thread is not inside the monitor!");
         sleep.add (current);
         unlock ();
         while (sleep.contains (current))
         { LockSupport.park (this);
           if (Thread.interrupted ())
              { sleep.remove (current);
                throw new InterruptedException ();
              }
         }
         lock ();
      }

     /**
      *  Signal operation.
      *
      */

      @Override
      public void signal () throws IllegalThreadStateException
      {
         Thread thr;                                       // thread reference

         if (Thread.currentThread () != threadInside)
            throw new IllegalThreadStateException ("Invoking thread is not inside the monitor!");
         if ((thr = sleep.peek ()) != null)
            { sleep.remove (thr);
              LockSupport.unpark (thr);
            }
      }

     /**
      *  Signal all operation.
      *
      */

      @Override
      public void signalAll () throws IllegalThreadStateException
      {
         Thread thr;                                       // thread reference

         if (Thread.currentThread () != threadInside)
            throw new IllegalThreadStateException ("Invoking thread is not inside the monitor!");
         while ((thr = sleep.peek ()) != null)
         { sleep.remove (thr);
           LockSupport.unpark (thr);
         }
      }
   }
}
