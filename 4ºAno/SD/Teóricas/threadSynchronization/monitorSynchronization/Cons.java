package monitorSynchronization;

import genclass.GenericIO;

/**
 *    Consumer thread.
 */

public class Cons extends Thread

{
  /**
   *   Thread name.
   */

   private String name;

  /**
   *   Storage region.
   */

   private ControlledInstantiation inst;

  /**
   *   Thread id.
   */

   @SuppressWarnings("unused")
private int id;

  /**
   *   Representation of data type GenericIO in Java virtual machine.
   */

   private final Class<?> cl;

  /**
   *   Thread instantiation.
   *
   *     @param name thread name
   *     @param inst storage region
   *     @param id thread id
   *     @param cl object representing data type GenericIO in Java virtual machine
   */

   public Cons (String name, ControlledInstantiation inst, int id, Class<?> cl)
   {
     super (name);
     this.name = name;
     this.inst = inst;
     this.id = id;
     this.cl = cl;
   }

  /**
   *   Consumer life cycle.
   */

   @Override
   public void run ()
   {
     int val;

     try
     { Thread.sleep ((long) (1 + 40 * Math.random ()));
     }
     catch (InterruptedException e) {}

     val = inst.getVal ();
     synchronized (cl)
     { GenericIO.writelnString ("I, " + name + ", read the value " + val + " in instantiation number " +
                                inst.getInstNumb () + " of data type ControlledInstantiation!");
     }
   }
}
