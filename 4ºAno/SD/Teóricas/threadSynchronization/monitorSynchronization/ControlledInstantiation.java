package monitorSynchronization;

import genclass.GenericIO;

/**
 *    Data type which controls the numbers of its instantiations.
 *    At the most, NMAX objects of this data type are instantiated.
 */

public class ControlledInstantiation

{
  /**
   *   Maximum number of simultaneous instantiations (specific of the data type).
   */

   private static final int NMAX = 2;

  /**
   *   Number of presently active instantiations (specific of the data type).
   */

   private static int n = 0;

  /**
   *   Total number of instantiations performed so far (specific of the data type).
   */

   private static int nInst = 0;

  /**
   *   Number of present instantiation (specific of each object).
   */

   private int instNumb;

  /**
   *   Storage region (specific of each object).
   */

   private int store;

   /**
    *   Internal instantiation.
    */

   private ControlledInstantiation ()
   {
     instNumb = nInst;
     store = -1;
   }

  /**
   *   Instantiation generation done according to the imposed restrictions.
   *
   *     @return an instantiation
   */

   public static synchronized ControlledInstantiation generateInst ()
   {
     while (n >= NMAX)
     { try
       { ( Class.forName ("ControlledInstantiation")).wait ();
       }
       catch (ClassNotFoundException e)
       { GenericIO.writelnString ("Data type ControlledInstantiation was not found (generation)!");
         e.printStackTrace ();
         System.exit (1);
       }
       catch (InterruptedException e)
       { GenericIO.writelnString ("Static method generateInst was interrupted!");
       }
     }
     n += 1;
     nInst += 1;
     return new ControlledInstantiation ();
   }

  /**
   *   Instantiation release done according to the imposed restrictions.
   */

   public static synchronized void releaseInst ()
   {
     n -= 1;
     try
     { (Class.forName ("ControlledInstantiation")).notify ();
     }
     catch (ClassNotFoundException e)
     { GenericIO.writelnString ("Data type ControlledInstantiation was not found (release)!");
       e.printStackTrace ();
       System.exit (1);
     }
   }

  /**
   *   Getting the instantiation number.
   *
   *     @return the instantiation number
   */

   public synchronized int getInstNumb ()
   {
     return instNumb;
   }

  /**
   *   Writing a value.
   *
   *    @param val value to be written
   */

   public synchronized void putVal (int val)
   {
     store = val;
     while (store != -1)
     { notify ();
       try
       { wait ();
       }
       catch (InterruptedException e)
       { GenericIO.writelnString ("Method putVal was interrupted!");
       }
     }
   }

  /**
   *   Reading a value.
   *
   *     @return read value
   */

   public synchronized int getVal ()
   {
     int val;

     while (store == -1)
     { try
       { wait ();
       }
       catch (InterruptedException e)
       { GenericIO.writelnString ("Method getVal was interrupted!");
       }
     }
     val = store;
     store = -1;
     notify ();

     return val;
   }
}