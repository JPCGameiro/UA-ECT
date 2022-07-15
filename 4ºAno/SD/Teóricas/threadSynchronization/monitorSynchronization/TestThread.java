package monitorSynchronization;

import genclass.GenericIO;

/**
 *    Thread that requests a buffer for value transfer and instantiates a producer and a consumer to
 *      perform the transfer.
 */

public class TestThread extends Thread

{
  /**
   *   Thread name.
   */

   private String name;

  /**
   *   Thread id.
   */

   private int id;

  /**
   *   Representation of data type GenericIO in Java virtual machine.
   */

   private final Class<?> cl;

  /**
   *   Thread instantiation.
   *
   *     @param name thread name
   *     @param id thread id
   *     @param cl object representing data type GenericIO in Java virtual machine
   */

   public TestThread (String name, int id, Class<?> cl)
   {
     super (name);
     this.name = name;
     this.id = id;
     this.cl = cl;
   }

  /**
   *   Thread life cycle.
   */

   @Override
   public void run ()
   {
     ControlledInstantiation inst;                         // storage region
     Prod prod;                                            // producer thread
     Cons cons;                                            // consumer thread

     try
     { Thread.sleep ((long) (1 + 40 * Math.random ()));
     }
     catch (InterruptedException e) {}

     inst = ControlledInstantiation.generateInst ();
     synchronized (cl)
     { GenericIO.writelnString ("I, " + name + ", got the instantiation number " + inst.getInstNumb () +
                                " of data type ControlledInstantiation!");
     }

     prod = new Prod (name + "_writer", inst, id, cl);
     cons = new Cons (name + "_reader", inst, id, cl);
     synchronized (cl)
     { GenericIO.writelnString ("I, " + name + ", am going to create the threads that will exchange the value!");
     }
     prod.start ();
     cons.start ();
     for (int i = 0; i < 2; i++)
     { try
       { if (i == 0)
            prod.join ();
            else cons.join ();
         synchronized (cl)
         { if (i == 0)
              GenericIO.writelnString ("My thread which writes the value, " + name + "_writer" + ", has terminated.");
              else GenericIO.writelnString ("My thread which reads the value, " + name + "_reader" + ", has terminated.");
         }
       }
       catch (InterruptedException e) {}
     }

     synchronized (cl)
     { GenericIO.writelnString ("I, " + name + ", am going to release the instantiation number " + inst.getInstNumb () +
                                " of data type ControlledInstantiation!");
     }
     ControlledInstantiation.releaseInst ();

   }
}