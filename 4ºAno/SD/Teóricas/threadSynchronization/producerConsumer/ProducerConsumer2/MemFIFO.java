package producerConsumer.ProducerConsumer2;

/**
 *    General description:
 *       definition of a FIFO memory of generic objects.
 *       It extends a generic memory data type.
 */

public class MemFIFO<R> extends MemObject<R>
{
  /**
   *  Characterization of the FIFO access discipline
   */

   private int inPnt,                                      // insertion pointer
               outPnt;                                     // retrieval pointer

  /**
   *  Characterization of the FIFO access discipline
   */

   private Semaphore access,                               // controls acess with mutual exclusion to the critical region
                     locFull,                              // counts the number of FIFO occupied locations
                     locEmpty;                             // counts the number of FIFO free locations

  /**
   *  Constructor
   *
   *    @param storage storage area
   */

   public MemFIFO (R [] storage)
   {
     super (storage);
     inPnt = outPnt = 0;
     access = new Semaphore ();
     access.up ();
     locFull = new Semaphore ();
     locEmpty = new Semaphore ();
     for (int i = 0; i < storage.length; i++)
       locEmpty.up ();

   }

  /**
   *  Writing a value in mutual exclusion regime.
   *
   *    @param val value to store
   */

   @Override
   public void write (R val)
   {
     locEmpty.down ();
     access.down ();
     mem[inPnt] = val;
     inPnt = (inPnt + 1) % mem.length;
     locFull.up();
     access.up ();
   }

  /**
   *  Reading a value in mutual exclusion regime.
   *
   *    @return the retrieved value
   */

   @Override
   public R read ()
   {
     R val = null;                                         // retrieved value

     locFull.down ();
     access.down ();
     val = mem[outPnt];
     outPnt = (outPnt + 1) % mem.length;
     locEmpty.up();
     access.up ();

     return val;
   }
}
