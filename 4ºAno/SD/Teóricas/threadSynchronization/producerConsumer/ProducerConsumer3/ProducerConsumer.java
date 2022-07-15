package producerConsumer.ProducerConsumer3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import genclass.GenericIO;

/**
 *    General description:
 *       producers / consumers problem - monitor-based solution using a thread pool.
 */

public class ProducerConsumer
{
  /**
   *    Main method.
   *
   *    @param args runtime arguments
   */

   public static void main (String [] args)
   {
     int nElem = 2;                                    // size of the data transfer buffer
     MemFIFO<Integer> fifo =                           // data transfer buffer
             new MemFIFO<> (new Integer [nElem]);
     int nProd = 4,                                    // number of producer threads
         nCons = 4,                                    // number of consumer threads
         nPoolSize = 5;                                // number of threads in the pool

     Producer [] prod = new Producer [nProd];          // array of producer threads
     Consumer [] cons = new Consumer [nCons];          // array of consumer threads
     int niter = 6;                                    // number of iterations of the life cycle
     ExecutorService thrManag;                         // thread management service

     GenericIO.writelnString ();
     GenericIO.writelnString ("Main thread ID is " + Thread.currentThread ().getId ());
     GenericIO.writelnString ("Main thread name is " + Thread.currentThread ().getName ());
     GenericIO.writelnString ("Current state of the main thread is " +
                              Thread.currentThread ().getState ().toString ());
     GenericIO.writelnString ();

     /* problem initialization */

     for (int i = 0; i < nProd; i++)
     { prod[i] = new Producer ("Producer_" + i, i, niter, fifo);
       GenericIO.writelnString ("Producer " + i + " thread ID " + " is " + prod[i].getId ());
       GenericIO.writelnString ("Producer " + i + " thread name " + " is " + prod[i].getName ());
       GenericIO.writelnString ("Current state of the producer " + i + " thread is " + prod[i].getState ().toString ());
     }

     for (int i = 0; i < nCons; i++)
     { cons[i] = new Consumer ("Consumer_" + i, i, niter, fifo);
       GenericIO.writelnString ("Consumer " + i + " thread ID " + " is " + cons[i].getId ());
       GenericIO.writelnString ("Consumer " + i + " thread name " + " is " + cons[i].getName ());
       GenericIO.writelnString ("Current state of the consumer " + i + " thread is " + cons[i].getState ().toString ());
     }
     GenericIO.writelnString ();

     thrManag = Executors.newFixedThreadPool (nPoolSize);
     GenericIO.writelnString ("A thread pool whose size is " + nPoolSize + " was instanciated.");
     GenericIO.writelnString ();

     /* start of the simulation */

     for (int i = 0; i < nProd; i++)
     { thrManag.execute (prod[i]);
       GenericIO.writelnString ("Current state of the producer " + i + " thread is " + prod[i].getState ().toString ());
     }

     for (int i = 0; i < nCons; i++)
     { thrManag.execute (cons[i]);
       GenericIO.writelnString ("Current state of the consumer " + i + " thread is " + cons[i].getState ().toString ());
     }
     GenericIO.writelnString ();

     /* wait for the end of the simulation */

     thrManag.shutdown ();
     GenericIO.writelnString ("The thread pool is shutting down.");

     GenericIO.writelnString ();
  }
}
