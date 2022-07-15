package monitorSynchronization;

import genclass.GenericIO;

/**
 *    Producer thread.
 */

public class Prod extends Thread

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

   public Prod (String name, ControlledInstantiation inst, int id, Class<?> cl)
   {
     super (name);
     this.name = name;
     this.inst = inst;
     this.id = id;
     this.cl = cl;
   }

  /**
   *   Producer life cycle.
   */

   @Override
   public void run ()
   {
     try
     { Thread.sleep ((long) (1 + 40 * Math.random ()));
     }
     catch (InterruptedException e) {}

     synchronized (cl)
     { GenericIO.writelnString ("I, " + name + ", am going to write the value " + id + " in instantiation number " +
                                inst.getInstNumb () + " of data type ControlledInstantiation!");
     }
     inst.putVal (id);
   }
}
